package com.teamabnormals.environmental.common.block;

import com.google.common.collect.Maps;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.function.Supplier;

public class DwarfSpruceHeadBlock extends DwarfSpruceBlock {
	private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
	public static final BooleanProperty TOP = BooleanProperty.create("top");
	public static final BooleanProperty STAR = BooleanProperty.create("star");
	private static final Map<Supplier<? extends Item>, Block> TORCH_SPRUCES = Maps.newHashMap();

	protected DwarfSprucePlantBlock bodyBlock;

	public DwarfSpruceHeadBlock(Properties properties) {
		this(properties, (Supplier<Item>) null);
	}

	public DwarfSpruceHeadBlock(Properties properties, ResourceLocation torch) {
		this(properties, () -> ForgeRegistries.ITEMS.getValue(torch));
	}

	public DwarfSpruceHeadBlock(Properties properties, Supplier<Item> torch) {
		super(properties, torch);
		this.registerDefaultState(this.stateDefinition.any().setValue(TOP, false).setValue(STAR, false));
	}

	public void setBodyBlock(DwarfSprucePlantBlock block) {
		this.bodyBlock = block;
	}

	@Override
	public Item getTorch() {
		return this.torch == null || this.torch.get() == Items.AIR ? null : this.torch.get();
	}

	@Override
	public Map<Supplier<? extends Item>, Block> getTorchSpruces() {
		return TORCH_SPRUCES;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
		int light = state.getLightEmission();
		return state.getValue(STAR) && light < 10 ? 10 : light;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState belowstate = level.getBlockState(pos.below());
		boolean flag = belowstate.getBlock() instanceof DwarfSpruceHeadBlock;

		if (isValidAboveBlock(level.getBlockState(pos.above())))
			return EnvironmentalBlocks.DWARF_SPRUCE_PLANT.get().defaultBlockState().setValue(DwarfSprucePlantBlock.BOTTOM, !flag);
		else if (flag)
			return this.defaultBlockState().setValue(TOP, true).setValue(STAR, belowstate.getValue(STAR));
		else
			return this.defaultBlockState();
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState offsetState, LevelAccessor level, BlockPos pos, BlockPos offsetPos) {
		if (!state.canSurvive(level, pos))
			level.scheduleTick(pos, this, 1);
		return direction == Direction.UP && isValidAboveBlock(offsetState) ? this.getBodyState(state) : state;
	}

	protected BlockState getBodyState(BlockState originalState) {
		return this.bodyBlock.defaultBlockState().setValue(DwarfSprucePlantBlock.BOTTOM, !originalState.getValue(TOP));
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		ItemStack itemstack = player.getItemInHand(hand);
		Item item = itemstack.getItem();

		if (itemstack.canPerformAction(ToolActions.SHEARS_HARVEST) && state.getValue(STAR)) {
			popResource(level, pos, new ItemStack(Items.NETHER_STAR));
			level.setBlockAndUpdate(pos, state.setValue(STAR, false));

			itemstack.hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(hand));
			level.playSound(null, pos, SoundEvents.SNOW_GOLEM_SHEAR, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
			level.gameEvent(player, GameEvent.SHEAR, pos);
			player.awardStat(Stats.ITEM_USED.get(item));
			return InteractionResult.sidedSuccess(level.isClientSide);
		} else if (!state.getValue(STAR) && item == Items.NETHER_STAR) {
			if (!player.isCreative())
				itemstack.shrink(1);

			level.setBlockAndUpdate(pos, state.setValue(STAR, true));

			level.playSound(null, pos, SoundEvents.AZALEA_LEAVES_PLACE, SoundSource.BLOCKS, 0.4F, 1.0F);
			level.playSound(null, pos, SoundEvents.AMETHYST_CLUSTER_PLACE, SoundSource.BLOCKS, 0.8F, 1.0F);

			level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
			player.awardStat(Stats.ITEM_USED.get(item));
			return InteractionResult.sidedSuccess(level.isClientSide);
		}

		return super.use(state, level, pos, player, hand, result);
	}

	@Override
	public BlockState getWithoutTorchesState(BlockState state) {
		return EnvironmentalBlocks.DWARF_SPRUCE.get().defaultBlockState().setValue(TOP, state.getValue(TOP)).setValue(STAR, state.getValue(STAR));
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
		return level.getBlockState(pos.above()).isAir();
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		level.setBlockAndUpdate(pos.above(), EnvironmentalBlocks.DWARF_SPRUCE.get().defaultBlockState().setValue(TOP, true).setValue(STAR, state.getValue(STAR)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(TOP, STAR);
	}
}