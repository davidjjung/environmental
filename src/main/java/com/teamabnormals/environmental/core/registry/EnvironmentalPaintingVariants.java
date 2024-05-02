package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnvironmentalPaintingVariants {
	public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, Environmental.MOD_ID);

	public static final RegistryObject<PaintingVariant> SLABFISH = PAINTING_VARIANTS.register("slabfish", () -> new PaintingVariant(32, 32));
	public static final RegistryObject<PaintingVariant> SNAKE_BLOCK = PAINTING_VARIANTS.register("snake_block", () -> new PaintingVariant(32, 32));
	public static final RegistryObject<PaintingVariant> ARCHIVE = PAINTING_VARIANTS.register("archive", () -> new PaintingVariant(64, 48));
	public static final RegistryObject<PaintingVariant> OPTIMAL_AERODYNAMICS = PAINTING_VARIANTS.register("optimal_aerodynamics", () -> new PaintingVariant(32, 48));
	public static final RegistryObject<PaintingVariant> IN_PLAINS_SIGHT = PAINTING_VARIANTS.register("in_plains_sight", () -> new PaintingVariant(32, 16));
	public static final RegistryObject<PaintingVariant> THE_PLACE_WITHIN_THE_PINES = PAINTING_VARIANTS.register("the_place_within_the_pines", () -> new PaintingVariant(48, 16));
	public static final RegistryObject<PaintingVariant> BOUQUET = PAINTING_VARIANTS.register("bouquet", () -> new PaintingVariant(16, 32));
	public static final RegistryObject<PaintingVariant> BOUQUET2 = PAINTING_VARIANTS.register("bouquet2", () -> new PaintingVariant(16, 16));
	public static final RegistryObject<PaintingVariant> LONE_PLUM = PAINTING_VARIANTS.register("lone_plum", () -> new PaintingVariant(48, 32));
	public static final RegistryObject<PaintingVariant> MARSHPATH = PAINTING_VARIANTS.register("marshpath", () -> new PaintingVariant(32, 48));
}
