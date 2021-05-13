package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

import java.util.*;

/**
 * @author s205353, s205354,  s205352
 */

//TODO: MVC??

public class PriorityAntenna extends Subject {

    private Space space;
    Map<Player, Integer> sortedMap;

    public PriorityAntenna() {
    }

    public void setSpace(Space space) {
        this.space = space;
        space.setPriorityAntenna(true);
        notifyChange();
    }

    public Player[] getPlayerTurns(Board board) {
        Map players = new HashMap();
        Player player;
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            player = board.getPlayer(i);
            setPlayerPriorityDistance(player);
            players.put(player, player.getDistance());
        }
        Player[] playerTurns = sortByValue(players);

        return playerTurns;
    }

    private void setPlayerPriorityDistance(Player player) {
        int x = player.getSpace().x;
        int y = player.getSpace().y;
        int distance = Math.abs(space.y - y) + Math.abs(space.x - x);
        player.setDistance(distance);
    }

    private Player[] sortByValue(Map<Player, Integer> map) {
        List<Map.Entry<Player, Integer>> list = new LinkedList<>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Player, Integer>>() {
            public int compare(Map.Entry<Player, Integer> o1, Map.Entry<Player, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Player, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        Player[] sortedplayers = sortedMap.keySet().toArray(new Player[sortedMap.size()]);
        return sortedplayers;
    }

    /**
     *
     * @param
     */
    public void printMap() {
        for (Map.Entry<Player, Integer> entry : sortedMap.entrySet()) {
            System.out.println(entry.getKey().getName() + "\t" + entry.getValue());
        }
        System.out.println("\n");
    }

    public Space getSpace(){
        return space;
    }

}
