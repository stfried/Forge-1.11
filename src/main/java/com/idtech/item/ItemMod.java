package com.idtech.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

public class ItemMod {

    // Toolsets
    //public static ItemGelPickaxe itemGelPickaxe;
    public static ToolMaterial emeraldT;
    public static ToolMaterial obsidianT;
    public static ToolSet eTools;
    public static ToolSet oTools;
    
    //Armorsets
    public static ArmorSet eArmor;
    public static ArmorMaterial emeraldA;
    public static ArmorSet oArmor;
    public static ArmorMaterial obsidianA;
    
    public static ArmorMaterial NVG_mat;
    public static PoweredArmor NVG;
    
     
    public static void preInit(){
    	//Materials			(Name,Harvest,Durability,Efficiency,Damage,Enchant)
    	emeraldT = EnumHelper.addToolMaterial("EMERALD", 3, 4000, 12, 5, 12);
    	emeraldT.setRepairItem(new ItemStack(Items.EMERALD));
    	obsidianT = EnumHelper.addToolMaterial("OBSIDIAN", 3, 9000, 6, 3.5f, 8);
    	obsidianT.setRepairItem(new ItemStack(Blocks.OBSIDIAN));
    	
    	emeraldA = EnumHelper.addArmorMaterial("EMERALD", "EMERALD", 38, new int[]{4, 10, 8, 4}, 12, 
    			SoundEvent.REGISTRY.getObject(new ResourceLocation("item.armor.equip_iron")), 4.0f);
    	emeraldA.setRepairItem(new ItemStack(Items.EMERALD));
    	obsidianA = EnumHelper.addArmorMaterial("OBSIDIAN", "OBSIDIAN", 50, new int[]{3, 9, 7, 3}, 10,
    			SoundEvent.REGISTRY.getObject(new ResourceLocation("item.armor.equip_iron")), 8.0f);
         
        // Tools and Armor
        eTools = new ToolSet(emeraldT, "Emerald", new ItemStack(Items.STICK), new ItemStack(Items.EMERALD));
        oTools = new ToolSet(obsidianT, "Obsidian", new ItemStack(Items.STICK), new ItemStack(Blocks.OBSIDIAN));
        eArmor = new ArmorSet(emeraldA, "Emerald", new ItemStack(Items.EMERALD), null);
        
        List<PotionEffect> fx = new ArrayList<PotionEffect>();
        fx.add(new PotionEffect(Potion.getPotionById(2), 5, 0));
        fx.add(new PotionEffect(Potion.getPotionById(4), 5, 0));
        
        oArmor = new ArmorSet(obsidianA, "Obsidian", new ItemStack(Blocks.OBSIDIAN), fx);
        
        
        NVG_mat = EnumHelper.addArmorMaterial("NVG", "NVG", 0, new int[]{1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
        NVG_mat.repairMaterial = new ItemStack(QuickItem.getItem("Battery"));
        fx = new ArrayList<PotionEffect>();
        fx.add(new PotionEffect(Potion.getPotionById(16), 5, 0));
        NVG = new PoweredArmor(ArmorMaterial.LEATHER, 0, EntityEquipmentSlot.HEAD, "Night Vision Goggles", "NVG", fx);
        
        
        ToolSet.register();
        ItemCustomArmor.preInit();
        //ArmorSet.register();
    }
 
    public static void init(){
 
        // Items
        //BaseMod.proxy.registerItemInventoryRender(itemGelPickaxe, itemGelPickaxe.name);
           
           
    	ToolSet.init();
    	ItemCustomArmor.init();
    	ArmorSet.init();
    }

}
 
 



