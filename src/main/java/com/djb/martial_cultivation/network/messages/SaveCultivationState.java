package com.djb.martial_cultivation.network.messages;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.capabilities.Cultivator;
import net.minecraft.entity.player.PlayerEntity;

import java.io.Serializable;

public class SaveCultivationState extends NetworkMessage implements Serializable {
    int playerId;
    boolean isCultivating;

    public SaveCultivationState(int playerId, boolean isCultivating) {
        this.playerId = playerId;
        this.isCultivating = isCultivating;
    }

    @Override
    public String getErrorMessage() {
        return "Error saving cultivation state " + this.isCultivating + " for " + this.playerId;
    }

    @Override
    public void handleSelf() {
        PlayerEntity player = getServerPlayer(this.playerId);

        if(player.getEntityId() == this.playerId) {
            Cultivator cultivator = Cultivator.getCultivatorFrom(player);
            cultivator.setIsCultivating(this.isCultivating);

            Main.LOGGER.debug(
                    "Saving cultivation state " + this.isCultivating +
                    " on server for " + player.getScoreboardName());
        }
    }
}
