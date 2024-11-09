package com.gpak.AccountManager.service;

import java.time.LocalDate;

public interface CsvServices {

   void getCsv(LocalDate startDate, LocalDate endDate);

  //     void getCsv(LocalDate startDate, LocalDate endDate);
   void getCsv(LocalDate startDate, LocalDate endDate, String thekedaarKaNaam);

   void getCsvSingleAccount(LocalDate startDate, LocalDate endDate,  String accountName, String thekedaarKaNaam, String department, String acIdentifier);

   void getCsvDeptWiseNameList(LocalDate stDate, LocalDate endDate, String department);
}
