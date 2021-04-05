package it.polimi.ingsw.Model.Resource;

import it.polimi.ingsw.Model.Exceptions.ResourceException;
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

        Map<String, Integer> test = new HashMap<>();

        test.put(Resource.COIN.toString(), 0);
        test.put(Resource.SERVANT.toString(), 0);
        test.put(Resource.SHIELD.toString(), 0);
        test.put(Resource.STONE.toString(), 0);

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

        Map<String, Integer> test = new HashMap<>();

        test.put(Resource.COIN.toString(), 0);
        test.put(Resource.SERVANT.toString(), 5);
        test.put(Resource.SHIELD.toString(), 5);
        test.put(Resource.STONE.toString(), 0);

        Strongbox strongbox = new Strongbox();
        strongbox.addMaterials(test);

        assert(test.get(Resource.COIN.toString()).equals(strongbox.getQuantity(Resource.COIN.toString())));
        assert(test.get(Resource.SHIELD.toString()).equals(strongbox.getQuantity(Resource.SHIELD.toString())));
        assert(test.get(Resource.SERVANT.toString()).equals(strongbox.getQuantity(Resource.SERVANT.toString())));
        assert(test.get(Resource.STONE.toString()).equals(strongbox.getQuantity(Resource.STONE.toString())));

        test.put(Resource.COIN.toString(), 0);
        test.put(Resource.SERVANT.toString(), 2);
        test.put(Resource.SHIELD.toString(), 2);
        test.put(Resource.STONE.toString(), 0);

        try{
            strongbox.returnMaterials(test);
        } catch (ResourceException e) {
            System.out.println(e.getMessage());
        }

        test.put(Resource.COIN.toString(), 0);
        test.put(Resource.SERVANT.toString(), 3);
        test.put(Resource.SHIELD.toString(), 3);
        test.put(Resource.STONE.toString(), 0);

        assert(test.equals(strongbox.getQuantity()));

        test.put(Resource.COIN.toString(), 0);
        test.put(Resource.SERVANT.toString(), 4);
        test.put(Resource.SHIELD.toString(), 4);
        test.put(Resource.STONE.toString(), 0);

        try{
            strongbox.returnMaterials((test));
        }catch(ResourceException e){
            assertEquals(e.getMessage(), "Trying to put negative Materials inside strongbox");
        }
    }
}