package com.gpak.AccountManager.helper;

import com.gpak.AccountManager.entity.Account;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class Convertor {

  public Account convertMapToEntity(Map<String, Object> objectMap) {
    Account entity = new Account();

    for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
      String fieldName = entry.getKey();
      Object fieldValue = entry.getValue();

      try {
        Field field = Account.class.getDeclaredField(fieldName);
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

  public Object convertValue(Class<?> fieldType, Object fieldValue) {
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
    } else if (fieldType == LocalDate.class) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      try {
        return LocalDate.parse(fieldValue.toString(), formatter);
      } catch (DateTimeParseException e) {
        e.printStackTrace();
      }
    }

    return fieldValue;
  }
}
