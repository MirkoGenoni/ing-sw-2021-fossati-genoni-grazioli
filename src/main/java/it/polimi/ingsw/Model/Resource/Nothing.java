package it.polimi.ingsw.Model.Resource;

/**
 * Nothing class
 * @author Mirko
 */
public class Nothing implements Resource {
    String material;

    /**
     * The resource is initialized with his name
     */
    public Nothing(){
        this.material="nothing";
    }

    /**
     * @return the name of the resource
     */
    public String getMaterialType(){
        return material;
    }
}
