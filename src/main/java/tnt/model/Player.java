package tnt.model;
import java.util.ArrayList;

import javafx.scene.paint.Color;
import tnt.util.Observable;

/**
 * A player with his attributes.
 */
public class Player extends Observable {

    public enum PlayerType {
        HUMAN,
//        REMOTE,
        AI_1,
        AI_2,
        AI_3,
    }
    private PlayerType levelOfIntelligence;
    private Game game;
    private String name;
    private Color color;
    private int amountOfFigures;
    private int amountOfTurns;
    private ArrayList<Figure> figures = new ArrayList<>();
    private String team;
    private int[] numberOfTile;

    /**
     * Constructing an object Player.
     * @param levelOfIntelligence Human, easyAI, mediumAI, hardAI
     * @param name initial name
     * @param color initial colour
     * @param figures ArrayList of figures, which belongs to the player
     */
    public Player(PlayerType levelOfIntelligence, String name, Color color, ArrayList<Figure> figures, int amountOfTurns) {
        this.levelOfIntelligence = levelOfIntelligence;
        this.name = name;
        this.color = color;
        this.figures = figures;
        this.amountOfTurns = amountOfTurns;
    }

    public Player(PlayerType levelOfIntelligence, String name, Color color, int amountOfFigures, Game game, String team, int amountOfTurns) {
        this.levelOfIntelligence = levelOfIntelligence;
        this.name = name;
        this.color = color;
        this.amountOfFigures = amountOfFigures;
        this.game = game;
        this.team = team;
        this.amountOfTurns = amountOfTurns;
    }

    /**
     * Constructor for a player
     */
    public Player(Game game) {
        this.game = game;
    }

    /**
     * Getter for the players tiles
     * @return the tiles of the player
     */
    public int getNrTile(int i) {
        if (i<0 || i >= numberOfTile.length){
            return 0;
        }
        return numberOfTile[i];
    }

    /**
     * Getter for the size of tiles
     * @return the size
     */
    public int getTileSize() {
        return numberOfTile.length;
    }

    /**
     * Getter for the player type
     * @return the type of the player
     */
    public PlayerType getLevelOfIntelligence() {
        return levelOfIntelligence;
    }

    /**
     * Setter for the player type
     * @param levelOfIntelligence the player type to be set
     */
    public void setLevelOfIntelligence(PlayerType levelOfIntelligence) {
        this.levelOfIntelligence = levelOfIntelligence;
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
     * @return team of the player
     */
    public String getTeam() {
        return team;
    }

    /**
     * @param team
     */
    public void setTeam(String team) {
        this.team = team;
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
     * Depending on the god type, the specific figure object will be created.
     *
     * @param amount creates new Figure objects for the player
     */
    public void addFigure(int amount) {
        for (int i = 0; i < amount; i++) {
            Figure newFigure = new Figure(game, this);
            this.figures.add(newFigure);
        }
    }

    /**
     * @return amountOfTurns the player has played
     */
    public int getAmountOfTurns() {
        return amountOfTurns;
    }

    /**
     * @param amountOfTurns the player has played
     */
    public void setAmountOfTurns(int amountOfTurns) {
        this.amountOfTurns = amountOfTurns;
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
        // Todo: remove a tile
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
        if(field.getTowerLevel() == game.getVictoryHeight()){
            game.setGameOverMode();
        }
    }

    /**
     * initialize the player
     */
    public void initPlayer() {
        addFigure(amountOfFigures);
    }

    /**
     * checks if all players are placed on the board
     */
    public boolean allFiguresPlaced() {
        for (Figure fig: figures) {
            if (!fig.isPlaced()){
                return false;
            }
        }
        return true;
    }

    /**
     * sets the number of figures of the player
     * @param i the number of figures
     */
    public void setAmountOfFigures(int i){
        if (game.selectingPlayers()) {
            this.amountOfFigures = i;
        }
        notifyObservers();
    }

    /**
     * getter for the number of figures this player should have
     * @return the number of figures
     */
    public int getAmountOfFigures() {
        return this.amountOfFigures;
    }

    /**
     * getter for the number of tiles this player should have
     * @param numberOfTile the amount of tiles
     */
    public void setNumberOfTile(int[] numberOfTile) {
        this.numberOfTile = numberOfTile;
    }

//    public int getNrOfTiles(int level) {
//        return nrOfTiles[level];
//    }

    public void removeTile(int buildLevel) {
        numberOfTile[buildLevel] -=1;
    }

    public void prePlayersTurn(){
        switch (levelOfIntelligence){
            case AI_1:
                ArtificialPlayer.easyAI(game.getBoard(), this, game);
                break;
            case AI_2:
                ArtificialPlayer.easyAI(game.getBoard(), this, game);
                break;
            case AI_3:
                ArtificialPlayer.easyAI(game.getBoard(), this, game);
                break;
            default:
                break;
        }
    }
}