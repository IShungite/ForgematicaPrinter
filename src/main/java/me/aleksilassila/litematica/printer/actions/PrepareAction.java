package me.aleksilassila.litematica.printer.actions;

import fi.dy.masa.litematica.util.InventoryUtils;
import me.aleksilassila.litematica.printer.implementation.PrinterPlacementContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class PrepareAction extends Action {
    public final PrinterPlacementContext context;
    public boolean modifyYaw = true;
    public boolean modifyPitch = true;
    public float yaw = 0;
    public float pitch = 0;

    public PrepareAction(PrinterPlacementContext context) {
        this.context = context;
        Direction lookDirection = context.lookDirection;

        if (lookDirection != null && lookDirection.getAxis().isHorizontal()) {
            this.yaw = switch (lookDirection) {
                case SOUTH -> 0f;
                case WEST -> 90f;
                case NORTH -> 180f;
                case EAST -> -90f;
                default -> 0f;
            };
        } else {
            this.modifyYaw = false;
        }

        if (lookDirection == Direction.UP) {
            this.pitch = -90;
        } else if (lookDirection == Direction.DOWN) {
            this.pitch = 90;
        } else if (lookDirection != null) {
            this.pitch = 0;
        } else {
            this.modifyPitch = false;
        }
    }

    public PrepareAction(PrinterPlacementContext context, float yaw, float pitch) {
        this.context = context;

        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public void send(Minecraft client, LocalPlayer player) {
        ItemStack itemStack = context.getItemInHand();
        int slot = context.requiredItemSlot;

        if (itemStack != null && client.gameMode != null) {
            Inventory inventory = player.getInventory();

            // This thing is straight from MinecraftClient#doItemPick()
            if (player.getAbilities().instabuild) {
                this.addPickBlock(inventory, itemStack);
                client.gameMode.handleCreativeModeItemAdd(player.getItemInHand(InteractionHand.MAIN_HAND),
                        36 + inventory.getSelectedSlot());
            } else if (slot != -1) {
                if (Inventory.isHotbarSlot(slot)) {
                    inventory.setSelectedSlot(slot);
                } else {
                    //client.interactionManager.pickFromInventory(slot);
                    InventoryUtils.setPickedItemToHand(slot, itemStack, client);
                }
            }
        }

        if (modifyPitch || modifyYaw) {
            float yaw = modifyYaw ? this.yaw : player.getYRot();
            float pitch = modifyPitch ? this.pitch : player.getXRot();

            ServerboundMovePlayerPacket packet = new ServerboundMovePlayerPacket.PosRot(player.getX(), player.getY(), player.getZ(), yaw,
                    pitch, player.onGround(), player.horizontalCollision);

            player.connection.send(packet);
        }
    }

    private void addPickBlock(Inventory inv, ItemStack stack) {
        int slot = inv.findSlotMatchingItem(stack);

        if (Inventory.isHotbarSlot(slot)) {
            inv.setSelectedSlot(slot);
        } else {
            if (slot == -1) {
                int selected = inv.getSuitableHotbarSlot();
                inv.setSelectedSlot(selected);
                NonNullList<ItemStack> items = inv.getNonEquipmentItems();

                if (!items.get(selected).isEmpty()) {
                    int empty = inv.getFreeSlot();

                    if (empty != -1) {
                        items.set(empty, items.get(selected));
                    }
                }
                items.set(selected, stack);
            } else {
                inv.pickSlot(slot);
            }
        }
    }

    @Override
    public String toString() {
        return "PrepareAction{" +
                "context=" + context +
                '}';
    }
}
