package com.djb.martial_cultivation.network;

import com.djb.martial_cultivation.helpers.SerializableConverter;
import net.minecraft.network.PacketBuffer;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class SerializableMessageConverter {
    public static <T extends Object> BiConsumer<T, PacketBuffer> getEncoder() {
        return (objectToEncode, packetBuffer) -> packetBuffer.writeString(SerializableConverter.Serialize(objectToEncode));
    }

    public static <T> Function<PacketBuffer, T> getDecoder() {
        return packetBuffer -> SerializableConverter.Deserialize(packetBuffer.readString());
    }
}
