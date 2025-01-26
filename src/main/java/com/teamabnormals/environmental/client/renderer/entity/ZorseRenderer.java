package com.teamabnormals.environmental.client.renderer.entity;

import com.google.common.collect.Maps;
import com.teamabnormals.environmental.client.model.ZorseModel;
import com.teamabnormals.environmental.client.renderer.entity.layers.ZorseArmorLayer;
import com.teamabnormals.environmental.client.renderer.entity.layers.ZorseMarkingsLayer;
import com.teamabnormals.environmental.client.renderer.entity.layers.ZorseStripesRenderLayer;
import com.teamabnormals.environmental.common.entity.animal.zebroid.Zorse;
import com.teamabnormals.environmental.core.other.EnvironmentalModelLayers;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Variant;

import java.util.Map;

public class ZorseRenderer extends MobRenderer<Zorse, ZorseModel<Zorse>> {
	private static final Map<Variant, ResourceLocation> LOCATION_BY_VARIANT = Util.make(Maps.newEnumMap(Variant.class), (map) -> {
		map.put(Variant.WHITE, new ResourceLocation("textures/entity/horse/horse_white.png"));
		map.put(Variant.CREAMY, new ResourceLocation("textures/entity/horse/horse_creamy.png"));
		map.put(Variant.CHESTNUT, new ResourceLocation("textures/entity/horse/horse_chestnut.png"));
		map.put(Variant.BROWN, new ResourceLocation("textures/entity/horse/horse_brown.png"));
		map.put(Variant.BLACK, new ResourceLocation("textures/entity/horse/horse_black.png"));
		map.put(Variant.GRAY, new ResourceLocation("textures/entity/horse/horse_gray.png"));
		map.put(Variant.DARK_BROWN, new ResourceLocation("textures/entity/horse/horse_darkbrown.png"));
	});

	public ZorseRenderer(EntityRendererProvider.Context context) {
		super(context, new ZorseModel<>(context.bakeLayer(EnvironmentalModelLayers.ZORSE)), 0.75F);
		this.addLayer(new ZorseStripesRenderLayer(this));
		this.addLayer(new ZorseMarkingsLayer(this));
		this.addLayer(new ZorseArmorLayer(this, context.getModelSet()));
	}

	@Override
	public ResourceLocation getTextureLocation(Zorse zorse) {
		return LOCATION_BY_VARIANT.get(zorse.getVariant());
	}
}