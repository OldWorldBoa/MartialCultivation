package com.djb.martial_cultivation.tools.combat;

import net.minecraft.item.ItemTier;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.SwordItem;

public class BasicStaff extends SwordItem {
	private static Properties getBasicStaffProperties() {
		Properties props = new Properties();
		props.group(ItemGroup.COMBAT);
		
		return props;
	}

	public BasicStaff() {
		super(ItemTier.WOOD, 50, 50, BasicStaff.getBasicStaffProperties());
	}
}
