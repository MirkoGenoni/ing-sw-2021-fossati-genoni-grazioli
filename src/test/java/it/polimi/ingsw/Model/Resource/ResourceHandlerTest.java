package it.polimi.ingsw.Model.Resource;

import it.polimi.ingsw.Model.Exceptions.ResourceException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ResourceHandlerTest {

    @Test
    public void testAdditionalDeposit() {
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

        ArrayList<Resource> ReturnGetterAdditionalDeposit;
        ReturnGetterAdditionalDeposit = resourceHandler.getAdditionalDeposit();
        assert (ReturnGetterAdditionalDeposit.equals(testArrayList));

        //This tries to add an additional deposit with a type already added, checks the error and
        // that the state of the additional deposit didn't change
        try {
            resourceHandler.addAdditionalDeposit(Resource.COIN);
        } catch (ResourceException e) {
            assertEquals(e.getMessage(), "Already added an additional deposit with this resource");
        }

        assert (ReturnGetterAdditionalDeposit.equals(testArrayList));

        //This tries to add another type of resource to the additional deposit and checks the
        // return of the method getAdditionalDeposit
        testArrayList.add(null);
        testArrayList.add(null);

        try {
            resourceHandler.addAdditionalDeposit(Resource.SHIELD);
        } catch (ResourceException e) {
        }

        ReturnGetterAdditionalDeposit = resourceHandler.getAdditionalDeposit();
        assert (ReturnGetterAdditionalDeposit.equals(testArrayList));

        //This tries to add a third type of resource to the additional deposit and then checks the
        // error received and that the state hasn't changed
        try {
            resourceHandler.addAdditionalDeposit(Resource.STONE);
        } catch (ResourceException e) {
            assertEquals(e.getMessage(), "Already 2 additional deposit");
        }

        ReturnGetterAdditionalDeposit = resourceHandler.getAdditionalDeposit();
        assert (ReturnGetterAdditionalDeposit.equals(testArrayList));

        //This tries to add two materials of a type compatible with the additional deposit and checks
        // the correct return of the method getAdditionalDeposit
        Map<Resource, Integer> TestInputMap = new HashMap<>();

        TestInputMap.put(Resource.COIN, 2);

        try {
            resourceHandler.addMaterialAdditionalDeposit(TestInputMap);
        } catch (ResourceException e) {
        }

        testArrayList.set(0, Resource.COIN);
        testArrayList.set(1, Resource.COIN);
        testArrayList.set(2, null);
        testArrayList.set(3, null);

        ReturnGetterAdditionalDeposit = resourceHandler.getAdditionalDeposit();

        assert (ReturnGetterAdditionalDeposit.equals(testArrayList));

        //This tries to add two materials of a type different from the previous but compatible
        // with the additional deposit and checks the correct return of the method getAdditionalDeposit
        TestInputMap.remove(Resource.COIN);
        TestInputMap.put(Resource.SHIELD, 2);

        try {
            resourceHandler.addMaterialAdditionalDeposit(TestInputMap);
        } catch (ResourceException e) {
        }

        testArrayList.set(0, Resource.COIN);
        testArrayList.set(1, Resource.COIN);
        testArrayList.set(2, Resource.SHIELD);
        testArrayList.set(3, Resource.SHIELD);

        ReturnGetterAdditionalDeposit = resourceHandler.getAdditionalDeposit();

        assert (ReturnGetterAdditionalDeposit.equals(testArrayList));

        //This tries to add more materials than the one that can be inserted inside the additional deposit
        // and checks the throw of the correct error and that the state of the additional deposit hasn't changed
        TestInputMap.put(Resource.COIN, 1);

        try {
            resourceHandler.addMaterialAdditionalDeposit(TestInputMap);
        } catch (ResourceException e) {
            assertEquals(e.getMessage(), "Additional deposit already full");
        }

        ReturnGetterAdditionalDeposit = resourceHandler.getAdditionalDeposit();

        assert (ReturnGetterAdditionalDeposit.equals(testArrayList));

        //This tries to add two materials of a type not compatible with the additional deposit and checks
        // the throw of the correct error and that the state of the additional deposit hasn't changed
        TestInputMap.clear();
        TestInputMap.put(Resource.SERVANT, 1);

        try {
            resourceHandler.addMaterialAdditionalDeposit(TestInputMap);
        } catch (ResourceException e) {
            assertEquals(e.getMessage(), "Resource not compatible with the additional deposit");
        }

        ReturnGetterAdditionalDeposit = resourceHandler.getAdditionalDeposit();
        assert (ReturnGetterAdditionalDeposit.equals(testArrayList));

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

        assert (inputState.equals(output));

        try {
            testResourceHandler.newDepositState(inputState);
        } catch (ResourceException e) {
        }

        output = testResourceHandler.getDepositState();
        assert (output.equals(inputState));

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
        assert (output.equals(inputState));

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
        assert (output.equals(inputState));

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
        assert (output.equals(inputState));

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
        assert (output.equals(inputState));

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

        assert (output.equals(testResourceHandler.getDepositState()));

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

        assert (output.equals(testResourceHandler.getDepositState()));
    }

    @Test
    public void testCheckTakeMaterialsSingleSource() {
        ResourceHandler resourceHandler = new ResourceHandler();
        Map<Resource, Integer> requirements = new HashMap<>();
        Map<Resource, Integer> checkState = new HashMap<>();

        for (Resource r : Resource.values()) {
            requirements.put(r, 5);
            checkState.put(r, 0);
        }

        //this checks that the material added to the strongbox are added correctly,
        //that they're available and that can be taken from the handler, then checks
        //the final state of the strongbox
        resourceHandler.addMaterialStrongbox(requirements);
        assert (resourceHandler.checkMaterials(requirements));
        try {
            resourceHandler.takeMaterials(requirements);
        } catch (ResourceException e) {
        }

        Map<Resource, Integer> expected = resourceHandler.getStrongboxState();
        assert (checkState.equals(expected));

        //Adds a new deposit state and checks the correct new deposit state inside the handler,
        //then takes the material only from the deposit and checks the correct final state
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

        assert (resourceHandler.getDepositState().equals(testAdd));

        requirements.put(Resource.COIN, 3);
        requirements.put(Resource.STONE, 0);
        requirements.remove(Resource.SHIELD);
        requirements.put(Resource.SERVANT, 0);

        assert (resourceHandler.checkMaterials(requirements));

        try {
            resourceHandler.takeMaterials(requirements);
        } catch (ResourceException e) {
        }

        testAdd.set(0, null);
        testAdd.set(1, null);
        testAdd.set(2, Resource.SERVANT);
        testAdd.set(3, null);
        testAdd.set(4, null);
        testAdd.set(5, null);

        assert (resourceHandler.getDepositState().equals(testAdd));
    }

    @Test
    public void checkCollectionResourcesTwoSources() {
        //Test that checks the correct initialization of the deposit state
        //and the strongbox state and the correct collect of resources, half from
        //the deposit, half from the strongbox without emptying them
        ResourceHandler resourceHandler = new ResourceHandler();

        ArrayList<Resource> testAdd = new ArrayList<>();
        testAdd.add(0, Resource.STONE);
        testAdd.add(1, Resource.SHIELD);
        testAdd.add(2, Resource.SHIELD);
        testAdd.add(3, Resource.COIN);
        testAdd.add(4, Resource.COIN);
        testAdd.add(5, Resource.COIN);

        Map<Resource, Integer> requirements = new HashMap<>();
        requirements.put(Resource.SHIELD, 3);

        Map<Resource, Integer> testStrongbox = new HashMap<>();
        testStrongbox.put(Resource.SHIELD, 2);

        Map<Resource, Integer> testStrongboxState = new HashMap<>();
        testStrongboxState.put(Resource.STONE, 0);
        testStrongboxState.put(Resource.COIN, 0);
        testStrongboxState.put(Resource.SHIELD, 2);
        testStrongboxState.put(Resource.SERVANT, 0);

        try {
            resourceHandler.newDepositState(testAdd);
            resourceHandler.addMaterialStrongbox(testStrongbox);
        } catch (ResourceException e) {
        }

        assert (resourceHandler.getDepositState().equals(testAdd));

        assertEquals(resourceHandler.getStrongboxState(), testStrongboxState);

        try {
            resourceHandler.takeMaterials(requirements);
        } catch (ResourceException e) {
        }

        testAdd.set(0, Resource.STONE);
        testAdd.set(1, null);
        testAdd.set(2, null);
        testAdd.set(3, Resource.COIN);
        testAdd.set(4, Resource.COIN);
        testAdd.set(5, Resource.COIN);

        testStrongboxState.put(Resource.SHIELD, 1);

        assert (resourceHandler.getDepositState().equals(testAdd));
        assert (resourceHandler.getStrongboxState().equals(testStrongboxState));

        //This takes the resources that are in excess from before and leaves all the
        //data structures at the initial state, empty
        requirements.put(Resource.COIN, 3);
        requirements.put(Resource.STONE, 2);
        requirements.put(Resource.SHIELD, 1);
        requirements.remove(Resource.SERVANT);


        try {
            resourceHandler.takeMaterials(requirements);
        } catch (ResourceException e) {
            assertEquals(e.getMessage(), "Trying to put negative Materials inside strongbox");
        }

        assert (resourceHandler.getDepositState().equals(testAdd));
        assert (resourceHandler.getStrongboxState().equals(testStrongboxState));
    }
}