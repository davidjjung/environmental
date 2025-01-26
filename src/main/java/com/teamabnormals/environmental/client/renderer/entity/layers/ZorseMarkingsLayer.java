package com.teamabnormals.environmental.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabnormals.environmental.client.model.ZorseModel;
import com.teamabnormals.environmental.common.entity.animal.zebroid.Zorse;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Markings;

import java.util.Map;

public class ZorseMarkingsLayer extends RenderLayer<Zorse, ZorseModel<Zorse>> {
	private static final Map<Markings, ResourceLocation> LOCATION_BY_MARKINGS = Util.make(Maps.newEnumMap(Markings.class), (map) -> {
		map.put(Markings.NONE, null);
		map.put(Markings.WHITE, new ResourceLocation("textures/entity/horse/horse_markings_white.png"));
		map.put(Markings.WHITE_FIELD, new ResourceLocation("textures/entity/horse/horse_markings_whitefield.png"));
		map.put(Markings.WHITE_DOTS, new ResourceLocation("textures/entity/horse/horse_markings_whitedots.png"));
		map.put(Markings.BLACK_DOTS, new ResourceLocation("textures/entity/horse/horse_markings_blackdots.png"));
	});

	public ZorseMarkingsLayer(RenderLayerParent<Zorse, ZorseModel<Zorse>> entityRenderer) {
		super(entityRenderer);
	}

	public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Zorse zorse, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
		ResourceLocation resourcelocation = LOCATION_BY_MARKINGS.get(zorse.getMarkings());
		if (resourcelocation != null && !zorse.isInvisible()) {
			VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityTranslucent(resourcelocation));
			this.getParentModel().renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(zorse, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}