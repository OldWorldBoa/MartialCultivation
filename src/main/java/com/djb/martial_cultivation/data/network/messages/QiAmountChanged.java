package com.djb.martial_cultivation.data.network.messages;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.capabilities.Cultivator;
import com.djb.martial_cultivation.capabilities.CultivatorCapabilityProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.*;
import java.util.Base64;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class QiAmountChanged implements Serializable {
    private final static long serialVersionUID = 1;

    public int qiAmount;

    public QiAmountChanged(int qiAmount) {
        this.qiAmount = qiAmount;
    }

    public static BiConsumer<QiAmountChanged, PacketBuffer> getQiAmountChangedEncoder() {
        return new BiConsumer<QiAmountChanged, PacketBuffer>() {
            @Override
            public void accept(QiAmountChanged qiAmountChanged, PacketBuffer packetBuffer) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = null;
                String objectString = "";

                try {
                    oos = new ObjectOutputStream(baos);
                    oos.writeObject(qiAmountChanged);
                    oos.close();

                    objectString = Base64.getEncoder().encodeToString(baos.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                packetBuffer.writeString(objectString);
            }
        };
    }

    public static Function<PacketBuffer, QiAmountChanged> getQiAmountChangedDecoder() {
        return new Function<PacketBuffer, QiAmountChanged>() {
            @Override
            public QiAmountChanged apply(PacketBuffer packetBuffer) {
                String objectString = packetBuffer.readString();

                byte [] data = Base64.getDecoder().decode(objectString);
                try {
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
                    Object o = ois.readObject();
                    ois.close();

                    return (QiAmountChanged)o;
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                return null;
            }
        };
    }

    public static void handleMessage(QiAmountChanged message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();

            try {
                assert player != null;

                Cultivator cultivator = player
                        .getCapability(CultivatorCapabilityProvider.capability)
                        .orElseThrow(IllegalStateException::new);

                cultivator.setQi(message.qiAmount);

                Main.LOGGER.debug("Setting qi for " + player.getScoreboardName() + " to " + message.qiAmount);
            } catch (Exception e) {
                Main.LOGGER.debug("Error setting qi for " + player.getScoreboardName());
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
