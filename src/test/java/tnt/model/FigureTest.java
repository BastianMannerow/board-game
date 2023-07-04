package tnt.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tnt.util.Observer;

import java.util.ArrayList;


/**
 * Unit tests for the Figure class.
 */
public class FigureTest implements Observer {

    private Figure figure;
    private Board board;

    /**
     * Set up the necessary dependencies before each test.
     */
    @BeforeEach
    public void setup() {
        board = new Board(new Field[5][5], 5, 5);
        figure = new Figure(2, 2, new Game());
    }

    /**
     * Tests the construction of a Figure object with specified coordinates.
     * It should initialize the figure with the given X and Y coordinates and mark it as placed.
     */
    @Test
    public void testConstructionWithCoordinates() {
        Assertions.assertEquals(2, figure.getX());
        Assertions.assertEquals(2, figure.getY());
        Assertions.assertTrue(figure.isPlaced());
    }

    /**
     * Tests the construction of a Figure object without specified coordinates.
     * It should initialize the figure with default coordinates and mark it as not placed.
     */
    @Test
    public void testConstructionWithoutCoordinates() {
        Figure figure = new Figure();
        Assertions.assertFalse(figure.isPlaced());
    }

    /**
     * Tests the setX method of Figure.
     * It should set the X coordinate of the figure to the specified value.
     */
    @Test
    public void testSetX() {
        figure.setX(3);
        Assertions.assertEquals(3, figure.getX());
    }

    /**
     * Tests the setY method of Figure.
     * It should set the Y coordinate of the figure to the specified value.
     */
    @Test
    public void testSetY() {
        figure.setY(4);
        Assertions.assertEquals(4, figure.getY());
    }

    /**
     * Tests the getValidMoves method of Figure.
     * It should return a list of valid moves for the figure on the given board.
     */
    @Test
    public void testGetValidMoves() {
        figure.addObserver(this);
        ArrayList<Field> validMoves = figure.getValidMoves(board);
    }

    /**
     * Tests the getValidBuilds method of Figure.
     * It should return a list of valid build positions for the figure on the given board.
     */
    @Test
    public void testGetValidBuilds() {
        figure.addObserver(this);
        ArrayList<Field> validBuilds = figure.getValidBuilds(board);
    }

    /**
     * Tests the isPlaced method of Figure.
     * It should return true if the figure is placed on the board, false otherwise.
     */
    @Test
    public void testIsPlaced() {
        Assertions.assertTrue(figure.isPlaced());
    }

    /**
     * Tests the setPlaced method of Figure.
     * It should mark the figure as placed on the board.
     */
    @Test
    public void testSetPlaced() {
        figure.setPlaced();
        Assertions.assertTrue(figure.isPlaced());
    }

    @Override
    public void update() {
        // Do Nothing
    }
}

