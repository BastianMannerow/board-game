package tnt.model;

import java.util.ArrayList;
import java.util.Arrays;

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
    public void createPlayer(int playerAmount, ArrayList<String> names, ArrayList<String> colour, int figureAmount) {
        for (int i = 0; i < playerAmount; i++) {
            Player newPlayer = new Player(names.get(i), colour.get(i), new ArrayList<Figure>(), null);
            newPlayer.addFigure(figureAmount);
            ArrayList<Player> newPlayerOrder = getPlayerOrder();
            newPlayerOrder.add(newPlayer);
            setPlayerOrder(newPlayerOrder);
        }
    }

    /**
     * Creates Board and containing Field objects.
     *
     * @param boardSize the Size of the board (will be a square).
     */
    public void createBoard(int boardSize) {
        Field[][] fields = new Field[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Field field = new Field();
                fields[i][j] = field;
            }
        }
        Board board = new Board(fields);

        // Testprint, kann später entfernt werden.
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.println(board.getField(i, j));
            }
        }
    }

    public void loadGame() {

    }
}