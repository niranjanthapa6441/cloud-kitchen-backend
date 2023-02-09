package com.example.CloudKitchenBackend.Util;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JsonUtil {
    private static Gson gson = (new GsonBuilder())
            .setPrettyPrinting()
            .create();

    private JsonUtil() {
    }

    public static <T> String toJsonObj(T obj) {
        return gson.toJson(obj);
    }

    public static <T> String toJsonList(List<T> objCol) {
        return gson.toJson(objCol);
    }

    public static <T> T fromJsonToObj(String jsonString, Class<T> obj) {
        if (!isValidJsonString(jsonString)) {
            throw new IllegalArgumentException(" Invalid Json data.");
        } else {
            return gson.fromJson(jsonString, obj);
        }
    }

    public static <T> List<T> toPojoList(String jsonString, Type t) {
        return Arrays.asList(gson.fromJson(jsonString, t));
    }

    public static <T> List<T> fromJsonToList(String jsonString, Type t) {
        return !isValidJsonString(jsonString) ? Collections.EMPTY_LIST : Arrays.asList((T[]) gson.fromJson(jsonString, t));
    }


    private static boolean isValidJsonString(String jsonString) {
        if (Strings.isNullOrEmpty(jsonString)) {
            return false;
        } else {
            try {
                gson.fromJson(jsonString, Object.class);
                return true;
            } catch (Exception var2) {
                return false;
            }
        }
    }
}

