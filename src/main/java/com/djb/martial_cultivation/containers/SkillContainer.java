package com.djb.martial_cultivation.containers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class SkillContainer extends Container {
    public static final String name = "skill_container";

    private PlayerEntity player;
    private PlayerInventory inventory;
    private IItemHandler itemHandler;
    private World world;

    public SkillContainer(int windowId, World world, PlayerInventory inventory, PlayerEntity player) {
        super(ModContainers.SKILL_CONTAINER.get(), windowId);

        this.player = player;
        this.world = world;
        this.inventory = inventory;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return playerIn.getEntityId() == player.getEntityId();
    }
}
