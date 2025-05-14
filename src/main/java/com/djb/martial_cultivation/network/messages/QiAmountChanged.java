package com.djb.martial_cultivation.network.messages;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.capabilities.Cultivator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

import java.io.Serializable;

public class QiAmountChanged extends NetworkMessage implements Serializable {
    public int qiAmount;
    public int playerId;

    public QiAmountChanged(int qiAmount, int playerId)
    {
        this.qiAmount = qiAmount;
        this.playerId = playerId;
    }

    @Override
    public String getErrorMessage() {
        return "Error setting qi amount for " + this.playerId;
    }

    @Override
    public void handleSelf() {
        PlayerEntity player = Minecraft.getInstance().player;

        if(player.getEntityId() == this.playerId) {
            Cultivator cultivator = Cultivator.getCultivatorFrom(player);

            cultivator.setQi(this.qiAmount);

            Main.LOGGER.debug("Setting qi for " + player.getScoreboardName() + " to " + this.qiAmount);
        }
    }
}
