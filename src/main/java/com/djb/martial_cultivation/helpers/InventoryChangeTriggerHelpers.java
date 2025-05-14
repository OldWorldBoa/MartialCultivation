package com.djb.martial_cultivation.helpers;

import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;

public class InventoryChangeTriggerHelpers {
    public static InventoryChangeTrigger.Instance hasItem(IItemProvider item) {
        return hasItem(ItemPredicate.Builder.create().item(item).build());
    }

    /**
     * Creates a new {@link InventoryChangeTrigger} that checks for a player having an item within the given tag.
     */
    public static InventoryChangeTrigger.Instance hasItem(ITag<Item> tag) {
        return hasItem(ItemPredicate.Builder.create().tag(tag).build());
    }

    /**
     * Creates a new {@link InventoryChangeTrigger} that checks for a player having a certain item.
     */
    public static InventoryChangeTrigger.Instance hasItem(ItemPredicate... predicate) {
        return new InventoryChangeTrigger.Instance(EntityPredicate.AndPredicate.ANY_AND, MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, predicate);
    }
}
