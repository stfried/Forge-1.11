package com.idtech.item;

import com.idtech.BaseMod;
import com.idtech.item.QuickItem.ItemType;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBattery extends QuickItem {
	
	public ItemBattery () {
		super();
		name = "Battery";
		tab = BaseMod.itemTab;
		texture = "battery";
		this.setMaxStackSize(1);
		this.setMaxDamage(1000);
	}

	@Override
	public void setDamage(ItemStack stack, int damage) {
		int d = 0;
		if (stack.isItemEnchanted()) {
			for (int i = 0; i < damage; i++)
				if (this.itemRand.nextFloat() >= 0.60)
					d++;
			super.setDamage(stack, d);		
		}
		else
			super.setDamage(stack, damage);
	}

}
