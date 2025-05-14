package com.djb.martial_cultivation.capabilities.skills;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import java.io.Serializable;

public abstract class CultivationSkill implements Serializable, INBTSerializable<CompoundNBT> {
    private CultivationSkillType type;
    private CultivationSkillSubType subType;
    protected String skillId;

    public CultivationSkill(CultivationSkillType type, CultivationSkillSubType subType, String skillId) {
        this.type = type;
        this.subType = subType;
        this.skillId = skillId;
    }

    public CultivationSkillType getSkillType() {
        return this.type;
    }

    public CultivationSkillSubType getSkillSubType() {
        return this.subType;
    }

    public abstract ResourceLocation getTextureLocation();
    public abstract String getSkillId();

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();

        nbt.putString("type", this.type.name());
        nbt.putString("subType", this.subType.name());
        nbt.putString("skillId", this.skillId);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (nbt != null) {
            this.type = CultivationSkillType.valueOf(nbt.getString("type"));
            this.subType = CultivationSkillSubType.valueOf(nbt.getString("subType"));
            this.skillId = nbt.getString("skillId");
        }
    }

}
