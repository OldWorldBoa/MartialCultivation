package com.airistotal.martial_cultivation.items.tools.combat;

import com.airistotal.martial_cultivation.helpers.InventoryChangeTriggerHelpers;
import com.airistotal.martial_cultivation.items.ModItems;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;

public class BasicStaff extends SwordItem {
	public static final String name = "basic_staff";

	public static ShapedRecipeBuilder getRecipeBuilder() {
		return ShapedRecipeBuilder
				.shapedRecipe(ModItems.BASIC_STAFF.get())
				.key('/', Items.STICK)
				.patternLine("  /")
				.patternLine(" / ")
				.patternLine("/  ")
				.addCriterion("has_item", InventoryChangeTriggerHelpers.hasItem(Items.STICK.asItem()));
	}

	private static Properties getBasicStaffProperties() {
		return new Properties().group(ItemGroup.COMBAT);
	}

	public BasicStaff() {
		super(ItemTier.WOOD, 50, 50, BasicStaff.getBasicStaffProperties());
	}
}
