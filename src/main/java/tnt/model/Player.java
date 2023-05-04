package tnt.model;
import java.util.ArrayList;
import tnt.model.enums.Gods;
import tnt.model.gods.movement.*;

/**
 * A player with his attributes.
 */
public class Player {
    private String levelOfIntelligence;
    private String name;
    private String colour;
    private ArrayList<Figure> figures;
    private ArrayList<Gods> gods;

    /**
     * Constructing an object Player.
     * @param levelOfIntelligence Human, easyAI, mediumAI, hardAI
     * @param name initial name
     * @param colour initial colour
     * @param figures ArrayList of figures, which belongs to the player
     * @param god the card, which affects the players abilities
     */
    public Player(String levelOfIntelligence, String name, String colour, ArrayList<Figure> figures, ArrayList<Gods> god) {
        this.levelOfIntelligence = levelOfIntelligence;
        this.name = name;
        this.colour = colour;
        this.figures = figures;
        this.gods = gods;
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
    public String getColour() {
        return colour;
    }

    /**
     * @param colour new colour of the player
     */
    public void setColour(String colour) {
        this.colour = colour;
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
    public void addFigure(int amount, Gods god) {
        for (int i = 0; i < amount; i++) {
            Figure newFigure;
            switch (god){
                case Apollo:
                    newFigure = new Apollo(i,i, god); // initiale Koordinaten i,i müssen noch geändert werden (csv laden beachten)
                case Artemis:
                    newFigure = new Artemis(i,i, god);
                case Charon:
                    newFigure = new Charon(i,i, god);
                case Hermes:
                    newFigure = new Hermes(i,i, god);
                case Minotaures:
                    newFigure = new Minotaures(i,i, god);
                case Triton:
                    newFigure = new Triton(i,i, god);
                default:
                    newFigure = new Figure(i, i, god);
            }
            this.figures.add(newFigure);
        }
    }

    /**
     * @return card god/demon card, which belongs to the player
     */
    public ArrayList<Gods> getGods() {
        return gods;
    }
}