package com.gpak.AccountManager.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Entry {
  private int amount;

  private LocalDate date;

  private boolean isPaid;
}
