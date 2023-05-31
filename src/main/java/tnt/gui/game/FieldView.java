package tnt.gui.game;

import javafx.scene.layout.HBox;
import tnt.model.Field;
import tnt.model.Figure;
import tnt.util.Observer;

public class FieldView extends HBox implements Observer {

    private Field field;

    public FieldView(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    @Override
    public void update() {

    }
}
