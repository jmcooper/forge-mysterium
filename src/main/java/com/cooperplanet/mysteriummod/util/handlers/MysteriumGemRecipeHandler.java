package com.cooperplanet.mysteriummod.util.handlers;

import com.cooperplanet.mysteriummod.recipe.RandomItemRecipe;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

public class MysteriumGemRecipeHandler {
	public static void registerRandomRecipe() {
		 ForgeRegistry<IRecipe> recipeRegistry = (ForgeRegistry<IRecipe>)ForgeRegistries.RECIPES;
		 
         recipeRegistry.register(new RandomItemRecipe());
	}

}
