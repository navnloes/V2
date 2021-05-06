package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class DoubleConveyorBeltFieldAction extends FieldAction{

    /**
     *
     * @Author S205354
     * */

    Heading heading;

    public DoubleConveyorBeltFieldAction(){
        //TODO:
    }

    public Heading getHeading(){
        return heading;
    }

    public void setHeading(Heading heading){
        this.heading = heading;
    }

    @Override
    public boolean doAction(GameController gameController, Space space) {
        Player player = space.getPlayer();
        Heading heading = player.getHeading();
        player.setHeading(this.heading);
        gameController.moveForward(player);
        gameController.moveForward(player);
        player.setHeading(heading);
        return false;
    }
}
