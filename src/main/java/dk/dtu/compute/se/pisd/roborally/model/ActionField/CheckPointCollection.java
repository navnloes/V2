package dk.dtu.compute.se.pisd.roborally.model.ActionField;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.ArrayList;
import java.util.List;

public class CheckPointCollection extends Subject {

    private List<CheckPointActionField> myCollection;

    public CheckPointCollection(){
        myCollection = new ArrayList<>();
    }

    public void addActionField(CheckPointActionField checkPointActionField) {
        myCollection.add(checkPointActionField);
    }


    public List<CheckPointActionField> getMyCollection() {
        return myCollection;
    }

    public boolean isCheckPoint(Space space){

        boolean arrived = false;
        for (CheckPointActionField c : myCollection){
            if (space.x == c.x && space.y == c.y){
                arrived = true;
            }
        }
        return arrived;
    }

    public int getCheckPointId(Space space){
        for (CheckPointActionField c : myCollection){
            if (space.x == c.x && space.y == c.y){
                return c.id();
            }
        }
        return 0;
    }


}
