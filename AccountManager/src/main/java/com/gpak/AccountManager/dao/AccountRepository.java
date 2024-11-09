package com.gpak.AccountManager.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.gpak.AccountManager.entity.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRepository.class);

    public boolean save(Account Account) {
        Account savedAccount = null;
        try {
            savedAccount = mongoTemplate.save(Account);
            return savedAccount != null && savedAccount.getId() != null;
        } catch (DataAccessException e) {
            LOGGER.error("Error occurred while saving the Account: {}", e.getMessage());
            return false;
        } finally {
            if (savedAccount != null) {
                LOGGER.info("Saved record ID: {}", savedAccount.getId());
            } else {
                LOGGER.info("Account could not be saved.");
            }
        }
    }

    public void saveAll(List<Account> entities) {
        try {
            mongoTemplate.insertAll(entities);
            LOGGER.info("Entities successfully saved");
        } catch (DataAccessException e) {
            LOGGER.error("Error occurred while saving entities: " + e.getMessage());
        }
    }

    public List<Account> findAll() {
        try {
            List<Account> entities = mongoTemplate.findAll(Account.class);
            return entities.isEmpty() ? null : entities;
        } catch (DataAccessException e) {
            LOGGER.error("Error occurred while fetching all entities: " + e.getMessage());
        }
        return null;
    }

    public Optional<Account> findById(String id) {
        try {
            Account Account = mongoTemplate.findById(id, Account.class);
            return Optional.ofNullable(Account);
        } catch (DataAccessException e) {
            LOGGER.error("Error occurred while fetching the Account by ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    public boolean deleteById(String id) {
        try {
            Optional<Account> AccountToDelete = findById(id);
            if (AccountToDelete.isPresent()) {
                mongoTemplate.remove(AccountToDelete.get());
                return true;
            }
            return false;
        } catch (DataAccessException e) {
            LOGGER.error("Error occurred while deleting the Account by ID: " + e.getMessage());
            return false;
        }
    }

    public List<Account> findByAcNameContainingIgnoreCase(String keyword) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("name").regex(".*" + keyword + ".*", "i"));
            return mongoTemplate.find(query, Account.class);
        } catch (DataAccessException e) {
            LOGGER.error("Error occurred while fetching entities by acName: " + e.getMessage());
            return null;
        }
    }

    public List<Account> findByEntryListDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("entryList.date").gte(startDate).lte(endDate));
            return mongoTemplate.find(query, Account.class);
        } catch (DataAccessException e) {
            LOGGER.error("Error occurred while fetching accounts by entry date range: {}", e.getMessage());
            return null;
        }
    }

    public List<Account> findByEntryListDateBetweenAndThekedaarKaNaam(LocalDate startDate, LocalDate endDate, String thekedaarKaNaam) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("entryList.date").gte(startDate).lte(endDate));
            query.addCriteria(Criteria.where("thekedaarKaNaam").is(thekedaarKaNaam));
            return mongoTemplate.find(query, Account.class);
        } catch (DataAccessException e) {
            LOGGER.error("Error occurred while fetching accounts by entry date range and contractor name: {}", e.getMessage());
            return null;
        }
    }

    public Account getAllAccountsWithDepartment(LocalDate startDate, LocalDate endDate, String acName, String thekedaarKaNaam, String dept, String acIdentifier) {
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("entryList.date").gte(startDate).lte(endDate));
            query.addCriteria(Criteria.where("thekedaarKaNaam").is(thekedaarKaNaam != null ? thekedaarKaNaam : ""));
            query.addCriteria(Criteria.where("department").is(dept));
            query.addCriteria(Criteria.where("name").is(acName));
            query.addCriteria(Criteria.where("accountIdentifier").is(acIdentifier != null ? acIdentifier : ""));

            List<Account> accountList = mongoTemplate.find(query, Account.class);
            return accountList.get(0);
        } catch (Exception e) {
            LOGGER.error("Error occurred while fetching accounts by entry date range and contractor name: {}", e.getMessage());
            return null;
        }
    }
}
