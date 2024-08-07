package tnt.model;
import tnt.util.Observable;

/**
 * The Field class contains the status of a single field inside a Board object.
 */
public class Field extends Observable {
    private int fieldX;
    private int fieldY;
    private int towerLevel;
    private boolean towerComplete;
    private Figure figure;

    /**
     * Constructing an empty (not null) Field object
     *
     * @param x coordinate of the field
     * @param y coordinate of the field
     */
    public Field(int x, int y) {
        this.fieldX = x;
        this.fieldY = y;
        this.towerLevel = 0;
        this.towerComplete = false;
        this.figure = null;
    }

    /**
     * Constructor for a field
     */
    public Field() {
    }

    /**
     *Removes the figure of the Field
     */
    public void figureLeft() {
        this.figure = null;
        notifyObservers();
    }

    /**
     * @return the status if the field is occupied by a figure
     */
    public boolean getIsFigureHere() {
        return this.figure != null;
    }

    /**
     * @param level sets the new tower level of the field
     */
    public void setTowerLevel(int level) {
        this.towerLevel = level;
        notifyObservers();
    }

    /**
     * @return the tower level of the field
     */
    public int getTowerLevel() {
        return this.towerLevel;
    }

    /**
     * @param towerComplete sets if the tower is completed
     */
    public void setTowerComplete(boolean towerComplete) {
        this.towerComplete = towerComplete;
        notifyObservers();
    }

    /**
     * @return boolean if the tower is completed
     */
    public boolean getTowerComplete() {
        return this.towerComplete;
    }

    /**
     * @return x coordinate of the field
     */
    public int getX() {
        return fieldX;
    }

    /**
     * @return y coordinate of the field
     */
    public int getY() {
        return fieldY;
    }

    /**
     * @param figure, which is placed on this Field
     */
    public void setFigure(Figure figure) {
        this.figure = figure;
        notifyObservers();
    }

    /**
     * @return the figure, which is placed on this Field
     */
    public Figure getFigure() {
        return figure;
    }
}