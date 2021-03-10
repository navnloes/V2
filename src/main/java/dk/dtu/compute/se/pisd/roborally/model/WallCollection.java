package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

import java.util.*;

public class WallCollection extends Subject {

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
           if (wall.x1() == x1 &&
               wall.y1() == y1 &&
                   wall.x2() == x2 &&
                   wall.y2() == y2){
               exists = true;
               break;
           }
        }
        return exists;
    }

    public List<Wall> getMyCollection(){
        return myCollection;
    }
}
