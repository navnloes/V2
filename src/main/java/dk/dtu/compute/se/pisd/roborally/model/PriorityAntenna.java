package dk.dtu.compute.se.pisd.roborally.model;

import java.util.*;

/**
 * @author s205352, s205353, s205354
 */

//TODO: MVC??

public class PriorityAntenna {

    private Space space;

    public PriorityAntenna() {
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public Player[] getPlayerTurns(Board board) {
        Map players = new HashMap();
        Player player;
        for (int i = 0; i < board.getPlayers().size(); i++) {
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

        Map<Player, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Player, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        printMap(sortedMap);
        Player[] sortedplayers = sortedMap.keySet().toArray(new Player[sortedMap.size()]);
        return sortedplayers;
    }

    //TODO: fjerne når alt er fikset
    public void printMap(Map<Player, Integer> map) {
        for (Map.Entry<Player, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
        System.out.println("\n");
    }

    public Space getSpace(){
        return space;
    }
}
