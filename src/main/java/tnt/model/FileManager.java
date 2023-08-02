package tnt.model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.File;
import java.nio.file.*;
import java.util.Arrays;
import java.util.stream.Collectors;

import javafx.scene.paint.Color;

/**
 * The FileManager is responsible for permanent savings as csv-files.
 */
public class FileManager {
    /**
     * loads a csv and parses it by using a comma
     *
     * @param filepath The filepath you want to load
     * @return the csv as rows and columns
     */
    public List<Object> loadCSV(String filepath) {
        List<List<String>> data = new ArrayList<>();
        List<String> header = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(";"); // A separator for parsing
                if(lineNumber == 0) { // The first line will be the header, which is used for an easier indexing
                    header = Arrays.asList(row);
                } else {
                    data.add(Arrays.asList(row));
                }
                lineNumber++;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        List<Object> csv = new ArrayList<>();
        csv.add(header);
        csv.add(data);
        return csv;
    }

    /**
     * converts a string representation to other object representation
     * @param str the string to be converted
     * @return the data with header
     */
    public List<Object> readString(String str) {
        String newstr = str.replace("new##notaseperator##data", "new####data");
        List<List<String>> data = new ArrayList<>();
        List<String> header = new ArrayList<>();
        String[] rows = newstr.split("\n");
        int lineNumber = 0;
        for(String line : rows){
            String[] row = line.split(";,"); // A separator for parsing
            if(lineNumber == 0) { // The first line will be the header, which is used for an easier indexing
                header = Arrays.stream(row).map((rowhere) -> rowhere.replace(";-,", ";,")).collect(Collectors.toList());
            } else {
                data.add(Arrays.stream(row).map((rowhere) -> rowhere.replace(";-,", ";,")).collect(Collectors.toList()));
            }
            lineNumber++;
        }
        List<Object> csv = new ArrayList<>();
        csv.add(header);
        csv.add(data);
        return csv;
    }

    /**
     * saves a csv and parses it by using a comma
     *
     * @param filepath Where and how you want to save your files
     * @param data The content of the file
     */
    public void saveCSV(String filepath, List<String[]> data){
        try {
            // Creates Filepath, if not existing
            Path pathToFile = Paths.get(filepath);
            Files.createDirectories(pathToFile.getParent());
            try (FileWriter writer = new FileWriter(filepath)) {
                // Saves the file and uses a separator for future parsing
                for (String[] row : data) {
                    StringBuilder csvLine = new StringBuilder();
                    for (int i = 0; i < row.length; i++) {
                        csvLine.append(row[i]);
                        if (i < row.length - 1) {
                            csvLine.append(";");
                        }
                    }
                    csvLine.append(System.lineSeparator());
                    writer.append(csvLine.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
<<<<<<< HEAD
     * makes a string from the list of string array
     * @param data the data to be converted to a single string
     * @return the data represented as string
=======
     * Transforms a list to string
     *
     * @param data The desired data
     * @return the transformed data
>>>>>>> master
     */
    public static String makeString(List<String[]> data){
        StringBuilder csvLine = new StringBuilder();
        for (String[] row : data) {
            for (int i = 0; i < row.length; i++) {
                if (row[i] == null){
                    row[i] = "";
                }
                csvLine.append(row[i].replace(";,", ";-,"));
                if (i < row.length - 1) {
                    csvLine.append(";,");
                }
            }
            csvLine.append(System.lineSeparator());
        }
        return csvLine.toString().replace("new####data", "new##notaseperator##data");
    }

    /**
     * Deletes a folder and its content
     *
     * @param filepath The folder you want to delete
     */
    public static void deleteFolder(File filepath) {
        if (filepath.isDirectory()) {
            File[] files = filepath.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteFolder(file);
                }
            }
        }
        filepath.delete();
    }

    /**
     * Helps the frontend to display all saved games.
     *
     * @return all saved games.
     */
    public ArrayList<String> getSavedGames(){
        String directory = System.getProperty("user.dir");
        File folder = new File(directory + File.separator + "savings");
        ArrayList<String> savedGames = new ArrayList<>();
        if (folder.isDirectory()) {
            File[] subfolders = folder.listFiles(File::isDirectory);
            if (subfolders != null) {
                for (File subfolder : subfolders) {
                    savedGames.add(subfolder.getName());
                }
            }
        }
        if (savedGames.isEmpty()){
            savedGames.add(0, "-");
        }
        return savedGames;
    }

    /**
     * Loads a saved game from their csv files
     *
     * @param savedGame The name of the game
     */
    public void loadGame(String savedGame, Game game){
        String filepath = System.getProperty("user.dir") + File.separator + "savings" + File.separator + savedGame;

        // Load Player
        List<Object> csv = loadCSV(filepath + File.separator + "player.csv");
        List<String> header = (List<String>) csv.get(0);
        List<List<String>> playerData = (List<List<String>>) csv.get(1);
        ArrayList<Player> playerList = setPlayerData(header, playerData, game);

        // Load Game
        csv = loadCSV(filepath + File.separator + "game.csv");
        header = (List<String>) csv.get(0);
        List<List<String>> gameData = (List<List<String>>) csv.get(1);
        setGameData(header, gameData, game, playerList);

        // Load Fields
        csv = loadCSV(filepath + File.separator + "fields.csv");
        header = (List<String>) csv.get(0);
        List<List<String>> fieldData = (List<List<String>>) csv.get(1);
        ArrayList<Field> allFields = setFieldData(header, fieldData);

        // Load Board
        csv = loadCSV(filepath + File.separator + "board.csv");
        header = (List<String>) csv.get(0);
        List<List<String>> boardData = (List<List<String>>) csv.get(1);
        setBoardData(header, boardData, game, allFields);

        // Load Figure
        csv = loadCSV(filepath + File.separator + "figure.csv");
        header = (List<String>) csv.get(0);
        List<List<String>> figureData = (List<List<String>>) csv.get(1);
        setFigureData(header, figureData, game, playerList);
    }

    /**
     * Part of load game
     *
     * @param header the header for the data
     * @param playerData the data
     */
    public ArrayList<Player> setPlayerData(List<String> header, List<List<String>> playerData, Game game){
        ArrayList<Player> playerList = new ArrayList<>();
        for (List<String> player:playerData) {
            String name = player.get(header.indexOf("name"));
            String levelOfIntelligence = player.get(header.indexOf("levelOfIntelligence"));
            String color = player.get(header.indexOf("color"));
            int amountOfFigures = Integer.parseInt(player.get(header.indexOf("amountOfFigures")));
            int amountOfTurns = Integer.parseInt(player.get(header.indexOf("amountOfTurns")));
            String team = player.get(header.indexOf("team"));
            String stringNumberOfTiles = player.get(header.indexOf("numberOfTiles"));
            Player playerObject = new Player(game);
            playerObject.setName(name);

            if(levelOfIntelligence.equals("HUMAN")) {
                playerObject.setLevelOfIntelligence(Player.PlayerType.HUMAN);
            }
            else if(levelOfIntelligence.equals("AI_1")) {
                playerObject.setLevelOfIntelligence(Player.PlayerType.AI_1);
            }
            else if(levelOfIntelligence.equals("AI_2")) {
                playerObject.setLevelOfIntelligence(Player.PlayerType.AI_2);
            }
            else if(levelOfIntelligence.equals("AI_3")) {
                playerObject.setLevelOfIntelligence(Player.PlayerType.AI_3);
            }

            color = "#" + color.substring(2); // Entfernt das "0x" und fügt "#" hinzu
            playerObject.setColor(Color.web(color));
            playerObject.setAmountOfFigures(amountOfFigures);
//            playerObject.addFigure(amountOfFigures);

            playerObject.setAmountOfTurns(amountOfTurns);
            playerObject.setTeam(team);
            // Parses the tile pool
            String[] parsedStringNumberOfTiles = stringNumberOfTiles.split(", ");
            int[] numberOfTiles = new int[stringNumberOfTiles.length()];
            for (int i = 0; i < parsedStringNumberOfTiles.length; i++) {
                numberOfTiles[i] = Integer.parseInt(parsedStringNumberOfTiles[i]);
            }
            playerObject.setNumberOfTile(numberOfTiles);
            playerList.add(playerObject);
        }
        return playerList;
    }

    /**
     * Part of load game
     *
     * @param header the header for the data
     * @param figureData the
     * @param playerList the list of players to add the figures to
     */
    public void setFigureData(List<String> header, List<List<String>> figureData, Game game, ArrayList<Player> playerList){
        for (List<String> figure: figureData) {
            for (Player player: playerList){
                if(player.getName().equals(figure.get(header.indexOf("player")))){
                    player.addFigure(1);
                    Figure currentFigure = player.getFigure().get(player.getRealAmountFigure()-1);
                    currentFigure.setX(Integer.parseInt(figure.get(header.indexOf("x"))));
                    currentFigure.setY(Integer.parseInt(figure.get(header.indexOf("y"))));
                    if(Boolean.valueOf(figure.get(header.indexOf("placed")))){
                        currentFigure.setPlaced();
                        game.getBoard().getField(Integer.parseInt(figure.get(header.indexOf("x"))), Integer.parseInt(figure.get(header.indexOf("y")))).setFigure(currentFigure);
                    }
                }
            }
        }
    }

    /**
     * Part of load game
     *
     * @param header the header for the data
     * @param fieldData the data
     */
    public ArrayList<Field> setFieldData(List<String> header, List<List<String>> fieldData){
        ArrayList<Field> allFields = new ArrayList<>();
        for (List<String> field: fieldData){
            String fieldX = field.get(header.indexOf("fieldX"));
            String fieldY = field.get(header.indexOf("fieldY"));
            Field fieldObject = new Field(Integer.valueOf(fieldX), Integer.valueOf(fieldY));
            String towerLevel = field.get(header.indexOf("towerLevel"));
            fieldObject.setTowerLevel(Integer.valueOf(towerLevel));
            String towerComplete = field.get(header.indexOf("towerComplete"));
            fieldObject.setTowerComplete(Boolean.valueOf(towerComplete));
            allFields.add(fieldObject);
        }
        return allFields;
    }

    /**
     * Part of load game
     *
     * @param header the header for the data
     * @param boardData the data
     * @param allFields all the fields, which will be placed on the board
     */
    public void setBoardData(List<String> header, List<List<String>> boardData, Game game, ArrayList<Field> allFields){
        String fieldX = boardData.get(0).get(header.indexOf("xSize"));
        String fieldY = boardData.get(0).get(header.indexOf("ySize"));
        game.createBoard(Integer.valueOf(fieldX), Integer.valueOf(fieldY));
        game.getBoard().setRoundWorld(Boolean.valueOf(boardData.get(0).get(header.indexOf("roundWorld"))));
        for (Field field: allFields){
            game.getBoard().setField(field.getX(), field.getY(), field);
        }
    }

    /**
     * Part of load game
     *
     * @param header the header for the data
     * @param gameData the data
     */
    public void setGameData(List<String> header, List<List<String>> gameData, Game game, ArrayList<Player> playerList){
        // Sorting the player order
        String playerOrder = gameData.get(0).get(header.indexOf("playerOrder"));

        String[] parsedStringPlayerOrder = playerOrder.split(" ,");
        ArrayList<Player> finalPlayerOrder = new ArrayList<>();
        for (int i = 0; i < parsedStringPlayerOrder.length; i++) {
            for (Player player: playerList){
                if (player.getName().equals(parsedStringPlayerOrder[i])){
                    finalPlayerOrder.add(player);
                }
            }
        }
        game.setPlayerOrder(finalPlayerOrder);

        game.setAmountOfTurns(Integer.valueOf(gameData.get(0).get(header.indexOf("amountOfTurns"))));
        game.setMaxStepUpHeight(Integer.valueOf(gameData.get(0).get(header.indexOf("maxStepUpHeight"))));
        game.setMaxStepDownHeight(Integer.valueOf(gameData.get(0).get(header.indexOf("maxStepDownHeight"))));
        game.setVictoryHeight(Integer.valueOf(gameData.get(0).get(header.indexOf("victoryHeight"))));
        game.setGameName(gameData.get(0).get(header.indexOf("gameName")));
        game.setGlobalTilePool(Boolean.valueOf(gameData.get(0).get(header.indexOf("globalTilePool"))));

        game.setGameStatus(Game.GameStatus.valueOf(gameData.get(0).get(header.indexOf("gameStatus"))));

        String stringNumberOfTiles = gameData.get(0).get(header.indexOf("numberOfTiles"));
        // Parses the tile pool
        String[] parsedStringNumberOfTiles = stringNumberOfTiles.split(", ");
        int[] numberOfTiles = new int[stringNumberOfTiles.length()];
        for (int i = 0; i < parsedStringNumberOfTiles.length; i++) {
            numberOfTiles[i] = Integer.parseInt(parsedStringNumberOfTiles[i]);
        }
        game.setNumberOfTile(numberOfTiles);
    }

    /**
     * Saves the game
     *
     * @param game The game which needs to be saved
     */
    public void saveGame(Game game){
        String saveGameName = game.getGameName();
        // If new game, create new saving folder
        if(saveGameName.equals("newGame")){
            if(getSavedGames().get(getSavedGames().size() - 1).equals("-")){
                int number = 1;
                saveGameName = "Game ".concat(Integer.toString(number));
            }
            else {
                String lastGame = getSavedGames().get(getSavedGames().size() - 1);
                lastGame = lastGame.replace("Game ", "").trim();
                int lastGameNumber = Integer.parseInt(lastGame);
                saveGameName = "Game ".concat(Integer.toString(lastGameNumber + 1));
            }
        }

        // If no, delete old game and save new one
        else{
            String filepath = System.getProperty("user.dir") + File.separator + "savings" + File.separator + saveGameName;
            File folder = new File(filepath);
            deleteFolder(folder);
        }

        // Save Game Information
        List<String[]> gameData = getGameData(game);
        String filepath = System.getProperty("user.dir") + File.separator + "savings" + File.separator + saveGameName + File.separator + "game.csv";
        saveCSV(filepath, gameData);

        // Save Player Information
        ArrayList<Player> playerList = game.getPlayerOrder();
        List<String[]> playerData = getPlayersData(playerList);
        filepath = System.getProperty("user.dir") + File.separator + "savings" + File.separator + saveGameName + File.separator + "player.csv";
        saveCSV(filepath, playerData);

        // Save Figure Information
        List<String[]> figureData = getFiguresData(playerList);
        filepath = System.getProperty("user.dir") + File.separator + "savings" + File.separator + saveGameName + File.separator + "figure.csv";
        saveCSV(filepath, figureData);

        // Save Field Information
        List<String[]> fieldsData = getFieldsData(game);
        filepath = System.getProperty("user.dir") + File.separator + "savings" + File.separator + saveGameName + File.separator + "fields.csv";
        saveCSV(filepath, fieldsData);

        // Save Board Information
        List<String[]> boardData = getBoardData(game);
        filepath = System.getProperty("user.dir") + File.separator + "savings" + File.separator + saveGameName + File.separator + "board.csv";
        saveCSV(filepath, boardData);

        // Rename the game
        game.setGameName(saveGameName);
        System.out.println("Saved this game: " + saveGameName);
    }

    /**
     * Part of save game
     *
     * @param game the game on which is played on
     * @return the data of the current games fields
     */
    public List<String[]> getFieldsData(Game game){
        List<String[]> fieldsData = new ArrayList<>();
        String[] header = {"fieldX", "fieldY", "towerLevel", "towerComplete"};
        fieldsData.add(header);
        for (int i = 0; i < game.getBoard().getXSize(); i++) {
            for (int j = 0; j < game.getBoard().getYSize(); j++) {
                String towerLevel = Integer.toString(game.getBoard().getField(i, j).getTowerLevel());
                String towerComplete = "false";
                if (game.getBoard().getField(i, j).getTowerComplete()) {
                    towerComplete = "true";
                }
                fieldsData.add(new String[]{Integer.toString(i), Integer.toString(j), towerLevel, towerComplete});
            }
        }
        return fieldsData;
    }

    /**
     * Part of save game
     *
     * @param playerList alle players
     * @return the data of the current games figures
     */
    public List<String[]> getFiguresData(ArrayList<Player> playerList){
        List<String[]> figureData = new ArrayList<>();
        String[] header = {"x", "y", "placed", "player"};
        figureData.add(header);
        for (Player player: playerList){
            ArrayList<Figure> figureList = player.getFigure();
            for (Figure figure: figureList){
                figureData.add(new String[]{Integer.toString(figure.getX()), Integer.toString(figure.getY()), Boolean.toString(figure.isPlaced()), player.getName()});
            }
        }
        return figureData;
    }

    /**
     * Part of save game
     *
     * @param playerList alle players
     * @return the data of the current games players
     */
    public List<String[]> getPlayersData(ArrayList<Player> playerList){
        List<String[]> playerData = new ArrayList<>();
        String[] header = {"name", "levelOfIntelligence", "color", "amountOfFigures", "amountOfTurns", "team", "numberOfTiles"};
        playerData.add(header);
        for (Player player: playerList){
            String name = player.getName();
            String color = player.getColor().toString();
            Player.PlayerType levelOfIntelligence = player.getLevelOfIntelligence();
            int amountOfFigures = player.getAmountOfFigures();
//            int amountOfFigures = player.getRealAmountFigure();
            int amountOfTurns = player.getAmountOfTurns();
            String team = player.getTeam();
            // Amount of tiles need a simple loop to be converted into String
            String numberOfTile = "";
            for (int i = 0; i < player.getTileSize(); i++) {
                if(i == 0) {
                    numberOfTile = numberOfTile.concat(String.valueOf(player.getNrTile(i)));
                }
                else{
                    numberOfTile = numberOfTile.concat(", ").concat(String.valueOf(player.getNrTile(i)));
                }
            }
            playerData.add(new String[]{name, String.valueOf(levelOfIntelligence), color, String.valueOf(amountOfFigures), String.valueOf(amountOfTurns), team, numberOfTile});
        }
        return playerData;
    }

    /**
     * Part of save game
     *
     * @param game the game which is played on
     * @return the data of the current game
     */
    public List<String[]> getGameData(Game game){
        List<String[]> gameData = new ArrayList<>();
        String[] header = {"playerOrder", "amountOfTurns", "maxStepUpHeight", "maxStepDownHeight", "gameName", "victoryHeight", "numberOfTiles", "globalTilePool", "gameStatus"};
        gameData.add(header);

        ArrayList<Player> playerList = game.getPlayerOrder();
        // Saving the players names
        String playerOrder = "";
        int i = 0;
        for (Player player: playerList){
            if(i >0){
                playerOrder = playerOrder.concat(" ,").concat(player.getName());
            }
            else{
                playerOrder = playerOrder.concat(player.getName());
            }
            i++;
        }

        String amountOfTurns = Integer.toString(game.getAmountOfTurns());
        String maxStepUpHeight = Integer.toString(game.getMaxStepUpHeight());
        String maxStepDownHeight = Integer.toString(game.getMaxStepDownHeight());
        String gameName = game.getGameName();
        String victoryHeight = Integer.toString(game.getVictoryHeight());
        String globalTilePool = Boolean.toString(game.isGlobalTilePool());
        String gameStatus = game.getGameStatus().toString();
        // Amount of tiles need a simple loop to be converted into String
        String amountOfTiles = "";
        for (int j = 0; j < game.getTileSize(); j++) {
            if(j == 0) {
                amountOfTiles = amountOfTiles.concat(String.valueOf(game.getNrTile(i)));
            }
            else{
                amountOfTiles = amountOfTiles.concat(", ").concat(String.valueOf(game.getNrTile(i)));
            }
        }
        gameData.add(new String[]{playerOrder, amountOfTurns, maxStepUpHeight, maxStepDownHeight, gameName, victoryHeight, amountOfTiles, globalTilePool, gameStatus});
        return gameData;
    }

    /**
     * Part of save game
     *
     * @param game the game which is played on
     * @return the data of the current board
     */
    public List<String[]> getBoardData(Game game){
        List<String[]> boardData = new ArrayList<>();
        String[] header = {"xSize", "ySize", "roundWorld"};
        boardData.add(header);
        String xSize = Integer.toString(game.getBoard().getXSize());
        String ySize = Integer.toString(game.getBoard().getYSize());
        String roundWorld = Boolean.toString(game.getBoard().isRoundWorld());
        boardData.add(new String[]{xSize, ySize, roundWorld});
        return boardData;
    }

    /**
     * Loads the current highscore.
     *
     * @return the highscore data
     */
    public ArrayList<String> loadHighscore() {
        List<Object> csv = loadCSV((System.getProperty("user.dir") + File.separator + "savings" + File.separator + "highscore.csv"));
        List<String> header = (List<String>) csv.get(0);
        List<List<String>> data = (List<List<String>>) csv.get(1);
        String topOneplayerNames = "";
        String topTwoplayerNames = "";
        String topThreeplayerNames = "";
        String topOneLevelOfIntelligence = "";
        String topTwoLevelOfIntelligence = "";
        String topThreeLevelOfIntelligence = "";
        String topOneAmountOfTurns = "";
        String topTwoAmountOfTurns = "";
        String topThreeAmountOfTurns = "";
        String topOneTeamName = "";
        String topTwoTeamName = "";
        String topThreeTeamName = "";

        if(!data.isEmpty()) {
            int name = header.indexOf("Name");
            int levelOfIntelligence = header.indexOf("LevelOfIntelligence");
            int gameAmountOfTurns = header.indexOf("AmountOfTurns");
            int nameOfTeam = header.indexOf("Team Name");
            topOneplayerNames = data.get(0).get(name);
            topTwoplayerNames = data.get(1).get(name);
            topThreeplayerNames = data.get(2).get(name);
            topOneLevelOfIntelligence = data.get(0).get(levelOfIntelligence);
            topTwoLevelOfIntelligence = data.get(1).get(levelOfIntelligence);
            topThreeLevelOfIntelligence = data.get(2).get(levelOfIntelligence);
            topOneAmountOfTurns = data.get(0).get(gameAmountOfTurns);
            topTwoAmountOfTurns = data.get(1).get(gameAmountOfTurns);
            topThreeAmountOfTurns = data.get(2).get(gameAmountOfTurns);
            topOneTeamName = data.get(0).get(nameOfTeam);
            topTwoTeamName = data.get(1).get(nameOfTeam);
            topThreeTeamName = data.get(2).get(nameOfTeam);
        }
        ArrayList<String> extractedData = new ArrayList<>();
        extractedData.addAll(Arrays.asList(topOneplayerNames, topOneLevelOfIntelligence, topOneAmountOfTurns, topOneTeamName, topTwoplayerNames, topTwoLevelOfIntelligence, topTwoAmountOfTurns, topTwoTeamName, topThreeplayerNames, topThreeLevelOfIntelligence, topThreeAmountOfTurns, topThreeTeamName));
        System.out.println(extractedData);
        return extractedData;
    }

    /**
     * Saves the new highscore.
     *
     * @param game The game object
     * @param winner The name of the winning team
     * @param position The position in the highscore ranking
     */
    public void saveHighscore(Game game, String winner, int position){
        String[] header = {"Name", "LevelOfIntelligence", "AmountOfTurns", "Team Name"};
        String filepath = (System.getProperty("user.dir") + File.separator + "savings" + File.separator + "highscore.csv");
        List<String[]> data = new ArrayList<>();
        ArrayList<Player> playerList = game.getPlayerOrder();

        // Getting the data
        String winnerNames = "";
        String winnerIntelligence = "";
        int winnerAmountOfTurns = 10000;
        int i = 0;
        for (Player player: playerList){
            if (player.getTeam().equals(winner)){
                if(i >0){
                    winnerNames = winnerNames.concat(" ,").concat(player.getName());
                    winnerIntelligence = winnerIntelligence.concat(" ,").concat(String.valueOf(player.getLevelOfIntelligence()));
                }
                else{
                    winnerNames = winnerNames.concat(player.getName());
                    winnerIntelligence = winnerIntelligence.concat(String.valueOf(player.getLevelOfIntelligence()));
                }
                i++;
                if(player.getAmountOfTurns() < winnerAmountOfTurns){
                    winnerAmountOfTurns = player.getAmountOfTurns();
                }
            }
        }
        ArrayList<String> oldHighscore = loadHighscore();
        // Saving in rows
        String[] topOneHighScore = {oldHighscore.get(0), oldHighscore.get(1), oldHighscore.get(2), oldHighscore.get(3)};
        String[] topTwoHighScore = {oldHighscore.get(4), oldHighscore.get(5), oldHighscore.get(6), oldHighscore.get(7)};
        String[] topThreeHighScore = {oldHighscore.get(8), oldHighscore.get(9), oldHighscore.get(10), oldHighscore.get(11)};
        List<String[]> oldHighscoreList = new ArrayList<>(Arrays.asList(topOneHighScore, topTwoHighScore, topThreeHighScore));
        data.add(header);

        for (int j = 0; j <= 2; j++) {
            if (j == position){
                String[] newHighscore = {winnerNames, winnerIntelligence, String.valueOf(winnerAmountOfTurns), winner};
                data.add(newHighscore);
            }
            else{
                data.add(oldHighscoreList.get(0));
                oldHighscoreList.remove(0);
            }
        }
        saveCSV(filepath, data);
    }

    /**
     * Ckecks if the highscore is beaten and saves the new one
     *
     * @param game The game object
     * @param winner The name of the winning team
     */
    public void checkHighscore (Game game, String winner){
        ArrayList<String> oldHighscore = loadHighscore();
        int potentialHighscore = 10000;
        ArrayList<Player> playerList = game.getPlayerOrder();
        int position = 4;

        // Compares the potential new highscore with the existing ones
        for (Player player: playerList) {
            if (player.getTeam().equals(winner)) {
                if (player.getAmountOfTurns() < potentialHighscore) {
                    potentialHighscore = player.getAmountOfTurns();
                }
            }
        }
        for (int k = 0; k <= 2; k++){
            if (Integer.parseInt(oldHighscore.get(2+k*4)) > potentialHighscore){
                position = k;
                break;
            }
        }
        saveHighscore(game, winner, position);
    }
}