package com.airistotal.martial_cultivation.network.messages;

import com.airistotal.martial_cultivation.capabilities.Cultivator;
import com.airistotal.martial_cultivation.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

import java.io.Serializable;

public class LoadCultivator extends NetworkMessage implements Serializable {
    public int playerId;
    public Cultivator savedCultivator;

    public LoadCultivator(int playerId, Cultivator cultivator) {
        this.playerId = playerId;
        this.savedCultivator = cultivator;
    }

    @Override
    public String getErrorMessage() {
        return "Error loading cultivator for " + this.playerId;
    }

    @Override
    public void handleSelf() {
        PlayerEntity player = Minecraft.getInstance().player;

        assert player != null;
        if(player.getEntityId() == this.playerId) {
            Cultivator cultivator = Cultivator.getCultivatorFrom(player);

            cultivator.loadCultivator(this.savedCultivator);

            Main.LOGGER.debug("Loading cultivator for " + player.getScoreboardName());
        }
    }
}
