package com.idtech.enchantment;

import com.idtech.item.ItemBattery;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentSuperCharged extends Enchantment {

	public EnchantmentSuperCharged() {
		super(Rarity.RARE, EnchantmentMod.BATTERY, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
	}

	@Override
	public boolean isTreasureEnchantment() {
		return false;
	}

	@Override
	public boolean isCurse() {
		return false;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return false;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public boolean canApply(ItemStack stack) {
		return stack.getItem() instanceof ItemBattery;
	}		
	
}
