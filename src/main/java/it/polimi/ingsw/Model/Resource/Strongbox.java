package it.polimi.ingsw.Model.Resource;

import it.polimi.ingsw.Model.Exceptions.ResourceException;

import java.util.HashMap;
import java.util.Map;

/**
 * Strongbox class
 * @author Mirko Genoni
 */
public class Strongbox {
    Map<String, Integer> materials;

    /**
     * initializes the strongbox adding every resource in the resource enum and setting the relative int to 0
     */
    Strongbox() {
        this.materials = new HashMap<>();

        for (Resource r : Resource.values()) {
            materials.put(r.toString(), 0);
        }
    }

    /**
     * @return a copy of the current strongbox state
     */
    public Map<String, Integer> getQuantity() {
        return new HashMap<>(materials);
    }

    /**
     * @param required is a string that identifies the material required
     * @return a single material of the required type
     */
    public int getQuantity(String required) {
        return materials.get(required);
    }

    /**
     * @param received is a map with keys from the resource enum and the quantity of the relative material to add
     */
    public void addMaterials(Map<String, Integer> received) {
        for (Resource r : Resource.values()) {
            materials.put(r.toString(), materials.get(r.toString()) + received.get(r.toString()));
        }
    }

    /**
     * @param required is a map with keys from the resource enum and the quantity of the relative material to remove
     */
    public void returnMaterials(Map<String, Integer> required) throws ResourceException {
        for (Resource r : Resource.values()) {
            if (materials.get(r.toString()) - required.get(r.toString()) < 0)
                throw new ResourceException("Trying to put negative Materials inside strongbox");
        }

        for (Resource r1 : Resource.values()) {
            materials.put(r1.toString(), materials.get(r1.toString()) - required.get(r1.toString()));
        }
    }
}