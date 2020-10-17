package com.cooperplanet.mysteriummod.init;

import java.util.ArrayList;
import java.util.List;

import com.cooperplanet.mysteriummod.blocks.BlockBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {
	public static final List<Block> BLOCKS = new ArrayList<Block>();

	public static final Block MYSTERIUM_BLOCK = new BlockBase("mysterium_block", Material.IRON);

}
