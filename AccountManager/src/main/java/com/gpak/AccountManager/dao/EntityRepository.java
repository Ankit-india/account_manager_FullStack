//package com.gpak.AccountManager.dao;
//
//import com.gpak.AccountManager.entity.Entity;
//import java.util.List;
//import java.util.Optional;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class EntityRepository {
//
//  @Autowired private MongoTemplate mongoTemplate;
//
//  private static final Logger LOGGER = LoggerFactory.getLogger(EntityRepository.class);
//
//  public List<Entity> findAll() {
//    try {
//      List<Entity> entities = mongoTemplate.findAll(Entity.class);
//      return entities.isEmpty() ? null : entities;
//    } catch (DataAccessException e) {
//      LOGGER.error("Error occurred while fetching all entities: " + e.getMessage());
//    }
//    return null;
//  }
//
//  public Optional<Entity> findById(String id) {
//    try {
//      Entity entity = mongoTemplate.findById(id, Entity.class);
//      return Optional.ofNullable(entity);
//    } catch (DataAccessException e) {
//      LOGGER.error("Error occurred while fetching the entity by ID: " + e.getMessage());
//      return Optional.empty();
//    }
//  }
//}
