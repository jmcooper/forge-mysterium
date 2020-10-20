package com.cooperplanet.mysteriummod.recipe;

import java.util.Random;

import com.cooperplanet.mysteriummod.init.ModItems;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RandomItemRecipe implements IRecipe {
	
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		int airStackCount = 0;
		int mysteriumGemStackCount = 0;
				
		for (int i = 0; i < 9; i++) {
			String itemName = inv.getStackInSlot(i).getItem().getUnlocalizedName();
			if (itemName.equals("tile.air")) airStackCount++;
			if (itemName.equals("item.mysterium_gem")) mysteriumGemStackCount++;
		}

		return airStackCount == 8 && mysteriumGemStackCount == 1;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return new ItemStack(Item.REGISTRY.getRandomObject(new Random()));
	}

	@Override
	public boolean canFit(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(Item.REGISTRY.getRandomObject(new Random()));
	}

	@Override
	public IRecipe setRegistryName(ResourceLocation name) {
		return null;
	}

	@Override
	public ResourceLocation getRegistryName() {
		return new ResourceLocation("mm:random-item-recipe");
	}

	@Override
	public Class<IRecipe> getRegistryType() {
		return null;
	}


}
