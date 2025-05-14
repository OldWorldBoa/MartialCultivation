package com.djb.martial_cultivation.capabilities.skills;

import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.INBTSerializable;

import java.io.Serializable;
import java.util.List;

public abstract class CultivationSkill implements Serializable, INBTSerializable<CompoundNBT> {
    private CultivationSkillType type;
    private CultivationSkillSubType subType;
    protected String skillId;

    public CultivationSkill(CultivationSkillType type, CultivationSkillSubType subType, String skillId) {
        this.type = type;
        this.subType = subType;
        this.skillId = skillId;
    }

    public abstract ResourceLocation getTextureLocation();
    public abstract String getSkillId();
    public abstract ITextComponent getStats();

    public CultivationSkillType getSkillType() {
        return this.type;
    }

    public CultivationSkillSubType getSkillSubType() {
        return this.subType;
    }

    private ITextComponent getDisplayName() {
        return (new StringTextComponent(""))
                .append(this.getTranslation("display_name"))
                .mergeStyle(TextFormatting.AQUA, TextFormatting.ITALIC);
    }

    private ITextComponent getDescription() {
        return new StringTextComponent("")
                .append(this.getTranslation("description"));
    }

    private ITextComponent getLore() {
        return new StringTextComponent("")
                .append(this.getTranslation("lore"))
                .mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC);
    }

    private ITextComponent getTranslation(String translationName) {
        return new TranslationTextComponent("skill." + this.skillId + "." + translationName);
    }

    public List<ITextComponent> getToolTip() {
        List<ITextComponent> list = Lists.newArrayList();

        list.add(getDisplayName());
        list.add(getDescription());
        list.add(getLore());
        list.add(getStats());

        return list;
    }

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
