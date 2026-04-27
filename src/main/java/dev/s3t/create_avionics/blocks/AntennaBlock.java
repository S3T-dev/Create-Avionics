package dev.s3t.create_avionics.blocks;

import com.mojang.serialization.MapCodec;

import dev.s3t.create_avionics.blockentities.AntennaBlockEntity;
import dev.s3t.create_avionics.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


public class AntennaBlock extends BaseEntityBlock {

    public AntennaBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AntennaBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
    
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.ANTENNA.get(), AntennaBlockEntity::tick);
    }

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return null;
	}
	
	
}
