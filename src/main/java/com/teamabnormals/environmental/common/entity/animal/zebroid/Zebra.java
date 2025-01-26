package com.teamabnormals.environmental.common.entity.animal.zebroid;

import com.teamabnormals.environmental.common.entity.ai.goal.HerdLandWanderGoal;
import com.teamabnormals.environmental.common.entity.ai.goal.zebroid.ZebraFleeGoal;
import com.teamabnormals.environmental.common.entity.ai.goal.zebroid.ZebroidFollowParentGoal;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import net.minecraft.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

public class Zebra extends AbstractUnchestedZebroid {
	private ZebraFleeGoal fleeGoal;

	public Zebra(EntityType<? extends AbstractHorse> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void addBehaviourGoals() {
		this.fleeGoal = new ZebraFleeGoal(this, 1.8D);
		this.goalSelector.addGoal(2, this.fleeGoal);
		this.goalSelector.addGoal(6, new ZebroidFollowParentGoal<>(this, 1.0D));
		this.goalSelector.addGoal(7, new HerdLandWanderGoal(this, 0.7D, 1.2D, 16));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return AbstractHorse.createBaseHorseAttributes().add(Attributes.ATTACK_DAMAGE).add(Attributes.ATTACK_KNOCKBACK, 1.0D).add(Attributes.FOLLOW_RANGE, 8.0D);
	}

	@Override
	public void setLastHurtByMob(@Nullable LivingEntity attacker) {
		if (attacker != null && this.level() instanceof ServerLevel) {
			int fleetime = this.getRandom().nextInt(40) + 100;
			float fleedirection = this.getRandom().nextFloat() * 360.0F;

			List<Zebra> zebras = this.level().getEntitiesOfClass(Zebra.class, this.getBoundingBox().inflate(10.0D, 4.0D, 10.0D), zebra -> zebra != this && !zebra.isFleeing() && !zebra.isTamed() && zebra.getTarget() == null)
					.stream().sorted(Comparator.comparingDouble(entity -> entity.distanceToSqr(this))).limit(3).toList();
			for (Zebra zebra : zebras)
				zebra.getFleeGoal().trigger(fleetime, fleedirection);

			if (this.isBaby())
				this.fleeGoal.trigger(fleetime, fleedirection);
		}
		super.setLastHurtByMob(attacker);
	}

	public ZebraFleeGoal getFleeGoal() {
		return this.fleeGoal;
	}

	@Override
	public boolean isFleeing() {
		return this.fleeGoal.running();
	}

	@Override
	public boolean canMate(Animal otherParent) {
		if (otherParent == this) {
			return false;
		} else if (!(otherParent instanceof Zebra) && !(otherParent instanceof Horse) && !(otherParent instanceof Donkey)) {
			return false;
		} else {
			return this.canParent() && ((AbstractHorse) otherParent).canParent();
		}
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
		if (otherParent instanceof Horse) {
			Horse horse = (Horse) otherParent;
			Zorse zorse = EnvironmentalEntityTypes.ZORSE.get().create(level);
			if (zorse != null) {
				int i = this.random.nextInt(9);
				Variant variant;
				if (i < 8) {
					variant = horse.getVariant();
				} else {
					variant = Util.getRandom(Variant.values(), this.random);
				}

				int j = this.random.nextInt(5);
				Markings markings;
				if (j < 2) {
					markings = Markings.NONE;
				} else if (j < 4) {
					markings = horse.getMarkings();
				} else {
					markings = Util.getRandom(Markings.values(), this.random);
				}

				zorse.randomizeStripeOpacity(level.getRandom());
				zorse.setVariantAndMarkings(variant, markings);
				this.setOffspringAttributes(otherParent, zorse);
			}
			return zorse;
		} else if (otherParent instanceof Donkey) {
			Zonkey zonkey = EnvironmentalEntityTypes.ZONKEY.get().create(level);
			if (zonkey != null) {
				zonkey.randomizeStripeOpacity(level.getRandom());
				this.setOffspringAttributes(otherParent, zonkey);
			}
			return zonkey;
		} else {
			Zebra zebra = EnvironmentalEntityTypes.ZEBRA.get().create(level);
			if (zebra != null) {
				this.setOffspringAttributes(otherParent, zebra);
			}
			return zebra;
		}
	}

	@Override
	public void setOffspringAttributes(AgeableMob otherParent, AbstractHorse child) {
		super.setOffspringAttributes(otherParent, child);
		this.setOffspringAttribute(otherParent instanceof Zebra ? otherParent : this, child, Attributes.ATTACK_DAMAGE, MIN_DAMAGE, MAX_DAMAGE);
	}

	@Override
	public double getPassengersRidingOffset() {
		return super.getPassengersRidingOffset() - 0.175D;
	}
}