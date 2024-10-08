package com.gpak.AccountManager.service;

import java.time.LocalDate;

public interface CsvServices {

   void getCsv(LocalDate startDate, LocalDate endDate);

  //     void getCsv(LocalDate startDate, LocalDate endDate);
   void getCsv(LocalDate startDate, LocalDate endDate, String thekedaarKaNaam);
}
