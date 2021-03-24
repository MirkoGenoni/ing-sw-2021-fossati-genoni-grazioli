package it.polimi.ingsw.Model.Resource;

public class Faith implements Resource {
    String material;

    public Faith(){
        this.material="faith";
    }

    public String GetMaterialType(){
        return material;
    }
}
