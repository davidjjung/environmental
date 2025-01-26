package com.teamabnormals.environmental.common.entity.ai.goal.zebroid;

import com.teamabnormals.environmental.common.entity.animal.zebroid.Zebroid;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;

public class ZebroidAvoidEntityGoal<Z extends AbstractHorse & Zebroid, T extends LivingEntity> extends AvoidEntityGoal<T> {

	public ZebroidAvoidEntityGoal(Z zeboid, Class<T> toAvoid, float maxDist, double speed, double sprintSpeed) {
		super(zeboid, toAvoid, maxDist, speed, sprintSpeed);
	}

	public boolean canUse() {
		return !((AbstractHorse) this.mob).isTamed() && super.canUse();
	}
}