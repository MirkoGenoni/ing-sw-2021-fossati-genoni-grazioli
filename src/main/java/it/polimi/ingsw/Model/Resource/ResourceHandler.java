package it.polimi.ingsw.Model.Resource;

import it.polimi.ingsw.Model.Exceptions.ResourceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * ResourceHandler class
 * @author Mirko Genoni
 *
 * This class handles all the deposits of resources available in the game
 */
public class ResourceHandler {
    ArrayList<Resource> deposit;
    Map<Resource, Integer> deposit_available;
    Strongbox strongbox;
    ArrayList<Resource> additional_deposit_state;
    Map<Resource, Integer> additional_deposit;

    /**
     * The constructor initializes all the necessary data structures
     */
    public ResourceHandler(){
        this.deposit  = new ArrayList<>();
        for(int i=0; i<6; i++)
            deposit.add(null);

        this.deposit_available = new HashMap<>();
        for(Resource r: Resource.values())
            deposit_available.put(r, 0);

        this.strongbox  = new Strongbox();
        this.additional_deposit = new HashMap<>();
        this.additional_deposit_state = new ArrayList<>();
    }

    /**
     * This method adds an additional deposit checking that there weren't already two created or that exists
     * already one with that resource
     * @param type represents the resource that can be inserted inside the additional deposit
     * @throws ResourceException when there are already two additional deposit or there is already another one with that resource
     */
    public void addAdditionalDeposit(Resource type) throws ResourceException{
        boolean check = additional_deposit.containsKey(type);

        if(additional_deposit.size()==2)
            throw new ResourceException("Already 2 additional deposit");
        if(!check) {
            additional_deposit.put(type, 0);
            this.additional_deposit_state.add(null);
            this.additional_deposit_state.add(null);
        }
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
     * This method returns the current state of the strongbox
     * @return a map that represents the current state of the strongbox
     */
    public Map<Resource,Integer> getStrongboxState(){
        return strongbox.getQuantity();
    }

    /**
     * This method returns the current state of the additional deposit
     * @return an arraylist that represents the current state of the additional deposit
     */
    public ArrayList<Resource> getAdditionalDeposit(){
        return new ArrayList<>(this.additional_deposit_state);
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

            //reinitialize the deposit resource counter for the new deposit state
            for(Resource r: Resource.values())
                deposit_available.put(r, 0);

            //counts the resources inside the deposit and add them to the resource counter
            for(Resource r: deposit)
                if(r!=null)
                    deposit_available.put(r, deposit_available.get(r)+1);
        } else
            throw new ResourceException("Error in deposit format");
    }

    /**
     * This method adds materials to the strongbox
     * @param received is a map that represents the amount of resource to add
     */
    public void addMaterialStrongbox(Map<Resource, Integer> received){
        strongbox.addMaterials(received);
    }

    /**
     * This method accept new additional deposits state checking that they're not full and that the
     * material is compatible with the type of the deposit
     * @param received is an arraylist that contains the new additional deposits state
     * @throws ResourceException if the deposit is already full or if the resource is not compatible with the deposit
     */
    public void newAdditionalDepositState(ArrayList<Resource> received) throws ResourceException{
        Map<Resource,Integer> tmp = new HashMap<>();

        //initialize the new additionalDeposit and checks its correct form
        for(Resource j: additional_deposit.keySet()){
            tmp.put(j,0);
        }

        //adds the correct amount of resources to the additional deposit
        for(Resource r: received){
            if(r!=null) {
                if (additional_deposit.containsKey(r) && tmp.get(r) < 2)
                    tmp.put(r, tmp.get(r) + 1);
                else
                    throw new ResourceException("Additional deposits already full or resource not compatible");
            }
        }

        this.additional_deposit_state = new ArrayList<>(received);
        this.additional_deposit = tmp;
    }

    /**
     * This method grants to the user the ability to check the presence of a certain quantity of
     * resource of different types
     * @param requirements is a map with keys from the resource enum and the relative quantity
     * @return a boolean that tells if there are the material asked
     */
    public boolean checkMaterials(Map<Resource, Integer> requirements) {
        for (Resource r : Resource.values()) {
            if(requirements.containsKey(r)) {
                int quantity_available = strongbox.getQuantity(r) + deposit_available.get(r);
                if(additional_deposit.size()>0 && additional_deposit.containsKey(r))
                    quantity_available += additional_deposit.get(r);
                if (requirements.get(r) > quantity_available)
                    return false;
            }
        }
        return true;
    }


    /**
     * This method removes a certain quantity of materials, firstly from the additional deposit, then from the deposit
     * and lastly from the strongbox
     * @param taken is a map with keys form the resource enum and the relative quantity
     * @throws ResourceException generated by the strongbox
     */

    public void takeMaterials(Map<Resource, Integer> taken) throws ResourceException{
        int i = 0;
        int j = 0;
        int k = 0;

        Map<Resource, Integer> tmpAdditional = new HashMap<>(this.additional_deposit);
        Map<Resource, Integer> tmpDeposit = new HashMap<>(this.deposit_available);
        Map<Resource, Integer> tmpStrongbox = new HashMap<>();

        for (Resource r : Resource.values()) {

            //This checks if there are eventual additional deposits and remove from them firstly the resources
            if(taken.containsKey(r) && taken.get(r)!=0) {
                if(tmpAdditional.containsKey(r) && tmpAdditional.get(r)>0){
                    if(taken.get(r) > tmpAdditional.get(r)){
                        taken.put(r, taken.get(r)-tmpAdditional.get(r));
                        tmpAdditional.put(r, 0);
                        k++;
                    }
                    if(taken.get(r).equals(tmpAdditional.get(r))){
                        taken.put(r, 0);
                        tmpAdditional.put(r, 0);
                        k++;
                    }
                    if(taken.get(r) < tmpAdditional.get(r)){
                        tmpAdditional.put(r, tmpAdditional.get(r)-taken.get(r));
                        taken.put(r, 0);
                        k++;
                    }
                }
            }

            //this checks eventual materials inside the deposit and remove them
            if(taken.containsKey(r) && taken.get(r)!=0 && taken.get(r)<=tmpDeposit.get(r)) {
                tmpDeposit.put(r, tmpDeposit.get(r) - taken.get(r));
                taken.put(r, 0);
                i++;
            }
            else if(taken.containsKey(r) && taken.get(r)!=0 && taken.get(r)>tmpDeposit.get(r)) {
                taken.put(r, taken.get(r) - tmpDeposit.get(r));
                tmpDeposit.put(r, 0);
                j++;
            }
        }

        //if there are other materials required remove them from the strongbox
        if (i< Resource.values().length)
            strongbox.returnMaterials(taken);

        //update of the resources counter
        this.additional_deposit.clear();
        this.additional_deposit.putAll(tmpAdditional);

        this.deposit_available.clear();
        this.deposit_available.putAll(tmpDeposit);

        if(i!=0 || j!=0)
            this.correctDeposit(deposit, deposit_available);

        if(k!=0)
            this.correctDeposit(additional_deposit_state, additional_deposit);

    }

    /**
     * This method checks the correct state of the new deposit
     * @param new_state is the new state passed by the user
     * @return a boolean that tells if the new state is correct or not
     */
    private boolean checkCorrectNewDeposit(ArrayList<Resource> new_state) {

        //checks that the deposit is not longer than expected
        if(new_state.size()>6)
            return false;

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

    /**
     * This method is used by the take materials method to synchronise the map that represents the counter of
     * resources available and the true representation inside the arraylists for additional deposit and the deposti
     * @param deposit is the additional deposit or the deposit
     * @param deposit_av is the relative map that represents the resource counter
     */
    private void correctDeposit(ArrayList<Resource> deposit, Map<Resource, Integer> deposit_av){
        Map<Resource,Integer> temp= new HashMap<>(deposit_av);

        for(int i=0; i<deposit.size(); i++){
            if(temp.containsKey(deposit.get(i)))
                if(temp.get(deposit.get(i))==0)
                    deposit.set(i, null);
                else
                    temp.put(deposit.get(i), temp.get(deposit.get(i))-1);
        }
    }
}