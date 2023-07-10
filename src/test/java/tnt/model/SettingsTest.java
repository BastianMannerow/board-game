package tnt.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tnt.remote.NetworkHandler;

/**
 * JUnit test class for the Settings class.
 */
public class SettingsTest {

    /**
     * Tests the getDefaultPlayer() method.
     */
    @Test
    public void testGetDefaultPlayer() {
        int defaultPlayer = Settings.getDefaultPlayer();
        Assertions.assertEquals(2, defaultPlayer);
    }

    /**
     * Tests the getFieldSizeX() method.
     */
    @Test
    public void testGetFieldSizeX() {
        int fieldSizeX = Settings.getFieldSizeX();
        Assertions.assertEquals(5, fieldSizeX);
    }

    /**
     * Tests the getFieldSizeY() method.
     */
    @Test
    public void testGetFieldSizeY() {
        int fieldSizeY = Settings.getFieldSizeY();
        Assertions.assertEquals(5, fieldSizeY);
    }

    /**
     * Tests the getActualGame() method.
     */
    @Test
    public void testGetActualGame() {
        Game actualGame = Settings.getActualGame();
        Assertions.assertNull(actualGame);
    }

    /**
     * Tests the setActualGame() method.
     */
    @Test
    public void testSetActualGame() {
        Game game = new Game(2);
        Settings.setActualGame(game);
        Game actualGame = Settings.getActualGame();
        Assertions.assertEquals(game, actualGame);
    }

    /**
     * Tests the getNetworkHandler() method.
     */
    @Test
    public void testGetNetworkHandler() {
        NetworkHandler networkHandler = Settings.getNetworkHandler();
        Assertions.assertNotNull(networkHandler);
    }

    /**
     * Tests the isServerMode() method.
     */
    @Test
    public void testIsServerMode() {
        boolean isServerMode = Settings.isServerMode();
        Assertions.assertTrue(isServerMode);
    }

    /**
     * Tests the setServerMode() method.
     */
    @Test
    public void testSetServerMode() {
        Settings.setServerMode(false);
        boolean isServerMode = Settings.isServerMode();
        Assertions.assertFalse(isServerMode);
    }

    /**
     * Tests the setClientMode() method.
     */
    @Test
    public void testSetClientMode() {
        Settings.setClientMode();
        boolean isServerMode = Settings.isServerMode();
        Assertions.assertFalse(isServerMode);
    }

    /**
     * Tests the getMaxFieldcount() method.
     */
    @Test
    public void testGetMaxFieldcount() {
        int maxFieldcount = Settings.getMaxFieldcount();
        Assertions.assertEquals(500, maxFieldcount);
    }

    /**
     * Tests the getMaxStepUp() method.
     */
    @Test
    public void testGetMaxStepUp() {
        int maxStepUp = Settings.getMaxStepUp();
        Assertions.assertEquals(1, maxStepUp);
    }

    /**
     * Tests the getMaxStepDown() method.
     */
    @Test
    public void testGetMaxStepDown() {
        int maxStepDown = Settings.getMaxStepDown();
        Assertions.assertEquals(3, maxStepDown);
    }

    /**
     * Tests the getMaxBuildingLevel() method.
     */
    @Test
    public void testGetMaxBuildingLevel() {
        int maxBuildingLevel = Settings.getMaxBuildingLevel();
        Assertions.assertEquals(3, maxBuildingLevel);
    }
}
