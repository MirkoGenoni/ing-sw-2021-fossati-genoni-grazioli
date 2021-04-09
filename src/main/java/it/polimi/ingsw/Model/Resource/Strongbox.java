package it.polimi.ingsw.Model.Resource;

import it.polimi.ingsw.Model.Exceptions.ResourceException;

import java.util.HashMap;
import java.util.Map;

/**
 * Strongbox class
 * @author Mirko Genoni
 */
public class Strongbox {
    Map<Resource, Integer> materials;

    /**
     * initializes the strongbox adding every resource in the resource enum and setting the relative int to 0
     */
    Strongbox() {
        this.materials = new HashMap<>();

        for (Resource r : Resource.values()) {
            materials.put(r, 0);
        }
    }

    /**
     * @return a copy of the current strongbox state
     */
    public Map<Resource, Integer> getQuantity() {
        return new HashMap<>(materials);
    }

    /**
     * @param required is a string that identifies the material required
     * @return a single material of the required type
     */
    public int getQuantity(Resource required) {
        return materials.get(required);
    }

    /**
     * @param received is a map with keys from the resource enum and the quantity of the relative material to add
     */
    public void addMaterials(Map<Resource, Integer> received) {
        for (Resource r : Resource.values()) {
            materials.put(r, materials.get(r) + received.get(r));
        }
    }

    /**
     * @param required is a map with keys from the resource enum and the quantity of the relative material to remove
     */
    public void returnMaterials(Map<Resource, Integer> required) throws ResourceException {
        for (Resource r : Resource.values()) {
            if (materials.get(r) - required.get(r) < 0)
                throw new ResourceException("Trying to put negative Materials inside strongbox");
        }

        for (Resource r1 : Resource.values()) {
            materials.put(r1, materials.get(r1) - required.get(r1));
        }
    }
}