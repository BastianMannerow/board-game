package tnt.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Observable;
import java.util.Observer;

/**
 * Unit tests for the Field class.
 */
public class FieldTest {

    private Field field;
    private boolean observerNotified;

    /**
     * Set up the necessary dependencies before each test.
     */
    @BeforeEach
    public void setup() {
        field = new Field(0, 0);
        observerNotified = false;
    }

    /**
     * Tests the construction of a Field object.
     * It should initialize the tower level to 0, tower completion flag to false, and figure to null.
     */
    @Test
    public void testConstruction() {
        Assertions.assertEquals(0, field.getTowerLevel());
        Assertions.assertFalse(field.getTowerComplete());
        Assertions.assertNull(field.getFigure());
    }

    /**
     * Tests the figureLeft method of Field.
     * It should remove the figure from the field and notify the observer.
     */
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

    /**
     * Tests the isFigureHere method of Field.
     * It should return true if a figure is present on the field, false otherwise.
     */
    @Test
    public void testIsFigureHere() {
        Assertions.assertFalse(field.getIsFigureHere());

        field.setFigure(new Figure());

        Assertions.assertTrue(field.getIsFigureHere());
    }

    /**
     * Tests the setTowerLevel method of Field.
     * It should set the tower level to the specified value.
     */
    @Test
    public void testSetTowerLevel() {
        field.setTowerLevel(3);

        Assertions.assertEquals(3, field.getTowerLevel());
    }

    /**
     * Tests the setTowerComplete method of Field.
     * It should set the tower completion flag to the specified value.
     */
    @Test
    public void testSetTowerComplete() {
        field.setTowerComplete(true);

        Assertions.assertTrue(field.getTowerComplete());
    }

    /**
     * Tests the getX method of Field.
     * It should return the X coordinate of the field.
     */
    @Test
    public void testGetX() {
        Assertions.assertEquals(0, field.getX());
    }

    /**
     * Tests the getY method of Field.
     * It should return the Y coordinate of the field.
     */
    @Test
    public void testGetY() {
        Assertions.assertEquals(0, field.getY());
    }

    /**
     * Tests the setFigure method of Field.
     * It should set the figure on the field and notify the observer.
     */
    @Test
    public void testSetFigure() {
        Figure figure = new Figure();
        field.addObserver((Observable o, Object arg) -> observerNotified = true);

        field.setFigure(figure);

        Assertions.assertEquals(figure, field.getFigure());
        Assertions.assertTrue(observerNotified);
    }
}
