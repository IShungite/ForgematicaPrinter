package me.aleksilassila.litematica.printer.implementation.mixin;

import com.mojang.authlib.GameProfile;
import fi.dy.masa.litematica.world.SchematicWorldHandler;
import fi.dy.masa.litematica.world.WorldSchematic;
import me.aleksilassila.litematica.printer.LitematicaMixinMod;
import me.aleksilassila.litematica.printer.Printer;
import me.aleksilassila.litematica.printer.SchematicBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundSignUpdatePacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(LocalPlayer.class)
public class MixinClientPlayerEntity extends AbstractClientPlayer {

    @Final
    @Shadow
    protected Minecraft client;
    @Final
    @Shadow
    public ClientPacketListener networkHandler;

    public MixinClientPlayerEntity(ClientLevel world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(at = @At("TAIL"), method = "tick")
    public void tick(CallbackInfo ci) {
        LocalPlayer clientPlayer = (LocalPlayer) (Object) this;

        if (LitematicaMixinMod.printer == null || LitematicaMixinMod.printer.player != clientPlayer) {
            Printer.printDebug("Initializing printer, player: {}, client: {}", clientPlayer, client);
            LitematicaMixinMod.printer = new Printer(client, clientPlayer);
        }

        // Dirty optimization
        boolean didFindPlacement = true;
        for (int i = 0; i < 10; i++) {
            if (didFindPlacement) {
                didFindPlacement = LitematicaMixinMod.printer.onGameTick();
            }
            LitematicaMixinMod.printer.actionHandler.onGameTick();
        }
    }

    @Inject(method = "openEditSignScreen", at = @At("HEAD"), cancellable = true)
    public void openEditSignScreen(SignBlockEntity sign, boolean front, CallbackInfo ci) {
        getTargetSignEntity(sign).ifPresent(signBlockEntity ->
        {
            ServerboundSignUpdatePacket packet = new ServerboundSignUpdatePacket(sign.getBlockPos(),
                    front,
                    signBlockEntity.getText(front).getMessage(0, false).getString(),
                    signBlockEntity.getText(front).getMessage(1, false).getString(),
                    signBlockEntity.getText(front).getMessage(2, false).getString(),
                    signBlockEntity.getText(front).getMessage(3, false).getString());
            this.networkHandler.send(packet);
            ci.cancel();
        });
    }

    @Unique
    private Optional<SignBlockEntity> getTargetSignEntity(SignBlockEntity sign) {
        WorldSchematic worldSchematic = SchematicWorldHandler.getSchematicWorld();
        if (sign.getLevel() == null || worldSchematic == null) {
            return Optional.empty();
        }

        SchematicBlockState state = new SchematicBlockState(sign.getLevel(), worldSchematic, sign.getBlockPos());
        BlockEntity targetBlockEntity = worldSchematic.getBlockEntity(state.blockPos);

        if (targetBlockEntity instanceof SignBlockEntity targetSignEntity) {
            return Optional.of(targetSignEntity);
        }

        return Optional.empty();
    }
}
