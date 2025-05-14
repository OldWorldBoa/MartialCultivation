package com.djb.martial_cultivation.capabilities;

import com.djb.martial_cultivation.capabilities.skills.CultivationSkill;
import com.djb.martial_cultivation.capabilities.skills.ToolSkillGroup;
import com.djb.martial_cultivation.capabilities.skills.ToolSkillSettings;
import com.djb.martial_cultivation.exceptions.NotEnoughQiException;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;

public interface Cultivator {
    static Cultivator getCultivatorFrom(PlayerEntity player) {
        return player
            .getCapability(CultivatorCapabilityProvider.capability)
            .orElseThrow(IllegalStateException::new);
    }

    void loadCultivator(Cultivator savedCultivator);

    void setIsCultivating(boolean isCultivating);
    void cultivate();

    void useQi(int qi) throws NotEnoughQiException;
    void setQi(int qi);
    void regenerateQi();

    int getStoredQi();
    int getMaxQi();

    void toggleActiveCultivation();
    boolean isEnteringCultivation();
    boolean isExitingCultivation();
    boolean isCultivating();

    void learnSkill(CultivationSkill skill);
    ArrayList<CultivationSkill> getSkills();
    void setToolSkillSettings(ToolSkillSettings toolSkillSettings);
    ArrayList<ToolSkillSettings> getAllToolSkillSettings();
    ToolSkillSettings getToolSkillSettings(ToolSkillGroup group);

    void setEnabled(boolean enabled);
    boolean isEnabled();
}
