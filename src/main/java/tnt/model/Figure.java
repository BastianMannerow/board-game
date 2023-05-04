package tnt.model;
import java.util.ArrayList;
import tnt.model.enums.Gods;

/**
 * A figure owned by the Player.
 */
public class Figure {
    private int x;
    private int y;
    private Gods god;

    /**
     * Constructing an object Figure.
     * @param x initial x coordinate
     * @param y initial y coordinate
     */
    public Figure(int x, int y, Gods god) {
        this.x = x;
        this.y = y;
        this.god = god;
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
     * @return name of the god
     */
    public Gods getGodName() {
        return god;
    }

    /**
     * Calculating the reachable fields.
     *
     * @param board the board the possible actions should be calculated on
     * @return reachable fields
     */
    public ArrayList<Field> movementOptions(Board board){
        ArrayList<Field> reachableFields = new ArrayList<Field>();
        int boardX = board.getXSize();
        int boardY = board.getYSize();

        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {

                // "Wahlpflichtfeature - Die Welt ist eine Kugel"
                if(x<0 && y < 0){
                    reachableFields.add(board.getField(boardX-x, boardY-y));
                }
                else if(x < 0 && y >=0){
                    reachableFields.add(board.getField(boardX-x, y));
                }
                else if(y <0 && x >=0){
                    reachableFields.add(board.getField(x, boardY-y));
                }
                else {
                    reachableFields.add(board.getField(x, y));
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
}