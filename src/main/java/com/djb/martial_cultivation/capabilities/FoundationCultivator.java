package com.djb.martial_cultivation.capabilities;

import com.djb.martial_cultivation.capabilities.attributes.CultivationSkill;
import com.djb.martial_cultivation.exceptions.NotEnoughQiException;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;

public class FoundationCultivator implements Cultivator, INBTSerializable<CompoundNBT> {
    private int storedQi = 0;
    private int maxQi = 100;
    private boolean isEnabled = false;

    private ArrayList<CultivationSkill> cultivationAttributeList = new ArrayList();

    @Override
    public void cultivate() {
        this.maxQi += 10;
    }

    @Override
    public void regenerateQi() {
        int regenerateQiAmount = 5;
        this.storedQi += regenerateQiAmount;

        this.qiChangeInvariant();
    }

    @Override
    public void setQi(int qi) {
        this.storedQi = qi;

        this.qiChangeInvariant();
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

    private void qiChangeInvariant() {
        if (this.storedQi > this.maxQi) {
            this.storedQi = this.maxQi;
        }
    }

    @Override
    public int getMaxQi() {
        return this.maxQi;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();

        nbt.putInt("storedQi", this.storedQi);
        nbt.putInt("maxQi", this.maxQi);
        nbt.putBoolean("isEnabled", this.isEnabled);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (nbt != null) {
            this.storedQi = nbt.getInt("storedQi");
            this.maxQi = nbt.getInt("maxQi");
            this.isEnabled = nbt.getBoolean("isEnabled");
        }
    }
}
