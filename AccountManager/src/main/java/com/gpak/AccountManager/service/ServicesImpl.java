package com.gpak.AccountManager.service;

import com.gpak.AccountManager.dao.AccountRepository;
import com.gpak.AccountManager.entity.Account;
import com.gpak.AccountManager.entity.Entry;
import com.gpak.AccountManager.helper.Convertor;
import com.gpak.AccountManager.security.EntityMapValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicesImpl implements Services {

  //  @Autowired private EntitRepository repository;
  @Autowired private AccountRepository repository;

  @Autowired private Convertor convertor;

  @Autowired private EntityMapValidator entityMapValidator;

  private static final Logger LOGGER = LoggerFactory.getLogger(Services.class);

  //  @Override
  //  public Map<String, Object> addNewEntry(Map<String, Object> entityMap) throws Exception {
  //    LOGGER.info("entityMap : {}", entityMap.toString());
  //    if (entityMapValidator.validateEntityMapToEntity(entityMap)) {
  //      // Entity entity = convertor.convertMapToEntity(entityMap);
  //      Account entity = convertor.convertMapToEntity(entityMap);
  //
  //      LOGGER.info("entity : {}", entity.toString());
  //
  //      Map<String, Object> response = new HashMap<>();
  //      if (repository.save(entity)) {
  //        response.put("message", "saved");
  //        response.put("status", 200);
  //      } else {
  //        response.put("message", "not saved");
  //      }
  //      return response;
  //    } else {
  //      throw new Exception("payload body does not poses the intended required values");
  //    }
  //  }

  @Override
  public Map<String, Object> addNewEntry(Map<String, Object> entityMap) throws Exception {
    LOGGER.info("entityMap : {}", entityMap.toString());

    if (entityMapValidator.validateEntityMapToEntity(entityMap)) {
      String id = (String) entityMap.get("id");
      Account entity = null;
      List<Entry> entryList = null;
      if (id != null && !id.isEmpty()) {
        // Attempt to find existing entity by id
        entity = repository.findById(id).orElse(new Account());
        entityMap.put("id", id); // Ensure the id is set
        entryList =
            entity != null && !entity.getEntryList().isEmpty() ? entity.getEntryList() : null;
      }

      entity = convertor.convertMapToEntity(entityMap);
      entryList = setEntryFromEntityMap(entityMap, entryList);
      entity.setEntryList(entryList);
      LOGGER.info("entity : {}", entity.toString());

      // Save or update entity
      boolean savedEntity = repository.save(entity);

      Map<String, Object> response = new HashMap<>();
      if (savedEntity) {
        response.put("message", "saved");
        response.put("status", 200);
      } else {
        response.put("message", "not saved");
      }

      return response;
    } else {
      throw new Exception("payload body does not pose the intended required values");
    }
  }

  private List<Entry> setEntryFromEntityMap(Map<String, Object> entityMap, List<Entry> entryList) {
    if (entryList == null) {
      entryList = new ArrayList<>();
    }

    Entry entry = new Entry();
    entry.setAmount(Integer.parseInt(entityMap.get("amount").toString()));
    //    entry.setDate(LocalDate.parse(entityMap.get("date").toString()));
    entry.setDate((LocalDate) convertor.convertValue(LocalDate.class, entityMap.get("date")));
    entryList.add(entry);
    return entryList;
  }

  @Override
  public List<Map<String, Object>> getAllAcNameWithDepartment(String keyword) {
    List<Account> filteredEntities = repository.findByAcNameContainingIgnoreCase(keyword);

    List<Map<String, Object>> response = new ArrayList<>();

    for (Account entity : filteredEntities) {
      Map<String, Object> map = new HashMap<>();
      map.put("acName", (entity.getName())); // .concat(" ").concat(entity.getAcIdentification())
      map.put("department", entity.getDepartment());
      map.put("thekedaarKaNaam", entity.getThekedaarKaNaam());
      map.put("acIdentification", entity.getAccountIdentifier());
      map.put("id", entity.getId());
      response.add(map);
    }
    Set<Map<String, Object>> uniqueObjects = new HashSet<>(response);
    response = new ArrayList<>(uniqueObjects);
    return response;
  }

  @Override
  public List<Account> getAllEntities() {
    return repository.findAll();
  }

  @Override
  public List<Account> getEntitiesForDateRange(LocalDate startDate, LocalDate endDate) {
    LocalDateTime startDateTime = startDate.atStartOfDay();
    LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

    List<Account> accounts = repository.findByEntryListDateBetween(startDateTime, endDateTime);

    return accounts;
  }

  @Override
  public List<Account> getEntitiesForDateRangeAndContractor(
      LocalDate startDate, LocalDate endDate, String thekedaarKaNaam) {

    List<Account> accounts =
        repository.findByEntryListDateBetweenAndThekedaarKaNaam(
            startDate, endDate, thekedaarKaNaam);
    getAllEntryDataRange(startDate, endDate, accounts);
    return accounts;
  }

  private void getAllEntryDataRange(LocalDate startDate, LocalDate endDate, List<Account> accountList) {
    List<Entry> filteredEntries = new ArrayList<>();

    for (Account account : accountList) {
      List<Entry> entriesInRange = account.getEntryList().stream()
              .filter(entry -> !entry.getDate().isBefore(startDate) && !entry.getDate().isAfter(endDate))
              .collect(Collectors.toList());

      account.getEntryList().removeIf(entry -> entry.getDate().isBefore(startDate) || entry.getDate().isAfter(endDate));

      filteredEntries.addAll(entriesInRange);
    }
  }


  @Override
  public int getTotalAmountForDateRange(String acName) {
    return 0;
  }
}
