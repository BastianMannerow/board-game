package tnt.gui.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import tnt.ResourceHandler;
import tnt.gui.SizeHandler;
import tnt.gui.StaticSizeHandler;
import tnt.util.Observer;

import java.io.IOException;


//public class BuildingLevel extends DragableObject {
public class BuildingLevel extends DragableObject implements Observer {

    private int level;
    public BuildingLevel(int level) throws IOException {
//        FXMLLoader loader;
        ImageView imageView = new ImageView();
        switch (level){
            case -1:
                imageView.setImage(ResourceHandler.getInstance().getImage("Turm_Kuppel"));
                break;
            case 1:
//                loader = ResourceHandler.getInstance().getFXML("buildingLevel1View");
                imageView.setImage(ResourceHandler.getInstance().getImage("Turm_1"));
                break;
            case 2:
                imageView.setImage(ResourceHandler.getInstance().getImage("Turm_2"));
                break;
            case 3:
                imageView.setImage(ResourceHandler.getInstance().getImage("Turm_3"));
                break;
            default:
                throw new IOException("Building got wrong level: " + level);
        }
        this.level = level;
        imageView.setPreserveRatio(true);
        StaticSizeHandler.getInstance().addObserver(this);
        imageView.setFitHeight(StaticSizeHandler.getPrefSize());
        this.getChildren().add(imageView);
//        loader.setRoot(this);
//        loader.load();
    }

    @Override
    public DragableObject copy() throws IOException {
        return new BuildingLevel(level);
    }

    public int getLevel() {
        return this.level;
    }

    @Override
    public void update() {
        ((ImageView) this.getChildren().get(0)).setFitHeight(StaticSizeHandler.getPrefSize());
    }
}
