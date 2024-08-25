package com.gpak.AccountManager.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Entity")
@Data
public class Entity {

    private String id;

    private String acName;

    private int amount;

    private String department;

    private String acIdentification;

    private Long time;
}
