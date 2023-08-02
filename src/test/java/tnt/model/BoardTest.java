package tnt.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Board class.
 */
public class BoardTest {

    private Board board;
    private Field[][] fields;

    /**
     * Set up the necessary dependencies before each test.
     */
    @BeforeEach
    public void setup() {
        fields = new Field[3][3];
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                fields[x][y] = new Field(x, y);
            }
        }
        board = new Board(fields, 3, 3);
    }

    /**
     * Tests the getField method of Board.
     * It should return the expected Field object at the specified coordinates.
     */
    @Test
    public void testGetField() {
        Field field = board.getField(1, 2);
        Assertions.assertEquals(1, field.getX());
        Assertions.assertEquals(2, field.getY());
    }

    /**
     * Tests the getXSize method of Board.
     * It should return the expected X size of the board.
     */
    @Test
    public void testGetXSize() {
        Assertions.assertEquals(3, board.getXSize());
    }

    /**
     * Tests the getYSize method of Board.
     * It should return the expected Y size of the board.
     */
    @Test
    public void testGetYSize() {
        Assertions.assertEquals(3, board.getYSize());
    }

    /**
     * Tests the setRoundWorld and getRoundWorld methods of Board.
     * It should set and retrieve the round world flag correctly.
     */
    @Test
    public void testSetRoundWorld() {
        board.setRoundWorld(true);
        Assertions.assertTrue(board.isRoundWorld());
    }

    /**
     * Tests the getRoundWorld method of Board.
     * It should return the default value of the round world flag.
     */
    @Test
    public void testGetRoundWorld() {
        Assertions.assertFalse(board.isRoundWorld());
    }
}
