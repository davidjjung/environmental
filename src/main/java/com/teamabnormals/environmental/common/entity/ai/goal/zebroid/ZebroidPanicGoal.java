package com.teamabnormals.environmental.common.entity.ai.goal.zebroid;

import com.teamabnormals.environmental.common.entity.animal.zebroid.Zebroid;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;

public class ZebroidPanicGoal<Z extends AbstractHorse & Zebroid> extends PanicGoal {

	public ZebroidPanicGoal(Z zebroid, double speedModifier) {
		super(zebroid, speedModifier);
	}

	@Override
	protected boolean shouldPanic() {
		return this.mob.isBaby() && super.shouldPanic();
	}
}