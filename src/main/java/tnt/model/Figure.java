package tnt.model;
import tnt.util.Observable;

import java.util.ArrayList;

/**
 * A figure owned by the Player.
 */
public class Figure extends Observable {
    private int x;
    private int y;
    private boolean placed;

    /**
     * Constructing an object Figure.
     * @param x initial x coordinate
     * @param y initial y coordinate
     */
    public Figure(int x, int y) {
        this.x = x;
        this.y = y;
        this.placed = true;

    }

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
                if(true){
                    reachableFields.add(board.getField((i+boardX)%boardX, (j+boardY)%boardY));
                }
                else if(i>= 0 && i < boardX && j>= 0 && j < boardY) {
                    reachableFields.add(board.getField(i, j));
                }
            }
        }

        // Filter the reachable fields, so that only the legal fields remain
        int ownTowerLevel = board.getField(x,y).getTowerLevel();
        ArrayList<Field> possibleFields = new ArrayList<Field>();

        for (Field field : reachableFields) {
            if(!field.getIsFigureHere() && !field.getTowerComplete() && field.getTowerLevel() <= ownTowerLevel+1){
                possibleFields.add(field);
            }
        }

        return possibleFields;
    }

    /**
     * Filters the reachable build positions by legal build options
     *
     * @return valid build positions
     */
    public ArrayList<Field> getValidBuilds(){
        // Daran denken, dass man nicht auf Feldern bauen darf, wo Figuren sind & nur Felder um Figur rum sind buildable.
        ArrayList<Field> validBuilds = new ArrayList<Field>();
        return validBuilds;
    }

    public boolean isPlaced(){
        return placed;
    }

    public void setPlaced() {
        placed = true;
    }
}