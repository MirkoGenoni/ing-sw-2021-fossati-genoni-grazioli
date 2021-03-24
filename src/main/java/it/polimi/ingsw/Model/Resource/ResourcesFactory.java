package it.polimi.ingsw.Model.Resource;

import java.util.ArrayList;

public class ResourcesFactory {

    private void ResourcesFactor(){}

    public static ArrayList<Resource> CreateMaterials(String material, int quantity){
        if(material.equals("coin") && quantity>0) {
            ArrayList<Resource> MaterialRequired = new ArrayList<>();
            for (int i=0 ; i < quantity; i++) {
                MaterialRequired.add(new Coin());
            }
            return MaterialRequired;
        }
        else if(material.equals("faith") && quantity>0){
            ArrayList<Resource> MaterialRequired = new ArrayList<>();
            for (int i=0 ; i < quantity; i++) {
                MaterialRequired.add(new Faith());
            }
            return MaterialRequired;
        }
        else if(material.equals("nothing") && quantity>0){
            ArrayList<Resource> MaterialRequired = new ArrayList<>();
            for (int i=0 ; i < quantity; i++) {
                MaterialRequired.add(new Nothing());
            }
            return MaterialRequired;
        }
        else if(material.equals("servant") && quantity>0){
            ArrayList<Resource> MaterialRequired = new ArrayList<>();
            for (int i=0 ; i < quantity; i++) {
                MaterialRequired.add(new Servant());
            }
            return MaterialRequired;
        }
        else if(material.equals("shield") && quantity>0){
            ArrayList<Resource> MaterialRequired = new ArrayList<>();
            for (int i=0 ; i < quantity; i++) {
                MaterialRequired.add(new Shield());
            }
            return MaterialRequired;
        }
        else if(material.equals("stone") && quantity>0){
            ArrayList<Resource> MaterialRequired = new ArrayList<>();
            for (int i=0 ; i < quantity; i++) {
                MaterialRequired.add(new Stone());
            }
            return MaterialRequired;
        }
        else{
            return null;
        }
    }
    public static Resource CreateMaterials(String material){
        if(material.equals("coin")) {
            return new Coin();
        }
        else if(material.equals("faith")){
            return new Faith();
        }
        else if(material.equals("nothing")){
            return new Nothing();
        }
        else if(material.equals("servant")){
            return new Servant();
        }
        else if(material.equals("shield")){
            return new Shield();
        }
        else if(material.equals("stone")){
            return new Stone();
        }
        else{
            return null;
        }

    }
}
