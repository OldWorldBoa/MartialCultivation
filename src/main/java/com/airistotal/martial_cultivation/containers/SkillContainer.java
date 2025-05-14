package com.airistotal.martial_cultivation.containers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class SkillContainer extends Container {
    public static final String name = "skill_container";

    private PlayerEntity player;
    private PlayerInventory inventory;

    public SkillContainer(int windowId, PlayerEntity player) {
        super(ModContainers.SKILL_CONTAINER.get(), windowId);

        this.player = player;
        this.inventory = player.inventory;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return playerIn.getEntityId() == player.getEntityId();
    }
}
