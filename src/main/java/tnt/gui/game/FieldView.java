package tnt.gui.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tnt.ResourceHandler;
import tnt.model.Field;
import tnt.util.Observer;

import java.io.IOException;

public class FieldView extends HBox implements Observer {

    private Field field;

    private FigureView figureView;

    public FieldView(Field field) throws IOException {
        FXMLLoader fieldLayout = ResourceHandler.getInstance().getFXML("field");
        fieldLayout.setRoot(this);
        fieldLayout.load();
        this.field = field;
        field.addObserver(this);
        update();
    }

    public Field getField() {
        return field;
    }

    @Override
    public void update() {
        ((Label)((VBox) this.getChildren().get(1)).getChildren().get(1)).setText(" " + field.getTowerLevel());
        if (field.getIsFigureHere()){
            ((Label)((VBox)this.getChildren().get(1)).getChildren().get(0)).setText(" Player here ");
            if (field.getIsFigureHere()) {
                if (!this.getChildren().contains(GameView.getFigureView(field.getFigure()))) {
                    this.getChildren().add(GameView.getFigureView(field.getFigure()));
                }
            } else {
                for (Node node: this.getChildren()){
                    if (node instanceof FigureView){
                        this.getChildren().remove(node);
                    }
                }
            }

        } else {
            ((Label)((VBox)this.getChildren().get(1)).getChildren().get(0)).setText("");
        }
    }
}
