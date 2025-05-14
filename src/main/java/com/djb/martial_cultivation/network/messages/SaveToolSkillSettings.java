package com.djb.martial_cultivation.network.messages;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.capabilities.Cultivator;
import com.djb.martial_cultivation.capabilities.skills.ToolSkillSettings;
import net.minecraft.entity.player.PlayerEntity;

import java.io.Serializable;

public class SaveToolSkillSettings extends NetworkMessage implements Serializable {
    public ToolSkillSettings toolSkillSettings;
    public int playerId;

    public SaveToolSkillSettings(int playerId, ToolSkillSettings toolSkillSettings) {
        this.toolSkillSettings = toolSkillSettings;
        this.playerId = playerId;
    }

    @Override
    public String getErrorMessage() {
        return "Error saving tool skill settings for " + this.playerId;
    }

    @Override
    public void handleSelf() {
        PlayerEntity player = getServerPlayer(this.playerId);

        if(player.getEntityId() == this.playerId) {
            Cultivator cultivator = Cultivator.getCultivatorFrom(player);
            cultivator.setToolSkillSettings(this.toolSkillSettings);

            Main.LOGGER.debug("Saving " + this.toolSkillSettings.toolSkillGroup.toString() +
                    " tool skill settings for " + player.getScoreboardName());
        }

    }
}
