package it.polimi.ingsw.Model.Resource;

public class Stone implements Resource {
    String material;

    public Stone(){
        this.material="stone";
    }

    public String GetMaterialType(){
        return material;
    }
}
