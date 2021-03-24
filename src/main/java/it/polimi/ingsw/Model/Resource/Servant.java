package it.polimi.ingsw.Model.Resource;

public class Servant implements Resource {
    String material;

    public Servant(){
        this.material="servant";
    }

    public String GetMaterialType(){
        return material;
    }
}
