package com.djb.martial_cultivation.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ReadBookScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Optional;

public class CultivationIntroBookScreen extends Screen {
    public CultivationIntroBookScreen() {
        this(new StringTextComponent("Cultivation Intro Book"));
    }

    protected CultivationIntroBookScreen(ITextComponent titleIn) {
        super(titleIn);
    }

    private static CompoundNBT getIntroPages() {
        CompoundNBT pages = new CompoundNBT();

        // Replace StringNBT with TranslationTextComponent to add formatting.
        ListNBT pageList = new ListNBT();
        pageList.add(0, getPageContent("one"));
        pageList.add(1, getPageContent("two"));
        pageList.add(2, getPageContent("three"));

        pages.put("pages", pageList);
        pages.put("title", getBookTitle());

        return pages;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    

    private static INBT getBookTitle() {
        TranslationTextComponent title = new TranslationTextComponent("book.cultivation_intro_book.title");
        title.mergeStyle(TextFormatting.DARK_RED);

        return StringNBT.valueOf(title.toString());
    }

    private static INBT getPageContent(String pageNumber) {
        TranslationTextComponent title = new TranslationTextComponent("book.cultivation_intro_book.page_" + pageNumber);

        return StringNBT.valueOf(title.toString());
    }
}
