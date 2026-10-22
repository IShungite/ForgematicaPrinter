# Forgematica Printer

Litematica Printer unofficial (Neo)Forge port.

> **This branch (`neoforge/26.1.2`) is a community port to Minecraft 26.1.2 / NeoForge 26.1.2.**
> It requires [Forgematica](https://github.com/CagayakeGirls/litematica-neoforge) 0.5.x and MaFgLib 0.5.x for 26.1.2.
> It is based on the 1.21.1 rework by Kyan / [Reime0](https://github.com/Reime0/NeoForgematicaPrinter),
> itself based on this project ([ThinkingStudios](https://github.com/ThinkingStudios/ForgematicaPrinter)) and on
> [aleksilassila/litematica-printer](https://github.com/aleksilassila/litematica-printer). Licensed under AGPL-3.0.
> Please report problems with this port on this fork's issue tracker, not to the upstream authors.

This extension adds printing functionality for Forgematica. Printer
allows players to build big structures more quickly by automatically placing the correct blocks around you.

![Demo](printer_demo.gif)

## How To Use

Using the printer is straightforward: You can toggle the feature by pressing `CAPS_LOCK` by default. To configure
variables such as
printing speed and range, open Forgematica's settings by pressing `M + C` and navigate to "Generic" tab. Printer's
configuration can be
found at the bottom of the page. You can also rebind the printing toggle under "Hotkeys" tab. Holding down `V` by
default will also
print regardless if the printer is toggled on or off.

## Issues

If you have issues with the printer, **do not** bother the original creator of
Litematica (maruohon) with them. Contact me instead. Feature requests or bugs can
be reported via [GitHub issues](https://github.com/ThinkingStudio/ForgematicaPrinter/issues). 
I'll try to keep a todo list of things
I'm planning to implement and fix, so please look for duplicates there first.

Before creating an issue, make sure you are using the latest version of the mod.
To make fixing bugs easier, include the following information in your issue:

- Minecraft version
- Forgematica version
- Printer version
- Detailed description of how to reproduce the issue
- If you can, any additional information, such as error logs, screenshots or **the incorrectly printed schematics**.

## Building

Requires JDK 25. Run `./gradlew build`; the mod jar is written to `build/libs/`.

### List of know issues

Currently, the following features are still broken or missing:

- Placing liquids (printing **in** liquids works though)
- Printing without support directly in air (printInAir)
- Current algorithm for placing rails isn't perfect,
  sometimes it can't place all the rails (to avoid placing anything incorrectly).
- Legit mode? (for anticheats)

Also, I have decided that features that fix existing builds,
such as automatic excavation or correcting incorrectly placed blocks are out of the scope of this mod.

