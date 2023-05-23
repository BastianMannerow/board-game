package tnt.gui.game;

import javafx.scene.layout.HBox;
import tnt.model.Field;
import tnt.model.Figure;

public class FieldView extends HBox {

    private Field field;

    public FieldView(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }
}
