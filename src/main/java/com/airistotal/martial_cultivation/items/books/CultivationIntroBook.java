package com.airistotal.martial_cultivation.items.books;

import com.airistotal.martial_cultivation.capabilities.Cultivator;
import com.airistotal.martial_cultivation.data.ModTags;
import com.airistotal.martial_cultivation.gui.screen.CultivationIntroBookScreen;
import com.airistotal.martial_cultivation.helpers.InventoryChangeTriggerHelpers;
import com.airistotal.martial_cultivation.items.ModItems;
import com.airistotal.martial_cultivation.capabilities.skills.base.BasicQiEnhanceSkill;
import net.minecraft.client.Minecraft;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BookItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class CultivationIntroBook extends BookItem {
    public static final String name = "cultivation_intro_book";

    public static ShapelessRecipeBuilder getRecipeBuilder() {
        return ShapelessRecipeBuilder
                .shapelessRecipe(ModItems.CULTIVATION_INTRO_BOOK.get())
                .addIngredient(ModTags.Items.QI_ESSENCE)
                .addIngredient(Items.BOOK)
                .addCriterion("has_item", InventoryChangeTriggerHelpers.hasItem(Items.BOOK.getItem()));
    }

    public CultivationIntroBook() {
        super(new Properties().group(ItemGroup.MISC));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        Minecraft.getInstance().displayGuiScreen(new CultivationIntroBookScreen());

        Cultivator cultivator = Cultivator.getCultivatorFrom(playerIn);
        cultivator.setEnabled(true);
        cultivator.learnSkill(new BasicQiEnhanceSkill());

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}
