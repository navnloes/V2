package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Direction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class GearsFieldAction extends FieldAction{

    Direction direction;

    /**
     * @author S205354 & S201192
     *  This method constructs a GearsActionfield.
     */
    public GearsFieldAction(){
        //TODO:
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

