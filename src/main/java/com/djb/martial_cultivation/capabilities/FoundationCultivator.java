package com.djb.martial_cultivation.capabilities;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.capabilities.skills.CultivationSkill;
import com.djb.martial_cultivation.capabilities.skills.CultivationSkillFactory;
import com.djb.martial_cultivation.exceptions.NotEnoughQiException;
import com.djb.martial_cultivation.exceptions.SkillNotImplementedException;
import com.djb.martial_cultivation.helpers.StringHelpers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.io.Serializable;
import java.util.ArrayList;

public class FoundationCultivator implements Cultivator, INBTSerializable<CompoundNBT>, Serializable {
    private int storedQi = 0;
    private int maxQi = 100;
    private boolean isEnabled = false;

    private ArrayList<CultivationSkill> learnedSkills = new ArrayList<>();

    @Override
    public void loadCultivator(Cultivator savedCultivator) {
        this.storedQi = savedCultivator.getStoredQi();
        this.maxQi = savedCultivator.getMaxQi();
        this.isEnabled = savedCultivator.isEnabled();
    }

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
    public void learnSkill(CultivationSkill skill) {
        this.learnedSkills.add(skill);
    }

    @Override
    public ArrayList<CultivationSkill> getSkills() {
        return this.learnedSkills;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
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

        int i = 0;
        for (CultivationSkill skill : learnedSkills) {
            nbt.putString("skillId_" + i, skill.getSkillId());
            i++;
        }

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (nbt != null) {
            this.storedQi = nbt.getInt("storedQi");
            this.maxQi = nbt.getInt("maxQi");
            this.isEnabled = nbt.getBoolean("isEnabled");

            int i = 0;
            String skillId = nbt.getString("skillId_" + i);
            while(!StringHelpers.isNullOrWhitespace(skillId)) {
                try {
                    this.learnedSkills.add(CultivationSkillFactory.create(skillId));
                } catch (SkillNotImplementedException e) {
                    Main.LOGGER.debug(e.getMessage());
                }

                i++;
                skillId = nbt.getString("skillId_" + i);
            }
        }
    }
}
