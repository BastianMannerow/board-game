package tnt.gui.game;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import tnt.ResourceHandler;
import tnt.model.Game;
import tnt.util.Observer;

import java.io.IOException;


public class GameView extends BorderPane implements Observer {

//    private Button btnBack = new Button("Back!");
//
//    private Button btnCounterUp = new Button("+");
//    private Button btnCounterDown = new Button("-");
//    private Label lblCounter = new Label();
    private Game game;

    public GameView(Game game) throws IOException {
//        setAlignment(Pos.CENTER);
//        setSpacing(10);
//        getChildren().addAll(btnCounterUp, lblCounter, btnCounterDown, btnBack);

//        FXMLLoader gameLoader = ResourceHandler.getInstance().getFXML("game");
//        gameLoader.setRoot(this);
////        BorderPane gameView =
//        gameLoader.load();
//        System.out.println(gameLoader.getController().toString());
//        game.addObserver(this);
        update();
    }

//    public GameView(Game g) {
//        getChildren().addAll(btnCounterUp, lblCounter, btnCounterDown, btnBack);
//        setGame(g);
//    }

//    public Button getBtnCounterDown() {
//        return btnCounterDown;
//    }
//
//    public void setBtnCounterDown(Button btnCounterDown) {
//        this.btnCounterDown = btnCounterDown;
//    }
//
//    public Button getBtnCounterUp() {
//        return btnCounterUp;
//    }
//
//    public void setBtnCounterUp(Button btnCounterUp) {
//        this.btnCounterUp = btnCounterUp;
//    }

//    public void setGame(Game game) {
//        this.game = game;
//        game.addObserver(this);
//        update();
//    }

    @Override
    public void update() {
//        if (game.getPlayersTurn() == null) {
//
//            lblCounter.setText("No Players available");
//            return;
//        }
//        lblCounter.setText("Counter: " + game.getPlayersTurn());
    }

//    public Game getGame() {
//        return game;
//    }

//    public Button getBtnBack() {
//        return btnBack;
//    }


}
