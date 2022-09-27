/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import edu.eci.arsw.blueprints.service.model.Blueprint;
import edu.eci.arsw.blueprints.service.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.service.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.service.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author hcadavid
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {
    @Autowired
    BlueprintsServices bps = null;
    @RequestMapping(method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBlueprints(){
        try {
            Set<Blueprint> bp = bps.getAllBlueprints();
            return new ResponseEntity<>(new Gson().toJson(bp),HttpStatus.OK);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/{author}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAuthorBlueprint(@PathVariable String author){
        try {
            Set<Blueprint> bp = bps.getBlueprintsByAuthor(author);
            return new ResponseEntity<>(new Gson().toJson(bp),HttpStatus.OK);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/{author}/{bpname}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAuthorBlueprintName(@PathVariable String author, @PathVariable String bpname){
        try {
            Set<Blueprint> bp = bps.getBlueprintsByAuthorBpName(author, bpname);
            return new ResponseEntity<>(new Gson().toJson(bp),HttpStatus.OK);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postBlueprint(@RequestBody String bp){
        try {
            Blueprint blueprint = new Gson().fromJson(bp, Blueprint.class);
            bps.postBlueprint(blueprint);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error this blueprint already exists, try with a PUT",HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{author}/{bpname}")
    public ResponseEntity<?> putBlueprint(@PathVariable String author, @PathVariable String bpname, @RequestBody String bp){
        try {
            Blueprint blueprint = new Gson().fromJson(bp, Blueprint.class);
            bps.putBlueprint(author, bpname, blueprint);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error this blueprint doesn't exists",HttpStatus.FORBIDDEN);
        }
    }
}
