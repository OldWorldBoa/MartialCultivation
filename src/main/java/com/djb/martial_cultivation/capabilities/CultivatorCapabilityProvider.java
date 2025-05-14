package com.djb.martial_cultivation.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CultivatorCapabilityProvider implements ICapabilitySerializable<INBT> {
    @CapabilityInject(Cultivator.class)
    public static Capability<Cultivator> capability = null;

    private LazyOptional<Cultivator> instance = LazyOptional.of(capability::getDefaultInstance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == capability ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return capability.getStorage().writeNBT(
            capability,
            this.instance.orElseThrow(
                () -> new IllegalArgumentException("LazyOptional must not be empty!")),
            null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        if (nbt != null) {
            capability.getStorage().readNBT(
                capability,
                this.instance.orElseThrow(
                        () -> new IllegalArgumentException("LazyOptional must not be empty!")),
                null,
                nbt);
        }
    }
}
