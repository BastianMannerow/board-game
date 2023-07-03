package tnt.model;
import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.paint.Color;
import tnt.gui.GUISettings;
import tnt.gui.StaticSizeHandler;
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
    private int levelOneTile;
    private int levelTwoTile;
    private int levelThreeTile;
    private int levelFourTile;

    private String gameName;
    private Figure lastMovedFigure;
    private GameStatus gameStatus;

    /**
     * Constructing an object Game.
     * @param playerOrder
     * @param amountOfTurns How many turns are completed so far (at beginning 0). It's for the highscore
     * @param levelOneTile The amount of tiles
     * @param levelTwoTile The amount of tiles
     * @param levelThreeTile The amount of tiles
     * @param levelFourTile The amount of tiles
     */
    public Game(ArrayList<Player> playerOrder, int amountOfTurns, int levelOneTile, int levelTwoTile, int levelThreeTile, int levelFourTile, String gameName) {
        this.playerOrder = playerOrder;
        this.amountOfTurns = amountOfTurns;
        this.levelOneTile = levelOneTile;
        this.levelTwoTile = levelTwoTile;
        this.levelThreeTile = levelThreeTile;
        this.levelFourTile = levelFourTile;
        this.gameName = gameName;
        createBoard(1,1);
    }
    public Game() {
        gameStatus = GameStatus.SELECT_PLAYER;
        this.playerOrder = new ArrayList<Player>();
        for (int i = 0; i< Settings.getDefaultPlayer(); i++) {
            addPlayer(2);
        }
        createBoard(1,1);
    }

    /**
     * @return gameName
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * @param gameName replaces old playerOrder
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
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
        this.levelOneTile = levelTwoTile;
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
     * @param levelFourTile replaces old playerOrder
     */
    public void setLevelFourTile(int levelFourTile) {
        this.levelFourTile = levelFourTile;
    }

    public Game(int amountOfTurns) {
        this.playerOrder = new ArrayList<Player>();
        this.amountOfTurns = amountOfTurns;
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
     * Creating new player objects and adding them to playerOrder
     *
     * @param playerAmount the amount of new players
     * @param names the names of the new players
     * @param colour the colours of the new players
     * @param figureAmount the amount of figures on the players disposal

    public void createPlayer(ArrayList<String> levelOfIntelligence, int playerAmount, ArrayList<String> names,
                             ArrayList<String> colour, int figureAmount, ArrayList<Gods> gods)                  {
        for (int i = 0; i < playerAmount; i++) {

            Player newPlayer = new Player(levelOfIntelligence.get(i), names.get(i), Color.RED, new ArrayList<Figure>(), gods);

            newPlayer.addFigure(figureAmount, gods.get(i));
            ArrayList<Player> newPlayerOrder = getPlayerOrder();
            newPlayerOrder.add(newPlayer);
            setPlayerOrder(newPlayerOrder);
        }
    }
    */

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
        StaticSizeHandler.setNrFieldsX(boardX);
        StaticSizeHandler.setNrFieldsY(boardY);
        Board board = new Board(fields, boardX, boardY);
        this.board = board;

        // Testprint, kann später entfernt werden.
        for (int i = 0; i < boardX; i++) {
            for (int j = 0; j < boardY; j++) {
                System.out.println(board.getField(i, j));
            }
        }
        notifyObservers();
    }

    /**
     * Checks if the Game is over
     */
    public boolean checkRegularEnding(){
        // Differenzierung 2 oder 4 Spieler einbauen.
        boolean gameEnded = false;
        return gameEnded;
    }

    /**
     * Checks if the game ended, because of a gods ability.
     *
     * @return boardY the Height of the board
     */
//<<<<<<< HEAD
//    public boolean checkSpecialEnding() {
//        boolean gameEnded = false;
//        for (int i = 1; i < playerOrder.size(); i++) {
//            Player player = playerOrder.get(i);
//            ArrayList<Gods> allGods = player.getGods();
//            if(allGods.contains(new Chronus())){
//                if(Chronus.checkSpecialEnding()){
//                    gameEnded = true;
//                }
//            }
//            for (Gods god : allGods) {
//                switch (god.getName()) {
//                    case "Chronus":
//                        if (Chronus.checkSpecialEnding()) {
//                            gameEnded = true;
//                            break;
//                        }
//                    case "Eros":
//                        if (Eros.checkSpecialEnding()) {
//                            gameEnded = true;
//                            break;
//                        }
//                    case "Hera":
//                        if (Hera.checkSpecialEnding()) {
//                            gameEnded = true;
//                            break;
//                        }
//                    case "Pan":
//                        if (Pan.checkSpecialEnding()) {
//                            gameEnded = true;
//                            break;
//                        }
//                    default:
//                        continue;
//                }
//            }
//        }
//        return gameEnded;
//    }
//=======
////    public boolean checkSpecialEnding() {
////        boolean gameEnded = false;
////        for (int i = 1; i < playerOrder.size(); i++) {
////            Player player = playerOrder.get(i);
////            ArrayList<Gods> allGods = player.getGods();
////
////            for (Gods god : allGods) {
////                switch (god) {
////                    case Chronus:
////                        if (Chronus.checkSpecialEnding()) {
////                            gameEnded = true;
////                            break;
////                        }
////                    case Eros:
////                        if (Eros.checkSpecialEnding()) {
////                            gameEnded = true;
////                            break;
////                        }
////                    case Hera:
////                        if (Hera.checkSpecialEnding()) {
////                            gameEnded = true;
////                            break;
////                        }
////                    case Pan:
////                        if (Pan.checkSpecialEnding()) {
////                            gameEnded = true;
////                            break;
////                        }
////                    default:
////                        continue;
////                }
////            }
////        }
////        return gameEnded;
////    }
//>>>>>>> scenebuilder

    /**
     * Sabotage of the players movement abilities by other players gods.
     *
     * @param figure the figure executing the movement
     * @param possibleMovement the possible movement before the sabotage
     *
     * @return the List of fields, which are reachable after sabotage
     */
    public ArrayList<Field> sabotageMovement(Figure figure, ArrayList<Field> possibleMovement){
//<<<<<<< HEAD
//        for (int i = 1; i < playerOrder.size(); i++) {
//            Player passivePlayer = playerOrder.get(i);
//            ArrayList<Gods> passiveGods = passivePlayer.getGods();
//            for(Gods god:passiveGods) {
//                switch (god.getName()) {
//                    case "Aphrodite":
//                        possibleMovement = Aphrodite.sabotage(figure, possibleMovement);
//                    case "Athena":
//                        possibleMovement = Athena.sabotage(figure, possibleMovement);
//                    case "Hypnus":
//                        possibleMovement = Hypnus.sabotage(figure, possibleMovement);
//                    case "Persephone":
//                        possibleMovement = Persephone.sabotage(figure, possibleMovement);
//                    default:
//                        continue;
//                }
//            }
//        }
//=======
////        for (int i = 1; i < playerOrder.size(); i++) {
////            Player passivePlayer = playerOrder.get(i);
////            ArrayList<Gods> passiveGods = passivePlayer.getGods();
////            for(Gods god:passiveGods) {
////                switch (god) {
////                    case Aphrodite:
////                        possibleMovement = Aphrodite.sabotage(figure, possibleMovement);
////                    case Athena:
////                        possibleMovement = Athena.sabotage(figure, possibleMovement);
////                    case Hypnus:
////                        possibleMovement = Hypnus.sabotage(figure, possibleMovement);
////                    case Persephone:
////                        possibleMovement = Persephone.sabotage(figure, possibleMovement);
////                    default:
////                        continue;
////                }
////            }
////        }
//>>>>>>> scenebuilder
        return possibleMovement;
    }

    /**
     * Sabotage of the players building abilities by other players gods.
     *
     * @param figure the figure executing the movement
     * @param possibleBuilds the possible builds before the sabotage
     *
     * @return the List of fields, which are buildable after sabotage
     */
    public ArrayList<Field> sabotageBuilds(Figure figure, ArrayList<Field> possibleBuilds){
        for (int i = 1; i < playerOrder.size(); i++) {
            Player passivePlayer = playerOrder.get(i);
            // ArrayList<Gods> passiveGods = passivePlayer.getGods();
//<<<<<<< HEAD
//            for (Gods god : passiveGods) {
//                switch (god.getName()) {
//                    case "Limus":
//                        possibleBuilds = Limus.sabotage(figure, possibleBuilds);
//                    default:
//                        continue;
//                }
//            }
//=======
////            for (Gods god : passiveGods) {
////                switch (god) {
////                    case Limus:
////                        possibleBuilds = Limus.sabotage(figure, possibleBuilds);
////                    default:
////                        continue;
////                }
////            }
//>>>>>>> scenebuilder
        }
        return possibleBuilds;
    }

    /**
     * Runs the Game
     */
    public void runGame() {
        boolean gameEnded = false;
        while(gameEnded == false) {
            Player activePlayer = playerOrder.get(0);

            // Checks the active players resources
            // ArrayList<Gods> activeGods = activePlayer.getGods();
            ArrayList<Figure> activeFigures = activePlayer.getFigure();

            // Movement
            boolean artemisIsAvailable = false;
//<<<<<<< HEAD
//            for(Figure figure:activeFigures){
//                ArrayList<Field> regularFields = sabotageMovement(figure, figure.getValidMoves(board));
//                for(Gods god:activeGods) {
//                    switch (god.getName()) {
//                        case "Apollo":
//                            ArrayList<Field> apolloFields = Apollo.getValidMove(playerOrder,figure, board);
//                            apolloFields = sabotageMovement(figure, apolloFields);
//                        case "Artemis":
//                            artemisIsAvailable = true;
//                        case "Charon":
//                        case "Hermes":
//                        case "Minotaures":
//                        case "Triton":
//                    }
//                }
//            }
//=======
////            for(Figure figure:activeFigures){
////                ArrayList<Field> regularFields = sabotageMovement(figure, figure.getValidMoves(board));
////                for(Gods god:activeGods) {
////                    switch (god) {
////                        case Apollo:
////                            ArrayList<Field> apolloFields = Apollo.getValidMove(playerOrder, board);
////                            apolloFields = sabotageMovement(figure, apolloFields);
////                        case Artemis:
////                            artemisIsAvailable = true;
////                        case Charon:
////                        case Hermes:
////                        case Minotaures:
////                        case Triton:
////                    }
////                }
////            }
//>>>>>>> scenebuilder
            Field field = board.getField(0, 0);
            Figure figure = activeFigures.get(0); // Figur und Field muss gewählt werden, hier nur Testwert
            // Artemis !!!!!!!!! muss noch angepasst werden für getvalid move
            if (artemisIsAvailable) {
                int originalFigureX = figure.getX();
                int originalFigureY = figure.getY();
                // Artemis.getValidMove(figure, originalFigureX, originalFigureY);
            }

            activePlayer.executeMove(field, board, figure); //Kann auch zB. Apollo.executeMove sein


            // Building
            for (Figure figure2 : activeFigures) {
//<<<<<<< HEAD
//                ArrayList<Field> possibleFields = sabotageBuilds(figure2, figure2.getValidBuilds());
//            }
//            //Testweise dieses Stück rausgenommen
//            //Field field = board.getField(0,0); // Feld muss aus possibleFields gewählt werden, hier nur Testwert
//            activePlayer.executeBuild(field, board); // Kann auch zB. BuildingGod.executeBuild sein
//=======
//                ArrayList<Field> possibleFields = sabotageBuilds(figure2, figure2.getValidBuilds(board));
//            }
//
//            Field field2 = board.getField(0,0); // Feld muss aus possibleFields gewählt werden, hier nur Testwert
//            activePlayer.executeBuild(field2, board); // Kann auch zB. BuildingGod.executeBuild sein
//>>>>>>> scenebuilder

                // Checks if the game is over
                this.amountOfTurns++;
                if (checkRegularEnding()) {
                    gameEnded = true;
                    break;
                }
//            if(checkSpecialEnding()){
//                gameEnded = true;
//                break;
//            }

                // Spielerwechsel
                nextPlayersTurn();
            }
        }
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
        int blockedBuilding = 0;

        for (Figure figure: player.getFigure()){
            ArrayList<Field> possibleBuilding = figure.getValidBuilds(board);
            if(possibleBuilding.isEmpty()){
                blockedBuilding++;
            }
        }
        if (blockedBuilding == player.getFigure().size()){
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
    public void addPlayer(int amountOfFigures) {
        if (selectingPlayers()) {
            playerOrder.add(new Player(Player.PlayerType.HUMAN, "" + (playerOrder.size() + 1), def_colors[playerOrder.size() % def_colors.length], amountOfFigures, this, String.valueOf(playerOrder.size() + 1)));
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
//        System.out.println("Now its " + getPlayersTurn().getName() + "turn.");
        notifyObservers();
        switch (getPlayersTurn().getLevelOfIntelligence()){
            case HUMAN:
                break;
//            case REMOTE:
//
//                ExecuteStuff ex = new ExecuteStuff();
//                TestThread tt = new TestThread(ex);
//                tt.setOnSucceeded(e -> {
//                    Integer test = (Integer) e.getSource().getValue();
//                    System.out.println("int " + test.toString());
//                });
//                tt.start();
//                System.out.println("should have started");
//                break;
            case AI_1:
                break;
            case AI_2:
                break;
            case AI_3:
                break;
            default:
                System.err.println("This player type does not exist: " + getPlayersTurn().getLevelOfIntelligence());
        }

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


}