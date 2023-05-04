package tnt.model;
import java.util.ArrayList;
import tnt.model.enums.Gods;

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
     * @param gods the cards, which affect the players abilities
     */
    public Player(String levelOfIntelligence, String name, String colour, ArrayList<Figure> figures, ArrayList<Gods> gods) {
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