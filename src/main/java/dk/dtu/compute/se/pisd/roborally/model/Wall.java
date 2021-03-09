package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

public class Wall extends Subject {

    public final int x1;
    public final int y1;
    public final int x2;
    public final int y2;
    private int[] wallCoordinates = new int[4];

    public Wall(int x1, int y1, int x2, int y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        wallCoordinates[0] = x1;
        wallCoordinates[1] = y1;
        wallCoordinates[2] = x2;
        wallCoordinates[3] = y2;
    }

    public int[] getCoordinates(){
        return wallCoordinates;
    }




}
