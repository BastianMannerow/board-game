package tnt.model;
import tnt.model.Figure;
import tnt.model.Card;
import java.util.ArrayList;

public class Player {
    private String name;
    private String colour;
    private ArrayList<Figure> figures;
    private Card card;

    public Player(String name, String colour, ArrayList<Figure> figures, Card card) {
        this.name = name;
        this.colour = colour;
        this.figures = figures;
        this.card = card;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public ArrayList<Figure> getFigure() {
        return figures;
    }
    public void addFigure(int amount) {
        for (int i = 0; i < amount; i++) {
            Figure newFigure = new Figure(i, i, card);
            this.figures.add(newFigure);
        }
    }
    public Card getCard() {
        return card;
    }
    public void setCard(Card card) {
        this.card = card;
    }
}