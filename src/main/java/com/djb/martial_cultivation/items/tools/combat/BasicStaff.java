package com.djb.martial_cultivation.items.tools.combat;

import com.djb.martial_cultivation.data.ModTags;
import com.djb.martial_cultivation.helpers.InventoryChangeTriggerHelpers;
import com.djb.martial_cultivation.items.ModItems;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.*;

import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.common.Tags;

public class BasicStaff extends SwordItem {
	public static final String name = "basic_staff";

	public static final ShapedRecipeBuilder getRecipeBuilder() {
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
