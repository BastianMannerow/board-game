package tnt.model;
import java.util.ArrayList;

import javafx.scene.paint.Color;

import tnt.model.interfaces.Gods;
import tnt.model.gods.building.*;
import tnt.model.gods.inventory.Chaos;
import tnt.model.gods.inventory.Circe;
import tnt.model.gods.movement.*;
import tnt.model.gods.sabotage.*;
import tnt.model.gods.victory.Chronus;
import tnt.model.gods.victory.Eros;
import tnt.model.gods.victory.Hera;
import tnt.model.gods.victory.Pan;
import tnt.util.Observable;

/**
 * A player with his attributes.
 */
public class Player extends Observable {
    private String levelOfIntelligence;
    private Game game;
    private String name;
    private Color color;
    private int amountOfFigures;
    private ArrayList<Figure> figures = new ArrayList<>();
    private ArrayList<Gods> gods;

    /**
     * Constructing an object Player.
     * @param levelOfIntelligence Human, easyAI, mediumAI, hardAI
     * @param name initial name
     * @param color initial colour
     * @param figures ArrayList of figures, which belongs to the player
     * @param gods the cards, which affect the players abilities
     */
    public Player(String levelOfIntelligence, String name, Color color, ArrayList<Figure> figures, ArrayList<Gods> gods) {
        this.levelOfIntelligence = levelOfIntelligence;
        this.name = name;
        this.color = color;
        this.figures = figures;
        this.gods = gods;
    }

    public Player(String levelOfIntelligence, String name, Color color, int amountOfFigures, Game game) {
        this.levelOfIntelligence = levelOfIntelligence;
        this.name = name;
        this.color = color;
        this.amountOfFigures = amountOfFigures;
        this.game = game;
    }

    /**
     * @return name of the player
     */
    public String getLevelOfIntelligence() {
        return levelOfIntelligence;
    }

    /**
     * @return name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * @param name new name of the player
     */
    public void setName(String name) {
        this.name = name;
        notifyObservers();
    }

    /**
     * @return colour of the player
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color new colour of the player
     */
    public void setColor(Color color) {
        this.color = color;
        notifyObservers();
    }

    /**
     * @return ArrayList of Figure objects, which belongs to the player
     */
    public ArrayList<Figure> getFigure() {
        return figures;
    }

    /**
     * Adds a god into the ArrayList gods
     *
     * @param god is a instance of a class implementing interface Gods
     */
    public void addGod(Gods god){
        gods.add(god);
    }

    /**
     * Adds all gods into the ArrayList gods
     *
     */
    public void addAllGods(){
        addGod(new Ares());
        addGod(new Atlas());
        addGod(new Demeter());
        addGod(new Dyonisus());
        addGod(new Hephaistos());
        addGod(new Hestia());
        addGod(new Medusa());
        addGod(new Morpheus());
        addGod(new Poseidon());
        addGod(new Prometheus());
        addGod(new Selene());
        addGod(new Zeus());

        addGod(new Chaos());
        addGod(new Circe());
        addGod(new Apollo());
        addGod(new Artemis());
        addGod(new Charon());
        addGod(new Hermes());
        addGod(new Minotaures());
        addGod(new Triton());

        addGod(new Aphrodite());
        addGod(new Athena());
        addGod(new Hypnus());
        addGod(new Limus());
        addGod(new Persephone());

        addGod(new Chronus());
        addGod(new Eros());
        addGod(new Hera());
        addGod(new Pan());
    }
    /**
     * Depending on the god type, the specific figure object will be created.
     *
     * @param amount creates new Figure objects for the player
     */
    public void addFigure(int amount) {
        for (int i = 0; i < amount; i++) {
            Figure newFigure = new Figure();
            this.figures.add(newFigure);
        }
    }
    public void addFigure(int x, int y) {
        Figure newFigure = new Figure(x, y, game);
        this.figures.add(newFigure);
        game.notifyObservers();
    }

    /**
     * @return card god/demon card, which belongs to the player
     */
    public ArrayList<Gods> getGods() {
        return gods;
    }

    /**
     * Increases the height of a field
     *
     * @param field the field chosen by the player
     */
    public void executeBuild(Field field){
        int newLevel = field.getTowerLevel()+1;
        field.setTowerLevel(newLevel);
        if(newLevel == 4){
            field.setTowerComplete(true);
        }
        // Remove Tile from game
        int tile = field.getTowerLevel();
        if(tile == 0){
            game.setLevelOneTile(game.getLevelOneTile() - 1);
        }
        else if(tile == 1){
            game.setLevelTwoTile(game.getLevelTwoTile() - 1);
        }
        else if(tile == 2){
            game.setLevelThreeTile(game.getLevelThreeTile() - 1);
        }
        else{
            game.setLevelFourTile(game.getLevelFourTile() - 1);
        }
    }

    /**
     * Moves the figure on the board
     *
     * @param field the field chosen by the player
     * @param board the board which is played on
     */
    public void executeMove(Field field, Board board, Figure figure){
        board.getField(figure.getX(), figure.getY()).figureLeft();
        figure.setX(field.getX());
        figure.setY(field.getY());
        field.setFigure(figure);

        // Check if game is already over
        if(field.getTowerLevel() == 3){
            game.setGameOverMode();
        }
    }

    public void initPlayer() {
        addFigure(amountOfFigures);
    }

    public boolean allFiguresPlaced() {
        for (Figure fig: figures) {
            if (!fig.isPlaced()){
                return false;
            }
        }
        return true;
    }

    public void setAmountOfFigures(int i){
        if (game.selectingPlayers()) {
            this.amountOfFigures = i;
        }
        notifyObservers();
    }
}