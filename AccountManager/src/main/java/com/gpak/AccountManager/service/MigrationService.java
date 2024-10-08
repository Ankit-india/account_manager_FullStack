//package com.gpak.AccountManager.service;
//
//import com.gpak.AccountManager.dao.AccountRepository;
//import com.gpak.AccountManager.dao.EntityRepository;
//import com.gpak.AccountManager.entity.Account;
//import com.gpak.AccountManager.entity.Entity;
//import com.gpak.AccountManager.entity.Entry;
//import com.gpak.AccountManager.helper.Convertor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class MigrationService {
//  @Autowired private EntityRepository entityRepository;
//
//  @Autowired private AccountRepository accountRepository;
//
//  @Autowired private Convertor convertor;
//
//  private String getKey(Entity entity) {
//    StringBuilder stringBuilder = new StringBuilder();
//
//    if (entity.getName() != null && !entity.getName().isEmpty()) {
//      stringBuilder.append(entity.getName());
//    }
//    stringBuilder.append("|");
//
//    if (entity.getDepartment() != null && !entity.getDepartment().isEmpty()) {
//      stringBuilder.append(entity.getDepartment());
//    }
//    stringBuilder.append("|");
//
//    if (entity.getThekedaarKaNaam() != null && !entity.getThekedaarKaNaam().isEmpty()) {
//      stringBuilder.append(entity.getThekedaarKaNaam());
//    }
//    stringBuilder.append("|");
//
//    if (entity.getAccountIdentifier() != null && !entity.getAccountIdentifier().isEmpty()) {
//      stringBuilder.append(entity.getAccountIdentifier());
//    }
//
//    return stringBuilder.toString();
//  }
//
//
//
//  private Entry getEntryObjFromEntity(Entity entity) {
//    Entry entry = new Entry();
//
//    entry.setAmount(entity.getAmount());
//    entry.setDate((LocalDate) convertor.convertValue(LocalDate.class, entity.getDate()));
//
//    return entry;
//  }
//  public void migrateEntityToAccount() {
//    List<Entity> entities = entityRepository.findAll();
//
//    List<Account> accountList = new ArrayList<>();
//    Map<String, List<Entry>> entityMap = new HashMap<>();
//
//    for (Entity entity : entities) {
//      String key = getKey(entity);
//      Entry entry = getEntryObjFromEntity(entity);
//
//      entityMap.computeIfAbsent(key, k -> new ArrayList<>()).add(entry);
//    }
//
//    for (Map.Entry<String, List<Entry>> entry : entityMap.entrySet()) {
//      Account account = new Account();
//      String[] keyParts = entry.getKey().split("\\|");
//
//      if (keyParts.length > 0 && !keyParts[0].isEmpty()) {
//        account.setName(keyParts[0]);
//      }
//      if (keyParts.length > 1 && !keyParts[1].isEmpty()) {
//        account.setDepartment(keyParts[1]);
//      }
//      if (keyParts.length > 2 && !keyParts[2].isEmpty()) {
//        account.setThekedaarKaNaam(keyParts[2]);
//      }
//      if (keyParts.length > 3 && !keyParts[3].isEmpty()) {
//        account.setAccountIdentifier(keyParts[3]);
//      }
//      account.setEntryList(entry.getValue());
//
//      accountList.add(account);
//    }
//    System.out.println(accountList);
//    accountRepository.saveAll(accountList);
//  }
//}
