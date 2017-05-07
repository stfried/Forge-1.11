package com.idtech.block;

import java.util.Random;

import com.idtech.BaseMod;
import com.idtech.block.tileEntity.LightningRodTileEntity;
import com.idtech.network.ModGuiHandler;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLightningRod extends QuickBlock implements ITileEntityProvider {
	
	public static final AxisAlignedBB PILLAR_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);
	
	{
	   name = "Lightning Rod";
	   tab = BaseMod.blockTab;
	   this.setHardness(1.2f);
	   this.isBlockContainer = true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new LightningRodTileEntity();
	}
	
	@Override
	 public void breakBlock(World world, BlockPos pos, IBlockState blockstate) {
	     LightningRodTileEntity te = (LightningRodTileEntity) world.getTileEntity(pos);
	     InventoryHelper.dropInventoryItems(world, pos, te);
	     super.breakBlock(world, pos, blockstate);
	     world.removeTileEntity(pos);
	 }
	
	@Override
	 public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
	     if (stack.hasDisplayName()) {
	         ((LightningRodTileEntity) worldIn.getTileEntity(pos)).setCustomName(stack.getDisplayName());
	     }
	 }

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			playerIn.openGui(BaseMod.instance, ModGuiHandler.LIGHTNING_ROD_GUI, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(QuickBlock.getBlockFromName("Lightning Rod"));
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return PILLAR_AABB;
    }

	@Override
	public void createJSONFiles() {
		//Do nothing
	}
	
    public boolean isFullyOpaque(IBlockState state)
    {
        return false;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return false;
    }
    
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        if (side != EnumFacing.UP && side != EnumFacing.DOWN && !super.shouldSideBeRendered(blockState, blockAccess, pos, side))
        {
            return false;
        }

        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
	
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP);
    }
    
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
    	if (worldIn.getBlockState(pos.down()).getBlock() == Blocks.AIR)
        {
    		this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }
	
}
