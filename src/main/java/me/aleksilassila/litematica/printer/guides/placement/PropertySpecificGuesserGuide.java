package me.aleksilassila.litematica.printer.guides.placement;

import me.aleksilassila.litematica.printer.SchematicBlockState;
import net.minecraft.block.*;
import net.minecraft.world.level.block.BambooStalkBlock;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.ComparatorBlock;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.RepeaterBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;

public class PropertySpecificGuesserGuide extends GuesserGuide {
    protected static Property<?>[] ignoredProperties = new Property[]{
            RepeaterBlock.DELAY,
            ComparatorBlock.MODE,
            RedStoneWireBlock.POWER,
            RedStoneWireBlock.EAST,
            RedStoneWireBlock.NORTH,
            RedStoneWireBlock.SOUTH,
            RedStoneWireBlock.WEST,
            BlockStateProperties.POWERED,
            BlockStateProperties.OPEN,
            PointedDripstoneBlock.THICKNESS,
            ScaffoldingBlock.DISTANCE,
            ScaffoldingBlock.BOTTOM,
            CactusBlock.AGE,
            BambooStalkBlock.AGE,
            BambooStalkBlock.LEAVES,
            BambooStalkBlock.STAGE,
            SaplingBlock.STAGE,
            CrossCollisionBlock.EAST,
            CrossCollisionBlock.NORTH,
            CrossCollisionBlock.SOUTH,
            CrossCollisionBlock.WEST,
            SnowLayerBlock.LAYERS,
            SeaPickleBlock.PICKLES,
            CandleBlock.CANDLES,
            EndPortalFrameBlock.HAS_EYE,
            BlockStateProperties.LIT,
            LeavesBlock.DISTANCE,
            LeavesBlock.PERSISTENT,
            BlockStateProperties.ATTACHED,
            BlockStateProperties.NOTE,
            BlockStateProperties.NOTEBLOCK_INSTRUMENT,

    };

    public PropertySpecificGuesserGuide(SchematicBlockState state) {
        super(state);
    }

    @Override
    protected boolean statesEqual(BlockState resultState, BlockState targetState) {
        return statesEqualIgnoreProperties(resultState, targetState, ignoredProperties);
    }
}
