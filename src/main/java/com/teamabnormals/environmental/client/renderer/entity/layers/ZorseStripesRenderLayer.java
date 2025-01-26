package com.teamabnormals.environmental.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabnormals.environmental.client.model.ZorseModel;
import com.teamabnormals.environmental.common.entity.animal.zebroid.Zorse;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.horse.Variant;

import java.util.Map;

public class ZorseStripesRenderLayer extends RenderLayer<Zorse, ZorseModel<Zorse>> {
	private static final Map<Variant, ResourceLocation> LOCATION_BY_VARIANT = Util.make(Maps.newEnumMap(Variant.class), (map) -> {
		map.put(Variant.WHITE, new ResourceLocation(Environmental.MOD_ID, "textures/entity/zebroid/zorse_overlay_white.png"));
		map.put(Variant.CREAMY, new ResourceLocation(Environmental.MOD_ID, "textures/entity/zebroid/zorse_overlay_creamy.png"));
		map.put(Variant.CHESTNUT, new ResourceLocation(Environmental.MOD_ID, "textures/entity/zebroid/zorse_overlay_chestnut.png"));
		map.put(Variant.BROWN, new ResourceLocation(Environmental.MOD_ID, "textures/entity/zebroid/zorse_overlay_brown.png"));
		map.put(Variant.BLACK, new ResourceLocation(Environmental.MOD_ID, "textures/entity/zebroid/zorse_overlay_black.png"));
		map.put(Variant.GRAY, new ResourceLocation(Environmental.MOD_ID, "textures/entity/zebroid/zorse_overlay_gray.png"));
		map.put(Variant.DARK_BROWN, new ResourceLocation(Environmental.MOD_ID, "textures/entity/zebroid/zorse_overlay_darkbrown.png"));
	});

	public ZorseStripesRenderLayer(RenderLayerParent<Zorse, ZorseModel<Zorse>> entityRenderer) {
		super(entityRenderer);
	}

	public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Zorse zorse, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!zorse.isInvisible()) {
			VertexConsumer builder = buffer.getBuffer(RenderType.entityTranslucent(LOCATION_BY_VARIANT.get(zorse.getVariant())));
			float f = Mth.clamp(zorse.getStripeOpacity() / 100.0F, 0.0F, 1.0F);
			this.getParentModel().renderToBuffer(poseStack, builder, packedLight, LivingEntityRenderer.getOverlayCoords(zorse, 0.0F), 1.0F, 1.0F, 1.0F, f);
		}
	}
}