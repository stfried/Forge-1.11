package com.idtech.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockLightningRodPowered extends BlockLightningRod {
	{
		this.lightValue = 15;
		name = "Lightning Rod-Powered";
		this.setCreativeTab(null);
		this.setTickRandomly(true);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
	}

	@Override
	protected void onRandomTick() {
		spawnParticles(EnumParticleTypes.FIREWORKS_SPARK, 100);
	}

	@Override
	protected void spawnParticles(EnumParticleTypes type, int intensity) {
		// TODO Auto-generated method stub
		super.spawnParticles(type, intensity);
	}

	@Override
	public CreativeTabs getCreativeTabToDisplayOn() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
