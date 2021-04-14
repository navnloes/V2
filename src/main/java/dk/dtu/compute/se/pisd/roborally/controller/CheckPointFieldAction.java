package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * @author s205353
 * This class is the specialized FieldAction class for CheckPointsActions
 */

public class CheckPointFieldAction extends FieldAction{

    /**
     * Each CheckPoint has an unique ID
     */
    private int checkPointId;

    /**
     * This method is used in order to fetch the ID of a given checkPoint
     * @return checkPointId : int
     */
    public int getCheckPointId() {
        return checkPointId;
    }

    /**
     * This method is a setter in order to set the CheckPoint ID of a given CheckPoint
     * @param checkPointId checkPointId : int
     */
    public void setCheckPointId(int checkPointId) {
        this.checkPointId = checkPointId;
    }

    /**
     *
     * @param gameController the gameController of the respective game
     * @param space the space this action should be executed for
     * @return boolean true if the action is executed and false, if the action isn't executed
     */
    @Override
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
