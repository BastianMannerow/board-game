package tnt.model;

import tnt.util.Observable;

/**
 * The Field class contains the status of a single field inside a Board object.
 */
public class Field extends Observable {
    private int fieldX;
    private int fieldY;
    private boolean isFigureHere;
    private int towerLevel;
    private boolean towerComplete;

    /**
     * Constructing an empty (not null) Field object
     *
     * @param x coordinate of the field
     * @param y coordinate of the field
     */
    public Field(int x, int y) {
        this.fieldX = x;
        this.fieldY = y;
        this.isFigureHere = false;
        this.towerLevel = 0;
        this.towerComplete = false;
    }

    /**
     * @param isFigureHere sets the status if the field is occupied by a figure
     */
    public void setIsFigureHere(boolean isFigureHere) {
        this.isFigureHere = isFigureHere;
    }

    /**
     * @return the status if the field is occupied by a figure
     */
    public boolean getIsFigureHere() {
        return this.isFigureHere;
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
}