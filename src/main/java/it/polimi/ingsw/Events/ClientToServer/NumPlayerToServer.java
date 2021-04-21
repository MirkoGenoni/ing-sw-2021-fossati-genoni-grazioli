package it.polimi.ingsw.Events.ClientToServer;

public class NumPlayerToServer extends EventToServer{
    private final int numPlayer;
    private final String namePlayer;

    public NumPlayerToServer(int numPlayer, String namePlayer) {
        this.numPlayer = numPlayer;
        this.namePlayer = namePlayer;
    }

    public int getNumPlayer() {
        return numPlayer;
    }

    public String getNamePlayer() {
        return namePlayer;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
