package com.djb.martial_cultivation.items.materials;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class QiEssence extends Item {
	public static final String name = "qi_essence";
	
	private static Properties getProperties() {
		return new Properties().group(ItemGroup.MATERIALS);
	}

	public QiEssence() {
		super(QiEssence.getProperties());
	}
}
