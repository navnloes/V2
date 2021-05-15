package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

import java.util.*;

/**
 * This is the PriorityAntenna class which calculates the activation order of the robots
 * @author s205353, s205354,  s205352
 */


public class PriorityAntenna extends Subject {

    private Space space;
    private Player[] playerTurns;
    private Map<Player, Integer> sortedMap;

    /**
     * This is the constructor
     */
    public PriorityAntenna() {
    }

    /**
     * This method sets the priorityAntenna on a given space
     * @param space
     */
    public void setSpace(Space space) {
        this.space = space;
        space.setPriorityAntenna(true);
        notifyChange();
    }

    /**
     * This method sorts the players on the board into Player[] playerTurns
     * @param board Board board parameter that is used to collect all players on the board in order to sort them
     */
    public void sortPlayerArray(Board board) {
        Map players = new HashMap();
        Player player;
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            player = board.getPlayer(i);
            setPlayerPriorityDistance(player);
            players.put(player, player.getDistance());
        }
        playerTurns = sortByValue(players);
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
    }

    /**
     * This method takes a parameter map and sorts it
     * The map is sorted in ascending order by values
     * if two players have the same values (distance to antenna) the player with the smallest space.x value is first
     * if both of these players have the same space.x-value, the player with the smaller space.y-value is first
     * @param map Map<Player, Integer> map - this map contains the players on the board, and their Integer distance to the antenna
     * @return Player[] sortedPlayers
     */
    private Player[] sortByValue(Map<Player, Integer> map) {
        List<Map.Entry<Player, Integer>> list = new LinkedList<>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Player, Integer>>() {
            public int compare(Map.Entry<Player, Integer> o1, Map.Entry<Player, Integer> o2) {
                int result =  o1.getValue().compareTo(o2.getValue());
                if (result == 0){
                    if (o1.getKey().getSpace().x == o2.getKey().getSpace().x){
                        result = o1.getKey().getSpace().y - o2.getKey().getSpace().y;
                    } else {
                        result = o1.getKey().getSpace().x - o2.getKey().getSpace().x;
                    }
                }
                return result;
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

    /**
     * This getter returns the playerTurns array where the players are sorted into order
     * @return Player[] playerTurns
     */
    public Player[] getPlayerTurns(){
        if (playerTurns == null){
            sortPlayerArray(space.board);
        }
        return playerTurns;
    }
}
