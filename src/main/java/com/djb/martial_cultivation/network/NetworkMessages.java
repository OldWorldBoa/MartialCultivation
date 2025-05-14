package com.djb.martial_cultivation.network;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.network.messages.*;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class NetworkMessages {
    private static int messageId = 1;
    public static void registerMessages() {
        registerSerializableMessage(QiAmountChanged.class, QiAmountChanged::handleMessage);
        registerSerializableMessage(LoadCultivator.class, LoadCultivator::handleMessage);
        registerSerializableMessage(SaveCultivator.class, SaveCultivator::handleMessage);
        registerSerializableMessage(SaveToolSkillSettings.class, SaveToolSkillSettings::handleMessage);
        registerSerializableMessage(SaveCultivationState.class, SaveCultivationState::handleMessage);
        registerSerializableMessage(MirrorCultivation.class, MirrorCultivation::handleMessage);
    }

    private static <MSG> void registerSerializableMessage(Class<MSG> messageClass, BiConsumer<MSG, Supplier<NetworkEvent.Context>> handler) {
        Main.NETWORK_CHANNEL.registerMessage(
                messageId,
                messageClass,
                SerializableMessageConverter.getEncoder(),
                SerializableMessageConverter.getDecoder(),
                handler
        );

        messageId++;
    }
}
