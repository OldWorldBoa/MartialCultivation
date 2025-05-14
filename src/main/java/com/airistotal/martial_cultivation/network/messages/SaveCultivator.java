package com.airistotal.martial_cultivation.network.messages;

import com.airistotal.martial_cultivation.Main;
import com.airistotal.martial_cultivation.capabilities.Cultivator;
import net.minecraft.entity.player.PlayerEntity;

import java.io.Serializable;

public class SaveCultivator extends NetworkMessage implements Serializable {
    public int playerId;
    public Cultivator cultivatorToSave;

    public SaveCultivator(int playerId, Cultivator cultivatorToSave) {
        this.playerId = playerId;
        this.cultivatorToSave = cultivatorToSave;
    }

    @Override
    public String getErrorMessage() {
        return "Error saving cultivator for " + this.playerId;
    }

    @Override
    public void handleSelf() {
        PlayerEntity player = getServerPlayer(this.playerId);

        if(player.getEntityId() == this.playerId) {
            Cultivator cultivator = Cultivator.getCultivatorFrom(player);

            cultivator.loadCultivator(this.cultivatorToSave);

            Main.LOGGER.debug("Saving cultivator " + player.getScoreboardName());
        }
    }
}
