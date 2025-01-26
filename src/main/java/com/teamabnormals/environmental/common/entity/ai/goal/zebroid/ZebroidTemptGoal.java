package com.teamabnormals.environmental.common.entity.ai.goal.zebroid;

import com.teamabnormals.environmental.common.entity.animal.zebroid.Zebroid;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.crafting.Ingredient;

public class ZebroidTemptGoal<Z extends AbstractHorse & Zebroid> extends TemptGoal {

	public ZebroidTemptGoal(Z zebroid, double speedModifier, Ingredient items) {
		super(zebroid, speedModifier, items, false);
	}

	@Override
	public boolean canUse() {
		return ((AbstractHorse) this.mob).isTamed() && super.canUse();
	}
}