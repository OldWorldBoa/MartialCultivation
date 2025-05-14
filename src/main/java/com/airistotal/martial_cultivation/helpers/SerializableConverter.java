package com.airistotal.martial_cultivation.helpers;

import com.airistotal.martial_cultivation.Main;

import java.io.*;
import java.util.Base64;

public class SerializableConverter {
    public static String Serialize(Object o) {
        String objectString = "";

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            oos.close();

            objectString = Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            Main.LOGGER.warn("Error serializing object: " + e.getMessage());
        }

        return objectString;
    }

    public static <T> T Deserialize(String serializedObject) {
        try {
            byte [] data = Base64.getDecoder().decode(serializedObject);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Object o = ois.readObject();
            ois.close();

            return (T)o;
        } catch (IOException | ClassNotFoundException e) {
            Main.LOGGER.warn("Error deserializing object: " + e.getMessage());
        }

        return null;
    }
}
