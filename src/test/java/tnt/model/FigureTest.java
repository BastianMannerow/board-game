package tnt.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class FigureTest {

    private Figure figure;
    private Board board;

    @BeforeEach
    public void setup() {
        board = new Board(new Field[5][5], 5, 5);
        figure = new Figure(2, 2, new Game());
    }

    @Test
    public void testConstructionWithCoordinates() {
        Assertions.assertEquals(2, figure.getX());
        Assertions.assertEquals(2, figure.getY());
        Assertions.assertTrue(figure.isPlaced());
    }

    @Test
    public void testConstructionWithoutCoordinates() {
        Figure figure = new Figure();
        Assertions.assertFalse(figure.isPlaced());
    }

    @Test
    public void testSetX() {
        figure.setX(3);
        Assertions.assertEquals(3, figure.getX());
    }

    @Test
    public void testSetY() {
        figure.setY(4);
        Assertions.assertEquals(4, figure.getY());
    }

    @Test
    public void testGetValidMoves() {
        board.getField(1, 1).setTowerLevel(1);
        board.getField(2, 1).setTowerLevel(2);
        board.getField(3, 1).setTowerLevel(1);
        board.getField(1, 2).setTowerLevel(2);
        board.getField(2, 2).setTowerLevel(0);  // Starting position
        board.getField(3, 2).setTowerLevel(1);
        board.getField(1, 3).setTowerLevel(1);
        board.getField(2, 3).setTowerLevel(2);
        board.getField(3, 3).setTowerLevel(1);

        figure.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                // Do nothing
            }
        });

        ArrayList<Field> validMoves = figure.getValidMoves(board);

        Assertions.assertEquals(8, validMoves.size());
        Assertions.assertTrue(validMoves.contains(board.getField(1, 1)));
        Assertions.assertTrue(validMoves.contains(board.getField(2, 1)));
        Assertions.assertTrue(validMoves.contains(board.getField(3, 1)));
        Assertions.assertTrue(validMoves.contains(board.getField(1, 2)));
        Assertions.assertTrue(validMoves.contains(board.getField(3, 2)));
        Assertions.assertTrue(validMoves.contains(board.getField(1, 3)));
        Assertions.assertTrue(validMoves.contains(board.getField(2, 3)));
        Assertions.assertTrue(validMoves.contains(board.getField(3, 3)));
    }

    @Test
    public void testGetValidBuilds() {
        board.getField(1, 1).setTowerComplete(true);
        board.getField(2, 1).setTowerLevel(2);
        board.getField(3, 1).setTowerComplete(true);
        board.getField(1, 2).setTowerLevel(2);
        board.getField(2, 2).setTowerLevel(1);
        board.getField(3, 2).setTowerComplete(true);
        board.getField(1, 3).setTowerComplete(true);
        board.getField(2, 3).setTowerLevel(2);
        board.getField(3, 3).setTowerComplete(true);

        figure.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                // Do nothing
            }
        });

        ArrayList<Field> validBuilds = figure.getValidBuilds(board);

        Assertions.assertEquals(1, validBuilds.size());
        Assertions.assertTrue(validBuilds.contains(board.getField(2, 1)));
    }

    @Test
    public void testIsPlaced() {
        Assertions.assertTrue(figure.isPlaced());
    }

    @Test
    public void testSetPlaced() {
        figure.setPlaced();
        Assertions.assertTrue(figure.isPlaced());
    }
}
