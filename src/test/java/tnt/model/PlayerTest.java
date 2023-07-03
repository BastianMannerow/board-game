package tnt.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/**
 * Testing the Example class with its arbitrary arithmetical computations
 */
public class PlayerTest {
    private Player player;
    private ArrayList<Figure> figures;
    private ArrayList<Object> gods;
    private Board board;

    @BeforeEach
    public void setup() {
        Player.PlayerType levelOfIntelligence = Player.PlayerType.HUMAN;
        String name = "John";
        Color color = Color.RED;
        ArrayList<Figure> figures = new ArrayList<>();

        player = new Player(levelOfIntelligence, name, color, figures);

        // Create a sample board with 3x3 fields
        Field[][] fields = new Field[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                fields[i][j] = new Field(i, j);
            }
        }
        board = new Board(fields, 3, 3);
    }

    @Test
    public void testGetLevelOfIntelligence() {
        Player.PlayerType levelOfIntelligence = player.getLevelOfIntelligence();
        Assertions.assertEquals(Player.PlayerType.HUMAN, levelOfIntelligence);
    }

    @Test
    public void testGetName() {
        String name = player.getName();
        Assertions.assertEquals("John", name);
    }

    @Test
    public void testSetName() {
        player.setName("Jane");
        String name = player.getName();
        Assertions.assertEquals("Jane", name);
    }

    @Test
    public void testGetColor() {
        Color color = player.getColor();
        Assertions.assertEquals(Color.RED, color);
    }

    @Test
    public void testSetColor() {
        player.setColor(Color.BLUE);
        Color color = player.getColor();
        Assertions.assertEquals(Color.BLUE, color);
    }

    @Test
    public void testGetFigures() {
        ArrayList<Figure> figures = player.getFigure();
        Assertions.assertEquals(0, figures.size());
    }

    /*
    @Test
    public void testAddGod() {
        Gods god = new Ares();
        player.addGod(god);
        ArrayList<Gods> gods = player.getGods();
        Assertions.assertEquals(1, gods.size());
        Assertions.assertEquals(god, gods.get(0));
    }

    @Test
    public void testAddAllGods() {
        player.AddAllGods();
        ArrayList<Gods> gods = player.getGods();
        Assertions.assertEquals(34, gods.size());
    }
    */

    @Test
    public void testAddFigureWithAmount() {
        player.addFigure(3);
        ArrayList<Figure> figures = player.getFigure();
        Assertions.assertEquals(3, figures.size());
    }

    @Test
    public void testAddFigureWithCoordinates() {
        Game game = new Game();
        player = new Player(Player.PlayerType.HUMAN, "John", Color.RED, figures);
        player.addFigure(1);
        ArrayList<Figure> figures = player.getFigure();
        Assertions.assertEquals(1, figures.size());
        Figure figure = figures.get(0);
        Assertions.assertEquals(1, figure.getX());
        Assertions.assertEquals(2, figure.getY());
    }

    /*
    @Test
    public void testGetGods() {
        ArrayList<Gods> gods = player.getGods();
        Assertions.assertEquals(0, gods.size());
    }
    */

    @Test
    public void testExecuteBuild() {
        Field field = new Field(0, 0);
        Field[][] boardArray = new Field[1][1];
        boardArray[0][0] = field;
        Board board = new Board(boardArray, 1, 1);

        // Set initial tower level and completion status
        field.setTowerLevel(0);
        field.setTowerComplete(false);

        // Execute the build action
        player.executeBuild(field, board);

        // Check if the tower level and completion status are updated correctly
        Assertions.assertEquals(1, field.getTowerLevel());
        Assertions.assertFalse(field.getTowerComplete());
    }

    @Test
    public void testExecuteMove() {
        // Set up the necessary objects for the test
        Field field = board.getField(1, 1);
        Figure figure = new Figure(0, 0, new Game());
        board.getField(0, 0).setFigure(figure);

        // Execute the move action
        player.executeMove(field, board, figure);

        // Check if the figure is moved to the correct field
        Assertions.assertNull(board.getField(0, 0).getFigure());
        Assertions.assertEquals(1, figure.getX());
        Assertions.assertEquals(1, figure.getY());
        Assertions.assertSame(figure, field.getFigure());
    }

    @Test
    public void testInitPlayer() {
        player = new Player(Player.PlayerType.HUMAN, "John", Color.RED, figures);
        player.initPlayer();
        ArrayList<Figure> figures = player.getFigure();
        Assertions.assertEquals(2, figures.size());
    }
}