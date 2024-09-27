package com.gpak.AccountManager.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "Entity")
@Data
public class Entity {

  private String id;

  private String name;

  private String department;

  private String thekedaarKaNaam;

  private int amount;

  private String acIdentification;

  private String isPaid;

  private LocalDate date;

  private String isActive;

  private Long time;
}
