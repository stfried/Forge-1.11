package com.idtech.item;

import com.idtech.BaseMod;
import com.idtech.SoundHelper;

import net.minecraft.util.SoundEvent;

public class ItemSpeaker extends QuickItem {
	
	int i = 0;
	
	{
		name = "Speaker";
		tab = BaseMod.exampleTab;
		texture = "speaker";
		type = ItemType.HandHeld;
	}

	public void onRightClick(){
		SoundEvent sound = SoundHelper.soundEvents[i];
		String soundName = SoundHelper.soundStrings[i];
		playSound(sound);
		displayMessage(soundName);
		if(!world.isRemote) i = ++i % SoundHelper.soundEvents.length;
	}
	
}
