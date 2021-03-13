package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

public class Wall extends Subject {

    /**
     * @author s205353, s205354, s205339, s201192
     */
    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;
    private int direction;

    /**
     * This is the constructor of the Wall object
     * (x1,y1) is the position of the space, which is blocked from a robot moving from space (x2, y2)
     * (x2,y2) is the position of the space, which is blocked from a robot moving from space (x1,y1)
     * @param x1 x1 is space x coordinate
     * @param y1 y1 is space y coordinate
     * @param x2 x2 is space x coordinate
     * @param y2 y2 is space y coordinate
     * @param direction direction shows direction of Wall: int 0 = vertical and 1 = horizontal
     */
    public Wall(int x1, int y1, int x2, int y2, int direction){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.direction = direction;
    }

    public int x1(){
        return x1;
    }

    public int x2(){
        return x2;
    }

    public int y1(){
        return y1;
    }

    public int y2(){
        return y2;
    }

    public int direction(){
        return direction;
    }


}
