package tnt.model;
import tnt.util.Observable;

import java.util.ArrayList;

/**
 * A figure owned by the Player.
 */
public class Figure extends Observable {
    private int x = 0;
    private int y = 0;
    private Game game;
    private boolean placed;

    /**
     * Constructing an object Figure.
     * @param x initial x coordinate
     * @param y initial y coordinate
     */
    public Figure(int x, int y, Game game) {
        this.x = x;
        this.y = y;
        this.placed = true;
        this.game = game;
    }

    /**
     * Constructor for a figure
     */
    public Figure() {
        this.placed = false;
    }

    /**
     * @return x coordinate of the figure
     */
    public int getX() {
        return x;
    }

    /**
     * @param x new x coordinate of the figure
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return x coordinate of Figure
     */
    public int getY() {
        return y;
    };

    /**
     * @param y new y coordinate of the figure
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Calculating the reachable fields.
     *
     * @param board the board the possible actions should be calculated on
     * @return reachable fields
     */
    public ArrayList<Field> getValidMoves(Board board){
        ArrayList<Field> reachableFields = new ArrayList<Field>();
        int boardX = board.getXSize();
        int boardY = board.getYSize();

        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {

                // "Wahlpflichtfeature - Die Welt ist eine Kugel"
                if(board.getRoundWorld()){
                    reachableFields.add(board.getField((i+boardX)%boardX, (j+boardY)%boardY));
                }
                else if(i>= 0 && i < boardX && j>= 0 && j < boardY) {
                    reachableFields.add(board.getField(i, j));
                }
            }
        }

        // Filter the reachable fields, so that only the legal fields remain
        int ownTowerLevel = board.getField(x,y).getTowerLevel();
        reachableFields.removeIf(field -> field.getIsFigureHere() || field.getTowerComplete() || field.getTowerLevel() > ownTowerLevel+1);
        return reachableFields;
    }

    /**
     * Filters the reachable build positions by legal build options
     *
     * @return valid build positions
     */
    public ArrayList<Field> getValidBuilds(Board board){
        ArrayList<Field> validBuilds = new ArrayList<Field>();

        int boardX = board.getXSize();
        int boardY = board.getYSize();

        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {

                // "Wahlpflichtfeature - Die Welt ist eine Kugel"
                if(board.getRoundWorld()){
                    validBuilds.add(board.getField((i+boardX)%boardX, (j+boardY)%boardY));
                }
                else if(i>= 0 && i < boardX && j>= 0 && j < boardY) {
                    validBuilds.add(board.getField(i, j));
                }
            }
        }

        // Filter the fields, so that only the legal fields remain
        validBuilds.removeIf(field -> field.getIsFigureHere() || field.getTowerComplete());
        // Check if tiles are available
        /*
        for (Field field: validBuilds) {
            int tile = field.getTowerLevel();

            if (tile == 0 && game.getLevelOneTile() == 0) {
                validBuilds.remove(field);
            } else if (tile == 1 && game.getLevelTwoTile() == 0) {
                validBuilds.remove(field);
            } else if (tile == 2 && game.getLevelThreeTile() == 0) {
                validBuilds.remove(field);
            } else if (tile == 3 && game.getLevelFourTile() == 0){
                validBuilds.remove(field);
            }
        }

         */
        return validBuilds;
    }

    /**
     * checks if the figure is placed on the board
     */
    public boolean isPlaced(){
        return placed;
    }

    /**
     * set the figure to be placed on the board
     */
    public void setPlaced() {
        placed = true;
    }
}