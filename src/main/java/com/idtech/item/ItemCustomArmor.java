package com.idtech.item;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.idtech.BaseMod;
import com.idtech.JSONManager;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemCustomArmor extends ItemArmor {
	
	public final String name;
	private final String texture;
	private List<PotionEffect> effects;

	//private static Set<ItemCustomArmor> registry = new HashSet<ItemCustomArmor>();
	private static List<ItemCustomArmor> registry = new ArrayList<ItemCustomArmor>();
    
    public ItemCustomArmor(ArmorMaterial material, int renderIndex,
            EntityEquipmentSlot armorType, String name, String texture, List<PotionEffect> effects) {
        super(material, renderIndex, armorType);
        this.name = name;
        this.texture = texture;
        this.setUnlocalizedName(BaseMod.MODID + "_" + name.replaceAll("\\s+",""));
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.effects = effects;
        
		this.registry.add(this);
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
	
	public static void register() {
		for (ItemCustomArmor a : registry) {
	        GameRegistry.register(a.setRegistryName(a.name.replaceAll("\\s+","")));
		}
	}
	
	public static void render() {
		for (ItemCustomArmor a : registry) {
	        BaseMod.proxy.registerItemInventoryRender(a, a.name.replaceAll("\\s+",""));
		}
	}
	
	public static void createJSON() {
		for (ItemCustomArmor a : registry) {
			createJSONFile(a.name.replaceAll("\\s+",""));
		}
	}
	
	public static void addLang() {
		StringBuilder builder = new StringBuilder();
		builder.append('\n');
		for (ItemCustomArmor a : registry) {
			builder.append(a.getUnlocalizedName() + ".name=" + a.name + "\n");
		}
		
		
		File f = Paths.get(".").resolve("../src/main/resources/assets/" + BaseMod.MODID + "/lang/en_US.lang").toFile();
		try {
			FileUtils.writeStringToFile(f, builder.toString(), true);			
		} catch (IOException e) {
			e.printStackTrace();
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
		builder.append("\"parent\": \"item/generated\",");
		builder.append("\"textures\": {");
		builder.append("   \"layer0\": \"" + BaseMod.MODID + ":items/" + JSONManager.jsonName(name) + "\"");
		builder.append("}");
		builder.append("}");

		try {
			FileUtils.writeStringToFile(f, builder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void preInit() {
		register();
	}
	
	public static void init() {
		render();
		createJSON();
	}
    
    
	

}
