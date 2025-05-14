package com.djb.martial_cultivation.network.messages;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.capabilities.Cultivator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.Serializable;
import java.util.function.Supplier;

public class SaveCultivator implements Serializable {
    public int playerId;
    public Cultivator cultivatorToSave;

    public SaveCultivator(int playerId, Cultivator cultivatorToSave) {
        this.playerId = playerId;
        this.cultivatorToSave = cultivatorToSave;
    }

    public static void handleMessage(SaveCultivator message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            try {
                SaveCultivatorServerSide(message);
            } catch (Exception e) {
                Main.LOGGER.warn("Error saving cultivator sent by " + ctx.get().toString() +
                        " for " + Minecraft.getInstance().player.getScoreboardName() +
                        ". " + e.getMessage());
            }
        });

        ctx.get().setPacketHandled(true);
    }

    private static void SaveCultivatorServerSide(SaveCultivator message) {
        PlayerEntity player = Minecraft.getInstance().player;

        if(player.getEntityId() == message.playerId) {
            Cultivator cultivator = Cultivator.getCultivatorFrom(player);

            cultivator.loadCultivator(message.cultivatorToSave);

            Main.LOGGER.debug("Saving cultivator " + player.getScoreboardName());
        }
    }
}
