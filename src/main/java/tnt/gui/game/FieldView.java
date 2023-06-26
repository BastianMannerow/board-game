package tnt.gui.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import tnt.ResourceHandler;
import tnt.gui.StaticSizeHandler;
import tnt.model.Field;
import tnt.util.Observer;

import java.io.IOException;
import java.util.HashMap;

/**
 * The view for each field
 */
public class FieldView extends HBox implements Observer {

    private Field field;

    private FigureView figureView;

    private HashMap<Integer, BuildingLevel> buildingHolder = new HashMap<Integer, BuildingLevel>();

    /**
     * Constructor for the vield view
     * @param field the field belonging to the view
     * @throws IOException Exception when the fxml file has an error / does not exist
     */
    public FieldView(Field field) throws IOException {
        FXMLLoader fieldLayout = ResourceHandler.getInstance().getFXML("field");
        fieldLayout.setRoot(this);
        fieldLayout.load();
        this.field = field;
        field.addObserver(this);
        StaticSizeHandler.getInstance().addObserver(this);
        update();
    }

    /**
     * Getter for the field
     * @return the field belonging to the veiw
     */
    public Field getField() {
        return field;
    }

    @Override
    public void update() {
        if (field.getIsFigureHere()){
            if (!((StackPane) this.getChildren().get(0)).getChildren().contains(GameView.getFigureView(field.getFigure()))) {
                ((StackPane) this.getChildren().get(0)).getChildren().add(GameView.getFigureView(field.getFigure()));
            }
        } else {
            ((StackPane) this.getChildren().get(0)).getChildren().removeIf(node -> node instanceof FigureView);
        }

        for (int level = 1; level <= field.getTowerLevel(); level++) {
            if (!buildingHolder.containsKey(level)) {
                BuildingLevel buildingLevel = null;
                try {
                    buildingLevel = new BuildingLevel(level);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                buildingHolder.put(level, buildingLevel);
            }
            if (!((StackPane) this.getChildren().get(0)).getChildren().contains(buildingHolder.get(level))) {
                ((StackPane) this.getChildren().get(0)).getChildren().add(buildingHolder.get(level));
            }

            // Todo: Remove not supported right now
//            ((StackPane) this.getChildren().get(0)).getChildren().removeIf(node -> node instanceof FigureView);
        }
        if (field.getTowerComplete()) {
            if (!buildingHolder.containsKey(-1)) {
                BuildingLevel buildingLevel = null;
                try {
                    buildingLevel = new BuildingLevel(-1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                buildingHolder.put(-1, buildingLevel);
            }
            if (!((StackPane) this.getChildren().get(0)).getChildren().contains(buildingHolder.get(-1))) {
                ((StackPane) this.getChildren().get(0)).getChildren().add(buildingHolder.get(-1));
            }
        }
        this.setPrefSize(StaticSizeHandler.getPrefSize(), StaticSizeHandler.getPrefSize());
        int size =StaticSizeHandler.getPrefSize();
        ((ImageView)((StackPane) this.getChildren().get(0)).getChildren().get(0)).setFitHeight(size);
        ((ImageView)((StackPane) this.getChildren().get(0)).getChildren().get(0)).setFitWidth(size);

    }
}
