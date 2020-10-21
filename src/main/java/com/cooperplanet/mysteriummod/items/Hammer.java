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
		this.setDamage(itemStack, this.getDamage(itemStack) + 1);
		return super.getContainerItem(itemStack);
	}
	
}
