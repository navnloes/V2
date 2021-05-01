package dk.dtu.compute.se.pisd.roborally.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author s205352,s205353,s205354
 */

//TODO: MVC??

public class PriorityAntenna {

    private int x;
    private int y;
    private List<Integer> ranking;
    List<Player> rankedplayers;

    public PriorityAntenna(){
        ranking = new ArrayList<>();
        rankedplayers = new ArrayList();
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setX(int i){
        x = i;
    }
    public void setY(int i) {
        y = i;
    }

    public Player getFirstPlayer(Board board){
        rankedplayers = new ArrayList();
        Player player = null;
        for (int i = 0; i < board.getPlayers().size(); i++){
            player = board.getPlayer(i);
            setPlayerPriorityDistance(player);
            ranking.add(player.getDistance());

            if (player.getDistance() == ranking.get(0))
            rankedplayers.add(player);
        }
        return rankedplayers.get(0);
    }

    private void setPlayerPriorityDistance(Player player){
            int x = player.getSpace().x;
            int y = player.getSpace().y;
            int distance = Math.abs(this.y - y) + Math.abs(this.x - x);
            player.setDistance(distance);
    }
}
