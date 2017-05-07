package com.idtech.network;

import com.idtech.block.tileEntity.LightningRodContainerTileEntity;
import com.idtech.block.tileEntity.LightningRodGui;
import com.idtech.block.tileEntity.LightningRodTileEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ModGuiHandler implements IGuiHandler {
	
	public static final int LIGHTNING_ROD_GUI = 0;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == LIGHTNING_ROD_GUI)
			return new LightningRodContainerTileEntity(player.inventory, (LightningRodTileEntity) world.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == LIGHTNING_ROD_GUI)
			return new LightningRodGui(player.inventory, (LightningRodTileEntity) world.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}

}
