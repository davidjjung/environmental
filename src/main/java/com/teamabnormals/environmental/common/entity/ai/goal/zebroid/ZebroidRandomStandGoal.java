package com.teamabnormals.environmental.common.entity.ai.goal.zebroid;

import com.teamabnormals.environmental.common.entity.animal.zebroid.Zebroid;
import net.minecraft.world.entity.ai.goal.RandomStandGoal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;

public class ZebroidRandomStandGoal<Z extends AbstractHorse & Zebroid> extends RandomStandGoal {
	private final Zebroid zebroid;

	public ZebroidRandomStandGoal(Z zebroid) {
		super(zebroid);
		this.zebroid = zebroid;
	}

	@Override
	public boolean canUse() {
		return zebroid.canDoIdleAnimation() && super.canUse();
	}
}