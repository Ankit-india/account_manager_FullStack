package com.gpak.AccountManager.service;

import com.gpak.AccountManager.dao.EntityRepository;
import com.gpak.AccountManager.entity.Entity;
import com.gpak.AccountManager.helper.Convertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServicesImpl implements Services {

    @Autowired
    private EntityRepository repository;

    @Autowired
    private Convertor convertor;

    private static final Logger LOGGER = LoggerFactory.getLogger(Services.class);

    @Override
    public Map<String, Object> addNewEntry(Map<String, Object> entityMap) {
        LOGGER.info("entityMap : {}", entityMap.toString());
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

    @Override
    public List<Map<String, Object>> getAllAcNameWithDepartment() {

        List<Entity> allEntities = repository.findAll();

        List<Map<String, Object>> response = new ArrayList<>();

        for (Entity entity: allEntities) {
            Map<String, Object> map = new HashMap<>();

            map.put("acName", entity.getAcName());
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
