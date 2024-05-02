package com.teamabnormals.environmental.common.block;

import com.google.common.collect.Maps;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class DwarfSprucePlantBlock extends BushBlock implements DwarfSpruceBlock, BonemealableBlock {
	private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");
	private static final Map<Supplier<? extends Item>, Block> TORCH_SPRUCES = Maps.newHashMap();

	private final DwarfSpruceHeadBlock headBlock;
	private final Supplier<Item> torch;

	public DwarfSprucePlantBlock(Properties properties, DwarfSpruceHeadBlock headBlock) {
		this(properties, (Supplier<Item>) null, headBlock);
	}

	public DwarfSprucePlantBlock(Properties properties, ResourceLocation torch, DwarfSpruceHeadBlock headBlock) {
		this(properties, () -> ForgeRegistries.ITEMS.getValue(torch), headBlock);
	}

	public DwarfSprucePlantBlock(Properties properties, Supplier<Item> torch, DwarfSpruceHeadBlock headBlock) {
		super(properties);
		this.headBlock = headBlock;
		this.headBlock.setBodyBlock(this);
		this.torch = torch;
		if (torch != null)
			TORCH_SPRUCES.put(torch, this);
		this.registerDefaultState(this.stateDefinition.any().setValue(BOTTOM, false));
	}

	@Override
	public Item getTorch() {
		return this.torch == null ? null : this.torch.get();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!state.canSurvive(level, pos))
			level.destroyBlock(pos, true);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockPos belowpos = pos.below();
		BlockState belowstate = level.getBlockState(belowpos);
		if (state.getValue(BOTTOM))
			return canSupportCenter(level, belowpos, Direction.UP) || belowstate.getBlock() instanceof LeavesBlock;
		else
			return belowstate.getBlock() instanceof DwarfSprucePlantBlock;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState offsetState, LevelAccessor level, BlockPos pos, BlockPos offsetPos) {
		if (!state.canSurvive(level, pos))
			level.scheduleTick(pos, this, 1);
		return direction == Direction.UP && !isValidAboveBlock(offsetState) ? this.getHeadState(state) : state;
	}

	protected BlockState getHeadState(BlockState originalState) {
		return this.headBlock.defaultBlockState().setValue(DwarfSpruceHeadBlock.TOP, !originalState.getValue(BOTTOM));
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		ItemStack itemstack = player.getItemInHand(hand);
		Item item = itemstack.getItem();
		Item torch = this.getTorch();

		if (itemstack.canPerformAction(ToolActions.SHEARS_HARVEST) && torch != null) {
			popResource(level, pos, new ItemStack(torch));
			level.setBlockAndUpdate(pos, this.getWithoutTorchesState(state));

			itemstack.hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(hand));
			level.playSound(null, pos, SoundEvents.SNOW_GOLEM_SHEAR, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
			level.gameEvent(player, GameEvent.SHEAR, pos);
			player.awardStat(Stats.ITEM_USED.get(item));
			return InteractionResult.sidedSuccess(level.isClientSide);
		} else if (item != Items.AIR && torch == null) {
			Block torchspruce = TORCH_SPRUCES.getOrDefault(TORCH_SPRUCES.keySet().stream().filter(key -> item == key.get()).findFirst().orElse(null), null);
			if (torchspruce != null) {
				if (!player.isCreative())
					itemstack.shrink(1);

				BlockState blockstate = torchspruce.defaultBlockState().setValue(BOTTOM, state.getValue(BOTTOM));
				if (item == Items.REDSTONE_TORCH)
					blockstate = RedstoneDwarfSpruceBlock.setLitPoweredState(blockstate, level, pos);
				level.setBlockAndUpdate(pos, blockstate);

				level.playSound(null, pos, SoundEvents.AZALEA_LEAVES_PLACE, SoundSource.BLOCKS, 0.4F, 1.0F);
				level.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 0.8F, 1.0F);

				level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
				player.awardStat(Stats.ITEM_USED.get(item));
				return InteractionResult.sidedSuccess(level.isClientSide);
			}
		}

		return super.use(state, level, pos, player, hand, result);
	}

	@Override
	public BlockState getWithoutTorchesState(BlockState state) {
		return EnvironmentalBlocks.DWARF_SPRUCE_PLANT.get().defaultBlockState().setValue(BOTTOM, state.getValue(BOTTOM));
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		List<ItemStack> drops = super.getDrops(state, builder);
		Item torch = this.getTorch();
		if (torch != null)
			drops.add(new ItemStack(torch));
		return drops;
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
		BlockPos headpos = getHeadPos(level, pos);
		return headpos != null && level.getBlockState(headpos.above()).isAir();
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		BlockPos headpos = getHeadPos(level, pos);
		if (headpos != null) {
			BlockState headstate = level.getBlockState(headpos);
			((DwarfSpruceHeadBlock) headstate.getBlock()).performBonemeal(level, random, headpos, headstate);
		}
	}

	@Override
	public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
		return new ItemStack(EnvironmentalBlocks.DWARF_SPRUCE.get());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BOTTOM);
	}

	public static boolean isValidAboveBlock(BlockState state) {
		return state.getBlock() instanceof DwarfSpruceHeadBlock && state.getValue(DwarfSpruceHeadBlock.TOP) || state.getBlock() instanceof DwarfSprucePlantBlock && !state.getValue(DwarfSprucePlantBlock.BOTTOM);
	}

	private static BlockPos getHeadPos(BlockGetter level, BlockPos pos) {
		MutableBlockPos mutable = pos.mutable();
		while (true) {
			mutable.move(Direction.UP);
			if (level.getBlockState(mutable).getBlock() instanceof DwarfSpruceHeadBlock)
				return mutable.immutable();
			else if (!(level.getBlockState(mutable).getBlock() instanceof DwarfSprucePlantBlock))
				return null;
		}
	}
}