package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * @author s205353, s205339, s201192
 */
public class LaserPlayerAction extends PlayerAction {

    /**
     * This integer defines the range, that the robots can shoot with their laser
     */
    private int range = 3;

    /**
     * This method models the action of the Laser
     * @param gameController the gameController of the respective game
     * @param player the player whose action should be executed
     * @return boolean true if the action is performed right
     */
    @Override
    public boolean doAction(GameController gameController, Player player) {
        if (player != null){
            Space space = player.getSpace();
            Heading heading = player.getHeading();

            boolean robotInRange = false;
            Player otherRobot = null;

            for (int i = 0; i < range; i++) {
                Space targetSpace = gameController.board.getNeighbour(space, heading);
                otherRobot = targetSpace.getPlayer();
                space = targetSpace;
                if (otherRobot != null) {
                    robotInRange = true;
                    break;
                }
            }
            if (robotInRange)
                otherRobot.spamDamage(1);

            return true;
        } else {
            return false;
        }
    }
}
