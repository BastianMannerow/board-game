package tnt.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for the FileManager class.
 */
public class FileManagerTest {
    private FileManager fileManager;

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
        List<String[]> expectedData = new ArrayList<>();
        expectedData.add(new String[]{"Hans", "Lulatsch"});
        expectedData.add(new String[]{"Stefan", "Bombig"});

        List<Object> actualData = fileManager.loadCSV(filepath);

        Assertions.assertEquals(expectedData, actualData);
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
        data.add(new String[]{"Hans", "Lulatsch"});
        data.add(new String[]{"Stefan", "Bombig"});

        String expectedString = "John,Doe\nJane,Smith";

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
        ArrayList<String> expectedGames = new ArrayList<>();
        expectedGames.add("Game 1");
        expectedGames.add("Game 2");

        ArrayList<String> actualGames = fileManager.getSavedGames();

        Assertions.assertEquals(expectedGames, actualGames);
    }

    /**
     * Test case for the loadGame method.
     */
    @Test
    public void testLoadGame() {
        String savedGame = "Game 1";
        Game game = new Game(2);
        // ... Set up the game object and other necessary data

        fileManager.loadGame(savedGame, game);

        // ... Implement assertions to verify the loaded game data is correct
    }

    /**
     * Test case for the saveGame method.
     */
    @Test
    public void testSaveGame() {
        Game game = new Game(2);
        // ... Set up the game object and other necessary data

        fileManager.saveGame(game);

        // ... Implement assertions to verify the game is saved correctly
    }

    /**
     * Test case for the getFieldsData method.
     */
    @Test
    public void testGetFieldsData() {
        Game game = new Game(2);
        // ... Set up the game object and its board with necessary data

        List<String[]> fieldsData = fileManager.getFieldsData(game);

        // ... Implement assertions to verify the fields data matches the expected data
    }

    /**
     * Test case for the getFiguresData method.
     */
    @Test
    public void testGetFiguresData() {
        ArrayList<Player> playerList = new ArrayList<>();
        // ... Set up the player list with necessary data

        List<String[]> figureData = fileManager.getFiguresData(playerList);

        // ... Implement assertions to verify the figure data matches the expected data
    }

    /**
     * Test case for the getPlayersData method.
     */
    @Test
    public void testGetPlayersData() {
        ArrayList<Player> playerList = new ArrayList<>();
        // ... Set up the player list with necessary data

        List<String[]> playerData = fileManager.getPlayersData(playerList);

        // ... Implement assertions to verify the player data matches the expected data
    }

    /**
     * Test case for the getGameData method.
     */
    @Test
    public void testGetGameData() {
        Game game = new Game(2);
        // ... Set up the game object with necessary data

        List<String[]> gameData = fileManager.getGameData(game);

        // ... Implement assertions to verify the game data matches the expected data
    }

    /**
     * Test case for the loadHighscore method.
     */
    @Test
    public void testLoadHighscore() {
        fileManager.loadHighscore();

        // ... Implement assertions to verify the highscore is loaded correctly
    }

    /**
     * Test case for the saveHighscore method.
     */
    @Test
    public void testSaveHighscore() {
        Game game = new Game(2);
        ArrayList<Player> playerList = new ArrayList<>();
        // ... Set up the game object, player list, and necessary data

        // Todo:
//        fileManager.saveHighscore(game, playerList);

        // ... Implement assertions to verify the highscore is saved correctly
    }
}
