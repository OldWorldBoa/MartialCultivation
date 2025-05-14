package com.djb.martial_cultivation.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ListHelpers {
    public static <T> List<T> getDistinctFrom(List<T> list, Function<T, String> getId) {
        List<T> distinctItems = new ArrayList<>();

        if (list != null) {
            List<String> keys = new ArrayList<>();

            for (T item: list) {
                String id = getId.apply(item);
                if(!keys.contains(id)) {
                    keys.add(id);
                    distinctItems.add(item);
                }
            }
        }

        return distinctItems;
    }
}
