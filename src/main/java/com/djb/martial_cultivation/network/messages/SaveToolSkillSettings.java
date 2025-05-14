package com.djb.martial_cultivation.network.messages;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.capabilities.Cultivator;
import com.djb.martial_cultivation.capabilities.skills.ToolSkillSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.Serializable;
import java.util.function.Supplier;

public class SaveToolSkillSettings implements Serializable {
    public ToolSkillSettings toolSkillSettings;
    public int playerId;

    public SaveToolSkillSettings(int playerId, ToolSkillSettings toolSkillSettings) {
        this.toolSkillSettings = toolSkillSettings;
        this.playerId = playerId;
    }

    public static void handleMessage(SaveToolSkillSettings message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            try {
                SaveToolSkillSettingsServerSide(message);
            } catch (Exception e) {
                assert Minecraft.getInstance().player != null;
                Main.LOGGER.warn("Error saving cultivator sent by " + ctx.get().toString() +
                        " for " + Minecraft.getInstance().player.getScoreboardName() +
                        ". " + e.getMessage());
            }
        });

        ctx.get().setPacketHandled(true);
    }

    private static void SaveToolSkillSettingsServerSide(SaveToolSkillSettings message) {
        PlayerEntity player = getServerPlayer(message.playerId);

        if(player.getEntityId() == message.playerId) {
            Cultivator cultivator = Cultivator.getCultivatorFrom(player);
            cultivator.setToolSkillSettings(message.toolSkillSettings);

            Main.LOGGER.debug("Saving " + message.toolSkillSettings.toolSkillGroup.toString() +
                              " tool skill settings for " + player.getScoreboardName());
        }
    }

    private static PlayerEntity getServerPlayer(int entityId) {
        return Minecraft.getInstance().getIntegratedServer()
                .getPlayerList().getPlayers()
                .stream().filter(x -> x.getEntityId() == entityId)
                .findFirst().get();
    }
}
