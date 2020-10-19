package com.cooperplanet.mysteriummod.util.handlers;

import com.cooperplanet.mysteriummod.blocks.mysteriumfurnace.MysteriumFurnaceTileEntity;
import com.cooperplanet.mysteriummod.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(MysteriumFurnaceTileEntity.class, new ResourceLocation(Reference.MOD_ID + ":mysterium_furnace"));

	}
}
