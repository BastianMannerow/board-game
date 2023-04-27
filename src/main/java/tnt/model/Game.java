package tnt.model;
import java.util.ArrayList;
import java.util.Collections;
import tnt.model.enums.Gods;

/**
 * The Game class, which is responsible for general mechanics during the Game.
 */
public class Game {
    private ArrayList<Player> playerOrder;

    /**
     * Constructing an object Game.
     * @param playerOrder
     */
    public Game(ArrayList<Player> playerOrder) {
        this.playerOrder = playerOrder;
    }

    /**
     * @return playerOrder
     */
    public ArrayList<Player> getPlayerOrder() {
        return playerOrder;
    }

    /**
     * @param playerOrder replaces old playerOrder
     */

    public void setPlayerOrder(ArrayList<Player> playerOrder) {
        this.playerOrder = playerOrder;
    }

    /**
     * Creating new player objects and adding them to playerOrder
     *
     * @param playerAmount the amount of new players
     * @param names the names of the new players
     * @param colour the colours of the new players
     * @param figureAmount the amount of figures on the players disposal
     */
    public void createPlayer(ArrayList<String> levelOfIntelligence, int playerAmount, ArrayList<String> names, ArrayList<String> colour, int figureAmount, ArrayList<Gods> gods) {
        for (int i = 0; i < playerAmount; i++) {
            Player newPlayer = new Player(levelOfIntelligence.get(i), names.get(i), colour.get(i), new ArrayList<Figure>(), null);
            newPlayer.addFigure(figureAmount, gods.get(i));
            ArrayList<Player> newPlayerOrder = getPlayerOrder();
            newPlayerOrder.add(newPlayer);
            setPlayerOrder(newPlayerOrder);
        }
    }

    /**
     * Creates Board and containing Field objects.
     *
     * @param boardX the Width of the board
     * @param boardY the Height of the board
     */
    public void createBoard(int boardX, int boardY) {
        Field[][] fields = new Field[boardX][boardY];
        for (int i = 0; i < boardX; i++) {
            for (int j = 0; j < boardY; j++) {
                Field field = new Field(i, j);
                fields[i][j] = field;
            }
        }
        Board board = new Board(fields, boardX, boardY);

        // Testprint, kann später entfernt werden.
        for (int i = 0; i < boardX; i++) {
            for (int j = 0; j < boardY; j++) {
                System.out.println(board.getField(i, j));
            }
        }
    }

    /**
     * Runs the Game
     */
    public void runGame() {
        boolean gameEnded = false;
        while(gameEnded == false){
            Player activePlayer = playerOrder.get(0);

            // Spielerwechsel
            gameEnded = checkEnd(gameEnded);
            Collections.rotate(playerOrder, -1);
        }
    }

    /**
     * Checks if the Game is over
     */
    public boolean checkEnd(boolean gameEnded){
        // Differenzierung 2 oder 4 Spieler einbauen.
        return gameEnded;
    }
}