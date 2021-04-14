package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class CheckPointFieldAction extends FieldAction{

    private int checkPointId;

    public int getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(int checkPointId) {
        this.checkPointId = checkPointId;
    }

    public boolean doAction(GameController gameController, Space space) {
        Player player = space.getPlayer();
        if (player != null){
            player.arrivedCheckPoint(checkPointId);
            return true;
        } else {
            return false;
        }
    }


}
