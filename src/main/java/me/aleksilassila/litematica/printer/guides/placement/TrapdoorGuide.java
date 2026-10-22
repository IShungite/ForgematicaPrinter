package me.aleksilassila.litematica.printer.guides.placement;

import me.aleksilassila.litematica.printer.SchematicBlockState;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class TrapdoorGuide extends GuesserGuide {
    public TrapdoorGuide(SchematicBlockState state) {
        super(state);
    }

    @Override
    protected boolean statesEqual(BlockState resultState, BlockState targetState) {
        if (!(targetState.getBlock() instanceof TrapDoorBlock)) {
            return super.statesEqual(resultState, targetState);
        }

        return statesEqualIgnoreProperties(resultState, targetState,
                BlockStateProperties.OPEN,
                BlockStateProperties.POWERED);
    }
}