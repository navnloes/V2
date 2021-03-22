package dk.dtu.compute.se.pisd.roborally.model.ActionField;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.model.Direction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.ArrayList;
import java.util.List;

public class GearsCollection extends Subject {

    private List<GearsActionField> myCollection;

    public GearsCollection(){
        myCollection = new ArrayList<>();
    }

    /**
     * This method adds a GearsActionField  to the collection
     * @param gearsActionField GearsActionField
     */
    public void addActionField(GearsActionField gearsActionField) {
        myCollection.add(gearsActionField);
    }

    /**
     * This method returns the collection of GearsActionField
     * @return myCollection - List<GearsActionField>
     */
    public List<GearsActionField> getMyCollection() {
        return myCollection;
    }

    /**
     * This method checks, if the given space is a gears
     * @param space Space - space on the board
     * @return arrived boolean - true if the space is a gears
     */
    public boolean isGears(Space space){

        boolean arrived = false;
        for (GearsActionField g : myCollection){
            if (space.x == g.x && space.y == g.y){
                arrived = true;
                break;
            }
        }
        return arrived;
    }

    /**
     * This method models the action that a gearActionField performs to the robot that lands on it
     * @param player player that lands on gearActionField
     * @param space space that the player has landed on
     * @return
     */
    public Heading gearAction(Player player,Space space){

        Heading heading = player.getHeading();
        for (GearsActionField g : myCollection){
            if (space.x == g.x && space.y == g.y){

                if(g.direction == Direction.LEFT) {
                heading = heading.prev(); }
                else {heading = heading.next();}
                break;
            }
        }
        return heading;
    }

}