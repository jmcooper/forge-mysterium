package com.cooperplanet.mysteriummod.blocks;

import com.cooperplanet.mysteriummod.Main;
import com.cooperplanet.mysteriummod.init.ModBlocks;
import com.cooperplanet.mysteriummod.init.ModItems;
import com.cooperplanet.mysteriummod.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block implements IHasModel {
	public BlockBase(String name, Material material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}


/*
To make new items:
1. Duplicate item line (i.e. RUBY) in ModItems.java (above)
2. Add item name in src/main/resources....lang/en.us.lang/  
3. Add a new json file in src/main/resources....models.item/
4. Add texture image here
*/