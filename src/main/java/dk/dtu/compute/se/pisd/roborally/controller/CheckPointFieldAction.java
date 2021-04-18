package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * @author s205353, @s205354
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
            arrivedCheckPoint(checkPointId,player);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method models how the player collects checkPoint tokens when arriving at a checkPoint
     * @param id - int id of the checkPoint
     * @param player - Player player that arrives at checkPoint
     */
    public void arrivedCheckPoint(int id, Player player){

        boolean[] checkPointArray = player.getCheckPointArray();

        switch (id) {
            case 0:
                if (!checkPointArray[0]){
                    player.addCheckPointToken();
                    checkPointArray[0] = true;
                }
                break;
            case 1:
                if (checkPointArray[0] && !checkPointArray[1]){
                    player.addCheckPointToken();
                    checkPointArray[1] = true;
                }
                break;
            case 2:
                if (checkPointArray[1] && !checkPointArray[2]){
                    player.addCheckPointToken();
                    checkPointArray[2] = true;
                }

                break;
            default:
                System.out.println("Illegal id - CheckPoint ID " + id + " in arrivedCheckPoint(int id)");

        }

    }


}
