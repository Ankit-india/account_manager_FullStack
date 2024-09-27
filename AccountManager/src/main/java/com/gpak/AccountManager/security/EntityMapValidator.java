package com.gpak.AccountManager.security;

import com.gpak.AccountManager.constants.AppConstant;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EntityMapValidator {

  public boolean validateEntityMapToEntity(Map<String, Object> map) {
    for (String field : AppConstant.REQUIRED_FIELDS) {
      if (!map.containsKey(field)) {
        return false;
      }
      Object value = map.get(field);
      if (value == null || (value instanceof String && ((String) value).isEmpty())) {
        return false;
      }
    }
    return true;
  }
}
