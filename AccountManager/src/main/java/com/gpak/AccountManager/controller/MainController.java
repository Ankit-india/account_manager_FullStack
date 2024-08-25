package com.gpak.AccountManager.controller;

import com.gpak.AccountManager.entity.Entity;
import com.gpak.AccountManager.service.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class MainController {

    @Autowired
    private Services service;

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @PostMapping("add")
    public ResponseEntity<?> addEntity(@RequestBody Map<String, Object> entity) {
        LOGGER.info("Add Entity API has been called !!!");
        Map<String, Object> response = service.addNewEntry(entity);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/acNameWithDepartment")
    public ResponseEntity<?> getAllAcNameWithDepartment() {
        LOGGER.info("getAllAcNameWithDepartment API has been called !!!");
        List<Map<String, Object>> response = service.getAllAcNameWithDepartment();

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }
    @GetMapping
    public ResponseEntity<List<Entity>> getAllEntitys() {
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
//    public ResponseEntity<Entity> updateEntity(@PathVariable String id, @RequestBody Entity Entity) {
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
