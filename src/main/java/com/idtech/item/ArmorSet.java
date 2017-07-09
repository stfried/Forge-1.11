package com.idtech.item;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.idtech.BaseMod;
import com.idtech.JSONManager;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ArmorSet {
	
	public ItemCustomArmor helmet, chest, legs, boots;
	public String name;
	public ItemStack metal;
	private static Set<ArmorSet> registry = new HashSet<ArmorSet>();
	
	public ArmorSet(ArmorMaterial material, String name, ItemStack m, List<PotionEffect> effects) {
		this.name = name;
		helmet = new ItemCustomArmor(material, 0, EntityEquipmentSlot.HEAD, name + "helmet", name, effects);
		chest = new ItemCustomArmor(material, 0, EntityEquipmentSlot.CHEST, name + "chest", name, effects);
		legs = new ItemCustomArmor(material, 0, EntityEquipmentSlot.LEGS, name + "legs", name, effects);
		boots = new ItemCustomArmor(material, 0, EntityEquipmentSlot.FEET, name + "boots", name, effects);
		metal = m;
		this.registry.add(this);
	}
	
	public static void register() {
		Set<ArmorSet> armorSets = Collections.unmodifiableSet(registry);
		for (ArmorSet armorSet : armorSets) {
	        GameRegistry.register(armorSet.helmet.setRegistryName(armorSet.helmet.name));
	        GameRegistry.register(armorSet.chest.setRegistryName(armorSet.chest.name));
	        GameRegistry.register(armorSet.legs.setRegistryName(armorSet.legs.name));
	        GameRegistry.register(armorSet.boots.setRegistryName(armorSet.boots.name));
		}
	}
	
	public static void render() {
		Set<ArmorSet> armorSets = Collections.unmodifiableSet(registry);
		for (ArmorSet armorSet : armorSets) {
	        BaseMod.proxy.registerItemInventoryRender(armorSet.helmet, armorSet.helmet.name);
	        BaseMod.proxy.registerItemInventoryRender(armorSet.chest, armorSet.chest.name);
	        BaseMod.proxy.registerItemInventoryRender(armorSet.legs, armorSet.legs.name);
	        BaseMod.proxy.registerItemInventoryRender(armorSet.boots, armorSet.boots.name);
		}
	}
	
	public static void createJSON() {
		Set<ArmorSet> armorSets = Collections.unmodifiableSet(registry);
		for (ArmorSet armorSet : armorSets) {
			createJSONFile(armorSet.name + "Helmet");
			createJSONFile(armorSet.name + "Chest");
			createJSONFile(armorSet.name + "Legs");
			createJSONFile(armorSet.name + "Boots");
		}
	}
	
	public static void addLang() {
		Set<ArmorSet> armorSets = Collections.unmodifiableSet(registry);
		StringBuilder builder = new StringBuilder();
		builder.append('\n');
		for(ArmorSet armorSet : armorSets) {
			builder.append(armorSet.helmet.getUnlocalizedName() + ".name=" + armorSet.name + " Helmet\n");
			builder.append(armorSet.chest.getUnlocalizedName() + ".name=" + armorSet.name + " Chest\n");
			builder.append(armorSet.legs.getUnlocalizedName() + ".name=" + armorSet.name + " Legs\n");
			builder.append(armorSet.boots.getUnlocalizedName() + ".name=" + armorSet.name + " Boots\n");
		}
		
		
		File f = Paths.get(".").resolve("../src/main/resources/assets/" + BaseMod.MODID + "/lang/en_US.lang").toFile();
		try {
			FileUtils.writeStringToFile(f, builder.toString(), true);			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public static void addRecipes() {
		Set<ArmorSet> armorSets = Collections.unmodifiableSet(registry);
		for (ArmorSet armorSet : armorSets) {
			GameRegistry.addShapedRecipe(new ItemStack(armorSet.helmet), "xxx", "x x", 'x', armorSet.metal);
			GameRegistry.addShapedRecipe(new ItemStack(armorSet.chest), "x x", "xxx", "xxx", 'x', armorSet.metal);
			GameRegistry.addShapedRecipe(new ItemStack(armorSet.legs), "xxx", "x x", "x x", 'x', armorSet.metal);
			GameRegistry.addShapedRecipe(new ItemStack(armorSet.boots), "x x", "x x", 'x', armorSet.metal);
		}
	}
	
	private static void createJSONFile(String name) {
		File f = Paths.get(".").resolve(JSONManager.assetsDir + "/models/item/" + JSONManager.jsonName(name) + ".json")
				.toFile();

		if (f.exists()) {
			f.delete();
		}

		StringBuilder builder = new StringBuilder();

		builder.append("{");
		builder.append("\"parent\": \"item/handheld\",");
		builder.append("\"textures\": {");
		builder.append("   \"layer0\": \"" + BaseMod.MODID + ":items/" + name + "\"");
		builder.append("}");
		builder.append("}");

		try {
			FileUtils.writeStringToFile(f, builder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
