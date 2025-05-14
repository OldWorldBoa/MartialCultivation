package com.airistotal.martial_cultivation.blocks.materials;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class CondensedQi extends Block {
	public static final String name = "condensed_qi";
	
	private static Properties getProperties() {
		return Properties.create(Material.AIR).harvestLevel(1);
	}

	public CondensedQi() {
		super(CondensedQi.getProperties());
	}
}
