package it.polimi.ingsw.Model.Resource;

/**
 * Servant class
 * @author Mirko
 */
public class Servant implements Resource {
    String material;

    /**
     * The resource is initialized with his name
     */
    public Servant(){
        this.material="servant";
    }

    /**
     * @return the name of the resource
     */
    public String getMaterialType(){
        return material;
    }
}
