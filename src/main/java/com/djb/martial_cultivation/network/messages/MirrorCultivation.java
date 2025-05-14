package com.djb.martial_cultivation.network.messages;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.capabilities.Cultivator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

import java.io.Serializable;

public class MirrorCultivation extends NetworkMessage implements Serializable {
    int playerId;

    public MirrorCultivation(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public String getErrorMessage() {
        return "Unable to cultivate for " + this.playerId;
    }

    @Override
    public void handleSelf() {
        PlayerEntity player = Minecraft.getInstance().player;

        if (player.getEntityId() == this.playerId) {
            Cultivator cultivator = Cultivator.getCultivatorFrom(player);
            cultivator.cultivate();

            Main.LOGGER.debug("Cultivate on client side for " + player.getScoreboardName());
        }
    }
}
