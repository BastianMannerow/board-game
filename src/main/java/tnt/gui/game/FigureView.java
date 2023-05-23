package tnt.gui.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import tnt.ResourceHandler;
import tnt.model.Figure;
import tnt.model.Player;

import java.io.IOException;


public class FigureView extends Polygon {

    private Player player;


    private Figure figure;

    public FigureView(Player player) throws IOException {
        this.player = player;
        FXMLLoader choosePlayerMenu = ResourceHandler.getInstance().getFXML("figureView");
        choosePlayerMenu.setRoot(this);
        choosePlayerMenu.load();
        this.setFill(player.getColor());
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.setFill(player.getColor());
    }

    public Player getPlayer() {
        return player;
    }
    public void setFigure(Figure figure) {
        this.figure = figure;
    }
    public Figure getFigure() {
        return figure;
    }
}
