package com.djb.martial_cultivation.capabilities;

import com.djb.martial_cultivation.capabilities.skills.CultivationSkill;
import com.djb.martial_cultivation.capabilities.skills.ToolSkillGroup;
import com.djb.martial_cultivation.capabilities.skills.ToolSkillSettings;
import com.djb.martial_cultivation.exceptions.NotEnoughQiException;
import com.djb.martial_cultivation.helpers.ListHelpers;
import com.djb.martial_cultivation.helpers.SerializableConverter;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FoundationCultivator implements Cultivator, INBTSerializable<CompoundNBT>, Serializable {
    private int storedQi = 0;
    private int maxQi = 100;
    private boolean isEnabled = false;

    private final ArrayList<CultivationSkill> learnedSkills;
    private final ArrayList<ToolSkillSettings> toolSkillSettings;

    public FoundationCultivator() {
        this.learnedSkills = new ArrayList<>();
        this.toolSkillSettings = new ArrayList<>();
    }

    @Override
    public void loadCultivator(Cultivator savedCultivator) {
        this.storedQi = savedCultivator.getStoredQi();
        this.maxQi = savedCultivator.getMaxQi();
        this.isEnabled = savedCultivator.isEnabled();
        this.learnedSkills.addAll(ListHelpers.getDistinctFrom(savedCultivator.getSkills(), CultivationSkill::getSkillId));

        if(savedCultivator.getAllToolSkillSettings() != null) {
            this.toolSkillSettings.addAll(savedCultivator.getAllToolSkillSettings());
        }
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
        if (!this.learnedSkills.stream().anyMatch(x -> x.getSkillId() == skill.getSkillId())) {
            this.learnedSkills.add(skill);
        }
    }

    @Override
    public void setToolSkillSettings(ToolSkillSettings toolSkillSettings) {
        this.toolSkillSettings.removeIf((x) -> x.toolSkillGroup == toolSkillSettings.toolSkillGroup);
        this.toolSkillSettings.add(toolSkillSettings);
    }

    @Override
    public ArrayList<CultivationSkill> getSkills() {
        return this.learnedSkills;
    }

    @Override
    public ArrayList<ToolSkillSettings> getAllToolSkillSettings() {
        return this.toolSkillSettings;
    }

    @Override
    public ToolSkillSettings getToolSkillSettings(ToolSkillGroup group) {
        if (this.toolSkillSettings.stream().noneMatch(x -> x.toolSkillGroup == group)) {
            ToolSkillSettings newToolSkillSettings = new ToolSkillSettings();
            newToolSkillSettings.toolSkillGroup = group;
            this.toolSkillSettings.add(newToolSkillSettings);

            return newToolSkillSettings;
        }

        return toolSkillSettings.stream().filter(x -> x.toolSkillGroup == group).findFirst().get();
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
        nbt.putString("toolSkillSettings", SerializableConverter.Serialize(this.toolSkillSettings));
        nbt.putString("learnedSkills", SerializableConverter.Serialize(this.learnedSkills));

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (nbt != null) {
            this.storedQi = nbt.getInt("storedQi");
            this.maxQi = nbt.getInt("maxQi");
            this.isEnabled = nbt.getBoolean("isEnabled");

            List<ToolSkillSettings> toolSkillSettings = SerializableConverter.Deserialize(nbt.getString("toolSkillSettings"));
            if (toolSkillSettings != null) {
                this.toolSkillSettings.addAll(toolSkillSettings);
            }

            List<CultivationSkill> skills = SerializableConverter.Deserialize(nbt.getString("learnedSkills"));
            if (skills != null) {
                this.learnedSkills.addAll(skills);
            }
        }
    }
}
