package com.teamabnormals.environmental.common.item;

import com.teamabnormals.environmental.common.block.WallHibiscusBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;

import javax.annotation.Nullable;

public class HibiscusBlockItem extends StandingAndWallBlockItem {

	public HibiscusBlockItem(Block block, Block wallBlock, Properties properties) {
		super(block, wallBlock, properties, Direction.DOWN);
	}

	@Nullable
	protected BlockState getPlacementState(BlockPlaceContext context) {
		BlockState normalState = this.getBlock().getStateForPlacement(context);
		BlockState wallState = this.wallBlock.getStateForPlacement(context);
		LevelReader level = context.getLevel();
		BlockPos blockPos = context.getClickedPos();

		boolean pressingShift = context.getPlayer() != null && context.getPlayer().isSecondaryUseActive();
		boolean canPlaceNormalState = normalState != null && this.canPlace(level, normalState, blockPos);
		boolean wallStateIsFloor = wallState != null && wallState.getValue(WallHibiscusBlock.FACE) == AttachFace.FLOOR;

		if (!pressingShift && canPlaceNormalState && (context.getClickedFace() == Direction.UP || wallStateIsFloor))
			return normalState;

		return wallState != null && this.canPlace(level, wallState, blockPos) ? wallState : null;
	}
}