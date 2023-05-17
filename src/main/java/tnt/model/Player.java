package tnt.model;
import java.util.ArrayList;

import javafx.scene.paint.Color;
import tnt.model.enums.Gods;

/**
 * A player with his attributes.
 */
public class Player {
    private String levelOfIntelligence;
    private String name;
    private Color color;
    private ArrayList<Figure> figures;
    private ArrayList<Gods> gods;

    /**
     * Constructing an object Player.
     * @param levelOfIntelligence Human, easyAI, mediumAI, hardAI
     * @param name initial name
     * @param color initial colour
     * @param figures ArrayList of figures, which belongs to the player
     * @param gods the cards, which affect the players abilities
     */
    public Player(String levelOfIntelligence, String name, Color color, ArrayList<Figure> figures, ArrayList<Gods> gods) {
        this.levelOfIntelligence = levelOfIntelligence;
        this.name = name;
        this.color = color;
        this.figures = figures;
        this.gods = gods;
    }

    public Player(String levelOfIntelligence, String name, Color color) {
        this.levelOfIntelligence = levelOfIntelligence;
        this.name = name;
        this.color = color;
    }

    /**
     * @return name of the player
     */
    public String getLevelOfIntelligence() {
        return levelOfIntelligence;
    }

    /**
     * @return name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * @param name new name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return colour of the player
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color new colour of the player
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return ArrayList of Figure objects, which belongs to the player
     */
    public ArrayList<Figure> getFigure() {
        return figures;
    }

    /**
     * Depending on the god type, the specific figure object will be created.
     *
     * @param amount creates new Figure objects for the player
     */
    public void addFigure(int amount) {
        for (int i = 0; i < amount; i++) {
            Figure newFigure = new Figure(i, i);
            this.figures.add(newFigure);
        }
    }

    /**
     * @return card god/demon card, which belongs to the player
     */
    public ArrayList<Gods> getGods() {
        return gods;
    }

    /**
     * Increases the height of a field
     *
     * @param field the field chosen by the player
     * @param board the board which is played on
     */
    public void executeBuild(Field field, Board board){
        int newLevel = field.getTowerLevel()+1;
        field.setTowerLevel(newLevel);
        if(newLevel == 4){
            field.setTowerComplete(true);
        }
    }

    /**
     * Moves the figure on the board
     *
     * @param field the field chosen by the player
     * @param board the board which is played on
     */
    public void executeMove(Field field, Board board, Figure figure){
        board.getField(figure.getX(), figure.getY()).setIsFigureHere(false);
        field.setIsFigureHere(true);
        figure.setX(field.getX());
        figure.setY(field.getY());
    }
}