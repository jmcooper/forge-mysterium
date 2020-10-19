package com.cooperplanet.mysteriummod.blocks.mysteriumfurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MysteriumFurnaceContainer extends Container {
	private final MysteriumFurnaceTileEntity tileEntity;
	private int cookTime, totalCookTime, burnTime, currentBurnTime;

	public MysteriumFurnaceContainer(InventoryPlayer player, MysteriumFurnaceTileEntity tileEntity) {
		this.tileEntity = tileEntity;
		IItemHandler handler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		this.addSlotToContainer(new SlotItemHandler(handler, 0, 26, 11)); // Input Slot
		this.addSlotToContainer(new SlotItemHandler(handler, 1, 26, 59)); // Fuel Slot (Was Slot 2)
		this.addSlotToContainer(new SlotItemHandler(handler, 2, 81, 36)); // Output slot (Was Slot 3)

		// this.addSlotToContainer(new SlotItemHandler(handler, 1, 26, 59)); //Unused
		// Input Slot 2

		// Inventory Slots
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}

		for (int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
		}
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener listener = (IContainerListener) this.listeners.get(i);

			if (this.cookTime != this.tileEntity.getField(2))
				listener.sendWindowProperty(this, 2, this.tileEntity.getField(2));
			if (this.burnTime != this.tileEntity.getField(0))
				listener.sendWindowProperty(this, 0, this.tileEntity.getField(0));
			if (this.currentBurnTime != this.tileEntity.getField(1))
				listener.sendWindowProperty(this, 1, this.tileEntity.getField(1));
			if (this.totalCookTime != this.tileEntity.getField(3))
				listener.sendWindowProperty(this, 3, this.tileEntity.getField(3));
		}

		this.cookTime = this.tileEntity.getField(2);
		this.burnTime = this.tileEntity.getField(0);
		this.currentBurnTime = this.tileEntity.getField(1);
		this.totalCookTime = this.tileEntity.getField(3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.tileEntity.setField(id, data);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.tileEntity.isUsableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slotForIndexPassedIn = (Slot) this.inventorySlots.get(index);

		if (slotForIndexPassedIn != null && slotForIndexPassedIn.getHasStack()) {
			ItemStack stackForSlotIndexPassedIn = slotForIndexPassedIn.getStack();
			stack = stackForSlotIndexPassedIn.copy();

			if (index == 2) {
				if (!this.mergeItemStack(stackForSlotIndexPassedIn, 3, 39, true))
					return ItemStack.EMPTY;
				slotForIndexPassedIn.onSlotChange(stackForSlotIndexPassedIn, stack);
			} else if (index != 1 && index != 0) {
				// If the item(s) ARE cookable
				if (!MysteriumFurnaceTileEntity.getCookingResult(stackForSlotIndexPassedIn).isEmpty()) {
					// If possible, Place item in first available furnace slot
					if (!this.mergeItemStack(stackForSlotIndexPassedIn, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
					// If Item is fuel
					else if (MysteriumFurnaceTileEntity.isItemFuel(stackForSlotIndexPassedIn)) {
						// Attempt to put in Fuel slot
						if (!this.mergeItemStack(stackForSlotIndexPassedIn, 2, 2, false))
							return ItemStack.EMPTY;
					}
					// If slot index is an inventory slot, attempt to place in another inventory
					// slot
					else if (index >= 4 && index < 31) {
						if (!this.mergeItemStack(stackForSlotIndexPassedIn, 31, 40, false))
							return ItemStack.EMPTY;
					}
					// If item is player slot, attempt to merge into a player slot
					else if (index >= 31 && index < 40
							&& !this.mergeItemStack(stackForSlotIndexPassedIn, 4, 31, false)) {
						return ItemStack.EMPTY;
					}
				}
			}
			// if space in player's inventory, merge into player's inventory
			else if (!this.mergeItemStack(stackForSlotIndexPassedIn, 4, 40, false)) {
				return ItemStack.EMPTY;
			}

			if (stackForSlotIndexPassedIn.isEmpty()) {
				slotForIndexPassedIn.putStack(ItemStack.EMPTY);
			} else {
				slotForIndexPassedIn.onSlotChanged();

			}
			if (stackForSlotIndexPassedIn.getCount() == stack.getCount())
				return ItemStack.EMPTY;
			slotForIndexPassedIn.onTake(playerIn, stackForSlotIndexPassedIn);
		}
		return stack;
	}
}