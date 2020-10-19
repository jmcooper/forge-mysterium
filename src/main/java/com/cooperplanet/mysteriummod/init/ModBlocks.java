package com.cooperplanet.mysteriummod.init;

import java.util.ArrayList;
import java.util.List;

import com.cooperplanet.mysteriummod.blocks.MysteriumBlock;
import com.cooperplanet.mysteriummod.blocks.mysteriumfurnace.MysteriumFurnaceBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {
	public static final List<Block> BLOCKS = new ArrayList<Block>();

	public static final Block MYSTERIUM_BLOCK = new MysteriumBlock("mysterium_block");
	public static final Block MYSTERIUM_FURNACE = new MysteriumFurnaceBlock("mysterium_furnace");

}
