package edu.eci.arsw.blueprints.service.filters;

import edu.eci.arsw.blueprints.service.model.Blueprint;

/**
 * @author Juan Camilo Rojas y Francisco Rojas
 */
public interface Filter {
    /**
     * Method that filter and delete points
     * @param bp is teh Blueprint to apply the filter
     * @return Blueprint filtered
     */
    public Blueprint filterPoints(Blueprint bp);
}
