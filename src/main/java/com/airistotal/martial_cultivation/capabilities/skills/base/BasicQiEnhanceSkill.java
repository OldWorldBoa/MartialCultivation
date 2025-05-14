package com.airistotal.martial_cultivation.capabilities.skills.base;

import com.airistotal.martial_cultivation.Main;
import com.airistotal.martial_cultivation.capabilities.skills.CultivationSkill;
import com.airistotal.martial_cultivation.capabilities.skills.CultivationSkillSubType;
import com.airistotal.martial_cultivation.capabilities.skills.CultivationSkillType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class BasicQiEnhanceSkill extends CultivationSkill {
    public static final String skillId = "basic_qi_enhance_skill";

    public BasicQiEnhanceSkill() {
        super(
            CultivationSkillType.BASE,
            CultivationSkillSubType.QI_ENHANCEMENT,
            skillId);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();

        nbt.put("base", super.serializeNBT());

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        super.deserializeNBT((CompoundNBT) nbt.get("base"));
    }

    @Override
    public ResourceLocation getTextureLocation() {
        return new ResourceLocation(Main.MOD_ID, "textures/skill/" + this.skillId + ".png");
    }

    @Override
    public String getSkillId() {
        return skillId;
    }

    @Override
    public ITextComponent getStats() {
        return new StringTextComponent("");
    }
}
