package com.gpak.AccountManager.helper;

import com.gpak.AccountManager.entity.Entity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;

@Component
public class Convertor {

    public Entity convertMapToEntity(Map<String, Object> objectMap) {
        Entity entity = new Entity();

        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();

            try {
                Field field = Entity.class.getDeclaredField(fieldName);
                field.setAccessible(true);

                Object convertedValue = convertValue(field.getType(), fieldValue);

                field.set(entity, convertedValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        entity.setTime(System.currentTimeMillis());

        return entity;
    }

    private Object convertValue(Class<?> fieldType, Object fieldValue) {
        if (fieldValue == null) {
            return null;
        }

        if (fieldType.isAssignableFrom(fieldValue.getClass())) {
            return fieldValue;
        }

        if (fieldType == String.class) {
            return fieldValue.toString();
        } else if (fieldType == int.class || fieldType == Integer.class) {
            return Integer.parseInt(fieldValue.toString());
        } else if (fieldType == long.class || fieldType == Long.class) {
            return Long.parseLong(fieldValue.toString());
        } else if (fieldType == double.class || fieldType == Double.class) {
            return Double.parseDouble(fieldValue.toString());
        } else if (fieldType == float.class || fieldType == Float.class) {
            return Float.parseFloat(fieldValue.toString());
        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
            return Boolean.parseBoolean(fieldValue.toString());
        } else if (fieldType == short.class || fieldType == Short.class) {
            return Short.parseShort(fieldValue.toString());
        } else if (fieldType == byte.class || fieldType == Byte.class) {
            return Byte.parseByte(fieldValue.toString());
        } else if (fieldType == char.class || fieldType == Character.class) {
            return fieldValue.toString().charAt(0);
        }

        return fieldValue;
    }

}
