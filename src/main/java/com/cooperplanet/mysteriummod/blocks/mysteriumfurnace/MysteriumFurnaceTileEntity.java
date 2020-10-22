package com.cooperplanet.mysteriummod.blocks.mysteriumfurnace;

import java.util.Random;

import com.cooperplanet.mysteriummod.init.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;


//slots: 0, 1 = inputs, 2 = fuel, 3 = output

public class MysteriumFurnaceTileEntity extends TileEntity implements ITickable
{
	private ItemStackHandler inputHandler = new ItemStackHandler(1);
	private ItemStackHandler outputHandler = new ItemStackHandler(1);
	private ItemStackHandler fuelHandler = new ItemStackHandler(1);
	private String customName;
	private ItemStack outputItem = ItemStack.EMPTY;
	
	private int burnTime;
	private int currentBurnTime;
	private int cookTime;
	private int totalCookTime = 200;


	public static ItemStack getCookingResult(ItemStack input) {
		ItemStack output = ItemStack.EMPTY;
		if (input.getItem() == ModItems.MYSTERIUM_POWDER) { 
			output = new ItemStack(ModItems.MYSTERIUM_GEM);
			NBTTagCompound nbt;
			if (output.hasTagCompound()) 
				nbt = output.getTagCompound();
			else
				nbt = new NBTTagCompound();
			
			int randomItemId = Item.getIdFromItem(Item.REGISTRY.getRandomObject(new Random()));
			nbt.setInteger("randomItemId", randomItemId);
			output.setTagCompound(nbt);
		}
		return output;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
		else return false;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			//if the block at myself isn't myself, allow full access (Block Broken)
			if(world != null && world.getBlockState(pos).getBlock() != getBlockType())
				return (T) new CombinedInvWrapper(inputHandler, fuelHandler, outputHandler);
				
			if (facing == EnumFacing.UP)
				return (T) inputHandler;
			
			if (facing == EnumFacing.DOWN)
				return (T) outputHandler;

			if (facing != null)
				return (T) fuelHandler;
			
			return (T) new CombinedInvWrapper(inputHandler, fuelHandler, outputHandler);			
		}
		return super.getCapability(capability, facing);
	}
	
	public boolean hasCustomName() 
	{
		return this.customName != null && !this.customName.isEmpty();
	}
	
	public void setCustomName(String customName) 
	{
		this.customName = customName;
	}
	
	@Override
	public ITextComponent getDisplayName() 
	{
		return this.hasCustomName() ? new TextComponentString(this.customName) : new TextComponentTranslation("container.mysterium_furnace");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.inputHandler.deserializeNBT(compound.getCompoundTag("InputInventory"));
		this.outputHandler.deserializeNBT(compound.getCompoundTag("OutputInventory"));
		this.fuelHandler.deserializeNBT(compound.getCompoundTag("FuelInventory"));
		
		this.burnTime = compound.getInteger("BurnTime");
		this.cookTime = compound.getInteger("CookTime");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
		this.currentBurnTime = getItemBurnTime((ItemStack)this.fuelHandler.getStackInSlot(0));
		
		if(compound.hasKey("CustomName", 8)) this.setCustomName(compound.getString("CustomName"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		super.writeToNBT(compound);
		compound.setInteger("BurnTime", (short)this.burnTime);
		compound.setInteger("CookTime", (short)this.cookTime);
		compound.setInteger("CookTimeTotal", (short)this.totalCookTime);
		
		compound.setTag("InputInventory", this.inputHandler.serializeNBT());
		compound.setTag("OutputInventory", this.outputHandler.serializeNBT());
		compound.setTag("FuelInventory", this.fuelHandler.serializeNBT());
		
		if(this.hasCustomName()) compound.setString("CustomName", this.customName);
		return compound;
	}
	
	public boolean isBurning() 
	{
		return this.burnTime > 0;
	}
	
	@SideOnly(Side.CLIENT)
	public static boolean isBurning(MysteriumFurnaceTileEntity te) 
	{
		return te.getField(0) > 0;
	}
	
	public void update() 
	{	
		ItemStack[] inputs = new ItemStack[] {inputHandler.getStackInSlot(0)};
		ItemStack fuel = this.fuelHandler.getStackInSlot(0);

		if(this.isBurning())
		{
			--this.burnTime;
			MysteriumFurnaceBlock.setState(true, world, pos);
		}
		
		//If burning or fuel is not empty and input is not empty
		if(this.isBurning() || !fuel.isEmpty() && !this.inputHandler.getStackInSlot(0).isEmpty())
		{
			//If it's not burning and input item is smeltable
			if(!this.isBurning() && this.canInputBeSmelted())
			{
				// set burntime to fuel's burn time since we're not burning currently
				this.burnTime = getItemBurnTime(fuel);
				this.currentBurnTime = burnTime;
				
				
				if(this.isBurning() && !fuel.isEmpty())
				{
					fuel.shrink(1);
				}
			}
		}
		
		//If we've been cooking for a bit already (and we're burning and input can be smelted)
		if(this.isBurning() && this.canInputBeSmelted() && cookTime > 0)
		{
			cookTime++;
			
			//if cook time has been met, add output and consume input
			if(cookTime == totalCookTime)
			{
				//grow the stack or insert a new item
				if(outputHandler.getStackInSlot(0).getCount() > 0)
				{
					outputHandler.getStackInSlot(0).grow(1);
				}
				else
				{
					outputHandler.insertItem(0, outputItem, false);
				}
				
				//consume an input
				inputs[0].shrink(1);
				
				outputItem = ItemStack.EMPTY;
				cookTime = 0;
				return;
			}
		}
		else
		{
			// We just inserted new, good fuel (and item can be smelted)
			if(this.canInputBeSmelted() && this.isBurning())
			{
				ItemStack output = getCookingResult(inputs[0]);
				if(!output.isEmpty())
				{
					outputItem = output;
					cookTime++;
					inputHandler.setStackInSlot(0, inputs[0]);
				}
			}
		}
	}
	
	private boolean canInputBeSmelted() 
	{
		//if input is empty
		if(((ItemStack)this.inputHandler.getStackInSlot(0)).isEmpty()) 
			return false;
		
		//if the output slot is already occupied (only one gem can be stacked at a time
		if (outputHandler.getStackInSlot(0).getCount() > 0)
			return false;
		
		ItemStack result = getCookingResult((ItemStack)this.inputHandler.getStackInSlot(0));
		//if input can be smelted
		if(result.isEmpty()) 
			return false;
		else
		{
			//return true if the output slot is empty or the item in the output slot matches smelting output for current input
			ItemStack output = (ItemStack)this.outputHandler.getStackInSlot(0);
			if(output.isEmpty()) return true;
			if(!output.isItemEqual(result)) return false;
			int res = output.getCount() + result.getCount();
			return res <= 64 && res <= output.getMaxStackSize();
		}
	}
	
	public static int getItemBurnTime(ItemStack fuel) 
	{
		if(fuel.isEmpty()) 
			return 0;
		else 
		{
			Item item = fuel.getItem();

			if (item == ModItems.ENERGIZED_COAL) return 1600;

			return 0;
		}
	}
		
	public static boolean isItemFuel(ItemStack fuel)
	{
		return getItemBurnTime(fuel) > 0;
	}
	
	public boolean isUsableByPlayer(EntityPlayer player) 
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}

	public int getField(int id) 
	{
		switch(id) 
		{
		case 0:
			return this.burnTime;
		case 1:
			return this.currentBurnTime;
		case 2:
			return this.cookTime;
		case 3:
			return this.totalCookTime;
		default:
			return 0;
		}
	}

	public void setField(int id, int value) 
	{
		switch(id) 
		{
		case 0:
			this.burnTime = value;
			break;
		case 1:
			this.currentBurnTime = value;
			break;
		case 2:
			this.cookTime = value;
			break;
		case 3:
			this.totalCookTime = value;
		}
	}
}