package com.airistotal.martial_cultivation.data;

import com.airistotal.martial_cultivation.Main;
import com.airistotal.martial_cultivation.blocks.ModBlocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, Main.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        getOrCreateBuilder(ModTags.Blocks.CONDENSED_QI).add(ModBlocks.CONDENSED_QI.get());
    }
}
