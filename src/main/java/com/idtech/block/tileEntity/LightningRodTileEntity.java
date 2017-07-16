package com.idtech.block.tileEntity;

import java.util.List;

import com.idtech.BaseMod;
import com.idtech.block.QuickBlock;
import com.idtech.enchantment.EnchantmentMod;
import com.idtech.item.ItemMod;
import com.idtech.item.QuickItem;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class LightningRodTileEntity extends TileEntity implements IInventory, ITickable {
	
	private Item[] valid_items = {QuickItem.getItem("Battery"), ItemMod.NVG};
	
	private ItemStack[] inventory;
    private String customName;
    private int updates, charge_timer;
    static int MIN_UPDATES = 50, EXTRA_UPDATES = 50, FLAT_RANGE = 1, VERT_RANGE = 6, CHARGE_MAX = 1000;
    static double ZAP_RANGE = Math.pow(3, 2);
    
    public LightningRodTileEntity() {
    	this.inventory = new ItemStack[this.getSizeInventory()];
    	this.updates = 300;
    	this.charge_timer = CHARGE_MAX;
    	for (int i = 0; i < this.getSizeInventory(); i++)
    		this.inventory[i] = ItemStack.EMPTY;
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.hasCustomName() ? this.customName : "container.examplemod_lightning_rod_tile_entity";
	}
	
	public void setCustomName(String customName) {
        this.customName = customName;
    }

	@Override
	public boolean hasCustomName() {
		// TODO Auto-generated method stub
		return this.customName != null && !this.customName.equals("");
	}
	
	@Override
    public ITextComponent getDisplayName() {
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
    }

	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if (index < 0 || index >= this.getSizeInventory())
	        return null;
	    return this.inventory[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (this.getStackInSlot(index) != ItemStack.EMPTY) {
	        ItemStack itemstack;

	        if (this.getStackInSlot(index).getCount() <= count) {
	            itemstack = this.getStackInSlot(index);
	            this.setInventorySlotContents(index, ItemStack.EMPTY);
	            this.markDirty();
	            return itemstack;
	        } else {
	            itemstack = this.getStackInSlot(index).splitStack(count);

	            if (this.getStackInSlot(index).getCount() <= 0) {
	                this.setInventorySlotContents(index, ItemStack.EMPTY);
	            } else {
	                //Just to show that changes happened
	                this.setInventorySlotContents(index, this.getStackInSlot(index));
	            }

	            this.markDirty();
	            return itemstack;
	        }
	    } else {
	        return ItemStack.EMPTY;
	    }
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = getStackInSlot(index);
		setInventorySlotContents(index, ItemStack.EMPTY);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index < 0 || index >= this.getSizeInventory())
	        return;

	    if (stack != ItemStack.EMPTY && stack.getCount() > this.getInventoryStackLimit())
	    	stack.setCount(this.getInventoryStackLimit());
	        
	    if (stack != null && stack.getCount() == 0)
	        stack = ItemStack.EMPTY;

	    this.inventory[index] = stack;
	    this.markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) {
			//return stack.getItem() == QuickItem.getItem("Battery");
			for (Item i : valid_items) {
				if (stack.getItem() == i) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.getSizeInventory(); i++)
	        this.setInventorySlotContents(i, ItemStack.EMPTY);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
	    super.writeToNBT(nbt);

	    NBTTagList list = new NBTTagList();
	    for (int i = 0; i < this.getSizeInventory(); ++i) {
	        if (this.getStackInSlot(i) != ItemStack.EMPTY) {
	            NBTTagCompound stackTag = new NBTTagCompound();
	            stackTag.setByte("Slot", (byte) i);
	            this.getStackInSlot(i).writeToNBT(stackTag);
	            list.appendTag(stackTag);
	        }
	    }
	    nbt.setTag("Items", list);
	    nbt.setInteger("Updates", this.updates);
	    nbt.setInteger("ChargeTimer", this.charge_timer);
	    //nbt.setBoolean("Previous", this.previous);

	    if (this.hasCustomName()) {
	        nbt.setString("CustomName", this.getName());
	    }
	    return nbt;
	}


	@Override
	public void readFromNBT(NBTTagCompound nbt) {
	    super.readFromNBT(nbt);

	    NBTTagList list = nbt.getTagList("Items", 10);
	    for (int i = 0; i < list.tagCount(); ++i) {
	        NBTTagCompound stackTag = list.getCompoundTagAt(i);
	        int slot = stackTag.getByte("Slot") & 255;
	        this.setInventorySlotContents(slot, new ItemStack(stackTag));
	    }

	    if (nbt.hasKey("CustomName", 8)) {
	        this.setCustomName(nbt.getString("CustomName"));
	    }
	    if (nbt.hasKey("Updates")) {
	    	this.updates = nbt.getInteger("Updates");
	    }
	    if (nbt.hasKey("ChargeTimer")) {
	    	this.charge_timer = nbt.getInteger("ChargeTimer");
	    }
	    /*
	    if (nbt.hasKey("Ticks")) {
	    	this.ticks = nbt.getInteger("Ticks");
	    }
	    if (nbt.hasKey("Previous")) {
	    	this.previous = nbt.getBoolean("Previous");
	    }
	    */
	}

	@Override
	public void update() {
		if (!world.isRemote && can_be_struck() && --updates < 0) {
			updates = this.world.rand.nextInt(EXTRA_UPDATES) + MIN_UPDATES;
			BlockPos strike_pos = pick_strike();
			if (strike_pos != null) {
				this.world.addWeatherEffect(new EntityLightningBolt(world, strike_pos.getX(), strike_pos.getY(), strike_pos.getZ(), false));
				if (strike_pos.equals(pos)) {
					//Direct hit
					if (!this.inventory[0].isEmpty()/* && this.inventory[0].getItem() == QuickItem.getItem("Battery")*/) {
						//Inventory has battery
						if (this.inventory[0].isItemDamaged())
							this.inventory[0].setItemDamage(0);
						else {
							if (EnchantmentHelper.getEnchantmentLevel(EnchantmentMod.SUPERCHARGED, this.inventory[0]) > 0) {
								this.inventory[0].addEnchantment(EnchantmentMod.SUPERCHARGED, 1);
							}
						}
					}
					//Change to powered
					//world.setBlockState(pos, QuickBlock.getBlock("Lightning Rod-Powered").getDefaultState());
					power();
				}
			}
			else {
				updates /= 2;
			}
		}
		
		if (is_powered()) {
			//Scan radius for targets, apply zap damage
			List<Entity> entities = world.getLoadedEntityList();
			for (Entity e: entities) {
				if (e instanceof EntityLivingBase && e.getDistanceSq(pos) <= ZAP_RANGE) {
					e.attackEntityFrom(BaseMod.ZAP, 1.0F);
					//Play sound effect here
					//e.playSound(SoundEvents.ENTITY_LIGHTNING_THUNDER, 0.5f, world.rand.nextFloat() * 0.4F + 0.8F);
					
				}
			}
		}
		
		if (--charge_timer < 0) {
			charge_timer = CHARGE_MAX;
			//Change to unpowered
			//world.setBlockState(pos, QuickBlock.getBlock("Lightning Rod").getDefaultState());
			unpower();
		}
	}
	
	private boolean is_powered() {
		return world.getBlockState(pos) == QuickBlock.getBlock("Lightning Rod-Powered").getDefaultState();
	}
	
	private void power() {
		if (world.getBlockState(pos) == QuickBlock.getBlock("Lightning Rod").getDefaultState()) {
			ItemStack item = inventory[0];
			inventory[0] = ItemStack.EMPTY;
			world.setBlockState(pos, QuickBlock.getBlock("Lightning Rod-Powered").getDefaultState());
			((LightningRodTileEntity) world.getTileEntity(pos)).inventory[0] = item;
		}
	}
	
	private void unpower() {
		if (world.getBlockState(pos) == QuickBlock.getBlock("Lightning Rod-Powered").getDefaultState()) {
			ItemStack item = inventory[0];
			inventory[0] = ItemStack.EMPTY;
			world.setBlockState(pos, QuickBlock.getBlock("Lightning Rod").getDefaultState());
			((LightningRodTileEntity) world.getTileEntity(pos)).inventory[0] = item;
		}
	}
	
	private boolean exposed_to_air() {
		for (int i = 1; i < 255 - pos.getY(); i++) {
			if (world.getBlockState(pos.up(i)).getBlock() != Blocks.AIR)
				return false;
		}
		return true;
	}
	
	private boolean is_raining() {
		return world.isRainingAt(pos) || world.isRainingAt(pos.up());
	}
	
	private boolean can_be_struck() {
		return exposed_to_air() && is_raining();
	}
	
	private BlockPos get_highest_block(BlockPos p) {
		BlockPos highest = new BlockPos(p.getX(), 255, p.getZ());
		while (world.getBlockState(highest).getBlock() == Blocks.AIR && highest.getY() > 0)
			highest = highest.down();
		return highest;		
	}
	
	private BlockPos pick_strike() {
		int x_off = world.rand.nextInt(FLAT_RANGE * 2) - FLAT_RANGE;
		int z_off = world.rand.nextInt(FLAT_RANGE * 2) - FLAT_RANGE;
		BlockPos p = new BlockPos(pos.getX() + x_off, pos.getY(), pos.getZ() + z_off);
		BlockPos highest = get_highest_block(p);
		if ((Math.abs(highest.getY() - pos.getY())) <= VERT_RANGE) {
			return highest;
		}
		else {
			return null;
		}
		
	}

}
