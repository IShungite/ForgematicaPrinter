## Changelog
### 0.2.0+mc26.1.2
- port to Minecraft 26.1.2 / NeoForge 26.1.2 (Mojang names, ModDevGradle build)
- sneaking now uses the player input packet (the shift-key player commands were removed in 1.21.6), sent right around the click so walking or jumping while printing can't cancel it
- printer settings and hotkeys also show in Forgematica's "All" config tab
- fix crash "Invalid selected slot" when picking a block that sits in the first main-inventory slot in creative
- fix top slabs printed as bottom slabs (then turned into double slabs) when the block below was clicked
- fix mixin targets that still used Yarn names (sign editing, axe stripping)

- port to 1.21.1 neoforge