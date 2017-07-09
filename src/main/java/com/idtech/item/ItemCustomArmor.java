package com.idtech.item;

import java.util.List;

import com.idtech.BaseMod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemCustomArmor extends ItemArmor {
	
	public final String name;
	private final String texture;
	private List<PotionEffect> effects;
    
    public ItemCustomArmor(ArmorMaterial material, int renderIndex,
            EntityEquipmentSlot armorType, String name, String texture, List<PotionEffect> effects) {
        super(material, renderIndex, armorType);
        this.name = name;
        this.texture = texture;
        this.setUnlocalizedName(BaseMod.MODID + "_" + name);
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.effects = effects;
        // TODO Auto-generated constructor stub
    }
 
     
 
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot,
            String type) {
 
        if(slot == EntityEquipmentSlot.LEGS){
            return "examplemod:textures/models/armor/" + texture + "_armor_layer_2.png";
        }
        return "examplemod:textures/models/armor/" + texture + "_armor_layer_1.png";
    }


	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		// TODO Auto-generated method stub
		super.onArmorTick(world, player, itemStack);
		if (effects != null) {
			for (PotionEffect effect : effects) {
				player.addPotionEffect(effect);
			}
		}
	}
    
    
	

}
