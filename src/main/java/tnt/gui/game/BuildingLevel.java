package tnt.gui.game;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import tnt.ResourceHandler;
import tnt.gui.SizeHandler;
import tnt.util.Observer;

import java.io.IOException;


/**
 * The view for all building levels
 */

public class BuildingLevel extends DragableObject implements Observer {

    private int level;
    private static final int MAX_BUILD_IN_LEVEL = 3;

    /**
     * Constructor for the building level
     * @param level the level of the building
     * @throws IOException Exception when the given level does not exist
     */
    public BuildingLevel(int level) throws IOException {
//        FXMLLoader loader;
        ImageView imageView = new ImageView();
        switch (level){
            case -1:
                imageView.setImage(ResourceHandler.getInstance().getImage("Turm_Kuppel"));
                break;
            case 1:
                imageView.setImage(ResourceHandler.getInstance().getImage("Turm_1"));
                break;
            case 2:
                imageView.setImage(ResourceHandler.getInstance().getImage("Turm_2"));
                break;
            case 3:
                imageView.setImage(ResourceHandler.getInstance().getImage("Turm_3"));
                break;
            default:
                if (level < MAX_BUILD_IN_LEVEL) {
                    throw new IOException("Building got wrong level: " + level);
                }
                imageView.setImage(ResourceHandler.getInstance().getImage("Turm_3"));
        }
        this.level = level;
        imageView.setPreserveRatio(true);
        SizeHandler.getInstance().addObserver(this);
        imageView.setFitHeight(SizeHandler.getPrefSize());
        this.getChildren().add(imageView);
        if(level > 1){
            Label height = new Label(String.valueOf(level));
            height.setTextFill(Color.WHITE);
            height.setAlignment(Pos.CENTER);
            this.getChildren().add(height);
        }
        update();
    }

    @Override
    public DragableObject copy() throws IOException {
        return new BuildingLevel(level);
    }

    /**
     * getter for the level
     * @return the level
     */
    public int getLevel() {
        return this.level;
    }

    @Override
    public void update() {
        ((ImageView) this.getChildren().get(0)).setFitHeight(SizeHandler.getPrefSize());
        if(level > MAX_BUILD_IN_LEVEL){
            Label height = (Label) this.getChildren().get(1);
            height.setPrefSize(SizeHandler.getPrefSize(),SizeHandler.getPrefSize());
            height.setStyle("-fx-font: " + SizeHandler.getPrefSize()/2 + " arial; -fx-font-weight: bold");
        }
    }
}
