package tnt.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    private ArrayList<Player> playerOrder;

    public Game(ArrayList<Player> playerOrder) {
        this.playerOrder = playerOrder;
    }

    public ArrayList<Player> getPlayerOrder() {
        return playerOrder;
    }

    public void setPlayerOrder(ArrayList<Player> playerOrder) {
        this.playerOrder = playerOrder;
    }

    public void createPlayer(int playerAmount, ArrayList<String> names, ArrayList<String> colour, int figureAmount) {
        for (int i = 0; i < playerAmount; i++) {
            Player newPlayer = new Player(names.get(i), colour.get(i), new ArrayList<Figure>(), null);
            newPlayer.addFigure(figureAmount);
            ArrayList<Player> newPlayerOrder = getPlayerOrder();
            newPlayerOrder.add(newPlayer);
            setPlayerOrder(newPlayerOrder);
        }
    }

    public void createBoard(int boardSize) {
        Field[][] fields = new Field[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Field field = new Field();
                fields[i][j] = field;
            }
        }
        Board board = new Board(fields);
        // Theoretisch kann man board jetzt returnen.
        // Testprint
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.println(board.getField(i, j));
            }
        }
    }

    public void loadGame() {

    }
}