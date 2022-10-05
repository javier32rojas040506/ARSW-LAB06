/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.service.persistence.impl;

import edu.eci.arsw.blueprints.service.model.Blueprint;
import edu.eci.arsw.blueprints.service.model.Point;
import edu.eci.arsw.blueprints.service.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.service.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.service.persistence.BlueprintsPersistence;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

    private final ConcurrentHashMap<Tuple<String,String>, Blueprint> blueprints=new ConcurrentHashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Point[] pts1=new Point[]{new Point(0, 0),new Point(10, 11)};
        Blueprint bp=new Blueprint("_authorname_", "_bpname_",pts);
        Blueprint bp1=new Blueprint("pedro","casa38", pts1);
        Blueprint bp2=new Blueprint("juan","casa39", pts1);
        Blueprint bp3=new Blueprint("pedro","casa40", pts1);
        Blueprint bp4=new Blueprint("pedro","casa41", pts1);
        Blueprint bp5=new Blueprint("juan","casa42", pts1);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        blueprints.put(new Tuple<>(bp1.getAuthor(),bp1.getName()), bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);
        blueprints.put(new Tuple<>(bp4.getAuthor(),bp4.getName()), bp4);
        blueprints.put(new Tuple<>(bp5.getAuthor(),bp5.getName()), bp5);
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> authorBlueprints = new HashSet<>();
        for (Tuple<String,String> key: blueprints.keySet()){
            if(key.o1.equals(author)){
                authorBlueprints.add(getBlueprint(author, key.o2));
            }
        }
        if (authorBlueprints.size() == 0) throw new BlueprintNotFoundException("Autor no encontrado");
        return authorBlueprints;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
        Set<Blueprint> authorBlueprints = new HashSet<>();
        for (Tuple<String,String> key: blueprints.keySet()){
            authorBlueprints.add(getBlueprint(key.o1, key.o2));
        }
        return authorBlueprints;
    }

    @Override
    public void postBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))) throw new BlueprintPersistenceException("This object already exists");
        else {
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void putBlueprint(String author, String bpname ,Blueprint bp) throws  BlueprintPersistenceException {
        if (!blueprints.containsKey(new Tuple<>(author,bpname))) throw new BlueprintPersistenceException("This object doesn't exists");
        else
        {
            blueprints.replace(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void deleteBlueprint(String author, String bpname) throws  BlueprintPersistenceException {
        if (!blueprints.containsKey(new Tuple<>(author,bpname))) throw new BlueprintPersistenceException("This object doesn't exists");
        else
        {
            blueprints.remove(new Tuple<>(author,bpname));
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
