package it.polimi.ingsw.model.resource;

import it.polimi.ingsw.model.exceptions.ResourceException;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class StrongboxTest {

    /**
     * This test checks the correct initialization and the correct functionality of the method getQuantity without
     * parameters passed
     */
    @Test
    public void getQuantity() {

        Map<Resource, Integer> test = new HashMap<>();

        test.put(Resource.COIN, 0);
        test.put(Resource.SERVANT, 0);
        test.put(Resource.SHIELD, 0);
        test.put(Resource.STONE, 0);

        Strongbox strongbox = new Strongbox();

        assert(test.equals(strongbox.getQuantity()));
    }

    /**
     * This test checks the correct functionality of the addMaterials method and the getQuantity method passing a
     * String that identifies the material needed, then removes an amount of materials and checks the correct
     * functionality of the returnMaterials method and its error handling
     */
    @Test
    public void testAddReturnMaterials() {

        Map<Resource, Integer> test = new HashMap<>();

        test.put(Resource.COIN, 0);
        test.put(Resource.SERVANT, 5);
        test.put(Resource.SHIELD, 5);
        test.put(Resource.STONE, 0);

        Strongbox strongbox = new Strongbox();
        strongbox.addMaterials(test);

        assert(test.get(Resource.COIN).equals(strongbox.getQuantity(Resource.COIN)));
        assert(test.get(Resource.SHIELD).equals(strongbox.getQuantity(Resource.SHIELD)));
        assert(test.get(Resource.SERVANT).equals(strongbox.getQuantity(Resource.SERVANT)));
        assert(test.get(Resource.STONE).equals(strongbox.getQuantity(Resource.STONE)));

        test.put(Resource.COIN, 0);
        test.put(Resource.SERVANT, 2);
        test.put(Resource.SHIELD, 2);
        test.put(Resource.STONE, 0);

        try{
            strongbox.returnMaterials(test);
        } catch (ResourceException e) {
            System.out.println(e.getMessage());
        }

        test.put(Resource.COIN, 0);
        test.put(Resource.SERVANT, 3);
        test.put(Resource.SHIELD, 3);
        test.put(Resource.STONE, 0);

        assert(test.equals(strongbox.getQuantity()));

        test.put(Resource.COIN, 0);
        test.put(Resource.SERVANT, 4);
        test.put(Resource.SHIELD, 4);
        test.put(Resource.STONE, 0);

        try{
            strongbox.returnMaterials((test));
        }catch(ResourceException e){
            assertEquals(e.getMessage(), "Trying to put negative Materials inside strongbox");
        }
    }
}