package com.gpak.AccountManager.service;

import com.gpak.AccountManager.dao.EntityRepository;
import com.gpak.AccountManager.entity.Entity;
import com.gpak.AccountManager.helper.Convertor;
import java.util.*;

import com.gpak.AccountManager.security.EntityMapValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicesImpl implements Services {

  @Autowired private EntityRepository repository;

  @Autowired private Convertor convertor;

  @Autowired private EntityMapValidator entityMapValidator;

  private static final Logger LOGGER = LoggerFactory.getLogger(Services.class);

  @Override
  public Map<String, Object> addNewEntry(Map<String, Object> entityMap) throws Exception {
    LOGGER.info("entityMap : {}", entityMap.toString());
    if(entityMapValidator.validateEntityMapToEntity(entityMap)){
      Entity entity = convertor.convertMapToEntity(entityMap);
      LOGGER.info("entity : {}", entity.toString());

      Map<String, Object> response = new HashMap<>();
      if (repository.save(entity)) {
        response.put("message", "saved");
        response.put("status", 200);
      } else {
        response.put("message", "not saved");
      }
      return response;
    }
    else {
        throw new Exception("payload body does not poses the intended required values");
    }
  }

  @Override
  public List<Map<String, Object>> getAllAcNameWithDepartment(String keyword) {
    List<Entity> filteredEntities = repository.findByAcNameContainingIgnoreCase(keyword);

    List<Map<String, Object>> response = new ArrayList<>();

    for (Entity entity : filteredEntities) {
      Map<String, Object> map = new HashMap<>();
      map.put("acName", entity.getName());
      map.put("department", entity.getDepartment());
      response.add(map);
    }

    return response;
  }

  @Override
  public List<Entity> getAllEntities() {
    return null;
  }

  @Override
  public List<Entity> getEntitiesForDateRange(String stDate, String endDate) {
    return null;
  }

  @Override
  public int getTotalAmountForDateRange(String acName) {
    return 0;
  }
}
