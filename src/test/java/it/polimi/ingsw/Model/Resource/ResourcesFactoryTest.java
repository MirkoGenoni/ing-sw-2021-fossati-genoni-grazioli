package it.polimi.ingsw.Model.Resource;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ResourcesFactoryTest {

    /**
     * This test creates an ArraryList of resource manually, building manually every resource using their
     * constructors and another using the ResourceFactory's method, passing the name of the resource and the quantity
     * needed and then it compares the two arraylist.
     */
    @Test
    public void testCreateMultipleMaterials() {
        ArrayList<Resource> standardMaterials = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            standardMaterials.add(new Coin());
        }
        for (int i = 0; i < 5; i++) {
            standardMaterials.add(new Faith());
        }
        for (int i = 0; i < 5; i++) {
            standardMaterials.add(new Nothing());
        }
        for (int i = 0; i < 5; i++) {
            standardMaterials.add(new Servant());
        }
        for (int i = 0; i < 5; i++) {
            standardMaterials.add(new Shield());
        }
        for (int i = 0; i < 5; i++) {
            standardMaterials.add(new Stone());
        }

        ArrayList<Resource> testMaterials = new ArrayList<>();
        testMaterials.addAll(ResourcesFactory.createMaterials("coin", 5));
        testMaterials.addAll(ResourcesFactory.createMaterials("faith", 5));
        testMaterials.addAll(ResourcesFactory.createMaterials("nothing", 5));
        testMaterials.addAll(ResourcesFactory.createMaterials("servant", 5));
        testMaterials.addAll(ResourcesFactory.createMaterials("shield", 5));
        testMaterials.addAll(ResourcesFactory.createMaterials("stone", 5));

        for (int j = 0; j < 30; j++) {
            String receivedStandard = standardMaterials.get(j).getMaterialType();
            String receivedTest = testMaterials.get(j).getMaterialType();
            assertEquals(receivedStandard, receivedTest);
        }
    }

    /**
     * This test creates a single resource using the ResourcesFactory, passing only the name of the material
     * and then compares the object received with the type of Resource I requested
     */
    @Test
    public void testCreateSingleMaterial() {
        //TODO: there are warning here but they'll be dealt with when I'll make the exception in ResourcesFactory
        assertEquals(ResourcesFactory.createMaterials("coin").getMaterialType(), "coin");
        assertEquals(ResourcesFactory.createMaterials("faith").getMaterialType(), "faith");
        assertEquals(ResourcesFactory.createMaterials("nothing").getMaterialType(), "nothing");
        assertEquals(ResourcesFactory.createMaterials("servant").getMaterialType(), "servant");
        assertEquals(ResourcesFactory.createMaterials("shield").getMaterialType(), "shield");
        assertEquals(ResourcesFactory.createMaterials("stone").getMaterialType(), "stone");
    }
}