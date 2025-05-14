package com.djb.martial_cultivation.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;

public class CultivatorStorage implements Capability.IStorage<Cultivator> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<Cultivator> capability, Cultivator instance, Direction side) {
        return ((INBTSerializable<CompoundNBT>)instance).serializeNBT();
    }

    @Override
    public void readNBT(Capability<Cultivator> capability, Cultivator instance, Direction side, INBT nbt) {
        if (nbt != null) {
            ((INBTSerializable<CompoundNBT>)instance).deserializeNBT((CompoundNBT) nbt);
        }
    }
}
