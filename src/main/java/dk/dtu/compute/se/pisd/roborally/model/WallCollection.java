package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

import java.util.*;


public class WallCollection extends Subject {

    /**
     * @author s205353
     * This class keeps a collection of all wall objects
     */
    private List<Wall> myCollection;
    private WallCollection wallCollection;

    /**
     * private constructor --> Singleton Design Pattern
     */
    public WallCollection() {
        myCollection = new ArrayList<>();
    }

    /**
     * static getter
     * @return Singleton WallCollection instance
     */

    /**
     * @author s205353
     * This method checks if a wall is blocking the path of the robot
     *
     * @param currentX x coordinate of robot
     * @param currentY y coordinate of robot
     * @param newX x coordinate robot wants to move to
     * @param newY y coordinate robot wants to move to
     * @return boolean blocked, true if there is a wall.
     */

    public boolean isWallBlocking(int currentX, int currentY, int newX, int newY){

        boolean blocked = false;

        for (Wall wall : myCollection){
           if (currentX == wall.x1() && currentY == wall.y1() &&
                   newX == wall.x2() && newY == wall.y2() ||
                   currentX == wall.x2() && currentY == wall.y2() &&
                           newX == wall.x1() && newY == wall.y1())
            {
               blocked = true;
               break;
           }
        }
        return blocked;
    }

    public List<Wall> getMyCollection(){
        return myCollection;
    }
}
