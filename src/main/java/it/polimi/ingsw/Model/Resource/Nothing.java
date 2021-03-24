package it.polimi.ingsw.Model.Resource;

public class Nothing implements Resource {
    String material;

    public Nothing(){
        this.material="nothing";
    }

    public String GetMaterialType(){
        return material;
    }
}
