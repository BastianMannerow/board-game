package tnt.model;

/**
 * The Field class contains the status of a single field inside a Board object.
 */
public class Field {
    private boolean isFigureHere;
    private int towerLevel;
    private boolean towerComplete;

    /**
     * Constructing an empty (not null) Field object
     */
    public Field() {
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
}