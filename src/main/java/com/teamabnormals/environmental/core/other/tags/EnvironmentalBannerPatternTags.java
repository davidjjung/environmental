package com.teamabnormals.environmental.core.other.tags;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BannerPattern;

public class EnvironmentalBannerPatternTags {
	public static final TagKey<BannerPattern> PATTERN_ITEM_LUMBERER = bannerPatternTag("pattern_item/lumberer");

	private static TagKey<BannerPattern> bannerPatternTag(String name) {
		return TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(Environmental.MOD_ID, name));
	}
}