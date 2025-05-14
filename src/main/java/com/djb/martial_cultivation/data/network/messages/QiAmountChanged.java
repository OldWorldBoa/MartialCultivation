package com.djb.martial_cultivation.data.network.messages;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.capabilities.Cultivator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.Serializable;
import java.util.function.Supplier;

public class QiAmountChanged implements Serializable {
    private final static long serialVersionUID = 1;

    public int qiAmount;
    public int playerId;

    public QiAmountChanged(int qiAmount, int playerId)
    {
        this.qiAmount = qiAmount;
        this.playerId = playerId;
    }

    public static void handleMessage(QiAmountChanged message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> TrySetQiForPlayer(message, ctx));

        ctx.get().setPacketHandled(true);
    }

    private static void TrySetQiForPlayer(QiAmountChanged message, Supplier<NetworkEvent.Context> ctx) {
        PlayerEntity player = Minecraft.getInstance().player;

        try {
            if(player.getEntityId() == message.playerId) {
                Cultivator cultivator = Cultivator.getCultivatorFrom(player);

                cultivator.setQi(message.qiAmount);

                Main.LOGGER.debug("Setting qi for " + player.getScoreboardName() + " to " + message.qiAmount);
            }
        } catch (Exception e) {
            Main.LOGGER.warn("Error setting qi sent by " + ctx.get().toString() +
                             " to " + message.qiAmount + " for " + player.getScoreboardName() +
                             ". " + e.getMessage());
        }
    }
}
