package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class LaserPlayerAction extends PlayerAction {

    private int range = 3;

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

            if (robotInRange) {
                otherRobot.hit();
                if (otherRobot.isReboot()) {
                    otherRobot.setSpace(otherRobot.getStartSpace());
                    otherRobot.setReboot(false);
                }
            }

            return true;
        } else {
            return false;
        }

    }
}
