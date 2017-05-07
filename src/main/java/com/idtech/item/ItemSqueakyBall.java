package com.idtech.item;

import com.idtech.BaseMod;

import net.minecraft.init.SoundEvents;

public class ItemSqueakyBall extends QuickItem {
	
	{
		name = "Squeaky Ball";
		texture = "squeakyball";
		tab = BaseMod.exampleTab;
	}
	
	public void onRightClick() {
		displayMessage("Squeak!");
		playSound(SoundEvents.ENTITY_BAT_AMBIENT);
	}
}
