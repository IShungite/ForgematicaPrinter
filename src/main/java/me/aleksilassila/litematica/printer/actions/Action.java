package me.aleksilassila.litematica.printer.actions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.world.entity.player.Input;

public abstract class Action {
    abstract public void send(Minecraft client, LocalPlayer player);

    // Since 1.21.6 sneaking is part of the player input packet (PRESS/RELEASE_SHIFT_KEY commands were removed).
    static void setSneaking(LocalPlayer player, boolean sneak) {
        Input keys = player.input.keyPresses;
        player.input.keyPresses = new Input(keys.forward(), keys.backward(), keys.left(), keys.right(), keys.jump(), sneak, keys.sprint());
        player.connection.send(new ServerboundPlayerInputPacket(player.input.keyPresses));
    }
}
