package com.djb.martial_cultivation.capabilities.skills;

import com.djb.martial_cultivation.capabilities.skills.base.BasicQiEnhanceSkill;
import com.djb.martial_cultivation.exceptions.SkillNotImplementedException;

public class CultivationSkillFactory {
    public static CultivationSkill create(String skillId) throws SkillNotImplementedException {
        switch (skillId) {
            case BasicQiEnhanceSkill.skillId:
                return new BasicQiEnhanceSkill();
            default:
                throw new SkillNotImplementedException("Skill not implemented: " + skillId);

        }
    }
}
