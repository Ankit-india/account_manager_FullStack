package com.gpak.AccountManager.controller;

import com.gpak.AccountManager.entity.Account;
import com.gpak.AccountManager.service.CsvServices;
import com.gpak.AccountManager.service.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class MainController {

  @Autowired private Services service;

  @Autowired private CsvServices csvServices;

  //  @Autowired private MigrationService migrationService;

  private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

  //  @PostMapping("migration")
  //  public void migrate() {
  //    migrationService.migrateEntityToAccount();
  //  }

  @GetMapping("service/csv")
  public ResponseEntity<?> getCsv(
      @RequestParam(name = "startDate", required = true) LocalDate startDate,
      @RequestParam(name = "endDate", required = true) LocalDate endDate,
      @RequestParam(name = "contractorName", required = false) String contractorName) {
    if (contractorName != null && !contractorName.isEmpty()) {
      csvServices.getCsv(startDate, endDate, contractorName);
    } else {
      csvServices.getCsv(startDate, endDate);
    }
    return new ResponseEntity<>("file will download in few minutes.", HttpStatus.OK);
  }

  @PostMapping("add")
  public ResponseEntity<?> addEntity(@RequestBody Map<String, Object> entity) throws Exception {
    LOGGER.info("Add Entity API has been called !!!");
    Map<String, Object> response = service.addNewEntry(entity);

    return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
  }

  @GetMapping("/acNameWithDepartment")
  public ResponseEntity<?> getAllAcNameWithDepartment(
      @RequestParam(value = "keyword", required = true) String keyword) {
    LOGGER.info("getAllAcNameWithDepartment API has been called !!!");
    List<Map<String, Object>> response = service.getAllAcNameWithDepartment(keyword);

    return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
  }

  //  @GetMapping
  //  public ResponseEntity<List<Entity>> getAllEntitys() {
  //    return ResponseEntity.ok(service.getAllEntities());
  //  }
  @GetMapping
  public ResponseEntity<List<Account>> getAllEntitys() {
    return ResponseEntity.ok(service.getAllEntities());
  }

  //    @GetMapping("/{id}")
  //    public ResponseEntity<Entity> getEntityById(@PathVariable String id) {
  //        return Entity.getEntityById(id)
  //                .map(ResponseEntity::ok)
  //                .orElse(ResponseEntity.notFound().build());
  //    }
  //
  //    @PutMapping("/{id}")
  //    public ResponseEntity<Entity> updateEntity(@PathVariable String id, @RequestBody Entity
  // Entity) {
  //        Entity updatedEntity = Entity.updateEntity(id, Entity);
  //        if (updatedEntity != null) {
  //            return ResponseEntity.ok(updatedEntity);
  //        } else {
  //            return ResponseEntity.notFound().build();
  //        }
  //    }
  //
  //    @DeleteMapping("/{id}")
  //    public ResponseEntity<Void> deleteEntity(@PathVariable String id) {
  //        Entity.deleteEntity(id);
  //        return ResponseEntity.noContent().build();
  //    }
}
