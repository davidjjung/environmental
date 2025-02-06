package com.teamabnormals.environmental.core.data.server.tags;

import com.teamabnormals.blueprint.core.data.server.tags.BlueprintItemTagsProvider;
import com.teamabnormals.blueprint.core.other.tags.BlueprintItemTags;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBlockTags;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalItemTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.teamabnormals.environmental.core.registry.EnvironmentalBlocks.CATTAIL;
import static com.teamabnormals.environmental.core.registry.EnvironmentalBlocks.DIANTHUS;
import static com.teamabnormals.environmental.core.registry.EnvironmentalItems.*;

public class EnvironmentalItemTagsProvider extends BlueprintItemTagsProvider {

	public EnvironmentalItemTagsProvider(PackOutput output, CompletableFuture<Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> lookup, ExistingFileHelper helper) {
		super(Environmental.MOD_ID, output, provider, lookup, helper);
	}

	@Override
	public void addTags(Provider provider) {
		this.copy(EnvironmentalBlockTags.WILLOW_LOGS, EnvironmentalItemTags.WILLOW_LOGS);
		this.copy(EnvironmentalBlockTags.PINE_LOGS, EnvironmentalItemTags.PINE_LOGS);
		this.copy(EnvironmentalBlockTags.WISTERIA_LOGS, EnvironmentalItemTags.WISTERIA_LOGS);
		this.copy(EnvironmentalBlockTags.PLUM_LOGS, EnvironmentalItemTags.PLUM_LOGS);
		this.tag(EnvironmentalItemTags.DUCK_FOOD).add(Items.SEAGRASS, EnvironmentalItems.DUCKWEED.get()).addOptionalTag(new ResourceLocation("forge", "crops/rice"));
		this.tag(EnvironmentalItemTags.DEER_FOOD).add(Items.SWEET_BERRIES).addTag(EnvironmentalItemTags.FRUITS_CHERRY).addOptionalTag(new ResourceLocation("forge", "fruits/strawberry"));
		this.tag(EnvironmentalItemTags.DEER_PLANTABLES).addTag(ItemTags.SMALL_FLOWERS).addTag(ItemTags.TALL_FLOWERS);
		this.tag(EnvironmentalItemTags.DEER_TEMPT_ITEMS).addTag(EnvironmentalItemTags.DEER_FOOD).addTag(EnvironmentalItemTags.DEER_PLANTABLES).addTag(EnvironmentalItemTags.DEER_FLOWER_ITEMS);
		this.tag(EnvironmentalItemTags.REINDEER_FOOD).add(EnvironmentalBlocks.CUP_LICHEN.get().asItem(), Items.CARROT);
		this.tag(EnvironmentalItemTags.REINDEER_TEMPT_ITEMS).addTag(EnvironmentalItemTags.REINDEER_FOOD).addTag(EnvironmentalItemTags.DEER_PLANTABLES).addTag(EnvironmentalItemTags.DEER_FLOWER_ITEMS);
		this.tag(EnvironmentalItemTags.SLABFISH_FOOD).addTag(ItemTags.FISHES);
		this.tag(EnvironmentalItemTags.SLABFISH_TAME_ITEMS).add(Items.TROPICAL_FISH);
		this.tag(EnvironmentalItemTags.SLABFISH_SNACKS).add(Items.CHORUS_FRUIT).addOptional(new ResourceLocation("atmospheric", "passion_fruit")).addOptional(new ResourceLocation("atmospheric", "shimmering_passion_fruit")).addOptional(new ResourceLocation("endergetic", "bolloom_fruit")).addOptional(new ResourceLocation("caverns_and_chasms", "bejeweled_apple"));
		this.tag(EnvironmentalItemTags.YAK_FOOD).add(Items.WHEAT);
		this.tag(EnvironmentalItemTags.MUDDY_PIG_DECORATIONS).addTag(ItemTags.SMALL_FLOWERS).addTag(ItemTags.SAPLINGS).addTag(Tags.Items.MUSHROOMS).add(Items.LILY_PAD, Items.DEAD_BUSH, Items.FERN, Items.BIG_DRIPLEAF, CATTAIL.get().asItem());
		this.tag(EnvironmentalItemTags.SPAWNS_ON_MUDDY_PIG).add(Items.BLUE_ORCHID, DIANTHUS.get().asItem(), Items.POPPY, Items.DANDELION, Items.RED_MUSHROOM, Items.BROWN_MUSHROOM, CATTAIL.get().asItem(), Items.LILY_PAD);
		this.tag(EnvironmentalItemTags.MUDDY_PIG_DRYING_ITEMS).add(Items.WHEAT).addOptional(new ResourceLocation("farmersdelight", "straw"));
		this.tag(EnvironmentalItemTags.PIG_TRUFFLE_ITEMS).add(Items.GOLDEN_CARROT);
		this.tag(BlueprintItemTags.PIG_FOOD).addTag(EnvironmentalItemTags.PIG_TRUFFLE_ITEMS);
		this.tag(EnvironmentalItemTags.DEER_FLOWER_ITEMS).add(Items.APPLE).addTag(EnvironmentalItemTags.DEER_STRONG_FLOWER_ITEMS).addTag(EnvironmentalItemTags.DEER_SUPER_FLOWER_ITEMS);
		this.tag(EnvironmentalItemTags.DEER_STRONG_FLOWER_ITEMS).add(Items.GOLDEN_APPLE);
		this.tag(EnvironmentalItemTags.DEER_SUPER_FLOWER_ITEMS).add(Items.ENCHANTED_GOLDEN_APPLE);
		this.tag(Tags.Items.ARMORS_LEGGINGS).add(YAK_PANTS.get());

		this.copyWoodsetTags();
		this.copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
		this.copy(BlockTags.SLABS, ItemTags.SLABS);
		this.copy(BlockTags.WALLS, ItemTags.WALLS);
		this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
		this.tag(ItemTags.SMALL_FLOWERS).add(EnvironmentalBlocks.CARTWHEEL.get().asItem(), EnvironmentalBlocks.BLUEBELL.get().asItem(), EnvironmentalBlocks.VIOLET.get().asItem(), EnvironmentalBlocks.DIANTHUS.get().asItem(), EnvironmentalBlocks.RED_LOTUS_FLOWER.get().asItem(), EnvironmentalBlocks.WHITE_LOTUS_FLOWER.get().asItem(), EnvironmentalBlocks.TASSELFLOWER.get().asItem(), EnvironmentalBlocks.YELLOW_HIBISCUS.get().asItem(), EnvironmentalBlocks.ORANGE_HIBISCUS.get().asItem(), EnvironmentalBlocks.RED_HIBISCUS.get().asItem(), EnvironmentalBlocks.PINK_HIBISCUS.get().asItem(), EnvironmentalBlocks.MAGENTA_HIBISCUS.get().asItem(), EnvironmentalBlocks.PURPLE_HIBISCUS.get().asItem());
		this.copy(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
		this.copy(BlockTags.DAMPENS_VIBRATIONS, ItemTags.DAMPENS_VIBRATIONS);
		this.tag(ItemTags.BOATS).add(WILLOW_BOAT.getFirst().get(), PINE_BOAT.getFirst().get(), WISTERIA_BOAT.getFirst().get(), PLUM_BOAT.getFirst().get());
		this.tag(ItemTags.CHEST_BOATS).add(WILLOW_BOAT.getSecond().get(), PINE_BOAT.getSecond().get(), WISTERIA_BOAT.getSecond().get(), PLUM_BOAT.getSecond().get());
		this.tag(BlueprintItemTags.FURNACE_BOATS).add(WILLOW_FURNACE_BOAT.get(), PINE_FURNACE_BOAT.get(), WISTERIA_FURNACE_BOAT.get(), PLUM_FURNACE_BOAT.get());
		this.tag(BlueprintItemTags.LARGE_BOATS).add(LARGE_WILLOW_BOAT.get(), LARGE_PINE_BOAT.get(), LARGE_WISTERIA_BOAT.get(), LARGE_PLUM_BOAT.get());
		this.tag(ItemTags.MUSIC_DISCS).add(MUSIC_DISC_LEAVING_HOME.get(), MUSIC_DISC_SLABRAVE.get());
		this.tag(ItemTags.FISHES).add(KOI.get());
		this.copy(BlockTags.DIRT, ItemTags.DIRT);

		this.tag(EnvironmentalItemTags.FRUITS).addTag(EnvironmentalItemTags.FRUITS_CHERRY);
		this.tag(EnvironmentalItemTags.FRUITS_CHERRY).add(CHERRIES.get());
		this.tag(EnvironmentalItemTags.RAW_DUCK).add(DUCK.get());
		this.tag(EnvironmentalItemTags.COOKED_DUCK).add(COOKED_DUCK.get());
		this.tag(EnvironmentalItemTags.RAW_VENISON).add(VENISON.get());
		this.tag(EnvironmentalItemTags.COOKED_VENISON).add(COOKED_VENISON.get());
		this.tag(EnvironmentalItemTags.RAW_FISHES).addTag(EnvironmentalItemTags.RAW_FISHES_KOI);
		this.tag(EnvironmentalItemTags.RAW_FISHES_KOI).add(KOI.get());
		this.tag(Tags.Items.EGGS).add(DUCK_EGG.get());
	}
}