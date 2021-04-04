package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

public class Wall extends Subject {

    /**
     * @author s205353, s205354, s205339, s201192
     */
    private Heading heading;


    public Wall(String dir) {
        if (dir.equalsIgnoreCase("WEST"))
            heading = Heading.WEST;
        if (dir.equalsIgnoreCase("EAST"))
            heading = Heading.EAST;
        if (dir.equalsIgnoreCase("SOUTH"))
            heading = Heading.SOUTH;
        if (dir.equalsIgnoreCase("NORTH"))
            heading = Heading.NORTH;
    }


    public Heading getHeading() {
        return heading;
    }
}