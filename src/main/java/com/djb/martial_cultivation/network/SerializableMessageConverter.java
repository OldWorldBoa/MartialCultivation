package com.djb.martial_cultivation.network;

import com.djb.martial_cultivation.Main;
import net.minecraft.network.PacketBuffer;

import java.io.*;
import java.util.Base64;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class SerializableMessageConverter {
    public static <T extends Object> BiConsumer<T, PacketBuffer> getEncoder() {
        return (objectToEncode, packetBuffer) -> {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String objectString = "";

            try {
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(objectToEncode);
                oos.close();

                objectString = Base64.getEncoder().encodeToString(baos.toByteArray());
            } catch (IOException e) {
                Main.LOGGER.warn("Error serializing object: " + e.getMessage());
            }

            packetBuffer.writeString(objectString);
        };
    }

    public static <T extends Object> Function<PacketBuffer, T> getDecoder() {
        return packetBuffer -> {
            String objectString = packetBuffer.readString();

            byte [] data = Base64.getDecoder().decode(objectString);
            try {
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
                Object o = ois.readObject();
                ois.close();

                return (T)o;
            } catch (IOException | ClassNotFoundException e) {
                Main.LOGGER.warn("Error deserializing object: " + e.getMessage());
            }

            return null;
        };
    }
}
