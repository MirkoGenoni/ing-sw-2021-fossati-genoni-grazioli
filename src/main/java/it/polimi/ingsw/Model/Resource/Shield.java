package it.polimi.ingsw.Model.Resource;

/**
 * Shield class
 * @author Mirko
 */
public class Shield implements Resource {
    String material;

    /**
     * The resource is initialized with his name
     */
    public Shield(){
        this.material="shield";
    }

    /**
     * @return the name of the resource
     */
    public String getMaterialType(){
        return material;
    }
}
