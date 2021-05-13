package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

import java.util.*;

/**
 * @author s205353, s205354,  s205352
 */

public class PriorityAntenna extends Subject {

    private Space space;
    private Map<Player, Integer> sortedMap;

    /**
     * This is the contructor of the PriorityAntenna
     */
    public PriorityAntenna() {
    }

    /**
     * This setter assigns a space to the priorityAntenna
     * @param space Space space
     */
    public void setSpace(Space space) {
        this.space = space;
        space.setPriorityAntenna(true);
        notifyChange();
    }

    /**
     * This method returns a sorted array of players
     * @param board Board board parameter that is used to collect all players on the board in order to sort them
     * @return Player[] playerTurns - sorted playerArray which is sorted by distance to priorityAntenna
     */
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

    /**
     * This setter sets a given player's distance to the priorityAntenna
     * @param player Player player
     */
    private void setPlayerPriorityDistance(Player player) {
        int x = player.getSpace().x;
        int y = player.getSpace().y;
        int distance = Math.abs(space.y - y) + Math.abs(space.x - x);
        player.setDistance(distance);
        notifyChange();
    }

    /**
     * This method takes a parameter map and sorts it
     * The map is sorted in ascending order
     * @param map Map<Player, Integer> map - this map contains the players on the board, and their Integer distance to the antenna
     * @return Player[] sortedPlayers
     */
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
     * This method prints the sorted hashmap
     * used for testing : PriorityAntennaTest
     */
    public void printMap() {
        for (Map.Entry<Player, Integer> entry : sortedMap.entrySet()) {
            System.out.println(entry.getKey().getName() + "\t" + entry.getValue());
        }
        System.out.println("\n");
    }

    /**
     * This getter returns the space that the antenna has
     * @return Space space
     */
    public Space getSpace(){
        return space;
    }

}
