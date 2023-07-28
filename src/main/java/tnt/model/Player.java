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
    private int levelOneTile;
    private int levelTwoTile;
    private int levelThreeTile;
    private int levelFourTile;
    private int levelFiveTile;
    private int levelSixTile;

    /**
     * Constructing an object Player.
     * @param levelOfIntelligence Human, easyAI, mediumAI, hardAI
     * @param name initial name
     * @param color initial colour
     * @param figures ArrayList of figures, which belongs to the player
     * @param levelOneTile The amount of tiles
     * @param levelTwoTile The amount of tiles
     * @param levelThreeTile The amount of tiles
     * @param levelFourTile The amount of tiles
     * @param levelFiveTile The amount of tiles
     * @param levelSixTile The amount of tiles
     */
    public Player(PlayerType levelOfIntelligence, String name, Color color, ArrayList<Figure> figures, int amountOfTurns, int levelOneTile, int levelTwoTile, int levelThreeTile, int levelFourTile, int levelFiveTile, int levelSixTile) {
        this.levelOfIntelligence = levelOfIntelligence;
        this.name = name;
        this.color = color;
        this.figures = figures;
        this.amountOfTurns = amountOfTurns;
        this.levelOneTile = levelOneTile;
        this.levelTwoTile = levelTwoTile;
        this.levelThreeTile = levelThreeTile;
        this.levelFourTile = levelFourTile;
        this.levelFiveTile = levelFiveTile;
        this.levelSixTile = levelSixTile;
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
     * @return levelOneTile
     */
    public int getLevelOneTile() {
        return levelOneTile;
    }

    /**
     * @param levelOneTile replaces old playerOrder
     */
    public void setLevelOneTile(int levelOneTile) {
        this.levelOneTile = levelOneTile;
    }

    /**
     * @return levelTwoTile
     */
    public int getLevelTwoTile() {
        return levelTwoTile;
    }

    /**
     * @param levelTwoTile replaces old playerOrder
     */
    public void setLevelTwoTile(int levelTwoTile) {
        this.levelTwoTile = levelTwoTile;
    }

    /**
     * @return levelThreeTile
     */
    public int getLevelThreeTile() {
        return levelThreeTile;
    }

    /**
     * @param levelThreeTile replaces old playerOrder
     */
    public void setLevelThreeTile(int levelThreeTile) {
        this.levelThreeTile = levelThreeTile;
    }

    /**
     * @return levelFourTile
     */
    public int getLevelFourTile() {
        return levelFourTile;
    }

    /**
     * @param levelFourTile levelFourTile
     */
    public void setLevelFourTile(int levelFourTile) {
        this.levelFourTile = levelFourTile;
    }

    /**
     * @param levelFiveTile levelFiveTile
     */
    public void setLevelFiveTile(int levelFiveTile) {
        this.levelFiveTile = levelFiveTile;
    }

    /**
     * @return levelFiveTile
     */
    public int getLevelFiveTile() {
        return levelFiveTile;
    }

    /**
     * @return levelSixTile
     */
    public int getLevelSixTile() {
        return levelSixTile;
    }

    /**
     * @param levelSixTile levelSixTile
     */
    public void setLevelSixTile(int levelSixTile) {
        this.levelSixTile = levelSixTile;
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
            Figure newFigure = new Figure();
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
        // Remove Tile from game
        int tile = field.getTowerLevel();
        if(tile == 0){
            levelOneTile--;
        }
        else if(tile == 1){
            levelTwoTile--;
        }
        else if(tile == 2){
            levelThreeTile--;
        }
        else{
            levelFourTile--;
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