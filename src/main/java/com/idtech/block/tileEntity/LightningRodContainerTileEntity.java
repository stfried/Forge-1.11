package com.idtech.block.tileEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LightningRodContainerTileEntity extends Container {
	
	private LightningRodTileEntity te;
	
	public LightningRodContainerTileEntity(IInventory playerInv, LightningRodTileEntity te) {
		this.te = te;
		
		//Tile Entity, Slot 0
		this.addSlotToContainer(new SingleItemSlot(te, 0, 62 + 1 * 18, 17 + 0 * 18));
		
		// Player Inventory, Slot 9-35, Slot IDs 9-35
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        // Player Inventory, Slot 0-8, Slot IDs 0-8
        for (int x = 0; x < 9; ++x) {
            this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
        }
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.te.isUsableByPlayer(playerIn);
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
        ItemStack previous = ItemStack.EMPTY;
        Slot slot = (Slot) this.inventorySlots.get(fromSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack current = slot.getStack();
            previous = current.copy();

            // [...] Custom behaviour
            if (fromSlot == 0) {
                // From TE Inventory to Player Inventory
                if (!this.mergeItemStack(current, 1, 37, true))
                    return ItemStack.EMPTY;
            } else {
                // From Player Inventory to TE Inventory
            	//Check if stack is valid in slot, if not, then don't attempt to merge
                if (te.isItemValidForSlot(0, current) && !this.mergeItemStack(current, 0, 1, false))
                    return ItemStack.EMPTY;
            }

            if (current.getCount() == 0)
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();

            if (current.getCount() == previous.getCount())
                return ItemStack.EMPTY;
            slot.onSlotChanged();
        }
        return previous;
    }
    
    @Override
    protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean useEndIndex) {
        boolean success = false;
        int index = startIndex;

        if (useEndIndex)
            index = endIndex - 1;

        Slot slot;
        ItemStack stackinslot;

        if (stack.isStackable()) {
            while (stack.getCount() > 0 && (!useEndIndex && index < endIndex || useEndIndex && index >= startIndex)) {
                slot = (Slot) this.inventorySlots.get(index);
                stackinslot = slot.getStack();

                if (stackinslot != null && stackinslot.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getMetadata() == stackinslot.getMetadata()) && ItemStack.areItemStackTagsEqual(stack, stackinslot)) {
                    int l = stackinslot.getCount() + stack.getCount();
                    int maxsize = Math.min(stack.getMaxStackSize(), slot.getItemStackLimit(stack));

                    if (l <= maxsize) {
                        stack.setCount(0);
                        stackinslot.setCount(l);
                        slot.onSlotChanged();
                        success = true;
                    } else if (stackinslot.getCount() < maxsize) {
                    	stack.setCount(stack.getCount() - stackinslot.getCount());
                        stackinslot.setCount(stack.getMaxStackSize());
                        slot.onSlotChanged();
                        success = true;
                    }
                }

                if (useEndIndex) {
                    --index;
                } else {
                    ++index;
                }
            }
        }

        if (stack.getCount() > 0) {
            if (useEndIndex) {
                index = endIndex - 1;
            } else {
                index = startIndex;
            }

            while (!useEndIndex && index < endIndex || useEndIndex && index >= startIndex && stack.getCount() > 0) {
                slot = (Slot) this.inventorySlots.get(index);
                stackinslot = slot.getStack();

                // Forge: Make sure to respect isItemValid in the slot.
                if (stackinslot == ItemStack.EMPTY && slot.isItemValid(stack)) {
                    if (stack.getCount() < slot.getItemStackLimit(stack)) {
                        slot.putStack(stack.copy());
                        stack.setCount(0);
                        success = true;
                        break;
                    } else {
                        ItemStack newstack = stack.copy();
                        newstack.setCount(slot.getItemStackLimit(stack));
                        slot.putStack(newstack);
                        stack.setCount(stack.getCount() - slot.getItemStackLimit(stack));
                        success = true;
                    }
                }

                if (useEndIndex) {
                    --index;
                } else {
                    ++index;
                }
            }
        }

        return success;
    }

	 public class SingleItemSlot extends Slot {

	        public SingleItemSlot(IInventory inventory, int index, int xPosition, int yPosition) {
	            super(inventory, index, xPosition, yPosition);
	        }

	        @Override
	        public int getSlotStackLimit() {
	            return 1;
	        }
	    }
	
}
