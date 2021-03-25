package it.polimi.ingsw.Model.Resource;

/**
 * Faith class
 * @author Mirko
 */
public class Faith implements Resource {
    String material;

    /**
     * The resource is initialized with his name
     */
    public Faith(){
        this.material="faith";
    }

    /**
     * @return the name of the resource
     */
    public String getMaterialType(){
        return material;
    }
}
