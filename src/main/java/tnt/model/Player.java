package tnt.model;
import java.util.ArrayList;

import javafx.scene.paint.Color;

import tnt.model.interfaces.Gods;
import tnt.model.gods.building.*;
import tnt.model.gods.inventory.Chaos;
import tnt.model.gods.inventory.Circe;
import tnt.model.gods.movement.*;
import tnt.model.gods.sabotage.*;
import tnt.model.gods.victory.Chronus;
import tnt.model.gods.victory.Eros;
import tnt.model.gods.victory.Hera;
import tnt.model.gods.victory.Pan;
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
     * Adds a god into the ArrayList gods
     *
     * @param god is a instance of a class implementing interface Gods
     */
    public void addGod(Gods god){
        gods.add(god);
    }

    /**
     * Adds all gods into the ArrayList gods
     *
     */
    public void AddAllGods(){
        addGod(new Ares());
        addGod(new Atlas());
        addGod(new Demeter());
        addGod(new Dyonisus());
        addGod(new Hephaistos());
        addGod(new Hestia());
        addGod(new Medusa());
        addGod(new Morpheus());
        addGod(new Poseidon());
        addGod(new Prometheus());
        addGod(new Selene());
        addGod(new Zeus());

        addGod(new Chaos());
        addGod(new Circe());
        addGod(new Apollo());
        addGod(new Artemis());
        addGod(new Charon());
        addGod(new Hermes());
        addGod(new Minotaures());
        addGod(new Triton());

        addGod(new Aphrodite());
        addGod(new Athena());
        addGod(new Hypnus());
        addGod(new Limus());
        addGod(new Persephone());

        addGod(new Chronus());
        addGod(new Eros());
        addGod(new Hera());
        addGod(new Pan());
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