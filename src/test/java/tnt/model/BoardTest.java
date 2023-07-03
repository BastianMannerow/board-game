package tnt.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {

    private Board board;
    private Field[][] fields;

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

    @Test
    public void testGetField() {
        Field field = board.getField(1, 2);
        Assertions.assertEquals(1, field.getX());
        Assertions.assertEquals(2, field.getY());
    }

    @Test
    public void testGetXSize() {
        Assertions.assertEquals(3, board.getXSize());
    }

    @Test
    public void testGetYSize() {
        Assertions.assertEquals(3, board.getYSize());
    }

    @Test
    public void testSetRoundWorld() {
        board.setRoundWorld(true);
        Assertions.assertTrue(board.getRoundWorld());
    }

    @Test
    public void testGetRoundWorld() {
        Assertions.assertFalse(board.getRoundWorld());
    }
}
