package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;


public class ConveyorBeltFieldAction extends FieldAction{

    /**
     *
     * @author S205354, S205353
     *
     * ConveyorBelt has the responsilibty of containting the doAction of ConveyorBelt and is the element
     * we list to create the visual element in SpaceView.
     *
     * doAction method contains the logic for what will happen when a player steps onto the space that contains
     * a ConveyorBelt. This Method takes the player, and moves the player one space towards the heading the co-
     * nveyer belt points while the player still keeps its original heading.
     *
     * @param gameController is needed to use the methods in the gameController for player in question on space.
     * @param space is needed to distinguish the space doAction is used on.
     *
     * @param heading is needed as a parameter to create visual element so the arrow points the correct way
     *                and to used as a factor in doAction method.
     *
     * */

    Heading heading;

    /**
     * This is the constructor of the ConveyorBeltFieldAction
     */
    public ConveyorBeltFieldAction(){
    }

    /**
     * This getter returns the heading of this conveyorBeltFieldAction
     * @return Heading heading
     */
    public Heading getHeading(){
        return heading;
    }

    /**
     * This setter sets the heading of this ConveyorBeltFieldAction
     * @param heading Heading heading
     */
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
