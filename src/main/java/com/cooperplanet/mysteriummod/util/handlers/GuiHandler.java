package com.cooperplanet.mysteriummod.util.handlers;

import java.io.Console;

import com.cooperplanet.mysteriummod.blocks.mysteriumfurnace.MysteriumFurnaceContainer;
import com.cooperplanet.mysteriummod.blocks.mysteriumfurnace.MysteriumFurnaceGui;
import com.cooperplanet.mysteriummod.blocks.mysteriumfurnace.MysteriumFurnaceTileEntity;
import com.cooperplanet.mysteriummod.util.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		MysteriumFurnaceTileEntity tileEntity = (MysteriumFurnaceTileEntity)world.getTileEntity(new BlockPos(x,y,z));
		if(ID == Reference.GUI_MYSTERIUM_FURNACE) return new MysteriumFurnaceContainer(player.inventory, tileEntity);
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if(ID == Reference.GUI_MYSTERIUM_FURNACE) return new MysteriumFurnaceGui(player.inventory, (MysteriumFurnaceTileEntity)world.getTileEntity(new BlockPos(x,y,z)));
		return null;
	}
}