package com.gpak.AccountManager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpak.AccountManager.entity.Account;
import com.gpak.AccountManager.entity.Entry;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CsvService implements CsvServices {

  @Autowired private ObjectMapper objectMapper;
  @Autowired private Services services;

  public void writeJsonToCsv(List<Account> accountList, String csvFilePath) throws IOException {
    // Prepare data for CSV
    List<String[]> dataList = new ArrayList<>();

    String[] header = {
      "date", "name", "accountIdentifier", "department", "thekedaarKaNaam", "amount", "isPaid"
      // "isActive",
      // "time"
    };
    dataList.add(header);

    // Populate data for CSV
    for (Account account : accountList) {
      for (Entry entry : account.getEntryList()) {
        String[] row =
            new String[] {
              // account.getId(),
              entry.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
              account.getName(),
              account.getAccountIdentifier(),
              account.getDepartment(),
              account.getThekedaarKaNaam(),
              String.valueOf(entry.getAmount()),
              // entry.getDate().toString(),
              String.valueOf(entry.isPaid()),
              // String.valueOf(account.isActive()),
              // String.valueOf(account.getTime())
            };
        dataList.add(row);
      }
    }

    // Write data to CSV
    try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
      writer.writeAll(dataList);
    }
  }

  // Method to write a List of Account objects to CSV
  public void writeJsonToCsvTotalAmt(List<Account> accountList, String csvFilePath)
      throws IOException {
    List<String[]> dataList = new ArrayList<>();

    String[] header = {"name", "total_amount"};
    dataList.add(header);

    for (Account account : accountList) {
      int totalAmount = account.getEntryList().stream().mapToInt(Entry::getAmount).sum();

      String concatenatedName =
          account.getName()
              + (account.getAccountIdentifier() != null
                  ? " " + account.getAccountIdentifier()
                  : "");
      //      String concatenatedName = account.getName() + (account.getAccountIdentifier() != null
      // ? " " + account.getAccountIdentifier() + " " : "") + "(" + account.getThekedaarKaNaam() +
      // ")";

      String[] row = new String[] {concatenatedName, String.valueOf(totalAmount)};
      dataList.add(row);
    }
    CSVWriter writer = null;
    try {
      writer = new CSVWriter(new FileWriter(csvFilePath));
      writer.writeAll(dataList);
    } finally {
      writer.close();
    }
  }

  @Override
  public void getCsv(LocalDate startDate, LocalDate endDate) {
    List<Account> accountList = services.getEntitiesForDateRange(startDate, endDate);
    try {
      writeJsonToCsv(accountList, "C:/Users/kitan/Desktop/output.csv");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void getCsv(LocalDate startDate, LocalDate endDate, String thekedaarKaNaam) {
    List<Account> accountList =
        services.getEntitiesForDateRangeAndContractor(startDate, endDate, thekedaarKaNaam);
    try {
      writeJsonToCsv(accountList, "output_contractor.csv");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
