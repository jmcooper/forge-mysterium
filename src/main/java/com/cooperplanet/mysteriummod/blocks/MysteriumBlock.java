package com.cooperplanet.mysteriummod.blocks;

import java.util.Random;

import com.cooperplanet.mysteriummod.init.ModItems;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class MysteriumBlock extends BlockBase {

	public MysteriumBlock(String name) {
		super(name, Material.IRON);
//		setSoundType(SoundType.GLASS);
		setHardness(5.0F);
//		setResistance(15.0F);
		setHarvestLevel("pickaxe", 1);
		
	}
	
	@Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		drops.clear();
		Item geode = Item.REGISTRY.getObject(ModItems.MYSTERIUM_GEODE.getRegistryName());
		drops.add(new ItemStack(geode));
    }
}
