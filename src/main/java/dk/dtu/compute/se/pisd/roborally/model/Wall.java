package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

public class Wall extends Subject {

    public final Board board;
    public final int x;
    public final int y;
    private boolean hit;
    public final Heading heading;

    public Wall(Board board, int x, int y, Heading heading){
        this.board = board;
        this.x = x;
        this.y = y;
        this.heading = heading;
        hit = false;
    }

}
