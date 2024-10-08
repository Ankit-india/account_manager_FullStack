package com.gpak.AccountManager.service;

import com.gpak.AccountManager.entity.Account;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface Services {

  Map<String, Object> addNewEntry(Map<String, Object> entity) throws Exception;

  List<Map<String, Object>> getAllAcNameWithDepartment(String keyword);

  List<Account> getAllEntities();

  List<Account> getEntitiesForDateRange(LocalDate startDate, LocalDate endDate);

  List<Account> getEntitiesForDateRangeAndContractor(
      LocalDate startDate, LocalDate endDate, String thekedaarKaNaam);

  int getTotalAmountForDateRange(String acName);
  
}
