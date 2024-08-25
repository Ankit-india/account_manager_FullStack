package com.gpak.AccountManager.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class AppConfig {

    @Autowired
    private PropertyConfig propertyConfig;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(propertyConfig.getMongoDbUrl()) ;
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(mongoClient(), propertyConfig.getMongoDbName()));
    }
}
