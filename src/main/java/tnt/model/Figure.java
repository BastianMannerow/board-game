package tnt.model;
/**
 * A figure owned by the Player.
 */
public class Figure {
    private int x;
    private int y;
    private Card card;

    /**
     * Constructing an object Figure.
     * @param x initial x coordinate
     * @param y initial y coordinate
     * @param card the card, which affects the figure abilities
     */
    public Figure(int x, int y, Card card) {
        this.x = x;
        this.y = y;
        this.card = card;
    }

    /**
     * @return x coordinate of the figure
     */
    public int getX() {
        return x;
    }

    /**
     * @param x new x coordinate of the figure
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return x coordinate of Figure
     */
    public int getY() {
        return y;
    }

    /**
     * @param y new y coordinate of the figure
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return card, which the figure is owning
     */
    public Card getCard() {
        return card;
    }

    /**
     * @param card new card of the figure
     */
    public void setCard(Card card) {
        this.card = card;
    }
}