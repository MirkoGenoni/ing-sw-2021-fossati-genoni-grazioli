package it.polimi.ingsw.model.resource;

/**
 * Resource Enum Class
 * @author Mirko Genoni
 *
 * This enum collects a group of String costants necessary to identify different type of resources
 */
public enum  Resource {
    COIN("coin"),
    SERVANT("servant"),
    SHIELD("shield"),
    STONE("stone");

    private final String material;

    Resource(String material) {
        this.material=material;
    }
}