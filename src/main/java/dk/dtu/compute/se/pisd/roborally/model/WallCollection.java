package dk.dtu.compute.se.pisd.roborally.model;

import java.util.*;

public class WallCollection {

    private List<Wall> myCollection;
    public static WallCollection wallCollection;

    private WallCollection() {
        myCollection = new ArrayList<>();
    }

    public static WallCollection getInstance(){
        if (wallCollection == null){
            wallCollection = new WallCollection();
        }
        return wallCollection;
    }

    public void addWall(Wall wall){
        myCollection.add(wall);
    }

    public boolean wallExist(int x1, int y1, int x2, int y2){

        int[] coordinateArray;
        boolean exists = false;

        for (Wall wall : myCollection){
           coordinateArray = wall.getCoordinates();
           if (coordinateArray[0] == x1 &&
               coordinateArray[1] == y1 &&
                   coordinateArray[2] == x2 &&
                   coordinateArray[3] == y2){
               exists = true;
               break;
           }
        }
        return exists;
    }
}
