package it.polimi.ingsw.Model.Resource;

import it.polimi.ingsw.Model.Exceptions.ResourceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * ResourceHandler class
 * @author Mirko Genoni
 *
 * This class handles all the deposits available in the game
 */
public class ResourceHandler {
    ArrayList<Resource> deposit;
    Map<String, Integer> deposit_available;
    Strongbox strongbox;
    Map<String, Integer> additional_deposit;

    /**
     * The constructor initializes all the necessary data structures
     */
    public ResourceHandler(){
        this.deposit  = new ArrayList<>();
        this.deposit_available = new HashMap<>();
        this.strongbox  = new Strongbox();
        this.additional_deposit = new HashMap<>();
    }

    /**
     * This method adds an additional deposit checking that there weren't already two created or that exists
     * already one with that resource
     * @param type represents the resource that can be inserted inside the additional deposit
     * @throws ResourceException when there are already two additional deposit or there is already another one with that resource
     */
    public void addAdditionalDeposit(String type) throws ResourceException{
        boolean check = additional_deposit.containsKey(type);

        if(additional_deposit.size()==2)
            throw new ResourceException("Already 2 additional deposit");
        if(!check)
            additional_deposit.put(type, 0);
        else
            //There aren't 2 different leader card that add deposit of the same resource type
            throw new ResourceException("Already added an additional deposit with this resource");

    }

    /**
     * This method returns the current state of the deposit
     * @return a copy of the current deposit state
     */
    public ArrayList<Resource> getDepositState() {
        return new ArrayList<>(deposit);
    }

    /**
     * This method returns the current state of the additional deposit
     * @return an arraylist that represents the current state of the additional deposit
     */
    public ArrayList<Resource> getAdditionalDeposit(){
        ArrayList<Resource> tmp = new ArrayList<>();

        for(Resource r: Resource.values()){
            if(additional_deposit.containsKey(r.toString()) && additional_deposit.get(r.toString())==0)
                for(int i=0; i<2; i++)
                    tmp.add(null);
            if(additional_deposit.containsKey(r.toString())) {
                for (int i = 0; i < additional_deposit.get(r.toString()); i++)
                    tmp.add(r);
            }
        }

        return tmp;
    }

    /**
     * This method grants to the user the ability to modify the deposit state
     * @param new_state is the new deposit state passed by the user
     * @throws ResourceException if the new deposit state is incorrect
     */
    public void newDepositState(ArrayList<Resource> new_state) throws ResourceException {
        if (checkCorrectNewDeposit(new_state)) {
            deposit.clear();
            deposit.addAll(new_state);
        } else
            throw new ResourceException("Error in deposit format");
    }

    /**
     * This method adds resources to the additional deposits checking that they're not full and that the
     * material is compatible with the type of the deposit
     * @param received is a map that contains the resources that needs to be added
     * @throws ResourceException if the deposit is already full or if the resource is not compatible with the deposit
     */
    public void addMaterialAdditionalDeposit(Map<String, Integer> received) throws ResourceException{

        //This checks that the user is not trying to add more resources than the additional deposit can contain
        for(String r: received.keySet()){
            if(additional_deposit.containsKey(r) && additional_deposit.get(r) + received.get(r)>2)
                throw new ResourceException("Additional deposit already full");
        }

        //This checks that the deposit can contain the type of the resources passed by the user
        for(String r: received.keySet()){
            if(additional_deposit.containsKey(r) && received.get(r)>0)
                additional_deposit.put(r, additional_deposit.get(r) + received.get(r));

            if(!additional_deposit.containsKey(r) && received.get(r)>0)
                throw new ResourceException("Resource not compatible with the additional deposit");
        }
    }

    /**
     * This method grants to the user the ability to check the presence of a certain quantity of
     * resource of different types
     * @param requirements is a map with keys from the resource enum and the relative quantity
     * @return a boolean that tells if there are the material asked
     */
    public boolean checkMaterials(Map<String, Integer> requirements) {
        for (Resource r : Resource.values()) {
            int quantity_available = strongbox.getQuantity(r.toString()) + deposit_available.get(r.toString());
            if (requirements.get(r.toString()) > quantity_available)
                return false;
        }
        return true;
    }


    /**
     * This method removes a certain quantity of materials, firstly from the additional deposit, then from the deposit
     * and lastly from the strongbox
     * @param taken is a map with keys form the resource enum and the relative quantity
     * @throws ResourceException generated by the strongbox
     */

    public void takeMaterials(Map<String, Integer> taken) throws ResourceException{
        int i = 0;

        for (Resource r : Resource.values()) {

            //This checks if there are eventual additional deposits and remove from them firstly the resources
            if(taken.get(r.toString())!=0) {
                if(additional_deposit.containsKey(r.toString()) && additional_deposit.get(r.toString())>0){
                    if(taken.get(r.toString())-additional_deposit.get(r.toString())>0){
                        taken.put(r.toString(), taken.get(r.toString())-additional_deposit.get(r.toString()));
                        additional_deposit.put(r.toString(), 0);
                    }
                    if(additional_deposit.containsKey(r.toString()) && additional_deposit.get(r.toString())==0){
                        taken.put(r.toString(), 0);
                        additional_deposit.put(r.toString(), 0);
                    }
                    else{
                        taken.put(r.toString(), 0);
                        additional_deposit.put(r.toString(), additional_deposit.get(r.toString())-taken.get(r.toString()));
                    }
                }
            }

            //this checks eventual materials inside the deposit and remove them
            if(taken.get(r.toString())!=0 && taken.get(r.toString())<deposit_available.get(r.toString())) {
                deposit_available.put(r.toString(), deposit_available.get(r.toString()) - taken.get(r.toString()));
                taken.put(r.toString(), 0);
                i++;
            }
        }

        //if there are other materials required remove them from the strongbox
        if (i!= 0 && i< Resource.values().length)
            strongbox.returnMaterials(taken);
    }

    /**
     * This method checks the correct state of the new deposit
     * @param new_state is the new state passed by the user
     * @return a boolean that tells if the new state is correct or not
     */
    private boolean checkCorrectNewDeposit(ArrayList<Resource> new_state) {

        //This checks if the two materials on the second shelf are of the same type or if one is empty
        if (new_state.get(1) != null && new_state.get(2) != null
                &&!new_state.get(1).toString().equals(new_state.get(2).toString()))
            return false;

        //This checks if the three materials on the third shelf are of the same type or, if one of the slot
        // is empty, the other two are of the same type
        if ((new_state.get(3) != null && new_state.get(4) != null
                && !new_state.get(3).toString().equals(new_state.get(4).toString()))
                || (new_state.get(4) != null && new_state.get(5) != null
                && !new_state.get(4).toString().equals(new_state.get(5).toString()))
                || (new_state.get(3) != null && new_state.get(5) != null
                && !new_state.get(3).toString().equals(new_state.get(5).toString())))
            return false;

        //This checks that the material on the first shelf is different from the materials on the other one
        for (int i = 1; i < new_state.size(); i++) {
            if (new_state.get(i) != null && new_state.get(0)!=null && new_state.get(0).toString().equals(new_state.get(i).toString()))
                return false;
        }

        int j = 1;

        //This return, if there is one, the first position where the slot is not empty on the second shelf
        while (j < 3 && new_state.get(j) == null) {
            j++;
        }


        if (j != 3) {
            String compare = new_state.get(j).toString();

            //This checks that the material on the second shelf is different from the one on the third
            for (int k = 3; k < new_state.size(); k++) {
                if (new_state.get(k)!=null && compare.equals(new_state.get(k).toString()))
                    return false;
            }
        }
        return true;
    }
}