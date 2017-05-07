package com.idtech.item;

import com.idtech.BaseMod;
import com.idtech.block.BlockRemoteReceiver;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemRedstoneRemote extends QuickItem {
	
	//Works as remote lever, look at receiving block and right click to toggle on or off
	
	public ItemRedstoneRemote()	{
		super();
		name = "Redstone Remote";
		tab = BaseMod.itemTab;
		texture = "remote";
		this.setMaxStackSize(1);
		this.setMaxDamage(1000);
	}

	/*
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		BlockPos pos = this.findBlockAtCursor(20);
		if (worldIn.getBlockState(pos).getBlock() instanceof BlockRemoteReceiver) {
			((BlockRemoteReceiver) worldIn.getBlockState(pos).getBlock()).onRemoteActivated(worldIn, pos, worldIn.getBlockState(pos), playerIn);
			playerIn.getHeldItem(handIn).damageItem(10, playerIn);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	*/
	
	@Override
	protected BlockPos findBlockAtCursor(float range) {
		Vec3d posVec = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
		Vec3d lookVec = player.getLookVec();

		// Draw a line from the player to where the player is aiming, save it if
		// we hit a block.
		RayTraceResult blockHit = world.rayTraceBlocks(posVec,
				posVec.addVector(lookVec.xCoord * range, lookVec.yCoord * range, lookVec.zCoord * range));
		if (blockHit == null)
			return null;
		BlockPos block = blockHit.getBlockPos();
		return block;
	}

	@Override
	protected void onRightClick() {
		BlockPos pos = (this.findBlockAtCursor(20));
		if (pos != null && world.getBlockState(pos).getBlock() instanceof BlockRemoteReceiver) {
			((BlockRemoteReceiver) world.getBlockState(pos).getBlock()).onRemoteActivated(world, pos, world.getBlockState(pos), player);
			player.getHeldItem(hand).damageItem(10, player);
		}
	}

}
