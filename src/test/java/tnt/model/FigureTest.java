package tnt.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tnt.util.Observer;

import javafx.scene.paint.Color;
import java.util.ArrayList;


/**
 * Unit tests for the Figure class.
 */
public class FigureTest implements Observer {

    private Figure figure;
    private Board board;
    private Player player;
    private Game game;

    /**
     * Set up the necessary dependencies before each test.
     */
    @BeforeEach
    public void setup() {
        board = new Board(new Field[5][5], 5, 5);
        player = new Player(Player.PlayerType.HUMAN, "John", Color.RED, new ArrayList<>(), 10);
        game = new Game(1);
        figure = new Figure(2, 2, game, game.getPlayersTurn());
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
        Game game = new Game(2);
        Figure figure = new Figure(game, game.getPlayersTurn());
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
        Figure figure = new Figure(1, 1, game, player);
        board.getField(1, 1).setFigure(figure);

        // Test when the world is not round
        ArrayList<Field> validMoves = figure.getValidMoves(board);
        // Assert that the reachable fields are correct based on the test configuration
        // In this example, the figure is at (1, 1), so the reachable fields should be 8
        Assertions.assertEquals(8, validMoves.size());

        // Test when the world is round
        board.setRoundWorld(true);
        validMoves = figure.getValidMoves(board);
        // In this example, the figure is at (1, 1), and the world is round, so the reachable fields should still be 8
        Assertions.assertEquals(8, validMoves.size());

        // Test when the figure is at the edge of the board in a round world
        figure = new Figure(0, 0, game, player);
        board.getField(0, 0).setFigure(figure);
        board.setRoundWorld(true);
        validMoves = figure.getValidMoves(board);
        // In this case, the figure is at (0, 0), and the world is round, so the reachable fields should be 8
        // because it can move to (1, 0), (1, 1), (0, 1), (2, 0), (2, 1), (2, 2), (0, 2), and (1, 2)
        Assertions.assertEquals(8, validMoves.size());

    }

    /**
     * Tests the getValidBuilds method of Figure.
     * It should return a list of valid build positions for the figure on the given board.
     */
    @Test
    public void testGetValidBuilds() {
        Figure figure = new Figure(1, 1, game, player);
        board.getField(1, 1).setFigure(figure);

        // Test when the world is not round
        ArrayList<Field> validBuilds = figure.getValidBuilds(board);
        // In this example, the figure is at (1, 1), so it can build on any adjacent field without a tower
        Assertions.assertEquals(8, validBuilds.size());

        // Test when the world is round
        board.setRoundWorld(true);
        validBuilds = figure.getValidBuilds(board);
        // In this example, the figure is at (1, 1), and the world is round, so it can build on any adjacent field without a tower
        Assertions.assertEquals(8, validBuilds.size());

        // Test when the figure is at the edge of the board in a round world
        figure = new Figure(0, 0, game, player);
        board.getField(0, 0).setFigure(figure);
        board.setRoundWorld(true);
        validBuilds = figure.getValidBuilds(board);
        // In this case, the figure is at (0, 0), and the world is round, so it can build on any adjacent field without a tower
        // which includes (1, 0), (1, 1), (0, 1), (2, 0), (2, 1), (2, 2), (0, 2), and (1, 2)
        Assertions.assertEquals(8, validBuilds.size());

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

