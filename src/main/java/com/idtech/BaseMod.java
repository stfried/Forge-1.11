package com.idtech;

import com.idtech.block.QuickBlock;
import com.idtech.block.tileEntity.TileEntityMod;
import com.idtech.item.ArmorSet;
import com.idtech.item.ItemMod;
import com.idtech.item.QuickItem;
import com.idtech.item.ToolSet;
import com.idtech.proxy.CommonProxy;
import com.idtech.recipe.RecipeMod;
import com.idtech.world.WorldMod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = BaseMod.MODID, version = BaseMod.VERSION)
public class BaseMod
{



	/**
	 * Change MODID to a unique name for your mod.
	 * You can change VERSION to higher numbers as you make new versions.
	 */
	public static final String MODID = "examplemod";
	public static final String VERSION = "1.0";

	/**
	 * ----DO NOT EDIT----
	 * BaseMod.instance will get the currently running copy of your mod.
	 * Used in other mod classes.
	 */
	@Instance(MODID)
	public static BaseMod instance;

	/**
	 * ----DO NOT EDIT----
	 * Sided proxies for your mod. Used in cases where code must be only run on either the client or the server.
	 */
	@SidedProxy(modId=MODID, clientSide="com.idtech.proxy.ClientProxy", serverSide="com.idtech.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	
	public static CreativeTabs exampleTab = new CreativeTabs("exampleTab") {
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			return new ItemStack(QuickItem.getItem("Speaker"));
		}
	};
		
	public static CreativeTabs itemTab = new CreativeTabs("itemTab") {
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			return new ItemStack(QuickItem.getItem("Speaker"));
		}
	};
	public static CreativeTabs blockTab = new CreativeTabs("blockTab") {
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			return new ItemStack(QuickItem.getItem("Speaker"));
		}
	};	
	
	public static final DamageSource ZAP = (new DamageSource("zap")).setDamageBypassesArmor();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		// Mod PreInit
		ItemMod.preInit();
		QuickItem.preInit();
		QuickBlock.preInit();
		TileEntityMod.preInit();

		LanguageLocalization.createLanguageFile();
		ToolSet.addLang();
		ArmorSet.addLang();
		JSONManager.buildJSON();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{

		// Mod Init
		ItemMod.init();
		QuickItem.init();
		QuickBlock.init();
		RecipeMod.init();
		
		WorldMod.init();
		CommonProxy.init();


	}


}
