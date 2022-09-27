/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.service.services;


import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import edu.eci.arsw.blueprints.service.filters.Filter;
import edu.eci.arsw.blueprints.service.model.Blueprint;
import edu.eci.arsw.blueprints.service.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.service.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.service.persistence.BlueprintsPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class BlueprintsServices {
   
    @Autowired
    BlueprintsPersistence bpp=null;
    @Autowired
    Filter bpf;
    
    public void addNewBlueprint(Blueprint bp){
        try {
            bpp.saveBlueprint(bp);
        }catch (Exception e){
            throw new UnsupportedOperationException("Error with the operation on services.");
        }
    }

    /**
     * Method that search all the blueprints
     * @return Set of BluePrints
     * @throws BlueprintNotFoundException if the blueprint already exists
     */
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
        Set<Blueprint> blueprints;
        Set<Blueprint> blueprintsFiltered = new HashSet<>();
        try {
            blueprints = bpp.getAllBlueprints();
            for(Blueprint bp: blueprints){
                bp = bpf.filterPoints(bp);
                blueprintsFiltered.add(bp);
            }
        }catch (BlueprintNotFoundException e){
            throw e;
        }
        return blueprintsFiltered;
    }
    
    /**
     * 
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String name) throws BlueprintNotFoundException {
        Blueprint blueprint;
        try {
             blueprint = bpp.getBlueprint(author, name);
             blueprint =  bpf.filterPoints(blueprint);
        }catch (Exception e){
            throw new UnsupportedOperationException("Error with the operation on services.");
        }
        return blueprint;
    }

    /**
     * Method that search all the blueprints for one author
     * @param author the name of the author owner of blueprints
     * @return Set of BluePrints
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException{
        Set<Blueprint> blueprints;
        Set<Blueprint> blueprintsFiltered = new HashSet<>();
        try {
            blueprints = bpp.getBlueprintsByAuthor(author);
            for(Blueprint bp: blueprints){
                bp = bpf.filterPoints(bp);
                blueprintsFiltered.add(bp);
            }
        }catch (BlueprintNotFoundException e){
            throw e;
        }
        return blueprintsFiltered;
    }

    /**
     * Method that search a blueprint for one author and name
     * @param author blueprint's author
     * @param bpname blueprint's name
     * @return the blueprint of the given name and author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Set<Blueprint> getBlueprintsByAuthorBpName(String author, String bpname) throws BlueprintNotFoundException{
        Set<Blueprint> blueprints;
        Set<Blueprint> blueprintsFiltered = new HashSet<>();
        try {
            System.out.println(bpname);
            blueprints = bpp.getBlueprintsByAuthor(author);
            for(Blueprint bp: blueprints){
                System.out.println(bp.getName());
                if (bp.getName().equals(bpname)) {
                    bp = bpf.filterPoints(bp);
                    blueprintsFiltered.add(bp);
                }
            }
        }catch (BlueprintNotFoundException e){
            throw e;
        }
        return blueprintsFiltered;
    }

    /**
     * Method that save a given blueprint
     * @param bp blueprint
     * @throws BlueprintPersistenceException if the blueprint already exists
     */
    public void postBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        bpp.postBlueprint(bp);
    }

    /**
     * Method that modify a given blueprint
     * @param author blueprint's author
     * @param bpname blueprint's name
     * @param bp blueprint
     * @throws BlueprintPersistenceException if the blueprint doesn't exists
     */
    public void putBlueprint(String author, String bpname ,Blueprint bp) throws BlueprintPersistenceException {
        bpp.putBlueprint(author, bpname, bp);
    }
}
