package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

import java.util.*;


public class WallCollection extends Subject {

    /**
     * @author s205353
     * This class keeps a collection of all wall objects
     */
    private List<Wall> myCollection;

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
     * @return boolean blocked, true if there is a wall.
     */

    public boolean isWallBlocking(){

        boolean blocked = false;

        return blocked;
    }

    public List<Wall> getMyCollection(){
        return myCollection;
    }
}
