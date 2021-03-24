package it.polimi.ingsw.Model.Resource;

public class Shield implements Resource {
    String material;

    public Shield(){
        this.material="shield";
    }

    public String GetMaterialType(){
        return material;
    }
}
