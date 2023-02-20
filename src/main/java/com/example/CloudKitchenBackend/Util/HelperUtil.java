package com.example.CloudKitchenBackend.Util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
public class HelperUtil {
    public static Object checkAndCorrectNullValueOfObject(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        if (obj instanceof Collection<?>) {
            Collection<?> collection = (Collection<?>) obj;
            Class genericClass = null;
            Iterator it = collection.iterator();
            if (it.hasNext()) {
                genericClass = it.next().getClass();
            }
            if (genericClass != null) {
                Field[] listFields = genericClass.getDeclaredFields();
                collection.forEach(object -> {
                    for (Field field : listFields) {
                        try {
                            field.setAccessible(true);
                            Object value = field.get(object);
                            if (value == null) {
                                field.set(object, "");
                            }
                        } catch (IllegalAccessException | IllegalArgumentException e) {
                                //LOG.log(Level.INFO, "ERROR AT CHECK AND CORRECT NULL VALUE OF OBJECT :: {0}", e.getMessage());
                        }
                    }
                });
                return collection;
            }

        }
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (value == null) {
                    field.set(obj, "");
                }
            } catch (IllegalAccessException | IllegalArgumentException e) {
//                LOG.log(Level.INFO, "ERROR AT CHECK AND CORRECT NULL VALUE OF OBJECT :: {0}", e.getMessage());
            }
        }
        return obj;
    }
}
