package com.idtech.block.tileEntity;

import com.idtech.BaseMod;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityMod {
	
	public static void preInit() {
		GameRegistry.registerTileEntity(LightningRodTileEntity.class, BaseMod.MODID + "LightningRodTileEntity");
	}
	
}
