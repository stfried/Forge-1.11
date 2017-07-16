package com.idtech.item;

import java.util.List;

import com.idtech.enchantment.EnchantmentMod;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class PoweredArmor extends ItemCustomArmor {
	
	private List<PotionEffect> effects_u, effects_s;

	public PoweredArmor(ArmorMaterial material, int renderIndex, EntityEquipmentSlot armorType, String name,
			String texture, List<PotionEffect> effects) {
		super(material, renderIndex, armorType, name, texture, effects);
		//material.repairMaterial = new ItemStack(QuickItem.getItem("Battery"));
	}
	
	public PoweredArmor(ArmorMaterial material, int renderIndex, EntityEquipmentSlot armorType, String name,
			String texture, List<PotionEffect> effects_p, List<PotionEffect> effects_u) {
		super(material, renderIndex, armorType, name, texture, effects_p);
		this.effects_u = effects_u;
	}
	
	public PoweredArmor(ArmorMaterial material, int renderIndex, EntityEquipmentSlot armorType, String name,
			String texture, List<PotionEffect> effects_p, List<PotionEffect> effects_u, List<PotionEffect> effects_s) {
		super(material, renderIndex, armorType, name, texture, effects_p);
		this.effects_u = effects_u;
		this.effects_s = effects_s;
	}

	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if (!isBroken(itemStack)) {
			super.onArmorTick(world, player, itemStack);
			if (world.rand.nextFloat() < 0.05) {
				itemStack.damageItem(2, player);
			}
			if (EnchantmentHelper.getEnchantmentLevel(EnchantmentMod.SUPERCHARGED, itemStack) > 0) {
				for (PotionEffect p : this.effects_s) {
					player.addPotionEffect(p);
				}
			}
		}
		else {
			if (this.effects_u != null) {
				for (PotionEffect p : this.effects_u) {
					player.addPotionEffect(p);
				}
			}
		}
	}

	@Override
	public void setDamage(ItemStack stack, int damage)
    {
		//Broken
		if (damage > this.getMaxDamage(stack)) {
			damage = this.getMaxDamage(stack) - 1;
			if (stack.hasTagCompound())
            {
                stack.getTagCompound().removeTag("ench");
            }
		}
		super.setDamage(stack, damage);
    }
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		// TODO Auto-generated method stub
		super.onCreated(stack, worldIn, playerIn);
	}

	public boolean isBroken(ItemStack stack) {
		return this.getDamage(stack) == this.getMaxDamage(stack) - 1;
	}
	
}