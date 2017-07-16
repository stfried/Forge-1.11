package com.idtech.block;

import java.util.Random;

import com.idtech.BaseMod;

import net.minecraft.block.BlockLever;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRemoteReceiver extends QuickBlock {
	
    public static final PropertyBool POWERED = PropertyBool.create("powered");

	{
		 name = "Remote Receiver";
	     texture = "receiver";
	     tab = BaseMod.blockTab;
	     this.setTickRandomly(true);
	}
	
		@SideOnly(Side.CLIENT)
	    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	    {
			if (worldIn.getBlockState(pos).getValue(POWERED)) {
				this.spawnParticles(worldIn, pos);
			}
	    }
	
	public boolean onRemoteActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            state = state.cycleProperty(POWERED);
            worldIn.setBlockState(pos, state, 3);
            float f = ((Boolean)state.getValue(POWERED)).booleanValue() ? 0.6F : 0.5F;
            worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, f);
            worldIn.notifyNeighborsOfStateChange(pos, this, false);
            return true;
        }
    }
	
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (((Boolean)state.getValue(POWERED)).booleanValue())
        {
            worldIn.notifyNeighborsOfStateChange(pos, this, false);
        }

        super.breakBlock(worldIn, pos, state);
    }

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		 return ((Boolean)blockState.getValue(POWERED)).booleanValue() ? 15 : 0;
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		 return ((Boolean)blockState.getValue(POWERED)).booleanValue() ? 15 : 0;
	}
	
    @Override
	public void createJSONFiles() {
		// TODO Auto-generated method stub
    	createModelJSON();
		//createBlockStatesJSON();
		createItemJSON();
	}

	public boolean canProvidePower(IBlockState state)
    {
        return true;
    }
    
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(POWERED, Boolean.valueOf((meta & 8) > 0));
    }
	
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;

        if (((Boolean)state.getValue(POWERED)).booleanValue())
        {
            i |= 8;
        }

        return i;
    }
    
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {POWERED});
    }
    
    private void spawnParticles(World worldIn, BlockPos pos)
    {
        Random random = worldIn.rand;
        double d0 = 0.0625D;

        for (int i = 0; i < 6; ++i)
        {
            double d1 = (double)((float)pos.getX() + random.nextFloat());
            double d2 = (double)((float)pos.getY() + random.nextFloat());
            double d3 = (double)((float)pos.getZ() + random.nextFloat());

            if (i == 0 && !worldIn.getBlockState(pos.up()).isOpaqueCube())
            {
                d2 = (double)pos.getY() + 0.0625D + 1.0D;
            }

            if (i == 1 && !worldIn.getBlockState(pos.down()).isOpaqueCube())
            {
                d2 = (double)pos.getY() - 0.0625D;
            }

            if (i == 2 && !worldIn.getBlockState(pos.south()).isOpaqueCube())
            {
                d3 = (double)pos.getZ() + 0.0625D + 1.0D;
            }

            if (i == 3 && !worldIn.getBlockState(pos.north()).isOpaqueCube())
            {
                d3 = (double)pos.getZ() - 0.0625D;
            }

            if (i == 4 && !worldIn.getBlockState(pos.east()).isOpaqueCube())
            {
                d1 = (double)pos.getX() + 0.0625D + 1.0D;
            }

            if (i == 5 && !worldIn.getBlockState(pos.west()).isOpaqueCube())
            {
                d1 = (double)pos.getX() - 0.0625D;
            }

            if (d1 < (double)pos.getX() || d1 > (double)(pos.getX() + 1) || d2 < 0.0D || d2 > (double)(pos.getY() + 1) || d3 < (double)pos.getZ() || d3 > (double)(pos.getZ() + 1))
            {
                worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d1, d2, d3, 0.0D, 0.0D, 0.0D, new int[0]);
            }
        }
    }
    
}
