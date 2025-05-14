package com.djb.martial_cultivation.data;

import com.djb.martial_cultivation.Main;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class ModTags {
    public static final class Blocks {
        public static final ITag.INamedTag<Block> CONDENSED_QI = forge("cultivation/condensed_qi");

        private static ITag.INamedTag<Block> forge(String path) {
            return BlockTags.makeWrapperTag(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Block> mod(String path) {
            return BlockTags.makeWrapperTag(new ResourceLocation(Main.MOD_ID, path).toString());
        }
    }

    public static final class Items {
        public static final ITag.INamedTag<Item> CONDENSED_QI = forge("cultivation/condensed_qi");
        public static final ITag.INamedTag<Item> STORAGE_BLOCKS_CONDENSED_QI = forge("storage_blocks/condensed_qi");

        public static final ITag.INamedTag<Item> BASIC_STAFF = forge("weapons/basic_staff");
        public static final ITag.INamedTag<Item> QI_ESSENCE = forge("cultivation/qi_essence");
        public static final ITag.INamedTag<Item> CULTIVATION_INTRO_BOOK = forge("cultivation/cultivation_intro_book");

        private static ITag.INamedTag<Item> forge(String path) {
            return ItemTags.makeWrapperTag(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Item> mod(String path) {
            return ItemTags.makeWrapperTag(new ResourceLocation(Main.MOD_ID, path).toString());
        }
    }
}
