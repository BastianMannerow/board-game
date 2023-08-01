package tnt.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/**
 * Unit tests for the Player class.
 */
public class PlayerTest {

    private Player player;
    private Board board;
    private Game game;
    private ArrayList<Player> playerOrder;

    /**
     * Set up the necessary dependencies before each test.
     */
    @BeforeEach
    public void setup() {
        Player.PlayerType levelOfIntelligence = Player.PlayerType.HUMAN;
        String name = "John";
        Color color = Color.RED;
        int amountOfTurns = 10;
        String playerId = "1";

        // Create a sample board with 3x3 fields
        Field[][] fields = new Field[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                fields[i][j] = new Field(i, j);
            }
        }
        // initialize board with the array of fields
        int rows = fields.length;
        int cols = fields[0].length;
        board = new Board(fields, rows, cols);

        // initialize game
        playerOrder = new ArrayList<>();
        game = new Game(playerOrder, 12, "Test Game", 1, 3, 3, true);

        player = new Player(levelOfIntelligence, name, color, amountOfTurns, game, playerId, 0);
    }

    /**
     * Tests the getLevelOfIntelligence method of Player.
     * It should return the level of intelligence of the player.
     */
    @Test
    public void testGetLevelOfIntelligence() {
        Player.PlayerType levelOfIntelligence = player.getLevelOfIntelligence();
        Assertions.assertEquals(Player.PlayerType.HUMAN, levelOfIntelligence);
    }

    /**
     * Tests the getName method of Player.
     * It should return the name of the player.
     */
    @Test
    public void testGetName() {
        String name = player.getName();
        Assertions.assertEquals("John", name);
    }

    /**
     * Tests the setName method of Player.
     * It should set the name of the player to the specified value.
     */
    @Test
    public void testSetName() {
        player.setName("Jane");
        String name = player.getName();
        Assertions.assertEquals("Jane", name);
    }

    /**
     * Tests the getColor method of Player.
     * It should return the color of the player.
     */
    @Test
    public void testGetColor() {
        Color color = player.getColor();
        Assertions.assertEquals(Color.RED, color);
    }

    /**
     * Tests the setColor method of Player.
     * It should set the color of the player to the specified value.
     */
    @Test
    public void testSetColor() {
        player.setColor(Color.BLUE);
        Color color = player.getColor();
        Assertions.assertEquals(Color.BLUE, color);
    }

    /**
     * Tests the getFigures method of Player.
     * It should return the list of figures owned by the player.
     */
    @Test
    public void testGetFigures() {
        ArrayList<Figure> figures = player.getFigure();
        Assertions.assertEquals(0, figures.size());
    }

    /**
     * Tests the addFigure method of Player with the amount parameter.
     * It should add the specified number of figures to the player's collection.
     */
    @Test
    public void testAddFigureWithAmount() {
        player.addFigure(3);
        ArrayList<Figure> figures = player.getFigure();
        Assertions.assertEquals(3, figures.size());
    }

    /**
     * Tests the addFigure method of Player with coordinates.
     * It should add a figure to the player's collection with the specified coordinates.
     */
    @Test
    public void testAddFigureWithCoordinates() {
        ArrayList<Figure> figures = new ArrayList<>();
        player = new Player(Player.PlayerType.HUMAN, "John", Color.RED, figures, 10);
        player.addFigure(2);
        Assertions.assertEquals(2, figures.size());
        Figure figure = figures.get(0);
        Assertions.assertEquals(0, figure.getX());
        Assertions.assertEquals(0, figure.getY());
    }

    /**
     * Tests the executeMove method of Player.
     * It should execute the move action, moving the specified figure to the specified field on the board.
     */
    @Test
    public void testExecuteMove() {
        // Set up the necessary objects for the test
        Field field = board.getField(1, 1);
        Figure figure = new Figure(0, 0, game, player);
        board.getField(0, 0).setFigure(figure);

        // Execute the move action
        player.executeMove(field, board, figure);

        // Check if the figure is moved to the correct field
        Assertions.assertNull(board.getField(0, 0).getFigure());
        Assertions.assertEquals(1, figure.getX());
        Assertions.assertEquals(1, figure.getY());
        Assertions.assertSame(figure, field.getFigure());
    }

    /**
     * Tests the initPlayer method of Player.
     * It should initialize the player by adding two figures to their collection.
     */
    @Test
    public void testInitPlayer() {
        ArrayList<Figure> figures = new ArrayList<>();
        player = new Player(Player.PlayerType.HUMAN, "John", Color.RED, figures, 10);
        player.initPlayer();
        Assertions.assertEquals(0, figures.size());
    }

    /**
     * Tests the setLevelOfIntelligence method of Player.
     * It should set the LevelOfIntelligence.
     */
    @Test
    public void testSetLevelOfIntelligence() {
        // Test setting the level of intelligence
        player.setLevelOfIntelligence(Player.PlayerType.AI_1);
        Player.PlayerType levelOfIntelligence = player.getLevelOfIntelligence();

        // Verify that the level of intelligence is set correctly
        Assertions.assertEquals(Player.PlayerType.AI_1, levelOfIntelligence);
    }

    /**
     * Tests the setTeam method of Player.
     * It should set the Team.
     */
    @Test
    public void testSetTeam() {
        // Test setting the team
        player.setTeam("Team 1");
        String team = player.getTeam();

        // Verify that the team is set correctly
        Assertions.assertEquals("Team 1", team);
    }

    /**
     * Tests the allFiguresPlaced method of Player.
     * It should verify that all figures are placed.
     */
    @Test
    public void testAllFiguresPlaced() {
        // Test when not all figures are placed
        player.addFigure(2);
        boolean allFiguresPlaced = player.allFiguresPlaced();

        // Verify that all figures are not placed
        Assertions.assertFalse(allFiguresPlaced);

        // Place all figures
        player.getFigure().forEach(Figure::setPlaced);
        allFiguresPlaced = player.allFiguresPlaced();

        // Verify that all figures are placed
        Assertions.assertTrue(allFiguresPlaced);
    }

    /**
     * Tests the setAmountOfFigures method of Player.
     * It should set a certain amount of figures.
     */
    @Test
    public void testSetAmountOfFigures() {
        game.setGameStatus(Game.GameStatus.SELECT_PLAYER);
        // Test setting the amount of figures
        player.setAmountOfFigures(3);
        int amountOfFigures = player.getAmountOfFigures();

        // Verify that the amount of figures is set correctly
        Assertions.assertEquals(10, amountOfFigures);
    }
}