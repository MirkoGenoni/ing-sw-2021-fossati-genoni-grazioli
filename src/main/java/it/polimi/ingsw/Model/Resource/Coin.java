package it.polimi.ingsw.Model.Resource;

public class Coin implements Resource {
    String material;

    public Coin(){
        this.material="coin";
    }

    public String GetMaterialType(){
        return material;
    }
}
