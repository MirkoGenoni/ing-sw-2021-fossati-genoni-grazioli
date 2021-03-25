package it.polimi.ingsw.Model.Resource;

/**
 * Stone class
 * @author Mirko
 */
public class Stone implements Resource {
    String material;

    /**
     * The resource is initialized with his name
     */
    public Stone(){
        this.material="stone";
    }

    /**
     * @return the name of the resource
     */
    public String getMaterialType(){
        return material;
    }
}
