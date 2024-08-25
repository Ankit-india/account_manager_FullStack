package com.gpak.AccountManager.service;

import com.gpak.AccountManager.entity.Entity;

import java.util.List;
import java.util.Map;

public interface Services {

    public Map<String, Object> addNewEntry(Map<String,Object> entity);

    public List<Map<String, Object>> getAllAcNameWithDepartment();
    public List<Entity> getAllEntities();

    public List<Entity> getEntitiesForDateRange(String stDate, String endDate);

    public int getTotalAmountForDateRange(String acName);
//    public Entity getAllEntitie();
}
