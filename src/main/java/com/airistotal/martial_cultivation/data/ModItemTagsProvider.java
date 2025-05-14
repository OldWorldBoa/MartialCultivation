package com.airistotal.martial_cultivation.data;

import com.airistotal.martial_cultivation.Main;
import com.airistotal.martial_cultivation.items.ModItems;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, Main.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        copy(ModTags.Blocks.CONDENSED_QI, ModTags.Items.CONDENSED_QI);

        getOrCreateBuilder(ModTags.Items.BASIC_STAFF).add(ModItems.BASIC_STAFF.get());
        getOrCreateBuilder(ModTags.Items.QI_ESSENCE).add(ModItems.QI_ESSENCE.get());
        getOrCreateBuilder(ModTags.Items.CULTIVATION_INTRO_BOOK).add(ModItems.CULTIVATION_INTRO_BOOK.get());
    }
}
