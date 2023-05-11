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
import java.util.Collection;

public class PlayerChooseView extends VBox {

    private HBox hbox1 = new HBox();
    private HBox hbox2 = new HBox();

    public PlayerChooseView(PlayerChooseController playerChooseController) throws IOException {
        FXMLLoader choosePlayerMenu = ResourceHandler.getInstance().getFXML("choosePlayerMenu");
        choosePlayerMenu.setRoot(this);
        VBox playerChooseLayout = choosePlayerMenu.load();
        ((VBox) this.getChildren().get(0)).getChildren().add(hbox1);
        ((VBox) this.getChildren().get(0)).getChildren().add(hbox2);
        FXMLLoader playerLayout1 = ResourceHandler.getInstance().getFXML("choosePlayer");
        playerLayout1.setRoot(hbox1);
        playerLayout1.load();
        FXMLLoader playerLayout2 = ResourceHandler.getInstance().getFXML("choosePlayer");
        playerLayout1.setRoot(hbox2);
        playerLayout1.load();



    }
}
