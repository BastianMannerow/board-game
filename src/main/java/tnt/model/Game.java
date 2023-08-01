package tnt.model;
import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.paint.Color;
import tnt.gui.SizeHandler;
import tnt.util.Observable;

/**
 * The Game class, which is responsible for general mechanics during the Game.
 */
public class Game extends Observable {

    public enum GameStatus {
        SELECT_PLAYER,
        PLACE_FIGURES,
        MOVE_FIGURE,
        BUILD,
        GAME_OVER
    }

    private Color[] def_colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.BLACK, Color.PINK};
    private ArrayList<Player> playerOrder;
    private Board board;
    private int amountOfTurns;
    private int maxStepUpHeight;
    private int maxStepDownHeight;
    private String gameName;
    private Figure lastMovedFigure;
    private GameStatus gameStatus;
    private int victoryHeight;
    private int[] numberOfTile = {};

    private boolean globalTilePool = true;

    /**
     * Constructing an object Game.
     * @param playerOrder
     * @param amountOfTurns How many turns are completed so far (at beginning 0). It's for the highscore
     */

    public Game(ArrayList<Player> playerOrder, int amountOfTurns, String gameName, int maxStepUpHeight, int maxStepDownHeight, int victoryHeight, boolean globalTilePool) {
        this.playerOrder = playerOrder;
        this.amountOfTurns = amountOfTurns;
        this.gameName = gameName;
        createBoard(1,1);
        this.maxStepUpHeight = maxStepUpHeight;
        this.maxStepDownHeight = maxStepDownHeight;
        this.victoryHeight = victoryHeight;
        this.globalTilePool = globalTilePool;
    }

    /**
     * Constructor for the game
     * @param defaultAmountPlayer the number of player, the game should get initialized with
     */
    public Game(int defaultAmountPlayer) {
        this.gameStatus = GameStatus.SELECT_PLAYER;
        this.playerOrder = new ArrayList<Player>();
        int amountFigures = 2;
        if (defaultAmountPlayer > 3) {
            amountFigures = 1;
        }
        this.maxStepUpHeight = Settings.getMaxStepUp();
        this.maxStepDownHeight = Settings.getMaxStepDown();
        this.victoryHeight = Settings.getVictoryHeight();
        createBoard(Settings.getFieldSizeX(), Settings.getFieldSizeY());
        numberOfTile = new int[victoryHeight + 1];
        for (int i = 0; i < numberOfTile.length; i++){
            numberOfTile[i] = Settings.getNrOfTile(i);
        }
        for (int i = 0; i < defaultAmountPlayer; i++) {
            addPlayer(amountFigures, String.valueOf((i % (1 + amountFigures)) + 1), amountOfTurns);
        }
    }
    /**
     * getter for the number of tiles this game should have
     * @param numberOfTile the amount of tiles
     */
    public void setNumberOfTile(int[] numberOfTile) {
        this.numberOfTile = numberOfTile;
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
     * Sets the Number of tiles
     * @param i ,the type of tile
     * @param nr ,the amount of tiles
     */
    public void setNrTile(int i, int nr) {
        if (i<0 || i >= numberOfTile.length){
            return;
        }
        numberOfTile[i] = nr;
    }

    /**
     * Getter for the size of tiles
     * @return the size
     */
    public int getTileSize() {
        return numberOfTile.length;
    }

    /**
     * Getter for the victory height
     * @return the height for the victory
     */
    public int getVictoryHeight() {
        return victoryHeight;
    }

    /**
     * Setter for the victory height
     * @param victoryHeight the victory height to be set
     */
    public void setVictoryHeight(int victoryHeight) {
        int[] new_tiles = new int[victoryHeight];
        for (int i = 0; i < new_tiles.length; i++){
            if (i >= this.numberOfTile.length){
                    new_tiles[i] = Settings.getNrOfTile(i);
                } else {
                    new_tiles[i] = this.numberOfTile[i];
                }
        }
        this.victoryHeight = victoryHeight;
        for (Player player: playerOrder){
            if (globalTilePool){
                player.setNumberOfTile(numberOfTile);
            } else {
                player.setNumberOfTile(numberOfTile.clone());
            }
        }
        notifyObservers();
    }

    /**
     * @return Maximum height to step up
     */
    public int getMaxStepUpHeight() {
        return maxStepUpHeight;
    }

    /**
     * @return Maximum height to step down
     */
    public int getMaxStepDownHeight() {
        return maxStepDownHeight;
    }

    /**
     * Setter for the possible step height up
     * @param maxStepUpHeight the maximum height to step up
     */
    public void setMaxStepUpHeight(int maxStepUpHeight) {
        this.maxStepUpHeight = maxStepUpHeight;
    }

    /**
     * Setter for the possible step height down
     * @param maxStepDownHeight the maximum height to step down
     */
    public void setMaxStepDownHeight(int maxStepDownHeight) {
        this.maxStepDownHeight = maxStepDownHeight;
    }

    /**
     * @return gameName
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * @return GameStatus
     */
    public void setGameStatus(GameStatus placeFigures) {
    }

    /**
     * @param gameName replaces old playerOrder
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * initialize the game
     */
    public void initGame() {
        for(Player player: playerOrder){
            player.initPlayer();
        }
    }

    /**
     * @return playerOrder
     */
    public ArrayList<Player> getPlayerOrder() {
        return playerOrder;
    }

    /**
     * @return amountOfTurns
     */
    public int getAmountOfTurns() {
        return amountOfTurns;
    }

    /**
     * @param amountOfTurns The amount of turns the game has already taken
     */
    public void setAmountOfTurns(int amountOfTurns) {
        this.amountOfTurns = amountOfTurns;
    }

    /**
     * @param playerOrder replaces old playerOrder
     */
    public void setPlayerOrder(ArrayList<Player> playerOrder) {
        this.playerOrder = playerOrder;
    }

    /**
     * Creates Board and containing Field objects.
     *
     * @param boardX the Width of the board
     * @param boardY the Height of the board
     */
    public void createBoard(int boardX, int boardY) {
        Field[][] fields = new Field[boardX][boardY];
        for (int i = 0; i < boardX; i++) {
            for (int j = 0; j < boardY; j++) {
                Field field = new Field(i, j);
                fields[i][j] = field;
            }
        }
        SizeHandler.setNrFieldsX(boardX);
        SizeHandler.setNrFieldsY(boardY);
        Board board = new Board(fields, boardX, boardY);
        this.board = board;
        notifyObservers();
    }

    /**
     * Checks if the player is unable to move.
     *
     * @param player the player object the method is checking on
     */
    public void checkBlockedMovement(Player player){
        int blockedMovement = 0;

        for (Figure figure: player.getFigure()){
            ArrayList<Field> possibleMovement = figure.getValidMoves(board);
            if(possibleMovement.isEmpty()){
                blockedMovement++;
            }
        }
        if (blockedMovement == player.getFigure().size()){
            setGameOverMode();
        }
    }

    /**
     * Checks if the player is unable to build.
     *
     * @param player the player object the method is checking on
     */
    public void checkBlockedBuilding(Player player){
        if (this.lastMovedFigure.getValidBuilds(this.board).isEmpty()){
            setGameOverMode();
        }
    }

    public Player getPlayersTurn(){
        if (playerOrder.isEmpty()){
            return null;
        }
        return playerOrder.get(0);
    }

    /**
     * Checks if the game is in run mode
     */
    public boolean isRunnung(){
        return gameStatus == GameStatus.BUILD || gameStatus == GameStatus.MOVE_FIGURE;
    }

    /**
     * Checks if the actual player has to move
     */
    public boolean isMoveMode(){
        return gameStatus == GameStatus.MOVE_FIGURE;
    }

    /**
     * Set the gamemode to build, so the actual player has to build
     */
    public void setBuildMode() {
        gameStatus = GameStatus.BUILD;
        notifyObservers();
    }

    /**
     * Set the gamemode to move, so the player (should be the next one) has to move
     */
    public void setMoveMode() {
        gameStatus = GameStatus.MOVE_FIGURE;
        notifyObservers();
    }

    /**
     * Set the gamemode to gameover
     */
    public void setGameOverMode() {
        gameStatus = GameStatus.GAME_OVER;
        notifyObservers();
    }
    /**
     * Checks if the game player has to build
     */
    public boolean isBuildMode(){
        return gameStatus == GameStatus.BUILD;
    }

    /**
     * Checks if the game is in a mode, where the number of players etc gets set
     */
    public boolean selectingPlayers(){
        return gameStatus == GameStatus.SELECT_PLAYER;
    }

    /**
     * Sets the gamemode in a mode, where the player can choose their positions
     */
    public void startPlaceFigures(){
        gameStatus = GameStatus.PLACE_FIGURES;
    }

    /**
     * Sets the gamemode to move, so the actual player has to move a figure
     */
    public void startGame(){
        gameStatus = GameStatus.MOVE_FIGURE;
        notifyObservers();
    }

    /**
     * Adds a player to the game
     * @param amountOfFigures the number of figures this player should have
     */
    public void addPlayer(int amountOfFigures, String team, int amountOfTurns) {
        if (selectingPlayers()) {

            Player newPlayer = new Player(Player.PlayerType.HUMAN, "" + (playerOrder.size() + 1), def_colors[playerOrder.size() % def_colors.length], amountOfFigures, this, team, amountOfTurns);
            playerOrder.add(newPlayer);
            if (globalTilePool){
                newPlayer.setNumberOfTile(numberOfTile);
            } else {
                newPlayer.setNumberOfTile(numberOfTile.clone());
            }
            notifyObservers();
        }
    }

    /**
     * Removes the player of the game
     * @param player the player to remove
     */
    public void removePlayer(Player player) {
        if (selectingPlayers()) {
            playerOrder.remove(player);
            notifyObservers();
        }
        playerOrder.remove(player);
        notifyObservers();
    }

    /**
     * Getter for the board
     * @return the board of the game
     */
    public Board getBoard(){
        return this.board;
    }

    /**
     * Checks if the player should place their figures
     */
    public boolean placeFigures(){
        return gameStatus == GameStatus.PLACE_FIGURES;
    };

    /**
     * Getter for the Gamestatus
     * @return the gamestatus of the game
     */
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    /**
     * Change the player, who's turn is
     */
    public void nextPlayersTurn(){
        Collections.rotate(playerOrder, -1);
        notifyObservers();
    }

    /**
     * Sets the last moved figure (important for the building possibility)
     * @param figure the figure moved latest
     */
    public void setLastMovedFigure(Figure figure) {
        lastMovedFigure = figure;
    }

    /**
     * Get the latest moved figure
     * @return the latest moved figure
     */
    public Figure getLastMovedFigure() {
        return lastMovedFigure;
    }

    /**
     * Returns the bool globalTilePool
     * @return globalTilePool ,the checkmark of activation with 1 beeing yes and 0 no
     */
    public boolean isGlobalTilePool() {
        return globalTilePool;
    }

    /**
     * Setter for the globalTilePool bool
     * @param globalTilePool ,the to be setted Value
     */
    public void setGlobalTilePool(boolean globalTilePool) {
        this.globalTilePool = globalTilePool;
    }
}