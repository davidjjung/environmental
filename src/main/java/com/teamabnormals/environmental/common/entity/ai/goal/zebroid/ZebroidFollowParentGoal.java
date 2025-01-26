package com.teamabnormals.environmental.common.entity.ai.goal.zebroid;

import com.teamabnormals.environmental.common.entity.animal.zebroid.Zebroid;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;

import java.util.EnumSet;

public class ZebroidFollowParentGoal<Z extends AbstractHorse & Zebroid> extends FollowParentGoal {

	public ZebroidFollowParentGoal(Z zebroid, double speed) {
		super(zebroid, speed);
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}
}