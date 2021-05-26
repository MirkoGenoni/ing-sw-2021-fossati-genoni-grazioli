package it.polimi.ingsw.Model.Resource;

import it.polimi.ingsw.Model.Exceptions.ResourceException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * This tests try every type of deposit and the strongbox.
 * They try them firstly one at the time and checks the correct save of the state inside the data structures.
 * They then try them alone and checks the correct collection of resources from them, then in couples and
 * finally all of them.
 *
 * The tests of resource collection are structured as follows:
 * 1) Initialize the deposits or the strongbox with a random state, almost full
 * 2) They collect some resources from them without emptying any deposit or the strongbox
 * 3) They collect more resources than the available and check two thing: that has been thrown the correct error and
 *    that the deposits and the strongbox haven't changed
 * 4) They then collect enough resource to empty all the deposits and the strongbox
 * 5) They try to collect the resources from a state where all the deposit and the strongbox are empty
 *
 */
public class ResourceHandlerTest {

    @Test
    public void testNewAdditionalDepositState() {
        ResourceHandler resourceHandler = new ResourceHandler();
        ArrayList<Resource> testArrayList = new ArrayList<>();

        //This Test checks the creation of a additional deposit and the return of the
        //method getAdditionalDeposit
        testArrayList.add(null);
        testArrayList.add(null);

        try {
            resourceHandler.addAdditionalDeposit(Resource.COIN);
        } catch (ResourceException e) {
        }

        assertEquals(resourceHandler.getAdditionalDeposit(), testArrayList);

        //These tests check that all the possible correct new state for additional deposit
        // with one material available are recognised and saved

        //TEST1
        testArrayList.clear();
        testArrayList.add(Resource.COIN);
        testArrayList.add(Resource.COIN);

        try {
            resourceHandler.newAdditionalDepositState(testArrayList);
        } catch(ResourceException e){}

        assertEquals(testArrayList, resourceHandler.getAdditionalDeposit());

        //TEST2
        testArrayList.clear();
        testArrayList.add(Resource.COIN);
        testArrayList.add(null);

        try {
            resourceHandler.newAdditionalDepositState(testArrayList);
        } catch(ResourceException e){}

        assertEquals(testArrayList, resourceHandler.getAdditionalDeposit());

        //TEST3
        testArrayList.clear();
        testArrayList.add(null);
        testArrayList.add(null);

        try {
            resourceHandler.newAdditionalDepositState(testArrayList);
        } catch(ResourceException e){}

        assertEquals(testArrayList, resourceHandler.getAdditionalDeposit());

        //TEST4
        testArrayList.clear();
        testArrayList.add(null);
        testArrayList.add(Resource.COIN);

        try {
            resourceHandler.newAdditionalDepositState(testArrayList);
        } catch(ResourceException e){}

        assertEquals(testArrayList, resourceHandler.getAdditionalDeposit());

        //These tests check that if I try to add a wrong additional deposit it will be recognised as
        // an error and it won't be saved inside the model, with a random state

        // Saves the state that will be kept for all the execution

        ArrayList<Resource> notModified = new ArrayList<>(testArrayList);

        //TEST1
        testArrayList.clear();
        testArrayList.add(Resource.SHIELD);
        testArrayList.add(Resource.SHIELD);

        try {
            resourceHandler.newAdditionalDepositState(testArrayList);
        } catch(ResourceException e){}

        assertEquals(notModified, resourceHandler.getAdditionalDeposit());

        //TEST2
        testArrayList.clear();
        testArrayList.add(Resource.SHIELD);
        testArrayList.add(null);

        try {
            resourceHandler.newAdditionalDepositState(testArrayList);
        } catch(ResourceException e){}

        assertEquals(notModified, resourceHandler.getAdditionalDeposit());

        //TEST3
        testArrayList.clear();
        testArrayList.add(null);
        testArrayList.add(Resource.SHIELD);

        try {
            resourceHandler.newAdditionalDepositState(testArrayList);
        } catch(ResourceException e){}

        assertEquals(notModified, resourceHandler.getAdditionalDeposit());

        //TEST4
        testArrayList.clear();
        testArrayList.add(Resource.COIN);
        testArrayList.add(Resource.SHIELD);

        try {
            resourceHandler.newAdditionalDepositState(testArrayList);
        } catch(ResourceException e){}

        assertEquals(notModified, resourceHandler.getAdditionalDeposit());

        //TEST5
        testArrayList.clear();
        testArrayList.add(Resource.SHIELD);
        testArrayList.add(Resource.COIN);

        try {
            resourceHandler.newAdditionalDepositState(testArrayList);
        } catch(ResourceException e){}

        assertEquals(notModified, resourceHandler.getAdditionalDeposit());

        //TEST6
        testArrayList.clear();
        testArrayList.add(Resource.SERVANT);
        testArrayList.add(Resource.SHIELD);

        try {
            resourceHandler.newAdditionalDepositState(testArrayList);
        } catch(ResourceException e){}

        assertEquals(notModified, resourceHandler.getAdditionalDeposit());

        //This tries to add an additional deposit with a type already added, checks the error and
        // that the state of the additional deposit didn't change

        try {
            resourceHandler.addAdditionalDeposit(Resource.COIN);
        } catch (ResourceException e) {
            assertEquals(e.getMessage(), "Already added an additional deposit with this resource");
        }

        assertEquals (resourceHandler.getAdditionalDeposit(), notModified);

        //This tries to add another type of resource to the additional deposit and checks the
        // return of the method getAdditionalDeposit

        testArrayList.clear();

        testArrayList.addAll(notModified);
        testArrayList.add(null);
        testArrayList.add(null);

        try {
            resourceHandler.addAdditionalDeposit(Resource.SHIELD);
        } catch (ResourceException e) {
        }
        
        assertEquals (resourceHandler.getAdditionalDeposit(), testArrayList);

        //This tries to add a third type of resource to the additional deposit and then checks the
        // error received and that the state hasn't changed

        try {
            resourceHandler.addAdditionalDeposit(Resource.STONE);
        } catch (ResourceException e) {
            assertEquals(e.getMessage(), "Already 2 additional deposit");
        }

        assertEquals (resourceHandler.getAdditionalDeposit(), testArrayList);

        //These tests check that some of the possible correct new state for additional deposit
        // with two materials available are recognised and saved correctly

        //TEST1: all resources
        testArrayList.clear();

        testArrayList.add(Resource.COIN);
        testArrayList.add(Resource.COIN);
        testArrayList.add(Resource.SHIELD);
        testArrayList.add(Resource.SHIELD);

        try{
            resourceHandler.newAdditionalDepositState(testArrayList);
        } catch(ResourceException e){}

        assertEquals(testArrayList, resourceHandler.getAdditionalDeposit());

        //TEST2: three resources
        testArrayList.clear();

        testArrayList.add(Resource.COIN);
        testArrayList.add(null);
        testArrayList.add(Resource.SHIELD);
        testArrayList.add(Resource.SHIELD);

        try{
            resourceHandler.newAdditionalDepositState(testArrayList);
        } catch(ResourceException e){}

        assertEquals(testArrayList, resourceHandler.getAdditionalDeposit());

        //TEST3: three resources
        testArrayList.clear();

        testArrayList.add(Resource.COIN);
        testArrayList.add(Resource.COIN);
        testArrayList.add(Resource.SHIELD);
        testArrayList.add(null);

        try{
            resourceHandler.newAdditionalDepositState(testArrayList);
        } catch(ResourceException e){}

        assertEquals(testArrayList, resourceHandler.getAdditionalDeposit());


        //TEST4:two resources
        testArrayList.clear();

        testArrayList.add(null);
        testArrayList.add(Resource.COIN);
        testArrayList.add(null);
        testArrayList.add(Resource.SHIELD);

        try{
            resourceHandler.newAdditionalDepositState(testArrayList);
        } catch(ResourceException e){}


        assertEquals(testArrayList, resourceHandler.getAdditionalDeposit());

        //TEST5:two resources
        testArrayList.clear();

        testArrayList.add(Resource.COIN);
        testArrayList.add(null);
        testArrayList.add(null);
        testArrayList.add(Resource.SHIELD);

        try{
            resourceHandler.newAdditionalDepositState(testArrayList);
        } catch(ResourceException e){}

        assertEquals(testArrayList, resourceHandler.getAdditionalDeposit());

        //TEST6:only one resource
        testArrayList.clear();

        testArrayList.add(null);
        testArrayList.add(Resource.COIN);
        testArrayList.add(null);
        testArrayList.add(null);

        try{
            resourceHandler.newAdditionalDepositState(testArrayList);
        } catch(ResourceException e){}

        assertEquals(testArrayList, resourceHandler.getAdditionalDeposit());

        //TEST7:only one resource
        testArrayList.clear();

        testArrayList.add(null);
        testArrayList.add(null);
        testArrayList.add(Resource.SHIELD);
        testArrayList.add(null);

        try{
            resourceHandler.newAdditionalDepositState(testArrayList);
        } catch(ResourceException e){}

        assertEquals(testArrayList, resourceHandler.getAdditionalDeposit());

        //These tests will test two possible type of wrong additional deposit state with two resource available
        // and keeps a random state

        notModified = resourceHandler.additional_deposit_state; //saves the state that will never be modified

        //TEST1: all the types of resources, including the one correct
        testArrayList.clear();

        testArrayList.add(Resource.COIN);
        testArrayList.add(Resource.STONE);
        testArrayList.add(Resource.SERVANT);
        testArrayList.add(Resource.SHIELD);

        try{
            resourceHandler.newAdditionalDepositState(testArrayList);
        } catch(ResourceException e){
            assertEquals(e.getMessage(), "Additional deposits already full or resource not compatible");
        }

        assertEquals(resourceHandler.additional_deposit_state, notModified);

        //TEST2: only two type of resources, one correct one incorrect
        testArrayList.clear();

        testArrayList.add(Resource.COIN);
        testArrayList.add(null);
        testArrayList.add(Resource.SERVANT);
        testArrayList.add(null);

        try{
            resourceHandler.newAdditionalDepositState(testArrayList);
        } catch(ResourceException e){
            assertEquals(e.getMessage(), "Additional deposits already full or resource not compatible");
        }

        assertEquals(resourceHandler.additional_deposit_state, notModified);

    }

    @Test
    public void testNewDepositState() {
        ResourceHandler testResourceHandler = new ResourceHandler();

        ArrayList<Resource> inputState = new ArrayList<>();
        ArrayList<Resource> output;

        //Checks the correct initialization of the deposit inside the handler
        output = testResourceHandler.getDepositState();

        inputState.add(null);
        inputState.add(null);
        inputState.add(null);
        inputState.add(null);
        inputState.add(null);
        inputState.add(null);

        assertEquals(inputState, output);

        try {
            testResourceHandler.newDepositState(inputState);
        } catch (ResourceException e) {
        }

        output = testResourceHandler.getDepositState();
        assertEquals(output, inputState);

        //Checks the correct insertion of a correct new deposit state inside the handler
        inputState.set(0, Resource.SHIELD);
        inputState.set(1, Resource.COIN);
        inputState.set(2, Resource.COIN);
        inputState.set(3, Resource.STONE);
        inputState.set(4, Resource.STONE);
        inputState.set(5, Resource.STONE);

        try {
            testResourceHandler.newDepositState(inputState);
        } catch (ResourceException e) {
        }

        output = testResourceHandler.getDepositState();
        assertEquals(output, inputState);

        //Checks the correct insertion of a correct new deposit state inside the handler
        inputState.set(0, Resource.SHIELD);
        inputState.set(1, null);
        inputState.set(2, Resource.COIN);
        inputState.set(3, null);
        inputState.set(4, Resource.STONE);
        inputState.set(5, null);

        try {
            testResourceHandler.newDepositState(inputState);
        } catch (ResourceException e) {
        }

        output = testResourceHandler.getDepositState();
        assertEquals(output, inputState);

        //Checks the correct insertion of a correct new deposit state inside the handler
        inputState.set(0, null);
        inputState.set(1, null);
        inputState.set(2, Resource.COIN);
        inputState.set(3, Resource.STONE);
        inputState.set(4, null);
        inputState.set(5, null);

        try {
            testResourceHandler.newDepositState(inputState);
        } catch (ResourceException e) {
        }

        output = testResourceHandler.getDepositState();
        assertEquals(output, inputState);

        //Checks the correct insertion of a correct new deposit state inside the handler
        inputState.set(0, Resource.SHIELD);
        inputState.set(1, Resource.COIN);
        inputState.set(2, null);
        inputState.set(3, null);
        inputState.set(4, null);
        inputState.set(5, null);

        try {
            testResourceHandler.newDepositState(inputState);
        } catch (ResourceException e) {
        }

        output = testResourceHandler.getDepositState();
        assertEquals(output, inputState);

        //Checks the correct insertion of an incorrect new deposit state inside the handler
        inputState.set(0, Resource.COIN);
        inputState.set(1, null);
        inputState.set(2, Resource.COIN);
        inputState.set(3, Resource.STONE);
        inputState.set(4, null);
        inputState.set(5, null);

        try {
            testResourceHandler.newDepositState(inputState);
        } catch (ResourceException e) {
            assertEquals(e.getMessage(), "Error in deposit format");
        }

        assertEquals(output, testResourceHandler.getDepositState());

        //Checks the correct insertion of an incorrect new deposit state inside the handler
        inputState.set(0, Resource.STONE);
        inputState.set(1, null);
        inputState.set(2, Resource.COIN);
        inputState.set(3, null);
        inputState.set(4, null);
        inputState.set(5, Resource.COIN);

        try {
            testResourceHandler.newDepositState(inputState);
        } catch (ResourceException e) {
            assertEquals(e.getMessage(), "Error in deposit format");
        }

        assertEquals(output, testResourceHandler.getDepositState());

        //Checks the correct insertion of an incorrect new deposit state inside the handler
        inputState.set(0, null);
        inputState.set(1, null);
        inputState.set(2, Resource.COIN);
        inputState.set(3, null);
        inputState.set(4, Resource.SHIELD);
        inputState.set(5, Resource.COIN);

        try {
            testResourceHandler.newDepositState(inputState);
        } catch (ResourceException e) {
            assertEquals(e.getMessage(), "Error in deposit format");
        }

        assertEquals(output, testResourceHandler.getDepositState());

        //Checks the correct insertion of an incorrect new deposit state inside the handler
        inputState.set(0, null);
        inputState.set(1, Resource.STONE);
        inputState.set(2, Resource.COIN);
        inputState.set(3, null);
        inputState.set(4, null);
        inputState.set(5, Resource.COIN);

        try {
            testResourceHandler.newDepositState(inputState);
        } catch (ResourceException e) {
            assertEquals(e.getMessage(), "Error in deposit format");
        }

        assertEquals(output, testResourceHandler.getDepositState());
    }

    @Test
    public void testCheckTakeMaterialsStrongbox() {
        ResourceHandler resourceHandler = new ResourceHandler();
        Map<Resource, Integer> strongbox = new HashMap<>();
        Map<Resource, Integer> requirements1 = new HashMap<>();
        Map<Resource, Integer> requirements2 = new HashMap<>();
        Map<Resource, Integer> checkState = new HashMap<>();

        for (Resource r : Resource.values()) {
            strongbox.put(r, 5);
            requirements1.put(r, 3);
            requirements2.put(r, 2);
            checkState.put(r, 0);
        }

        //this checks that the material added to the strongbox are added correctly,
        //that they're available and that can be taken from the handler, then checks
        //the final state of the strongbox (not empty)

        resourceHandler.addMaterialStrongbox(strongbox);
        assertEquals(resourceHandler.getStrongboxState(), strongbox);
        try {
            assert (resourceHandler.checkMaterials(requirements1));
            resourceHandler.takeMaterials(requirements1);
        } catch (ResourceException e) {
        }

        Map<Resource, Integer> expected = resourceHandler.getStrongboxState();
        assertEquals(requirements2, expected);

        //this checks the take material and the final state of the strongbox (empty)
        try {
            assert (resourceHandler.checkMaterials(requirements2));
            resourceHandler.takeMaterials(requirements2);
        } catch (ResourceException e) {
        }

        expected = resourceHandler.getStrongboxState();
        assertEquals(checkState, expected);

        //this checks the correct error return from the strongbox empty and that the data structure has
        // not been changed

        try {
            assertFalse(resourceHandler.checkMaterials(requirements1));
            resourceHandler.takeMaterials(requirements1);
        } catch (ResourceException e) {
            assertEquals(e.getMessage(), "Trying to put negative Materials inside strongbox");
        }

        assertEquals(expected, resourceHandler.getStrongboxState());

        //this checks the correct error return from the strongbox full and that the data structure has
        // not been changed

        try {
            resourceHandler.addMaterialStrongbox(requirements2);
            assertFalse(resourceHandler.checkMaterials(requirements1));
            resourceHandler.takeMaterials(requirements1);
        } catch (ResourceException e) {
            assertEquals(e.getMessage(), "Trying to put negative Materials inside strongbox");
        }

        expected = resourceHandler.getStrongboxState();
        assertEquals(expected, requirements2);

    }

    @Test
    public void testCheckTakeMaterialsDeposit() {
        //Adds a new deposit state and checks the correct new deposit state inside the handler,
        //then takes the material only from the deposit and checks the correct final state

        ResourceHandler resourceHandler = new ResourceHandler();

        ArrayList<Resource> testAdd = new ArrayList<>();

        testAdd.add(null);
        testAdd.add(null);
        testAdd.add(Resource.SERVANT);
        testAdd.add(Resource.COIN);
        testAdd.add(Resource.COIN);
        testAdd.add(Resource.COIN);

        try {
            resourceHandler.newDepositState(testAdd);
        } catch (ResourceException e) {
        }

        assertEquals(resourceHandler.getDepositState(), testAdd);

        Map<Resource, Integer> take = new HashMap<>();
        take.put(Resource.COIN, 3);
        take.put(Resource.STONE, 0);
        take.remove(Resource.SHIELD);
        take.put(Resource.SERVANT, 0);

        assert (resourceHandler.checkMaterials(take));

        try {
            resourceHandler.takeMaterials(take);
        } catch (ResourceException e) {
        }

        testAdd.set(0, null);
        testAdd.set(1, null);
        testAdd.set(2, Resource.SERVANT);
        testAdd.set(3, null);
        testAdd.set(4, null);
        testAdd.set(5, null);

        assertEquals(resourceHandler.getDepositState(), testAdd);

        //this test takes more material than the available and checks the correct error return
        // and that the data structure hasn't been changed

        take.put(Resource.COIN, 1);
        take.put(Resource.STONE, 0);
        take.remove(Resource.SHIELD);
        take.put(Resource.SERVANT, 1);

        assertFalse(resourceHandler.checkMaterials(take));

        try {
            resourceHandler.takeMaterials(take);
        } catch (ResourceException e) {
            assertEquals(e.getMessage(), "Trying to put negative Materials inside strongbox");
        }

        assertEquals(resourceHandler.getDepositState(), testAdd);

        //this test empties the deposit
        take.put(Resource.COIN, 0);
        take.put(Resource.STONE, 0);
        take.put(Resource.SERVANT, 1);

        testAdd.set(0, null);
        testAdd.set(1, null);
        testAdd.set(2, null);
        testAdd.set(3, null);
        testAdd.set(4, null);
        testAdd.set(5, null);

        assert(resourceHandler.checkMaterials(take));

        try {
            resourceHandler.takeMaterials(take);
        } catch (ResourceException e) {}

        assertEquals(resourceHandler.getDepositState(), testAdd);
    }

    @Test
    public void testCheckTakeMaterialsAdditionalDeposit() {
        //This test adds material only to the additional deposit and checks the correct return of check materials
        //and getting materials from this
        ResourceHandler resourceHandler = new ResourceHandler();

        Map<Resource, Integer> take = new HashMap<>();

        ArrayList<Resource> testAdd = new ArrayList<>();

        testAdd.add(null);
        testAdd.add(null);
        testAdd.add(null);
        testAdd.add(null);
        testAdd.add(null);
        testAdd.add(null);

        try{
            resourceHandler.newDepositState(testAdd); //RESET the states of what i modified earlier
        } catch(ResourceException e){}

        //TEST1: full the additional deposits and take some materials
        take = new HashMap<>();
        take.put(Resource.COIN, 1);
        take.put(Resource.SHIELD,1);

        ArrayList<Resource> deposit = new ArrayList<>();

        deposit.add(Resource.COIN);
        deposit.add(Resource.COIN);
        deposit.add(Resource.SHIELD);
        deposit.add(Resource.SHIELD);

        try{
            resourceHandler.addAdditionalDeposit(Resource.COIN);
            resourceHandler.addAdditionalDeposit(Resource.SHIELD);
            resourceHandler.newAdditionalDepositState(deposit);
            resourceHandler.checkMaterials(take);
            resourceHandler.takeMaterials(take);
        } catch(ResourceException e){}

        deposit.clear();

        deposit.add(Resource.COIN);
        deposit.add(null);
        deposit.add(Resource.SHIELD);
        deposit.add(null);

        assertEquals(resourceHandler.getAdditionalDeposit(), deposit);

        //TEST2: some materials inside the additional deposit and takes some from them
        take.put(Resource.SHIELD,1);

        try{
            resourceHandler.newAdditionalDepositState(deposit);
            resourceHandler.checkMaterials(take);
            resourceHandler.takeMaterials(take);
        } catch(ResourceException e){}

        deposit.clear();

        deposit.add(Resource.COIN);
        deposit.add(null);
        deposit.add(null);
        deposit.add(null);

        assertEquals(resourceHandler.getAdditionalDeposit(), deposit);

        //TEST3: takes too much materials and checks error and that the state is the same as before
        take.put(Resource.SHIELD,1);



        try{
            resourceHandler.newAdditionalDepositState(deposit);
            assertFalse(resourceHandler.checkMaterials(take));
            assertFalse(resourceHandler.checkMaterials(take));
            resourceHandler.takeMaterials(take);
        } catch(ResourceException e){
            assertEquals(e.getMessage(), "Trying to put negative Materials inside strongbox");
        }

        assertEquals(resourceHandler.getAdditionalDeposit(), deposit);
    }

    @Test
    public void checkCollectionDepositStrongbox() {
        //Test that checks the correct initialization of the deposit state
        //and the strongbox state and the correct collect of resources, half from
        //the deposit, half from the strongbox without emptying them
        ResourceHandler resourceHandler = new ResourceHandler();

        ArrayList<Resource> testAdd = new ArrayList<>();
        testAdd.add(Resource.STONE);
        testAdd.add(Resource.SHIELD);
        testAdd.add(null);
        testAdd.add(Resource.COIN);
        testAdd.add(null);
        testAdd.add(Resource.COIN);

        Map<Resource, Integer> requirements = new HashMap<>();
        requirements.put(Resource.SHIELD, 2);
        requirements.put(Resource.COIN, 1);

        Map<Resource, Integer> testStrongbox = new HashMap<>();
        testStrongbox.put(Resource.SHIELD, 2);
        testStrongbox.put(Resource.SERVANT, 1);

        Map<Resource, Integer> testStrongboxState = new HashMap<>();
        testStrongboxState.put(Resource.STONE, 0);
        testStrongboxState.put(Resource.COIN, 0);
        testStrongboxState.put(Resource.SHIELD, 2);
        testStrongboxState.put(Resource.SERVANT, 1);

        try {
            resourceHandler.newDepositState(testAdd);
            resourceHandler.addMaterialStrongbox(testStrongbox);
        } catch (ResourceException e) {
        }

        assertEquals(resourceHandler.getDepositState(), testAdd);

        assertEquals(resourceHandler.getStrongboxState(), testStrongboxState);

        assert(resourceHandler.checkMaterials(requirements));

        try {
            resourceHandler.takeMaterials(requirements);
        } catch (ResourceException e) {
        }

        testStrongboxState.put(Resource.SHIELD, 1);

        testAdd.set(0, Resource.STONE);
        testAdd.set(1, null);
        testAdd.set(2, null);
        testAdd.set(3, Resource.COIN);
        testAdd.set(4, null);
        testAdd.set(5, null);

        assertEquals(resourceHandler.getDepositState(), testAdd);
        assertEquals(resourceHandler.getStrongboxState(), testStrongboxState);


        //This test takes more resources then the available and checks the error and that the data structures has not
        //been modified
        requirements.put(Resource.COIN, 3);
        requirements.put(Resource.STONE, 2);
        requirements.put(Resource.SHIELD, 1);
        requirements.remove(Resource.SERVANT);

        assertFalse(resourceHandler.checkMaterials(requirements));

        try {
            resourceHandler.takeMaterials(requirements);
        } catch (ResourceException e) {
            assertEquals(e.getMessage(), "Trying to put negative Materials inside strongbox");
        }

        assertEquals(resourceHandler.getDepositState(), testAdd);
        assertEquals(resourceHandler.getStrongboxState(), testStrongboxState);

        //this test takes all the material from the two sources and checks the data structures

        requirements.put(Resource.COIN, 1);
        requirements.put(Resource.STONE, 1);
        requirements.put(Resource.SHIELD, 1);
        requirements.put(Resource.SERVANT, 1);

        assert(resourceHandler.checkMaterials(requirements));

        try{
            resourceHandler.takeMaterials(requirements);
        } catch (ResourceException e){}

        testAdd.set(0, null);
        testAdd.set(1, null);
        testAdd.set(2, null);
        testAdd.set(3, null);
        testAdd.set(4, null);
        testAdd.set(5, null);

        testStrongboxState.put(Resource.SHIELD, 0);
        testStrongboxState.put(Resource.SERVANT, 0);

        assertEquals(resourceHandler.getDepositState(), testAdd);
        assertEquals(resourceHandler.getStrongboxState(), testStrongboxState);

        //this test checks the correct error return if deposit and strongbox are empty
        assertFalse(resourceHandler.checkMaterials(requirements));

        try{
            resourceHandler.takeMaterials(requirements);
        } catch (ResourceException e){
            assertEquals(e.getMessage(), "Trying to put negative Materials inside strongbox");
        }

        assertEquals(resourceHandler.getDepositState(), testAdd);
        assertEquals(resourceHandler.getStrongboxState(), testStrongboxState);    }

    @Test
    public void checkCollectionDepositAdditional() {
        /*THESE TESTS TEST THE ADDITIONAL DEPOSIT WITH ONE RESOURCE TYPE*/
        //This test checks the collection of resources half from deposit, half from additional deposit with one
        // resource without emptying them
        ResourceHandler resourceHandler = new ResourceHandler();

        ArrayList<Resource> additionalDepositState = new ArrayList<>();
        additionalDepositState.add(Resource.COIN);
        additionalDepositState.add(Resource.COIN);

        ArrayList<Resource> depositState = new ArrayList<>();

        depositState.add(Resource.SERVANT);
        depositState.add(null);
        depositState.add(Resource.COIN);
        depositState.add(Resource.SHIELD);
        depositState.add(null);
        depositState.add(Resource.SHIELD);


        try {
            resourceHandler.newDepositState(depositState);
            resourceHandler.addAdditionalDeposit(Resource.COIN);
            resourceHandler.newAdditionalDepositState(additionalDepositState);
        } catch(ResourceException e){}

        Map<Resource, Integer> take = new HashMap<>();

        take.put(Resource.COIN, 1);
        take.put(Resource.SHIELD, 1);

        assert(resourceHandler.checkMaterials(take));

        try{
            resourceHandler.takeMaterials(take);
        } catch(ResourceException e){}

        depositState.set(5, null);

        additionalDepositState.set(1, null);

        assertEquals(resourceHandler.getDepositState(), depositState);
        assertEquals(resourceHandler.getAdditionalDeposit(), additionalDepositState);

        //this test checks the collection of more resources than the available and checks
        // that the state is the same as before

        take.put(Resource.SERVANT, 1);
        take.put(Resource.COIN, 3);

        assertFalse(resourceHandler.checkMaterials(take));

        try{
            resourceHandler.takeMaterials(take);
        } catch (ResourceException e){
            assertEquals(e.getMessage(), "Trying to put negative Materials inside strongbox");
        }

        assertEquals(resourceHandler.getDepositState(), depositState);
        assertEquals(resourceHandler.getAdditionalDeposit(), additionalDepositState);

        //this test checks the collection of all the resources from the two deposit
        take.put(Resource.COIN, 2);
        take.put(Resource.SERVANT, 1);
        take.put(Resource.SHIELD, 1);

        depositState.clear();
        depositState.add(null);
        depositState.add(null);
        depositState.add(null);
        depositState.add(null);
        depositState.add(null);
        depositState.add(null);

        additionalDepositState.clear();
        additionalDepositState.add(null);
        additionalDepositState.add(null);

        assert(resourceHandler.checkMaterials(take));

        try{
            resourceHandler.takeMaterials(take);
        } catch(ResourceException e){}

        assertEquals(resourceHandler.getDepositState(), depositState);
        assertEquals(resourceHandler.getAdditionalDeposit(), additionalDepositState);

        //this test checks the collection of resources from all the deposit empty
        take.put(Resource.SERVANT, 1);
        assertFalse(resourceHandler.checkMaterials(take));

        try{
            resourceHandler.takeMaterials(take);
        } catch(ResourceException e){
            assertEquals(e.getMessage(), "Trying to put negative Materials inside strongbox");
        }

        assertEquals(resourceHandler.getDepositState(), depositState);
        assertEquals(resourceHandler.getAdditionalDeposit(), additionalDepositState);

        /*THESE TESTS TEST THE ADDITIONAL DEPOSIT WITH TWO RESOURCE TYPE*/

        //This test checks the collection of resources half from deposit, half from additional deposit with one
        // resource without emptying them

        additionalDepositState.clear();
        additionalDepositState.add(Resource.COIN);
        additionalDepositState.add(null);
        additionalDepositState.add(Resource.STONE);
        additionalDepositState.add(Resource.STONE);

        depositState.clear();
        depositState.add(null);
        depositState.add(Resource.COIN);
        depositState.add(null);
        depositState.add(Resource.SHIELD);
        depositState.add(Resource.SHIELD);
        depositState.add(null);


        try{
            resourceHandler.addAdditionalDeposit(Resource.STONE);
            resourceHandler.newDepositState(depositState);
            resourceHandler.newAdditionalDepositState(additionalDepositState);
        } catch(ResourceException e){}

        take.clear();
        take.put(Resource.COIN, 2);
        take.put(Resource.STONE, 1);
        take.put(Resource.SHIELD, 1);

        assert(resourceHandler.checkMaterials(take));

        try{
            resourceHandler.takeMaterials(take);
        } catch(ResourceException e){}

        additionalDepositState.clear();
        additionalDepositState.add(null);
        additionalDepositState.add(null);
        additionalDepositState.add(Resource.STONE);
        additionalDepositState.add(null);

        depositState.clear();
        depositState.add(null);
        depositState.add(null);
        depositState.add(null);
        depositState.add(Resource.SHIELD);
        depositState.add(null);
        depositState.add(null);

        assertEquals(resourceHandler.getDepositState(), depositState);
        assertEquals(resourceHandler.getAdditionalDeposit(), additionalDepositState);

        //this test checks the correct send of an error in case of take of too many resources
        // and that the state are not modified

        take.put(Resource.STONE, 1);
        take.put(Resource.SHIELD, 2);

        assertFalse(resourceHandler.checkMaterials(take));

        try{
            resourceHandler.takeMaterials(take);
        } catch(ResourceException e){}

        assertEquals(resourceHandler.getDepositState(), depositState);
        assertEquals(resourceHandler.getAdditionalDeposit(), additionalDepositState);

        //this test checks the correct take of all the material from the two deposits
        take.put(Resource.STONE, 1);
        take.put(Resource.SHIELD, 1);

        assert(resourceHandler.checkMaterials(take));

        try {
            resourceHandler.takeMaterials(take);
        } catch (ResourceException e) {}

        depositState.clear();
        depositState.add(null);
        depositState.add(null);
        depositState.add(null);
        depositState.add(null);
        depositState.add(null);
        depositState.add(null);

        additionalDepositState.clear();
        additionalDepositState.add(null);
        additionalDepositState.add(null);
        additionalDepositState.add(null);
        additionalDepositState.add(null);

        assertEquals(resourceHandler.getDepositState(), depositState);
        assertEquals(resourceHandler.getAdditionalDeposit(), additionalDepositState);

        //this test checks the collection of resources when all the deposits are empty
        take.put(Resource.SERVANT,2);
        take.put(Resource.SHIELD,1);

        assertFalse(resourceHandler.checkMaterials(take));

        try {
            resourceHandler.takeMaterials(take);
        } catch (ResourceException e) {
            assertEquals(e.getMessage(), "Trying to put negative Materials inside strongbox");
        }

        assertEquals(resourceHandler.getDepositState(), depositState);
        assertEquals(resourceHandler.getAdditionalDeposit(), additionalDepositState);
    }

    @Test
    public void checkCollectionAdditionalStrongbox() {
        /*test with additional deposit of one type*/

        //this test checks the correct take of materials, without emptying the deposit or
        // the strongbox
        ResourceHandler resourceHandler = new ResourceHandler();

        Map<Resource, Integer> strongboxState = new HashMap<>();
        strongboxState.put(Resource.COIN, 3);
        strongboxState.put(Resource.STONE, 1);
        strongboxState.put(Resource.SHIELD, 2);

        Map<Resource, Integer> take = new HashMap<>();
        take.put(Resource.COIN, 1);
        take.put(Resource.STONE, 1);

        ArrayList<Resource> additionalDeposit = new ArrayList<>();
        additionalDeposit.add(Resource.COIN);
        additionalDeposit.add(Resource.COIN);

        try {
            resourceHandler.addAdditionalDeposit(Resource.COIN);
            resourceHandler.addMaterialStrongbox(strongboxState);
            resourceHandler.newAdditionalDepositState(additionalDeposit);
            assert(resourceHandler.checkMaterials(take));
            resourceHandler.takeMaterials(take);
        } catch (ResourceException e){}

        strongboxState.put(Resource.COIN, 3);
        strongboxState.put(Resource.STONE, 0);
        strongboxState.put(Resource.SHIELD, 2);
        strongboxState.put(Resource.SERVANT, 0);

        additionalDeposit.set(1, null);

        assertEquals(resourceHandler.getAdditionalDeposit(), additionalDeposit);
        assertEquals(resourceHandler.getStrongboxState(), strongboxState);

        //This test takes more materials than the available, checks the error and that the
        // data structures have not changed

        take.put(Resource.COIN, 7);
        take.put(Resource.SHIELD,2);

        assertFalse(resourceHandler.checkMaterials(take));

        try{
            resourceHandler.takeMaterials(take);
        } catch(ResourceException e){
            assertEquals(e.getMessage(), "Trying to put negative Materials inside strongbox");
        }

        assertEquals(resourceHandler.getAdditionalDeposit(), additionalDeposit);
        assertEquals(resourceHandler.getStrongboxState(), strongboxState);

        //This test takes enough resources to empty the deposit and the strongbox
        take.put(Resource.STONE, 0);
        take.put(Resource.COIN,4);
        take.put(Resource.SHIELD,2);

        assert(resourceHandler.checkMaterials(take));

        try{
            resourceHandler.takeMaterials(take);
        } catch(ResourceException e){}

        additionalDeposit.clear();
        additionalDeposit.add(null);
        additionalDeposit.add(null);

        strongboxState.clear();
        strongboxState.put(Resource.SHIELD, 0);
        strongboxState.put(Resource.COIN, 0);
        strongboxState.put(Resource.STONE, 0);
        strongboxState.put(Resource.SERVANT, 0);

        assertEquals(resourceHandler.getAdditionalDeposit(), additionalDeposit);
        assertEquals(resourceHandler.getStrongboxState(), strongboxState);

        //tThis test tries to take resources from empty additionalDeposit and strongbox

        assertFalse(resourceHandler.checkMaterials(take));

        try{
            resourceHandler.takeMaterials(take);
        } catch(ResourceException e){
            assertEquals(e.getMessage(), "Trying to put negative Materials inside strongbox");
        }

        assertEquals(resourceHandler.getAdditionalDeposit(), additionalDeposit);
        assertEquals(resourceHandler.getStrongboxState(), strongboxState);

        /*test with additional deposit of two types*/

        //this test checks the correct take of materials, without emptying the deposit or
        // the strongbox
        strongboxState.clear();
        strongboxState.put(Resource.SERVANT, 2);
        strongboxState.put(Resource.STONE, 2);
        strongboxState.put(Resource.COIN, 3);
        strongboxState.put(Resource.SHIELD, 5);

        additionalDeposit.clear();
        additionalDeposit.add(Resource.COIN);
        additionalDeposit.add(Resource.COIN);
        additionalDeposit.add(Resource.SERVANT);
        additionalDeposit.add(Resource.SERVANT);

        take.clear();
        take.put(Resource.COIN, 3);
        take.put(Resource.SERVANT, 1);
        take.put(Resource.STONE, 1);
        take.put(Resource.SHIELD,5);

        try{
            resourceHandler.addAdditionalDeposit(Resource.SERVANT);
            resourceHandler.addMaterialStrongbox(strongboxState);
            resourceHandler.newAdditionalDepositState(additionalDeposit);
            assert(resourceHandler.checkMaterials(take));
            resourceHandler.takeMaterials(take);
        } catch(ResourceException e){}

        strongboxState.put(Resource.COIN, 2);
        strongboxState.put(Resource.STONE, 1);
        strongboxState.put(Resource.SHIELD, 0);

        additionalDeposit.set(0, null);
        additionalDeposit.set(1, null);
        additionalDeposit.set(2,Resource.SERVANT);
        additionalDeposit.set(3, null);

        assertEquals(resourceHandler.getStrongboxState(), strongboxState);
        assertEquals(resourceHandler.getAdditionalDeposit(), additionalDeposit);

        //this test tries to take more materials than the available
        take.put(Resource.COIN, 7);

        assertFalse(resourceHandler.checkMaterials(take));

        try{
            resourceHandler.takeMaterials(take);
        } catch(ResourceException e){
            assertEquals(e.getMessage(), "Trying to put negative Materials inside strongbox");
        }

        assertEquals(resourceHandler.getStrongboxState(), strongboxState);
        assertEquals(resourceHandler.getAdditionalDeposit(), additionalDeposit);

        //this test takes all the material from the deposit and the strongbox
        take.put(Resource.SERVANT, 3);
        take.put(Resource.STONE, 1);
        take.put(Resource.COIN, 2);
        take.put(Resource.SHIELD, 0);

        assert(resourceHandler.checkMaterials(take));

        try{
            resourceHandler.takeMaterials(take);
        } catch(ResourceException e){}

        strongboxState.clear();
        for(Resource r: Resource.values()){
            strongboxState.put(r, 0);
        }

        additionalDeposit.clear();
        for(int i=0; i<4; i++)
            additionalDeposit.add(null);

        assertEquals(resourceHandler.getStrongboxState(), strongboxState);
        assertEquals(resourceHandler.getAdditionalDeposit(), additionalDeposit);

        //this test tries to take materials from empty deposit and strongbox
        assertFalse(resourceHandler.checkMaterials(take));

        try{
            resourceHandler.takeMaterials(take);
        } catch(ResourceException e){
            assertEquals(e.getMessage(), "Trying to put negative Materials inside strongbox");
        }

        assertEquals(resourceHandler.getStrongboxState(), strongboxState);
        assertEquals(resourceHandler.getAdditionalDeposit(), additionalDeposit);
    }

    @Test
    public void checkCollectionAllSources() {
        //this test removes some resources and checks the correct state after the collection
        ResourceHandler resourceHandler = new ResourceHandler();

        Map<Resource, Integer> strongboxState = new HashMap<>();
        strongboxState.put(Resource.COIN, 2);
        strongboxState.put(Resource.STONE, 3);
        strongboxState.put(Resource.SERVANT, 4);
        strongboxState.put(Resource.SHIELD, 7);

        ArrayList<Resource> depositState = new ArrayList<>();
        depositState.add(null);
        depositState.add(Resource.SERVANT);
        depositState.add(null);
        depositState.add(Resource.SHIELD);
        depositState.add(null);
        depositState.add(Resource.SHIELD);

        ArrayList<Resource> additionalDeposit = new ArrayList<>();
        additionalDeposit.add(Resource.STONE);
        additionalDeposit.add(null);
        additionalDeposit.add(null);
        additionalDeposit.add(Resource.SERVANT);

        Map<Resource, Integer> take = new HashMap<>();
        take.put(Resource.STONE, 2);
        take.put(Resource.SERVANT, 3);
        take.put(Resource.COIN, 2);

        try{
            resourceHandler.addAdditionalDeposit(Resource.STONE);
            resourceHandler.addAdditionalDeposit(Resource.SERVANT);
            resourceHandler.newAdditionalDepositState(additionalDeposit);
            resourceHandler.addMaterialStrongbox(strongboxState);
            resourceHandler.newDepositState(depositState);
            assert(resourceHandler.checkMaterials(take));
            resourceHandler.takeMaterials(take);
        } catch(ResourceException e){}

        strongboxState.put(Resource.COIN, 0);
        strongboxState.put(Resource.STONE, 2);
        strongboxState.put(Resource.SERVANT, 3);
        strongboxState.put(Resource.SHIELD, 7);

        depositState.set(0, null);
        depositState.set(1, null);
        depositState.set(2, null);
        depositState.set(3, Resource.SHIELD);
        depositState.set(4, null);
        depositState.set(5, Resource.SHIELD);

        additionalDeposit.set(0, null);
        additionalDeposit.set(1, null);
        additionalDeposit.set(2, null);
        additionalDeposit.set(3, null);

        assertEquals(resourceHandler.getAdditionalDeposit(), additionalDeposit);
        assertEquals(resourceHandler.getStrongboxState(), strongboxState);
        assertEquals(resourceHandler.getDepositState(), depositState);

        //this test tries to take more materials than the available
        take.put(Resource.SHIELD, 2);
        take.put(Resource.COIN, 7);
        take.put(Resource.STONE, 1);

        assertFalse(resourceHandler.checkMaterials(take));

        try{
            resourceHandler.takeMaterials(take);
        } catch(ResourceException e){}

        assertEquals(resourceHandler.getAdditionalDeposit(), additionalDeposit);
        assertEquals(resourceHandler.getStrongboxState(), strongboxState);
        assertEquals(resourceHandler.getDepositState(), depositState);

        //this test empties all the deposits and the strongbox
        take.put(Resource.STONE, 2);
        take.put(Resource.SERVANT, 3);
        take.put(Resource.COIN, 0);
        take.put(Resource.SHIELD, 9);

        try{
            resourceHandler.takeMaterials(take);
        } catch(ResourceException e){}

        additionalDeposit.clear();
        additionalDeposit.add(null);
        additionalDeposit.add(null);
        additionalDeposit.add(null);
        additionalDeposit.add(null);

        depositState.clear();
        depositState.add(null);
        depositState.add(null);
        depositState.add(null);
        depositState.add(null);
        depositState.add(null);
        depositState.add(null);

        strongboxState.clear();
        strongboxState.put(Resource.COIN, 0);
        strongboxState.put(Resource.STONE, 0);
        strongboxState.put(Resource.SERVANT, 0);
        strongboxState.put(Resource.SHIELD, 0);

        assertEquals(resourceHandler.getAdditionalDeposit(), additionalDeposit);
        assertEquals(resourceHandler.getStrongboxState(), strongboxState);
        assertEquals(resourceHandler.getDepositState(), depositState);

        //this tries to take from materials from empty deposits and strongbox
        assertFalse(resourceHandler.checkMaterials(take));

        try{
            resourceHandler.takeMaterials(take);
        } catch(ResourceException e){
            assertEquals(e.getMessage(), "Trying to put negative Materials inside strongbox");
        }

        assertEquals(resourceHandler.getAdditionalDeposit(), additionalDeposit);
        assertEquals(resourceHandler.getStrongboxState(), strongboxState);
        assertEquals(resourceHandler.getDepositState(), depositState);
    }
}
