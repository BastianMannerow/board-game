package tnt.gui.playerchoosemenu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tnt.ResourceHandler;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

public class PlayerChooseView extends VBox {

    private ArrayList<HBox> playerHolder = new ArrayList<HBox>();
    private Color[] def_colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.BLACK, Color.PINK};
    public PlayerChooseView(PlayerChooseController playerChooseController) throws IOException {
        FXMLLoader choosePlayerMenu = ResourceHandler.getInstance().getFXML("choosePlayerMenu");
        choosePlayerMenu.setRoot(this);
        VBox playerChooseMenu = choosePlayerMenu.load();
        ((ScrollPane) this.getChildren().get(0)).setFitToHeight(true);
        ((ScrollPane) this.getChildren().get(0)).setFitToWidth(true);
        ((VBox)((ScrollPane) this.getChildren().get(0)).getContent()).setSpacing(20);
        ((VBox)((ScrollPane) this.getChildren().get(0)).getContent()).setPadding(new Insets(20,0,5,0));
        for(int i = 0; i< 4; i++){
            HBox hbox1 = new HBox();
            playerHolder.add(hbox1);
            VBox playerBox = (VBox) ((ScrollPane) this.getChildren().get(0)).getContent();
            playerBox.getChildren().add(hbox1);
            FXMLLoader playerLayout1 = ResourceHandler.getInstance().getFXML("choosePlayer");
            playerLayout1.setRoot(hbox1);
            playerLayout1.load();
            ((Label) hbox1.getChildren().get(0)).setText("Player " + (i+1));
            ((TextField)((VBox) hbox1.getChildren().get(1)).getChildren().get(1)).setPromptText("Player " + (i+1));
            ((ColorPicker) ((VBox) hbox1.getChildren().get(3)).getChildren().get(1)).setValue(def_colors[i% def_colors.length]);
        }



    }
}
