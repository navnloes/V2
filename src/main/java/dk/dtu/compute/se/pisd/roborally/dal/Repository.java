/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.dal;
import dk.dtu.compute.se.pisd.roborally.fileaccess.LoadBoard;
import dk.dtu.compute.se.pisd.roborally.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
class Repository implements IRepository {

    private static final String GAME_GAMEID = "gameID";

    private static final String GAME_NAME = "name";

    private static final String GAME_BOARDNAME = "boardName";

    private static final String GAME_CURRENTPLAYER = "currentPlayer";

    private static final String GAME_PHASE = "phase";

    private static final String GAME_STEP = "step";

    private static final String PLAYER_PLAYERID = "playerID";

    private static final String PLAYER_NAME = "name";

    private static final String PLAYER_COLOUR = "colour";

    private static final String PLAYER_GAMEID = "gameID";

    private static final String PLAYER_POSITION_X = "positionX";

    private static final String PLAYER_POSITION_Y = "positionY";

    private static final String PLAYER_HEADING = "heading";

    private static final String PLAYER_CHECKPOINTTOKENS = "checkpointTokens";

    private static final int FIELD_TYPE_REGISTER = 0;

    private static final int FIELD_TYPE_HAND = 1;

    private static final String FIELD_POS = "position";

    private static final String FIELD_VISIBLE = "visible";

    private static final String FIELD_COMMAND = "command";

    private static final String FIELD_PLAYERID = "playerID";

    private static final String FIELD_TYPE = "type";

    private static final int CARDSTACK_TYPE_DECK = 0;

    private static final int CARDSTACK_TYPE_DISCARD = 1;

    private static final String CARDSTACK_POS = "position";

    private static final String CARDSTACK_COMMAND = "command";

    private static final String CARDSTACK_PLAYERID = "playerID";

    private static final String CARDSTACK_TYPE = "type";




    private Connector connector;

    Repository(Connector connector) {
        this.connector = connector;
    }

    /**
     * This method creates/inserts the data of the game into the database
     * @param game Board game that needs to be saved
     * @return
     */
    @Override
    public boolean createGameInDB(Board game) {
        if (game.getGameId() == null) {
            Connection connection = connector.getConnection();
            try {
                connection.setAutoCommit(false);

                PreparedStatement ps = getInsertGameStatementRGK();

                ps.setString(1, "Date: " + new Date()); // instead of name
                ps.setString(2, game.boardName);
                ps.setNull(3, Types.TINYINT); // game.getPlayerNumber(game.getCurrentPlayer())); is inserted after players!
                ps.setInt(4, game.getPhase().ordinal());
                ps.setInt(5, game.getStep());

                int affectedRows = ps.executeUpdate();
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (affectedRows == 1 && generatedKeys.next()) {
                    game.setGameId(generatedKeys.getInt(1));
                }
                generatedKeys.close();

                createPlayersInDB(game);
                createCardStackinDB(game);
                createCardFieldsinDB(game);


                ps = getSelectGameStatementU();
                ps.setInt(1, game.getGameId());

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    rs.updateInt(GAME_CURRENTPLAYER, game.getPlayerNumber(game.getCurrentPlayer()));
                    rs.updateRow();
                } else {
                }
                rs.close();

                connection.commit();
                connection.setAutoCommit(true);



                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Some DB error");

                try {
                    connection.rollback();
                    connection.setAutoCommit(true);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } else {
            System.err.println("Game cannot be created in DB, since it has a game id already!");
        }

        return false;
    }

    @Override
    public boolean updateGameInDB(Board game) {
        assert game.getGameId() != null;

        Connection connection = connector.getConnection();
        try {
            connection.setAutoCommit(false);

            PreparedStatement ps = getSelectGameStatementU();
            ps.setInt(1, game.getGameId());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rs.updateInt(GAME_CURRENTPLAYER, game.getPlayerNumber(game.getCurrentPlayer()));
                rs.updateInt(GAME_PHASE, game.getPhase().ordinal());
                rs.updateInt(GAME_STEP, game.getStep());
                rs.updateRow();
            } else {
            }
            rs.close();

            updatePlayersInDB(game);
            updateCardStacksinDB(game);
            updateCardFieldsinDB(game);

            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Some DB error");

            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public Board loadGameFromDB(int id) {
        try {
            PreparedStatement ps = getSelectGameStatementU();
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Board game;
            int playerNo;
            if (rs.next()) {
                String boardName = rs.getString(GAME_BOARDNAME);
                // TODO currently we do not set the games name (needs to be added)
                 game = LoadBoard.loadBoard(boardName);
                // TODO and we should also store the used game board in the database
                //      for now, we use the default game board

                playerNo = rs.getInt(GAME_CURRENTPLAYER);
                game.setPhase(Phase.values()[rs.getInt(GAME_PHASE)]);
                game.setStep(rs.getInt(GAME_STEP));
            } else {
                // TODO error handling
                return null;
            }
            rs.close();

            game.setGameId(id);
            loadPlayersFromDB(game);
            loadCardStackFromDB(game);
            loadCardFieldsFromDB(game);
            if (playerNo >= 0 && playerNo < game.getPlayersNumber()) {
                game.setCurrentPlayer(game.getPlayer(playerNo));
            } else {
                return null;
            }
            return game;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Some DB error");
        }
        return null;
    }

    @Override
    public List<GameInDB> getGames() {
        List<GameInDB> result = new ArrayList<>();
        try {
            PreparedStatement ps = getSelectGameIdsStatement();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(GAME_GAMEID);
                String name = rs.getString(GAME_NAME);
                result.add(new GameInDB(id, name));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void createPlayersInDB(Board game) throws SQLException {
        PreparedStatement ps = getSelectPlayersStatementU();
        ps.setInt(1, game.getGameId());

        ResultSet rs = ps.executeQuery();
        for (int i = 0; i < game.getPlayersNumber(); i++) {
            Player player = game.getPlayer(i);
            player.setPlayerId(i);
            rs.moveToInsertRow();
            rs.updateInt(PLAYER_GAMEID, game.getGameId());
            rs.updateInt(PLAYER_PLAYERID, i);
            rs.updateString(PLAYER_NAME, player.getName());
            rs.updateString(PLAYER_COLOUR, player.getColor());
            rs.updateInt(PLAYER_POSITION_X, player.getSpace().x);
            rs.updateInt(PLAYER_POSITION_Y, player.getSpace().y);
            rs.updateInt(PLAYER_HEADING, player.getHeading().ordinal());
            rs.updateInt(PLAYER_CHECKPOINTTOKENS,player.getCheckPointToken());
            rs.insertRow();
        }
        rs.close();
    }

    private void loadPlayersFromDB(Board game) throws SQLException {
        PreparedStatement ps = getSelectPlayersASCStatement();
        ps.setInt(1, game.getGameId());

        ResultSet rs = ps.executeQuery();
        int i = 0;
        while (rs.next()) {
            int playerId = rs.getInt(PLAYER_PLAYERID);
            if (i++ == playerId) {
                String name = rs.getString(PLAYER_NAME);
                String colour = rs.getString(PLAYER_COLOUR);
                Player player = new Player(game, colour, name);
                player.setPlayerId(playerId);
                game.addPlayer(player);

                int x = rs.getInt(PLAYER_POSITION_X);
                int y = rs.getInt(PLAYER_POSITION_Y);
                player.setSpace(game.getSpace(x, y));
                int heading = rs.getInt(PLAYER_HEADING);
                player.setHeading(Heading.values()[heading]);
                int checkPointTokens = rs.getInt(PLAYER_CHECKPOINTTOKENS);
                player.setCheckPointToken(checkPointTokens);
                for (int j = 0; j < checkPointTokens; j++){
                    player.getCheckPointArray()[j] = true;
                }
            } else {
                System.err.println("Game in DB does not have a player with id " + i + "!");
            }
        }
        rs.close();
    }

    private void updatePlayersInDB(Board game) throws SQLException {
        PreparedStatement ps = getSelectPlayersStatementU();
        ps.setInt(1, game.getGameId());

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int playerId = rs.getInt(PLAYER_PLAYERID);
            Player player = game.getPlayer(playerId);
            rs.updateString(PLAYER_NAME, player.getName());
            rs.updateInt(PLAYER_POSITION_X, player.getSpace().x);
            rs.updateInt(PLAYER_POSITION_Y, player.getSpace().y);
            rs.updateInt(PLAYER_HEADING, player.getHeading().ordinal());
            rs.updateInt(PLAYER_CHECKPOINTTOKENS,player.getCheckPointToken());
            rs.updateRow();
        }
        rs.close();
    }


    private static final String SQL_INSERT_GAME =
            "INSERT INTO Game(name, boardName, currentPlayer, phase, step) VALUES (?, ?, ?, ?, ?)";
    private PreparedStatement insert_game_stmt = null;

    private PreparedStatement getInsertGameStatementRGK() {
        if (insert_game_stmt == null) {
            Connection connection = connector.getConnection();
            try {
                insert_game_stmt = connection.prepareStatement(
                        SQL_INSERT_GAME,
                        Statement.RETURN_GENERATED_KEYS);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return insert_game_stmt;
    }

    private static final String SQL_SELECT_GAME =
            "SELECT * FROM Game WHERE gameID = ?";

    private PreparedStatement select_game_stmt = null;

    private PreparedStatement getSelectGameStatementU() {
        if (select_game_stmt == null) {
            Connection connection = connector.getConnection();
            try {
                select_game_stmt = connection.prepareStatement(
                        SQL_SELECT_GAME,
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return select_game_stmt;
    }

    private static final String SQL_SELECT_PLAYERS =
            "SELECT * FROM Player WHERE gameID = ?";


    private PreparedStatement select_players_stmt = null;

    private PreparedStatement getSelectPlayersStatementU() {
        if (select_players_stmt == null) {
            Connection connection = connector.getConnection();
            try {
                select_players_stmt = connection.prepareStatement(
                        SQL_SELECT_PLAYERS,
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return select_players_stmt;
    }

    private static final String SQL_SELECT_PLAYERS_ASC =
            "SELECT * FROM Player WHERE gameID = ? ORDER BY playerID ASC";

    private PreparedStatement select_players_asc_stmt = null;

    private PreparedStatement getSelectPlayersASCStatement() {
        if (select_players_asc_stmt == null) {
            Connection connection = connector.getConnection();
            try {
                select_players_asc_stmt = connection.prepareStatement(
                        SQL_SELECT_PLAYERS_ASC);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return select_players_asc_stmt;
    }

    private static final String SQL_SELECT_GAMES =
            "SELECT gameID, name FROM Game";

    private PreparedStatement select_games_stmt = null;

    private PreparedStatement getSelectGameIdsStatement() {
        if (select_games_stmt == null) {
            Connection connection = connector.getConnection();
            try {
                select_games_stmt = connection.prepareStatement(
                        SQL_SELECT_GAMES);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return select_games_stmt;
    }

    public ArrayList<Integer> getGameIds(){
        ArrayList<Integer> gameIds = new ArrayList<>();
        Connection connection = connector.getConnection();
        String query = "select * from game order by gameId DESC";
        int id = 0;
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                id = rs.getInt("gameId");
                gameIds.add(id);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gameIds;
    }

    /**
     * This method gets the saved players from the database and adds them to ArrayList<Player> players
     * @param board Board board
     * @param gameId int gameId of the chosen game
     * @return ArrayList<Player> players
     */
    public ArrayList<Player> getPlayerList(Board board, int gameId){
        ArrayList<Player> players = new ArrayList<>();

        Connection connection = connector.getConnection();
        String query = "select * from player where gameId =" + gameId;
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Player player = new Player(board,rs.getString(4),rs.getString(3));

                player.setPlayerId(rs.getInt(2));
                player.setHeading(Heading.getHeading(rs.getInt(7)));
                player.setSpace(new Space (board, rs.getInt(5),rs.getInt(6)));
                players.add(player);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return players;
    }

    private static final String SQL_SELECT_CARD_FIELDS = "SELECT* FROM CardField WHERE gameID = ?";
    private PreparedStatement select_card_field_stmt = null;

    private PreparedStatement getSelectCardFieldStatement(){
        if (select_card_field_stmt == null){
            Connection connection = connector.getConnection();
            try {
                select_card_field_stmt = connection.prepareStatement(
                        SQL_SELECT_CARD_FIELDS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return select_card_field_stmt;
    }

    private void loadCardFieldsFromDB(Board game) throws SQLException {
        PreparedStatement ps = getSelectCardFieldStatement();
        ps.setInt(1,game.getGameId());

        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            int playerId = rs.getInt(FIELD_PLAYERID);
            Player player = game.getPlayer(playerId);
            int type = rs.getInt(FIELD_TYPE);
            int pos = rs.getInt(FIELD_POS);
            CommandCardField field;
            if (type == FIELD_TYPE_REGISTER){
                field = player.getProgramField(pos);
            } else if (type == FIELD_TYPE_HAND) {
                field = player.getCardField(pos);
            } else {
                field = null;
            }
            if (field != null) {
                field.setVisible(rs.getBoolean(FIELD_VISIBLE));
                Object c = rs.getObject(FIELD_COMMAND);
                if (c != null) {
                    Command card = Command.values()[rs.getInt(FIELD_COMMAND)];
                    field.setCard(new CommandCard(card));
                }
            }
        }
    }

    /**
     * This method creates the cardFields in the database
     * @param game
     * @throws SQLException
     */
    private void createCardFieldsinDB (Board game) throws SQLException {
        PreparedStatement ps = getSelectCardFieldStatement();
        ps.setInt(1, game.getGameId());
        ResultSet rs = ps.executeQuery();
        for (int i = 0; i < game.getPlayersNumber(); i++) {
            Player player = game.getPlayer(i);
            CommandCardField[] cards = player.getCards();
            CommandCardField[] program = player.getProgram();
            for (int j = 0; j < cards.length; j++) {
                rs.moveToInsertRow();
                rs.updateInt("gameID", game.getGameId());
                rs.updateInt(FIELD_PLAYERID, player.getPlayerId());
                rs.updateInt(FIELD_TYPE, FIELD_TYPE_HAND);
                rs.updateBoolean(FIELD_VISIBLE, cards[j].isVisible());
                rs.updateInt(FIELD_POS, j);
                if(cards[j].getCard() != null)
                    rs.updateObject(FIELD_COMMAND, cards[j].getCard().getCommand().ordinal());
                else
                    rs.updateObject(FIELD_COMMAND, null);
                rs.insertRow();
            }
            for (int j = 0; j < program.length; j++) {
                rs.moveToInsertRow();
                rs.updateInt("gameID", game.getGameId());
                rs.updateInt(FIELD_PLAYERID, player.getPlayerId());
                rs.updateInt(FIELD_TYPE, FIELD_TYPE_REGISTER);
                rs.updateInt(FIELD_POS, j);
                rs.updateBoolean(FIELD_VISIBLE, program[j].isVisible());
                if(program[j].getCard() != null)
                    rs.updateObject(FIELD_COMMAND, program[j].getCard().getCommand().ordinal());
                else
                    rs.updateObject(FIELD_COMMAND, null);
                rs.insertRow();
            }
        }
        rs.close();
    }

    /**
     * This method updates the cardFields in the databases
     * @param game which cards need to be saved
     * @author s205353
     */
    private void updateCardFieldsinDB(Board game){
        Connection connection = connector.getConnection();
        try {
            connection.setAutoCommit(false);

            PreparedStatement ps = getSelectCardFieldStatement();
            ps.setInt(1, game.getGameId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int playerId = rs.getInt(FIELD_PLAYERID);
                Player player = game.getPlayer(playerId);
                int pos = rs.getInt(FIELD_POS);
                int type = rs.getInt(FIELD_TYPE);
                boolean visible = false;
                if(type == FIELD_TYPE_REGISTER) {
                    visible = player.getProgram()[pos].isVisible();
                    rs.updateBoolean(FIELD_VISIBLE, visible);
                    if(player.getProgram()[pos].getCard() != null)
                        rs.updateObject(FIELD_COMMAND, player.getProgram()[pos].getCard().getCommand().ordinal());
                    else
                        rs.updateObject(FIELD_COMMAND, null);
                } else if (type == FIELD_TYPE_HAND){
                    visible = player.getCards()[pos].isVisible();
                    rs.updateBoolean(FIELD_VISIBLE, visible);
                    if(player.getCards()[pos].getCard() != null)
                        rs.updateObject(FIELD_COMMAND, player.getCards()[pos].getCard().getCommand().ordinal());
                    else
                        rs.updateObject(FIELD_COMMAND, null);

                }
                rs.updateRow();

            }
            rs.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static final String SQL_SELECT_CARDSTACK = "SELECT* FROM CardStack WHERE gameID = ?";
    private PreparedStatement select_cardstack_stmt = null;

    private PreparedStatement getSelectCardStackStatement(){
        if (select_cardstack_stmt == null){
            Connection connection = connector.getConnection();
            try {
                select_cardstack_stmt = connection.prepareStatement(
                        SQL_SELECT_CARDSTACK, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return select_cardstack_stmt;
    }

    /**
     * This method inserts each player's deck cards and discard cards into the table
     * @param game
     * @throws SQLException
     */
    private void createCardStackinDB(Board game) throws SQLException {
        PreparedStatement ps = getSelectCardStackStatement();
        ps.setInt(1, game.getGameId());
        ResultSet rs = ps.executeQuery();

        for (int i = 0; i < game.getPlayersNumber(); i++) {
            Player player = game.getPlayer(i);
            Stack<CommandCard> deck = player.getCardDeck();
            Stack<CommandCard> discards = player.getDiscardpile();
            int deckpos = 0;
            for (CommandCard c : deck){
                rs.moveToInsertRow();
                rs.updateInt("gameID", game.getGameId());
                rs.updateInt(CARDSTACK_PLAYERID, player.getPlayerId());
                rs.updateInt(CARDSTACK_TYPE, CARDSTACK_TYPE_DECK);
                rs.updateInt(CARDSTACK_POS, deckpos);
                rs.updateObject(CARDSTACK_COMMAND, c.getCommand().ordinal());
                rs.insertRow();
                deckpos++;
            }

            deckpos = 0;
            for (CommandCard c : discards){
                rs.moveToInsertRow();
                rs.updateInt("gameID", game.getGameId());
                rs.updateInt(CARDSTACK_PLAYERID, player.getPlayerId());
                rs.updateInt(CARDSTACK_TYPE, CARDSTACK_TYPE_DISCARD);
                rs.updateInt(CARDSTACK_POS, deckpos);
                rs.updateObject(CARDSTACK_COMMAND, c.getCommand().ordinal());
                rs.insertRow();
                deckpos++;
            }
        }
        rs.close();
    }


    /**
     * This method updates the cardStacks in the databases by deleting the current saved cards and inserting new ones
     * this is due to the fact that the size of deck and discardpile is variable
     * @param game which cards need to be saved
     * @author s205353
     */
    private void updateCardStacksinDB(Board game){
        Connection connection = connector.getConnection();
        try {
            connection.setAutoCommit(false);

            PreparedStatement ps = getSelectCardStackStatement();
            ps.setInt(1, game.getGameId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                rs.deleteRow();
            }
            rs.close();
            createCardStackinDB(game);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void loadCardStackFromDB(Board game) throws SQLException {

        for (Player player : game.getPlayers()){
            player.getCardDeck().clear();
            player.getDiscardpile().clear();
        }
        PreparedStatement ps = getSelectCardStackStatement();
        ps.setInt(1,game.getGameId());

        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            int playerId = rs.getInt(CARDSTACK_PLAYERID);
            Player player = game.getPlayer(playerId);
            int type = rs.getInt(CARDSTACK_TYPE);
            Object c = rs.getObject(CARDSTACK_COMMAND);

            if (c != null) {
                Command command = Command.getCommand(rs.getInt(CARDSTACK_COMMAND));
                if (type == CARDSTACK_TYPE_DECK) {
                    player.getCardDeck().push(new CommandCard(command));
                } else if (type == CARDSTACK_TYPE_DISCARD) {
                    player.getDiscardpile().push(new CommandCard(command));
                }
            }
        }
        rs.close();
    }
}