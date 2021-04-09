package it.polimi.ingsw.Model.Lorenzo;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class LorenzoIlMagnificoTest {

    LorenzoIlMagnifico lorenzo = new LorenzoIlMagnifico();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void getSoloActionToken(){
        ArrayList<SoloAction> actionList = lorenzo.getSoloActionToken();

        for(int i=0; i < lorenzo.getSoloActionToken().size(); i++)
            assertEquals(lorenzo.getSoloActionToken().get(i), actionList.get(i));
    }

    @Test
    public void getToken() {
        ArrayList<SoloAction> actionList = lorenzo.getSoloActionToken();


        if (actionList.get(0).name() != SoloAction.MOVESHUFFLE.name()){
            SoloAction action = lorenzo.getToken();

            assertEquals(action, lorenzo.getSoloActionToken().get(actionList.size()-1));

        }
        else{

        }

    }
}