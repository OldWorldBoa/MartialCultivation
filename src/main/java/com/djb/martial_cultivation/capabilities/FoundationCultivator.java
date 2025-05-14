package com.djb.martial_cultivation.capabilities;

import com.djb.martial_cultivation.exceptions.NotEnoughQiException;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class FoundationCultivator implements Cultivator, INBTSerializable<CompoundNBT> {
    private int storedQi = 0;
    private int maxQi = 100;

    @Override
    public void storeQi(int qi) {
        this.storedQi += qi;

        if (this.storedQi > this.maxQi) {
            this.storedQi = this.maxQi;
        }
    }

    @Override
    public void cultivate() {
        this.maxQi += 10;
    }

    @Override
    public void useQi(int qi) throws NotEnoughQiException {
        if (qi > this.storedQi) {
            throw new NotEnoughQiException();
        }

        this.storedQi -= qi;
    }

    @Override
    public int getStoredQi() {
        return this.storedQi;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();

        nbt.putInt("storedQi", this.storedQi);
        nbt.putInt("maxQi", this.maxQi);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (nbt != null) {
            this.storedQi = nbt.getInt("storedQi");
            this.maxQi = nbt.getInt("maxQi");
        }
    }
}
