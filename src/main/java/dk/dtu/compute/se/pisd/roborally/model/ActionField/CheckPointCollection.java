package dk.dtu.compute.se.pisd.roborally.model.ActionField;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.model.Wall;

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


}
