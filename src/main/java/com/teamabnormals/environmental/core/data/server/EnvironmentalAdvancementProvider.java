package com.teamabnormals.environmental.core.data.server;

import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalCriteriaTriggers;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalEntityTypeTags;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalItemTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import com.teamabnormals.environmental.core.registry.slabfish.EnvironmentalSlabfishTypes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.advancements.critereon.TameAnimalTrigger.TriggerInstance;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.common.data.ForgeAdvancementProvider.AdvancementGenerator;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class EnvironmentalAdvancementProvider implements AdvancementGenerator {

	public static ForgeAdvancementProvider create(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		return new ForgeAdvancementProvider(output, provider, helper, List.of(new EnvironmentalAdvancementProvider()));
	}

	@Override
	public void generate(Provider provider, Consumer<Advancement> consumer, ExistingFileHelper helper) {
		createAdvancement("backpack_slabfish", "husbandry", new ResourceLocation("husbandry/tame_an_animal"), Items.CHEST, FrameType.TASK, true, true, false)
				.addCriterion("backpack_slabfish", EnvironmentalCriteriaTriggers.BACKPACK_SLABFISH.createInstance())
				.save(consumer, Environmental.MOD_ID + ":husbandry/backpack_slabfish");

		createAdvancement("place_koi_in_village", "husbandry", new ResourceLocation("husbandry/tactical_fishing"), EnvironmentalItems.KOI_BUCKET.get(), FrameType.TASK, true, true, false)
				.addCriterion("place_koi_in_village", EnvironmentalCriteriaTriggers.PLACE_KOI_IN_VILLAGE.createInstance())
				.save(consumer, Environmental.MOD_ID + ":husbandry/place_koi_in_village");

		Advancement saddlePig = createAdvancement("saddle_pig", "husbandry", new ResourceLocation("husbandry/root"), Items.SADDLE, FrameType.TASK, true, true, false)
				.addCriterion("saddle_pig", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
						ItemPredicate.Builder.item().of(Items.SADDLE),
						EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EntityType.PIG).build())))
				.save(consumer, Environmental.MOD_ID + ":husbandry/saddle_pig");

		createAdvancement("when_pigs_fly", "husbandry", saddlePig, Items.CARROT_ON_A_STICK, FrameType.CHALLENGE, true, true, false)
				.addCriterion("when_pigs_fly", EnvironmentalCriteriaTriggers.WHEN_PIGS_FLY.createInstance())
				.save(consumer, Environmental.MOD_ID + ":husbandry/when_pigs_fly");

		Advancement throwMud = createAdvancement("throw_mud_at_pig", "husbandry", saddlePig, EnvironmentalItems.MUD_BALL.get(), FrameType.TASK, true, true, false)
				.addCriterion("throw_mud_at_pig", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(
						DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_PROJECTILE)).direct(EntityPredicate.Builder.entity().of(EnvironmentalEntityTypes.MUD_BALL.get()))),
						EntityPredicate.Builder.entity().of(EntityType.PIG).build()))
				.save(consumer, Environmental.MOD_ID + ":husbandry/throw_mud_at_pig");
		createAdvancement("plant_on_muddy_pig", "husbandry", throwMud, Items.RED_TULIP, FrameType.TASK, true, true, false)
				.addCriterion("plant_on_muddy_pig", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
						ItemPredicate.Builder.item().of(EnvironmentalItemTags.MUDDY_PIG_DECORATIONS),
						EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EntityType.PIG).build())))
				.save(consumer, Environmental.MOD_ID + ":husbandry/plant_on_muddy_pig");

		Advancement feedPig = createAdvancement("truffle_shuffle", "husbandry", saddlePig, Items.GOLDEN_CARROT, FrameType.TASK, true, true, false)
				.addCriterion("truffle_shuffle", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
						ItemPredicate.Builder.item().of(Items.GOLDEN_CARROT),
						EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EntityType.PIG).build())))
				.save(consumer, Environmental.MOD_ID + ":husbandry/truffle_shuffle");
		createAdvancement("find_truffle", "husbandry", feedPig, EnvironmentalItems.TRUFFLE.get(), FrameType.TASK, true, true, false)
				.addCriterion("find_truffle", InventoryChangeTrigger.TriggerInstance.hasItems(EnvironmentalItems.TRUFFLE.get()))
				.save(consumer, Environmental.MOD_ID + ":husbandry/find_truffle");

		createAdvancement("shear_yak_with_pants", "husbandry", new ResourceLocation("husbandry/root"), EnvironmentalItems.YAK_PANTS.get(), FrameType.TASK, true, true, false)
				.addCriterion("shear_yak_with_pants", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
						EntityPredicate.wrap(EntityPredicate.Builder.entity().equipment(EntityEquipmentPredicate.Builder.equipment().legs(ItemPredicate.Builder.item().of(EnvironmentalItems.YAK_PANTS.get()).build()).build()).build()),
						ItemPredicate.Builder.item().of(Tags.Items.SHEARS),
						EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EnvironmentalEntityTypes.YAK.get()).build())))
				.save(consumer, Environmental.MOD_ID + ":husbandry/shear_yak_with_pants");

		createAdvancement("shear_cattail", "husbandry", new ResourceLocation("husbandry/root"), EnvironmentalItems.CATTAIL_FLUFF.get(), FrameType.TASK, true, true, false)
				.addCriterion("shear_cattail", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
						LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(EnvironmentalBlocks.CATTAIL.get()).build()),
						ItemPredicate.Builder.item().of(Tags.Items.SHEARS)))
				.save(consumer, Environmental.MOD_ID + ":husbandry/shear_cattail");

		createAdvancement("feed_deer_flower", "husbandry", new ResourceLocation("husbandry/root"), Items.APPLE, FrameType.TASK, true, true, false)
				.addCriterion("feed_deer_flower", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
						ItemPredicate.Builder.item().of(EnvironmentalItemTags.DEER_PLANTABLES),
						EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EnvironmentalEntityTypeTags.DEER).build())))
				.save(consumer, Environmental.MOD_ID + ":husbandry/feed_deer_flower");

		Advancement.Builder tameSlabfish = createAdvancement("tame_all_slabfish", "husbandry", new ResourceLocation("husbandry/tame_an_animal"), Items.TROPICAL_FISH, FrameType.CHALLENGE, true, true, false);
		for (ResourceKey<SlabfishType> slabfish : provider.lookup(EnvironmentalRegistries.SLABFISH_TYPE).get().listElementIds().filter(key -> !EnvironmentalSlabfishTypes.COMPAT_SLABFISH.contains(key)).sorted().toList()) {
			tameSlabfish.addCriterion(slabfish.location().toString(), slabfishCriterion(slabfish));
		}
		tameSlabfish.save(consumer, Environmental.MOD_ID + ":husbandry/tame_all_slabfish");
	}

	public static TriggerInstance slabfishCriterion(ResourceKey<SlabfishType> slabfish) {
		CompoundTag tag = new CompoundTag();
		tag.putString("SlabfishType", slabfish.location().toString());
		return TameAnimalTrigger.TriggerInstance.tamedAnimal(EntityPredicate.Builder.entity().of(EnvironmentalEntityTypes.SLABFISH.get()).nbt(new NbtPredicate(tag)).build());
	}

	private static Advancement.Builder createAdvancement(String name, String category, Advancement parent, ItemLike icon, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
		return Advancement.Builder.advancement().parent(parent).display(icon,
				Component.translatable("advancements." + Environmental.MOD_ID + "." + category + "." + name + ".title"),
				Component.translatable("advancements." + Environmental.MOD_ID + "." + category + "." + name + ".description"),
				null, frame, showToast, announceToChat, hidden);
	}

	private static Advancement.Builder createAdvancement(String name, String category, ResourceLocation parent, ItemLike icon, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
		return createAdvancement(name, category, Advancement.Builder.advancement().build(parent), icon, frame, showToast, announceToChat, hidden);
	}
}