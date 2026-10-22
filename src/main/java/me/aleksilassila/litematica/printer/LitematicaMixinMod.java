package me.aleksilassila.litematica.printer;

import me.aleksilassila.litematica.printer.event.KeyCallbacks;
import net.minecraft.client.Minecraft;

public class LitematicaMixinMod {
    public static Printer printer;

    public static void onInitialize() {
        KeyCallbacks.init(Minecraft.getInstance());
        Printer.logger.info("{} initialized.", PrinterReference.MOD_STRING);
    }
}
