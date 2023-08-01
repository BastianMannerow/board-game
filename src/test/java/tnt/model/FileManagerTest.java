package tnt.model;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Unit tests for the FileManager class.
 */
public class FileManagerTest {
    private FileManager fileManager;
    private ArrayList<Player> playerOrder;

    /**
     * Set up the FileManager instance before each test.
     */
    @BeforeEach
    public void setUp() {
        fileManager = new FileManager();
    }

    /**
     * Test case for the loadCSV method.
     */
    @Test
    public void testLoadCSV() {
        String filepath = "path/to/file.csv";
        List<String> expectedHeader = Arrays.asList("Hans", "Lulatsch");
        List<List<String>> expectedData = new ArrayList<>();
        expectedData.add(Arrays.asList("Stefan", "Bombig"));

        List<Object> expected = new ArrayList<>();
        expected.add(expectedHeader);
        expected.add(expectedData);

        List<Object> actual = fileManager.loadCSV(filepath);

        Assertions.assertEquals(expected, actual);
    }


    /**
     * Test case for the readString method.
     */
    @Test
    public void testReadString() {
        String str = "Hans,Lulatsch\nStefan,Bombig";
        List<String[]> expectedData = new ArrayList<>();
        expectedData.add(new String[]{"Hans", "Lulatsch"});
        expectedData.add(new String[]{"Stefan", "Bombig"});

        List<String[]> actualData = fileManager.readString(str);

        Assertions.assertEquals(expectedData, actualData);
    }

    /**
     * Test case for the saveCSV method.
     */
    @Test
    public void testSaveCSV() {
        String filepath = "path/to/file.csv";
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"Hans", "Lulatsch"});
        data.add(new String[]{"Stefan", "Bombig"});

        fileManager.saveCSV(filepath, data);

        // Verify that the file exists
        File file = new File(filepath);
        Assertions.assertTrue(file.exists());
    }

    /**
     * Test case for the makeString method.
     */
    @Test
    public void testMakeString() {
        List<String[]> data = new ArrayList<>();
        String lineSeparator = System.getProperty("line.separator");
        data.add(new String[]{"Hans", "Lulatsch"});
        data.add(new String[]{"Stefan", "Bombig"});

        String expectedString = "Hans,Lulatsch" + lineSeparator + "Stefan,Bombig" + lineSeparator;

        String actualString = FileManager.makeString(data);

        Assertions.assertEquals(expectedString, actualString);
    }

    /**
     * Test case for the deleteFolder method.
     */
    @Test
    public void testDeleteFolder() {
        // Create a temporary folder for testing
        File folder = new File("path/to/temp/folder");
        folder.mkdirs();

        fileManager.deleteFolder(folder);

        // Verify that the folder no longer exists
        Assertions.assertFalse(folder.exists());
    }

    /**
     * Test case for the getSavedGames method.
     */
    @Test
    public void testGetSavedGames() {
        playerOrder = new ArrayList<>();
        ArrayList<Figure> figures = new ArrayList<>();
        int initialNumberOfGames = fileManager.getSavedGames().size();

        Game game = new Game(playerOrder, 12, "Test Game Remove Later", 1, 3, 3, true);
        Player player = new Player(Player.PlayerType.HUMAN, "John", Color.RED, figures, 10);
        playerOrder.add(player);
        fileManager.saveGame(game);

        ArrayList<String> savedGames = fileManager.getSavedGames();
        int numberOfGamesAfterAddingNew = savedGames.size();

        Assertions.assertEquals(initialNumberOfGames + 1, numberOfGamesAfterAddingNew);
        fileManager.deleteFolder(new File(System.getProperty("user.dir")+File.separator + "savings"+File.separator + "Test Game Remove Later"));
    }

    /**
     * Test case for the loadGame method.
     */
    @Test
    public void testLoadGame() {
        String savedGame = "Test Game";
        Game game = new Game(playerOrder, 12, "Test Game", 1, 3, 3, true);

        Player player1 = new Player(Player.PlayerType.HUMAN, "Player1", Color.BLUE, 1, game, "1", 0);
        Player player2 = new Player(Player.PlayerType.HUMAN, "Player2", Color.BLUE, 0, game, "2", 0);

        // Call the method under test.
        FileManager fileManager = new FileManager();
        fileManager.getSavedGames();
        fileManager.loadGame(savedGame, game);

        // Validate the results (e.g., check that the players have the correct figures).
        Assertions.assertEquals(1, player1.getAmountOfFigures());
        Assertions.assertEquals(1, player2.getAmountOfFigures());

        // Validate the position of figures
        Figure player1Figure = player1.getFigure().get(0);
        Figure player2Figure = player2.getFigure().get(0);

        Assertions.assertEquals(1, player1Figure.getX());
        Assertions.assertEquals(1, player1Figure.getY());

        Assertions.assertEquals(2, player2Figure.getX());
        Assertions.assertEquals(2, player2Figure.getY());

        // Validate the placed state of figures
        Assertions.assertTrue(player1Figure.isPlaced());
        Assertions.assertFalse(player2Figure.isPlaced());
    }

    /**
     * Test case for the saveGame method.
     */
    @Test
    public void testSaveGame() {
        playerOrder = new ArrayList<>();
        Game game = new Game(playerOrder, 12, "Test Game", 1, 3, 3, true);

        fileManager.saveGame(game);
    }

    /**
     * Test case for the getFieldsData method.
     */
    @Test
    public void testGetFieldsData() {
        Game game = new Game(2);
        List<String[]> fieldsData = fileManager.getFieldsData(game);

    }

    /**
     * Test case for the getFiguresData method.
     */
    @Test
    public void testGetFiguresData() {
        ArrayList<Player> playerList = new ArrayList<>();
        List<String[]> figureData = fileManager.getFiguresData(playerList);

    }

    /**
     * Test case for the getPlayersData method.
     */
    @Test
    public void testGetPlayersData() {
        ArrayList<Player> playerList = new ArrayList<>();
        List<String[]> playerData = fileManager.getPlayersData(playerList);

    }

    /**
     * Test case for the getGameData method.
     */
    @Test
    public void testGetGameData() {
        Game game = new Game(2);
        List<String[]> gameData = fileManager.getGameData(game);

    }

    /**
     * Test case for the loadHighscore method.
     */
    @Test
    public void testLoadHighscore() {
        fileManager.loadHighscore();
    }

    /**
     * Test case for the saveHighscore method.
     */
    @Test
    public void testSaveHighscore() {
        Game game = new Game(2);
        ArrayList<Player> playerList = new ArrayList<>();
        fileManager.saveHighscore(game, "Team A", 1);
    }
}
