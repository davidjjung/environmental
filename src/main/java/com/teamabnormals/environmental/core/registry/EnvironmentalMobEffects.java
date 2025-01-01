package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.blueprint.common.effect.BlueprintMobEffect;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = Environmental.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalMobEffects {
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Environmental.MOD_ID);

	public static final RegistryObject<MobEffect> SERENITY = MOB_EFFECTS.register("serenity", () -> new BlueprintMobEffect(MobEffectCategory.BENEFICIAL, 15494786));
}