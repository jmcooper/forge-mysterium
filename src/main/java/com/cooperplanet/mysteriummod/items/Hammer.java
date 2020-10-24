package com.cooperplanet.mysteriummod.items;

import net.minecraft.item.ItemStack;

public class Hammer extends ItemBase {

	public Hammer() {
		super("hammer");
		setMaxDamage(20);
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		ItemStack damagedStack = itemStack.copy();
		damagedStack.setItemDamage(itemStack.getItemDamage() + 1);
		return damagedStack;
	}
	
}
