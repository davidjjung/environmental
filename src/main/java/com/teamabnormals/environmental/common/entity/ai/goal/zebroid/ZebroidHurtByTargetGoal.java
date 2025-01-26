package com.teamabnormals.environmental.common.entity.ai.goal.zebroid;

import com.teamabnormals.environmental.common.entity.animal.zebroid.Zebroid;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;

public class ZebroidHurtByTargetGoal<Z extends AbstractHorse & Zebroid> extends HurtByTargetGoal {

	public ZebroidHurtByTargetGoal(Z zebroid) {
		super(zebroid);
	}

	@Override
	public void start() {
		super.start();
		if (this.mob.isBaby()) {
			this.stop();
		}
	}
}