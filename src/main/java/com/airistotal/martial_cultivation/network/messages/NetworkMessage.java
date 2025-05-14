package com.airistotal.martial_cultivation.network.messages;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class NetworkMessage {
    public abstract String getErrorMessage();

    public abstract void handleSelf();

    public static void handleMessage(NetworkMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            try {
                message.handleSelf();
            } catch (Exception e) {
                message.getErrorMessage();
            }
        });

        ctx.get().setPacketHandled(true);
    }

    protected static PlayerEntity getServerPlayer(int entityId) {
        return Minecraft.getInstance().getIntegratedServer()
                .getPlayerList().getPlayers()
                .stream().filter(x -> x.getEntityId() == entityId)
                .findFirst().get();
    }
}
