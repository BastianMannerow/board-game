package tnt.gui.playerchoosemenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import tnt.gui.game.GameView;

public class PlayerChooseController {

//    @FXML
//    private void initialize(){
//
//    }
    @FXML
    private void handlePlayButton(){
        System.out.println("teszete");
    }
    @FXML
    private void handleBtnCounterUpClick() {
        System.out.println("A");
    }

    @FXML
    private void handleBtnCounterDownClick() {
        System.out.println("B");
    }
}
