package com.airistotal.martial_cultivation.capabilities.skills.base;

import com.airistotal.martial_cultivation.capabilities.skills.CultivationSkill;
import com.airistotal.martial_cultivation.capabilities.skills.CultivationSkillSubType;
import com.airistotal.martial_cultivation.Main;
import com.airistotal.martial_cultivation.capabilities.skills.CultivationSkillType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class BaseSkill extends CultivationSkill {
    public static final String skillId = "base_skill";

    public BaseSkill() {
        super(CultivationSkillType.BASE, CultivationSkillSubType.EMPTY, skillId);
    }

    @Override
    public ResourceLocation getTextureLocation() {
        return new ResourceLocation(Main.MOD_ID, "textures/skill/base_skill.png");
    }

    @Override
    public String getSkillId() {
        return this.skillId;
    }

    @Override
    public ITextComponent getStats() {
        return new StringTextComponent("");
    }
}
