package it.polimi.ingsw.Model.Resource;

/**
 * Coin class
 * @author Mirko
 */
public class Coin implements Resource {
    String material;

    /**
     * The resource is initialized with his name
     */
    public Coin(){
        this.material="coin";
    }

    /**
     * @return the name of the resource
     */
    public String getMaterialType(){
        return material;
    }
}