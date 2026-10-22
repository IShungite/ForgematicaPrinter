package me.aleksilassila.litematica.printer.actions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;

public class ReleaseShiftAction extends Action {
    @Override
    public void send(Minecraft client, LocalPlayer player) {
        player.input.shiftKeyDown = false;
        player.connection.send(new ServerboundPlayerCommandPacket(player, ServerboundPlayerCommandPacket.Action.RELEASE_SHIFT_KEY));
    }
}
