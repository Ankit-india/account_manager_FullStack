package com.gpak.AccountManager.service;

import java.time.LocalDate;

public interface CsvServices {

   void getCsv(LocalDate startDate, LocalDate endDate);

  //     void getCsv(LocalDate startDate, LocalDate endDate);
   void getCsv(LocalDate startDate, LocalDate endDate, String thekedaarKaNaam);

   void getCsvDeptWiseNameList(LocalDate stDate, LocalDate endDate, String department);
}
