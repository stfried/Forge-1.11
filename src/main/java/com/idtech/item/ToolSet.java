package com.idtech.item;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.idtech.BaseMod;
import com.idtech.JSONManager;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ToolSet {
	
	public CustomPickaxe customPickaxe;
	public CustomSpade customSpade;
	public CustomAxe customAxe;
	public CustomHoe customHoe;
	public CustomSword customSword;
	public String name;
	public ItemStack stick, metal;
	
	private static Set<ToolSet> registry = new HashSet<ToolSet>();
	
	public ToolSet(ToolMaterial material, String name, ItemStack s, ItemStack m) {
		this.name = name;
		customPickaxe = new CustomPickaxe(material, name);
		customSpade = new CustomSpade(material, name);
		customAxe = new CustomAxe(material, name);
		customHoe = new CustomHoe(material, name);
		customSword = new CustomSword(material, name);
		this.registry.add(this);
		stick = s;
		metal = m;
	}
	
	public static void register() {
		Set<ToolSet> toolSets = Collections.unmodifiableSet(registry);
		for (ToolSet toolSet : toolSets) {
	        GameRegistry.register(toolSet.customPickaxe.setRegistryName(toolSet.customPickaxe.name));
	        GameRegistry.register(toolSet.customSpade.setRegistryName(toolSet.customSpade.name));
	        GameRegistry.register(toolSet.customAxe.setRegistryName(toolSet.customAxe.name));
	        GameRegistry.register(toolSet.customHoe.setRegistryName(toolSet.customHoe.name));
	        GameRegistry.register(toolSet.customSword.setRegistryName(toolSet.customSword.name));
		}
	}
	
	public static void render() {
		Set<ToolSet> toolSets = Collections.unmodifiableSet(registry);
		for (ToolSet toolSet : toolSets) {
	        BaseMod.proxy.registerItemInventoryRender(toolSet.customPickaxe, toolSet.customPickaxe.name);
	        BaseMod.proxy.registerItemInventoryRender(toolSet.customSpade, toolSet.customSpade.name);
	        BaseMod.proxy.registerItemInventoryRender(toolSet.customAxe, toolSet.customAxe.name);
	        BaseMod.proxy.registerItemInventoryRender(toolSet.customHoe, toolSet.customHoe.name);
	        BaseMod.proxy.registerItemInventoryRender(toolSet.customSword, toolSet.customSword.name);
		}

	}
	
	public static void createJSON() {
		Set<ToolSet> toolSets = Collections.unmodifiableSet(registry);
		for (ToolSet toolSet : toolSets) {
			createJSONFile(toolSet.name + "Pickaxe");
			createJSONFile(toolSet.name + "Spade");
			createJSONFile(toolSet.name + "Axe");
			createJSONFile(toolSet.name + "Hoe");
			createJSONFile(toolSet.name + "Sword");
		}
	}
	
	public static void addLang() {
		Set<ToolSet> toolSets = Collections.unmodifiableSet(registry);
		StringBuilder builder = new StringBuilder();
		builder.append('\n');
		for(ToolSet toolSet : toolSets) {
			builder.append(toolSet.customPickaxe.getUnlocalizedName() + ".name=" + toolSet.name + " Pickaxe\n");
			builder.append(toolSet.customSpade.getUnlocalizedName() + ".name=" + toolSet.name + " Spade\n");
			builder.append(toolSet.customAxe.getUnlocalizedName() + ".name=" + toolSet.name + " Axe\n");
			builder.append(toolSet.customHoe.getUnlocalizedName() + ".name=" + toolSet.name + " Hoe\n");
			builder.append(toolSet.customSword.getUnlocalizedName() + ".name=" + toolSet.name + " Sword\n");
		}
		
		
		File f = Paths.get(".").resolve("../src/main/resources/assets/" + BaseMod.MODID + "/lang/en_US.lang").toFile();
		try {
			FileUtils.writeStringToFile(f, builder.toString(), true);			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public static void addRecipes() {
		Set<ToolSet> toolSets = Collections.unmodifiableSet(registry);
		for (ToolSet toolSet : toolSets) {
			GameRegistry.addShapedRecipe(new ItemStack(toolSet.customPickaxe), "xxx", " y ", " y ", 'x', toolSet.metal, 'y', toolSet.stick);
			GameRegistry.addShapedRecipe(new ItemStack(toolSet.customSpade), "x", "y", "y", 'x', toolSet.metal, 'y', toolSet.stick);
			GameRegistry.addShapedRecipe(new ItemStack(toolSet.customAxe), "xx ", "xy ", " y ", 'x', toolSet.metal, 'y', toolSet.stick);
			GameRegistry.addShapedRecipe(new ItemStack(toolSet.customHoe), "xx ", " y ", " y ", 'x', toolSet.metal, 'y', toolSet.stick);
			GameRegistry.addShapedRecipe(new ItemStack(toolSet.customSword), "x", "x", "y", 'x', toolSet.metal, 'y', toolSet.stick);	
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
	
	
	
	
	private class CustomPickaxe extends ItemPickaxe {
		
		public String name;

		public CustomPickaxe(ToolMaterial material, String name) {
			super(material);
			this.name = name + "Pickaxe";
	        this.setUnlocalizedName(BaseMod.MODID + "_" + this.name);
	        this.setCreativeTab(CreativeTabs.TOOLS);
		}
		
	}
	
	private class CustomSpade extends ItemSpade {
		
		public String name;

		public CustomSpade(ToolMaterial material, String name) {
			super(material);
			this.name = name  + "Spade";
	        this.setUnlocalizedName(BaseMod.MODID + "_" + this.name);
	        this.setCreativeTab(CreativeTabs.TOOLS);
		}
		
	}

	private class CustomAxe extends ItemAxe {
		
		public String name;

		public CustomAxe(ToolMaterial material, String name) {
			super(material, material.getDamageVsEntity() + 6, -3.0f);
			this.name = name + "Axe";
	        this.setUnlocalizedName(BaseMod.MODID + "_" + this.name);
	        this.setCreativeTab(CreativeTabs.TOOLS);
		}
		
	}
	
	private class CustomHoe extends ItemHoe {
		
		public String name;

		public CustomHoe(ToolMaterial material, String name) {
			super(material);
			this.name = name + "Hoe";
	        this.setUnlocalizedName(BaseMod.MODID + "_" + this.name);
	        this.setCreativeTab(CreativeTabs.TOOLS);
		}
		
	}
	
	private class CustomSword extends ItemSword {
		
		public String name;

		public CustomSword(ToolMaterial material, String name) {
			super(material);
			this.name = name + "Sword";
	        this.setUnlocalizedName(BaseMod.MODID + "_" + this.name);
	        this.setCreativeTab(CreativeTabs.COMBAT);
		}
		
	}

}