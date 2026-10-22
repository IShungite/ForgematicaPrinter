package me.aleksilassila.litematica.printer.actions;

import me.aleksilassila.litematica.printer.Printer;
import me.aleksilassila.litematica.printer.implementation.PrinterPlacementContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.phys.BlockHitResult;

abstract public class InteractAction extends Action {
    public final PrinterPlacementContext context;

    public InteractAction(PrinterPlacementContext context) {
        this.context = context;
    }

    protected abstract void interact(Minecraft client, LocalPlayer player, InteractionHand hand, BlockHitResult hitResult);

    @Override
    public void send(Minecraft client, LocalPlayer player) {
        // Since 1.21.6 sneaking travels in the player input packet, which vanilla re-sends from the real
        // keys whenever any key changes, so the forced sneak state has to wrap the click itself.
        Input keys = player.input.keyPresses;
        boolean overrideShift = context.shouldSneak != keys.shift();

        if (overrideShift) {
            player.connection.send(new ServerboundPlayerInputPacket(new Input(keys.forward(), keys.backward(),
                    keys.left(), keys.right(), keys.jump(), context.shouldSneak, keys.sprint())));
        }

        interact(client, player, InteractionHand.MAIN_HAND, context.hitResult);

        if (overrideShift) {
            player.connection.send(new ServerboundPlayerInputPacket(keys));
        }

        Printer.printDebug("InteractAction.send: Blockpos: {} Side: {} HitPos: {}", context.getClickedPos(), context.getClickedFace(), context.getClickLocation());
    }

    @Override
    public String toString() {
        return "InteractAction{" +
                "context=" + context +
                '}';
    }
}
