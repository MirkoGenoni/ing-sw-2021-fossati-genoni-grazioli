package it.polimi.ingsw.Model.Resource;

import java.util.ArrayList;

/**
 * ResourceFactory class
 * @author Mirko
 *
 * This class creates and provides resource materials through 2 public static methods, it's intended as a
 * factory, as part of a factory method.
 */
public class ResourcesFactory {

    private void ResourcesFactor(){}

    /**
     *
     * @param material is a string that will identify the resource needed
     * @param quantity is the quantity of resource needed
     * @return an arraylist long as the quantity asked, all of the same resource that has been required
     */
    public static ArrayList<Resource> createMaterials(String material, int quantity){
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
            //TODO: replace null return with an exception
            return null;
        }
    }

    /**
     *
     * @param material is a string that will identify the resource needed
     * @return a single resource of the type required
     */
    public static Resource createMaterials(String material){
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
            //TODO: replace null return with an exception
            return null;
        }

    }
}
