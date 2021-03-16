package dk.dtu.compute.se.pisd.roborally.model.ActionField;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.ArrayList;
import java.util.List;

public class ConveyorBeltCollection extends Subject {

    private List<ConveyorBeltActionField> myCollection;

    public ConveyorBeltCollection(){
        myCollection = new ArrayList<>();
    }

    /**
     * This method adds a CheckPointActionField to the collection
     * @param conveyorBeltActionField CheckPointActionField
     */
    public void addActionField(ConveyorBeltActionField conveyorBeltActionField) {
        myCollection.add(conveyorBeltActionField);
    }

    /**
     * This method returns the collection of CheckPointActionField
     * @return myCollection - List<ConveyorBeltActionField>
     */
    public List<ConveyorBeltActionField> getMyCollection() {
        return myCollection;
    }

    /**
     * This method checks, if the given space is a checkPoint
     * @param space Space - space on the board
     * @return arrived boolean - true if the space is a checkpoint
     */
    public boolean isConveyorBelt(Space space){

        boolean arrived = false;
        for (ConveyorBeltActionField c : myCollection){
            if (space.x == c.x && space.y == c.y){
                arrived = true;
                break;
            }
        }
        return arrived;
    }
}