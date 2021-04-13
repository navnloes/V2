//package dk.dtu.compute.se.pisd.roborally.model.ActionField;
//
//import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
//import dk.dtu.compute.se.pisd.roborally.model.Space;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class CheckPointCollection extends Subject {
//
//    /**
//     * @Author (orignally S205353) reused, and changed by S205354
//     */
//    private List<CheckPointActionField> myCollection;
//
//    public CheckPointCollection(){
//        myCollection = new ArrayList<>();
//    }
//
//    /**
//     * This method adds a CheckPointActionField to the collection
//     * @param checkPointActionField CheckPointActionField
//     */
//    public void addActionField(CheckPointActionField checkPointActionField) {
//        myCollection.add(checkPointActionField);
//    }
//
//    /**
//     * This method returns the collection of CheckPointActionField
//     * @return myCollection - List<CheckPointActionField>
//     */
//    public List<CheckPointActionField> getMyCollection() {
//        return myCollection;
//    }
//
//    /**
//     * This method checks, if the given space is a checkPoint
//     * @param space Space - space on the board
//     * @return arrived boolean - true if the space is a checkpoint
//     */
//
//    public boolean isCheckPoint(Space space){
//
//        boolean arrived = false;
//        for (CheckPointActionField c : myCollection){
//            if (space.x == c.x && space.y == c.y){
//                arrived = true;
//                break;
//            }
//        }
//        return arrived;
//    }
//
//
//    /**
//     * This method returns the checkPoint ID of the Checkpoint on a given space
//     * it is called only when isCheckPoint() returns arrived = true
//     * @param space Space - space on the board
//     * @return checkPointId int
//     *
//     */
//
//    public int getCheckPointId(Space space){
//
//        int checkPointId = -1;
//        for (CheckPointActionField c : myCollection){
//            if (space.x == c.x && space.y == c.y){
//                checkPointId = c.id;
//                break;
//            }
//        }
//        return checkPointId;
//    }
//
//
//}
