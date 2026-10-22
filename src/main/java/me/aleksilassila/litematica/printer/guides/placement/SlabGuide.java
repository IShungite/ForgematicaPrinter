package me.aleksilassila.litematica.printer.guides.placement;

import me.aleksilassila.litematica.printer.SchematicBlockState;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.Vec3;
import java.util.ArrayList;
import java.util.List;

public class SlabGuide extends GeneralPlacementGuide {
    public SlabGuide(SchematicBlockState state) {
        super(state);
    }

    @Override
    protected List<Direction> getPossibleSides() {
        List<Direction> resultList = new ArrayList<>();

        Direction[] horizontalDirections = {
                Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST
        };

        for (Direction direction : horizontalDirections) {
            BlockState neighborState = state.offset(direction).currentState;
            if (neighborState.getBlock() instanceof SlabBlock) {
                resultList.add(direction);
            }
        }

        for (Direction direction : super.getPossibleSides()) {
            if (!resultList.contains(direction)) {
                resultList.add(direction);
            }
        }

        return resultList;
    }

    @Override
    protected Vec3 getHitModifier(Direction validSide) {
        Direction requiredHalf = getRequiredHalf(state);

        if (validSide.get2DDataValue() != -1) {
            return new Vec3(0, requiredHalf.getStepY() * 0.3, 0);
        }

        if (validSide == Direction.UP) {
            return new Vec3(0, -0.3, 0);
        }

        if (validSide == Direction.DOWN) {
            return new Vec3(0, 0.3, 0);
        }

        return Vec3.ZERO;
    }

    private Direction getRequiredHalf(SchematicBlockState state) {
        BlockState targetState = state.targetState;
        BlockState currentState = state.currentState;

        if (!currentState.hasProperty(SlabBlock.TYPE)) {
            return targetState.getValue(SlabBlock.TYPE) == SlabType.TOP ? Direction.UP : Direction.DOWN;
        }

        if (currentState.getValue(SlabBlock.TYPE) != targetState.getValue(SlabBlock.TYPE)) {
            return currentState.getValue(SlabBlock.TYPE) == SlabType.TOP ? Direction.DOWN : Direction.UP;
        }

        return targetState.getValue(SlabBlock.TYPE) == SlabType.TOP ? Direction.UP : Direction.DOWN;
    }
}
