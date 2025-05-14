package com.djb.martial_cultivation.network;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.network.messages.LoadCultivator;
import com.djb.martial_cultivation.network.messages.QiAmountChanged;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class NetworkMessages {
    private static int messageId = 1;
    public static void registerMessages() {
        registerSerializableMessage(QiAmountChanged.class, QiAmountChanged::handleMessage);
        registerSerializableMessage(LoadCultivator.class, LoadCultivator::handleMessage);
    }

    private static <MSG> void registerSerializableMessage(Class<MSG> messageClass, BiConsumer<MSG, Supplier<NetworkEvent.Context>> ctx) {
        Main.NETWORK_CHANNEL.registerMessage(
                messageId,
                messageClass,
                SerializableMessageConverter.getEncoder(),
                SerializableMessageConverter.getDecoder(),
                ctx
        );

        messageId++;
    }
}
