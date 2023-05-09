package tnt.gui.game;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tnt.model.Game;
import tnt.util.Observer;


public class GameView extends VBox implements Observer {

    private Button btnBack = new Button("Back!");

    private Button btnCounterUp = new Button("+");
    private Button btnCounterDown = new Button("-");
    private Label lblCounter = new Label();
    private Game game;

    public GameView() {
//        setAlignment(Pos.CENTER);
//        setSpacing(10);
        getChildren().addAll(btnCounterUp, lblCounter, btnCounterDown, btnBack);
    }

    public Button getBtnCounterDown() {
        return btnCounterDown;
    }

    public void setBtnCounterDown(Button btnCounterDown) {
        this.btnCounterDown = btnCounterDown;
    }

    public Button getBtnCounterUp() {
        return btnCounterUp;
    }

    public void setBtnCounterUp(Button btnCounterUp) {
        this.btnCounterUp = btnCounterUp;
    }

    public void setGame(Game game) {
        this.game = game;
        game.addObserver(this);
        update();
    }

    @Override
    public void update() {
        if (game.getPlayersTurn() == null) {

            lblCounter.setText("No Players available");
            return;
        }
        lblCounter.setText("Counter: " + game.getPlayersTurn());
    }

    public Game getGame() {
        return game;
    }

    public Button getBtnBack() {
        return btnBack;
    }


}
