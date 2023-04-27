package tnt.model;
import java.util.ArrayList;

/**
 * A player with his attributes.
 */
public class Player {
    private String levelOfIntelligence;
    private String name;
    private String colour;
    private ArrayList<Figure> figures;
    private Card card;

    /**
     * Constructing an object Player.
     * @param levelOfIntelligence Human, easyAI, mediumAI, hardAI
     * @param name initial name
     * @param colour initial colour
     * @param figures ArrayList of figures, which belongs to the player
     * @param card the card, which affects the players abilities
     */
    public Player(String levelOfIntelligence, String name, String colour, ArrayList<Figure> figures, Card card) {
        this.levelOfIntelligence = levelOfIntelligence;
        this.name = name;
        this.colour = colour;
        this.figures = figures;
        this.card = card;
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
     * @param amount creates new Figure objects for the player
     */
    public void addFigure(int amount) {
        for (int i = 0; i < amount; i++) {
            Figure newFigure = new Figure(i, i, card); // Hier müssen die initialen Koordinaten geändert werden (csv laden beachten)
            this.figures.add(newFigure);
        }
    }

    /**
     * @return card god/demon card, which belongs to the player
     */
    public Card getCard() {
        return card;
    }

    /**
     * @param card new god/demon card for the player
     */
    public void setCard(Card card) {
        this.card = card;
    }

    public void moveFigure(){

    }

    public void build(){
        // Wegen Zusatzaufgabe mit Stack arbeiten und Playerinventar um Gebäudeteile erweitern.
    }
}