package com.gpak.AccountManager.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Account")
@Data
public class Account {

  private String id;

  private String name;

  private String department;

  private String thekedaarKaNaam;

  private String accountIdentifier;

  private List<Entry> entryList;

  private boolean isActive;

  private Long time;
}
