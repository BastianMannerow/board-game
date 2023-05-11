package tnt.gui.playerchoosemenu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tnt.ResourceHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class PlayerChooseView extends VBox {

    private ArrayList<HBox> playerHolder = new ArrayList<HBox>();

    public PlayerChooseView(PlayerChooseController playerChooseController) throws IOException {
        FXMLLoader choosePlayerMenu = ResourceHandler.getInstance().getFXML("choosePlayerMenu");
        choosePlayerMenu.setRoot(this);
        VBox playerChooseMenu = choosePlayerMenu.load();
        ((VBox) this.getChildren().get(0)).setSpacing(30);
        for(int i = 0; i< 24; i++){
            HBox hbox1 = new HBox();
            playerHolder.add(hbox1);
            ((VBox) this.getChildren().get(0)).getChildren().add(hbox1);
            FXMLLoader playerLayout1 = ResourceHandler.getInstance().getFXML("choosePlayer");
            playerLayout1.setRoot(hbox1);
            playerLayout1.load();
        }



    }
}
