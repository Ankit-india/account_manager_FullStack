package com.gpak.AccountManager.dao;

import com.gpak.AccountManager.entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EntityRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityRepository.class);

    public boolean save(Entity entity) {
        Entity savedEntity = null;
        try {
            savedEntity = mongoTemplate.save(entity);
            return savedEntity != null && savedEntity.getId() != null;
        } catch (DataAccessException e) {
            LOGGER.error("Error occurred while saving the entity: {}", e.getMessage());
            return false;
        } finally {
            if (savedEntity != null) {
                LOGGER.info("Saved record ID: {}", savedEntity.getId());
            } else {
                LOGGER.info("Entity could not be saved.");
            }
        }
    }

    public List<Entity> findAll() {
        try {
            List<Entity> entities = mongoTemplate.findAll(Entity.class);
            return entities.isEmpty() ? null : entities;
        } catch (DataAccessException e) {
            LOGGER.error("Error occurred while fetching all entities: " + e.getMessage());
        }
        return null;
    }

    public Optional<Entity> findById(String id) {
        try {
            Entity entity = mongoTemplate.findById(id, Entity.class);
            return Optional.ofNullable(entity);
        } catch (DataAccessException e) {
            LOGGER.error("Error occurred while fetching the entity by ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    public boolean deleteById(String id) {
        try {
            Optional<Entity> entityToDelete = findById(id);
            if (entityToDelete.isPresent()) {
                mongoTemplate.remove(entityToDelete.get());
                return true;
            }
            return false;
        } catch (DataAccessException e) {
            LOGGER.error("Error occurred while deleting the entity by ID: " + e.getMessage());
            return false;
        }
    }
}
