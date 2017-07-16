package com.idtech.recipe;

import com.idtech.block.QuickBlock;
import com.idtech.enchantment.EnchantmentMod;
import com.idtech.item.ItemBattery;
import com.idtech.item.ItemMod;
import com.idtech.item.QuickItem;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeMod {
	
	public static void init(){
		
		ItemStack empty_battery = new ItemStack(QuickItem.getItem("Battery"), 1);
		empty_battery.setItemDamage(empty_battery.getMaxDamage());
		
		ItemStack super_charged_battery = new ItemStack(QuickItem.getItem("Battery"), 1);
		super_charged_battery.addEnchantment(EnchantmentMod.SUPERCHARGED, 1);
		
		ItemStack super_charged_NVG = new ItemStack(ItemMod.NVG);
		super_charged_NVG.addEnchantment(EnchantmentMod.SUPERCHARGED, 1);
		
		GameRegistry.addShapedRecipe(empty_battery, " x ", "yzy", " x ", 'x', Items.IRON_INGOT, 'y', Items.REDSTONE, 'z', Items.STICK);
		GameRegistry.addShapedRecipe(new ItemStack(QuickBlock.getBlock("Lightning Rod"), 1), " x ", "xxx", " y ", 'x', Items.IRON_INGOT, 'y', Item.getItemFromBlock(Blocks.PLANKS));
		GameRegistry.addShapedRecipe(new ItemStack(QuickItem.getItem("Lightning Hammer"),1), "xxx", " y ", " y ", 'x', Item.getItemFromBlock(Blocks.IRON_BLOCK), 'y', Item.getItemFromBlock(QuickBlock.getBlock("Lightning Rod")));
		GameRegistry.addShapedRecipe(new ItemStack(ItemMod.NVG), "xxx", "yzy", 'x', Items.LEATHER, 'y', Item.getItemFromBlock(Blocks.GLASS), 'z', QuickItem.getItem("Battery"));
		GameRegistry.addShapedRecipe(super_charged_NVG, "xxx", "yzy", 'x', Items.LEATHER, 'y', Item.getItemFromBlock(Blocks.GLASS), 'z', super_charged_battery);
		
		//ToolSet.addRecipes();
		//ArmorSet.addRecipes();
	}
	
	/*
	private void addBatteryRecipe(ItemStack result, Object... params) {
		GameRegistry.addShapedRecipe(result, params);
		for (Object o : params) {
			if (o instanceof ItemBattery) {
				
			}
		}
	}
	*/
	
}
