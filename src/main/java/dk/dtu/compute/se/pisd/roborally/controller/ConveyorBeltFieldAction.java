package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 *
 * @author S205354, S205353
 * */
public class ConveyorBeltFieldAction extends FieldAction{

    Heading heading;

    public ConveyorBeltFieldAction(){
    }

    public Heading getHeading(){
        return heading;
    }


    public void setHeading(Heading heading){
        this.heading = heading;
    }

    @Override
    public boolean doAction(GameController gameController, Space space) {
        if (space != null && heading != null){
            Player player = space.getPlayer();
            Heading heading = player.getHeading();
            player.setHeading(this.heading);
            gameController.moveForward(player);
            player.setHeading(heading);
            return true;
        } else {
            return false;
        }
    }
}
