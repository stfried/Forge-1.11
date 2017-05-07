package com.idtech.block;

import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockMod {
	
	public static BlockLightningRod lightningRod;
	
	public static void preInit(){
		
		lightningRod = new BlockLightningRod();
		
		GameRegistry.register(lightningRod.setRegistryName(lightningRod.name));
		GameRegistry.register(new ItemBlock(lightningRod).setRegistryName(lightningRod.getRegistryName()));
		

	}
	
	public static void init(){
		
		
	}

}
