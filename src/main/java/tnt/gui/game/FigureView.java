package tnt.gui.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.shape.Polygon;
import tnt.ResourceHandler;
import tnt.model.Figure;
import tnt.model.Player;
import tnt.util.Observer;

import java.io.IOException;


public class FigureView extends DragableObject implements Observer{

    private Player player;

    private Figure figure;

    public FigureView(Player player) throws IOException {
        this.player = player;
        FXMLLoader loader = ResourceHandler.getInstance().getFXML("figureView");
        loader.setRoot(this);
        loader.load();
        ((Polygon) this.getChildren().get(0)).setFill(player.getColor());
    }

    public FigureView(Player player, Figure figure) throws IOException {
        this.player = player;
        this.figure = figure;
        FXMLLoader loader = ResourceHandler.getInstance().getFXML("figureView");
        loader.setRoot(this);
        loader.load();
        ((Polygon) this.getChildren().get(0)).setFill(player.getColor());
    }

    public void setPlayer(Player player) {
        this.player = player;
        ((Polygon) this.getChildren().get(0)).setFill(player.getColor());
    }

    public Player getPlayer() {
        return player;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
        figure.addObserver(this);
    }

    public Figure getFigure() {
        return figure;
    }

    @Override
    public void update() {
        // Todo: check figure for updates
    }

    public FigureView copy() throws IOException
    {
        return new FigureView(this.player, this.figure);
    }
}
