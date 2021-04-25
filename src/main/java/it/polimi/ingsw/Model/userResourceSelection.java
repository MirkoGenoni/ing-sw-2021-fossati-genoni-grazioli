package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.ResourceException;
import it.polimi.ingsw.Model.Resource.Resource;
import it.polimi.ingsw.Model.Resource.ResourceHandler;

import java.util.ArrayList;

public class userResourceSelection {
    public static void main(String[] args) {
        ArrayList<Resource> incoming = new ArrayList<Resource>();
        incoming.add(Resource.SERVANT);
        incoming.add(Resource.STONE);
        incoming.add(Resource.COIN);
        incoming.add(Resource.SHIELD);

        ArrayList<Resource> initial_deposit = new ArrayList<Resource>();
        incoming.add(Resource.SERVANT);
        incoming.add(Resource.COIN);
        incoming.add(Resource.COIN);
        incoming.add(Resource.SHIELD);
        incoming.add(Resource.SHIELD);
        incoming.add(Resource.SHIELD);

        ResourceHandler resourceHandler = new ResourceHandler();
        try{
            resourceHandler.newDepositState(initial_deposit);
        }catch(ResourceException e){

        }


    }
}
