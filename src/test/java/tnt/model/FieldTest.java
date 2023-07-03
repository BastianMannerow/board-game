package tnt.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Observable;
import java.util.Observer;

public class FieldTest {

    private Field field;
    private boolean observerNotified;

    @BeforeEach
    public void setup() {
        field = new Field(0, 0);
        observerNotified = false;
    }

    @Test
    public void testConstruction() {
        Assertions.assertEquals(0, field.getTowerLevel());
        Assertions.assertFalse(field.getTowerComplete());
        Assertions.assertNull(field.getFigure());
    }

    @Test
    public void testFigureLeft() {
        field.setFigure(new Figure());
        field.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                observerNotified = true;
            }
        });

        field.figureLeft();

        Assertions.assertNull(field.getFigure());
        Assertions.assertTrue(observerNotified);
    }

    @Test
    public void testIsFigureHere() {
        Assertions.assertFalse(field.getIsFigureHere());

        field.setFigure(new Figure());

        Assertions.assertTrue(field.getIsFigureHere());
    }

    @Test
    public void testSetTowerLevel() {
        field.setTowerLevel(3);

        Assertions.assertEquals(3, field.getTowerLevel());
    }

    @Test
    public void testSetTowerComplete() {
        field.setTowerComplete(true);

        Assertions.assertTrue(field.getTowerComplete());
    }

    @Test
    public void testGetX() {
        Assertions.assertEquals(0, field.getX());
    }

    @Test
    public void testGetY() {
        Assertions.assertEquals(0, field.getY());
    }

    @Test
    public void testSetFigure() {
        Figure figure = new Figure();
        field.addObserver((Observable o, Object arg) -> observerNotified = true);

        field.setFigure(figure);

        Assertions.assertEquals(figure, field.getFigure());
        Assertions.assertTrue(observerNotified);
    }
}