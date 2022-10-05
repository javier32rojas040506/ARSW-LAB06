/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.service.persistence;

import edu.eci.arsw.blueprints.service.model.Blueprint;

import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author hcadavid
 */
public interface BlueprintsPersistence {
    
    /**
     * Method save a new blueprint
     * @param bp the new blueprint
     * @throws BlueprintPersistenceException if a blueprint with the same name already exists,
     *    or any other low-level persistence error occurs.
     */
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException;
    
    /**
     * Method that search a blueprint for one author and name
     * @param author blueprint's author
     * @param bprintname blueprint's name
     * @return the blueprint of the given name and author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String bprintname) throws BlueprintNotFoundException;

    /**
     * Method that search all the blueprints for one author
     * @param author the name of the author owner of blueprints
     * @return Set of BluePrints
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws  BlueprintNotFoundException;

    /**
     * Method that search all the blueprints
     * @return Set of BluePrints
     * @throws BlueprintPersistenceException if the blueprint already exists
     */
    public Set<Blueprint> getAllBlueprints() throws  BlueprintNotFoundException;

    /**
     * Method that save a given blueprint
     * @param bp blueprint
     * @throws BlueprintPersistenceException if the blueprint already exists
     */
    public void postBlueprint(Blueprint bp) throws BlueprintPersistenceException;

    /**
     * Method that modify a given blueprint
     * @param author blueprint's author
     * @param bpname blueprint's name
     * @param bp blueprint
     * @throws BlueprintPersistenceException if the blueprint doesn't exists
     */
    public void putBlueprint(String author, String bpname ,Blueprint bp) throws BlueprintPersistenceException;

    /**
     * Method that delete a given blueprint
     * @param author blueprint's author
     * @param bpname blueprint's name
     * @throws BlueprintPersistenceException if the blueprint doesn't exists
     */
    public void deleteBlueprint(String author, String bpname) throws BlueprintPersistenceException;
}
