package tnt.gui;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tnt.model.Game;
import tnt.util.Observer;


public class View extends VBox implements Observer {
    private Button btnCounterUp = new Button("+");
    private Button btnCounterDown = new Button("-");
    private Label lblCounter = new Label();
    private Game game;

    public View() {
//        setAlignment(Pos.CENTER);
//        setSpacing(10);
        getChildren().addAll(btnCounterUp, lblCounter, btnCounterDown);
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

    public void setModel(Game game) {
        this.game = game;
        game.addObserver(this);
        update();
    }

    @Override
    public void update() {
        lblCounter.setText("Counter: " + game.getPlayersTurn());
    }

    public Game getModel() {
        return game;
    }

}
