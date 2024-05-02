package com.teamabnormals.environmental.core.data.server.tags;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.teamabnormals.environmental.core.registry.EnvironmentalPaintingVariants.*;

public class EnvironmentalPaintingVariantTagsProvider extends PaintingVariantTagsProvider {

	public EnvironmentalPaintingVariantTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, provider, Environmental.MOD_ID, helper);
	}

	@Override
	public void addTags(Provider provider) {
		this.tag(PaintingVariantTags.PLACEABLE).add(
				SNAKE_BLOCK.getKey(), SLABFISH.getKey(), ARCHIVE.getKey(),
				OPTIMAL_AERODYNAMICS.getKey(), IN_PLAINS_SIGHT.getKey(), THE_PLACE_WITHIN_THE_PINES.getKey(),
				BOUQUET.getKey(), BOUQUET2.getKey(), LONE_PLUM.getKey(), MARSHPATH.getKey()
		);
	}
}