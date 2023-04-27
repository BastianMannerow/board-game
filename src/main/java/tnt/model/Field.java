package tnt.model;

public class Field {
    private boolean isFigureHere;
    private int towerLevel;
    private boolean towerComplete;

    public Field() {
        this.isFigureHere = false;
        this.towerLevel = 0;
        this.towerComplete = false;
    }

    public void setIsFigureHere(boolean isFigureHere) {
        this.isFigureHere = isFigureHere;
    }

    public boolean getIsFigureHere() {
        return this.isFigureHere;
    }

    public void setTowerLevel(int level) {
        this.towerLevel = level;
    }

    public int getTowerLevel() {
        return this.towerLevel;
    }

    public void setTowerComplete(boolean towerComplete) {
        this.towerComplete = towerComplete;
    }

    public boolean getTowerComplete() {
        return this.towerComplete;
    }
}