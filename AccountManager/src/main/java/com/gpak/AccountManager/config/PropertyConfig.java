package com.gpak.AccountManager.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class PropertyConfig {

  @Value("${mongodb.url}")
  private String mongoDbUrl;

  @Value("${mongodb.name}")
  private String mongoDbName;
}
