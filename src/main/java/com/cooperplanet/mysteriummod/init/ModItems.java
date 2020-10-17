package com.cooperplanet.mysteriummod.init;

import java.util.ArrayList;
import java.util.List;

import com.cooperplanet.mysteriummod.items.ItemBase;

import net.minecraft.item.Item;

public class ModItems {
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	//public static final Item RUBY = new ItemBase("ruby");

//	To make new items:
//	1. Duplicate item line (i.e. RUBY) in ModItems.java
//	2. Add item name in src/main/resources....lang/en.us.lang/  
//	3. Add a new json file in src/main/resources....models.item/
//	4. Add 16x16 png texture image in src/main/resources.../textures.items/
}
