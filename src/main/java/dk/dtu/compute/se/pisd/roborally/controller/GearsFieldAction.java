package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Direction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class GearsFieldAction extends FieldAction{

    /**
     * @author S205354, S201192
     *
     * doAction method for GearsFieldAction will take the player in question and turn
     * the player in whichever the direction the GearsFieldAction is set to.
     *
     * @param gameController is needed to use the methods in the gameController for player in question on space.
     * @param space is needed to distinguish the space doAction is used on.
     *
     * @param direction is used for determining what direction the player in question needs to be turned,
     *                  and for the visual element in SpaceView where the direction determines a specific
     *                  visual feature to the board.
     */

    Direction direction;


    public GearsFieldAction(){
    }

    public Direction getDirection(){
        return direction;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    @Override
    public boolean doAction(GameController gameController, Space space) {
        Player player = space.getPlayer();
        Heading heading = player.getHeading();

        if (direction == Direction.RIGHT){
            player.setHeading(heading.next());
        } else if (direction == Direction.LEFT){
            player.setHeading(heading.prev());
        } else {
            return false;
        }
        return true;
    }
}

