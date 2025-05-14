package com.djb.martial_cultivation.network.messages;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.capabilities.Cultivator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.Serializable;
import java.util.function.Supplier;

public class LoadCultivator implements Serializable {
    public int playerId;
    public Cultivator savedCultivator;

    public LoadCultivator(int playerId, Cultivator cultivator) {
        this.playerId = playerId;
        this.savedCultivator = cultivator;
    }

    public static void handleMessage(LoadCultivator message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            try {
                LoadCultivatorClientside(message);
            } catch (Exception e) {
                Main.LOGGER.warn("Error loading cultivator sent by " + ctx.get().toString() +
                        " for " + Minecraft.getInstance().player.getScoreboardName() +
                        ". " + e.getMessage());
            }
        });

        ctx.get().setPacketHandled(true);
    }

    private static void LoadCultivatorClientside(LoadCultivator message) {
        PlayerEntity player = Minecraft.getInstance().player;

        if(player.getEntityId() == message.playerId) {
            Cultivator cultivator = Cultivator.getCultivatorFrom(player);

            cultivator.loadCultivator(message.savedCultivator);

            Main.LOGGER.debug("Loading cultivator for " + player.getScoreboardName());
        }
    }
}
