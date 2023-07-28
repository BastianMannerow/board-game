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
                String[] row = line.split(";");
                if(lineNumber == 0) {
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

    public List<String[]> readString(String str) {
        List<String[]> data = new ArrayList<>();
        String[] rows = str.split("\n");
        for(String line : rows){
            String[] row = line.split(";");
            data.add(row);
        }
        return data;
    }

    /**
     * saves a csv and parses it by using a comma
     *
     * @param filepath Where and how you want to save your files
     * @param data The content of the file
     */
    public void saveCSV(String filepath, List<String[]> data){
        try {
            // Creates Filepath, if not existend
            Path pathToFile = Paths.get(filepath);
            Files.createDirectories(pathToFile.getParent());
            try (FileWriter writer = new FileWriter(filepath)) {
                // Saves the file
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

    public static String makeString(List<String[]> data){
        StringBuilder csvLine = new StringBuilder();
        for (String[] row : data) {
            for (int i = 0; i < row.length; i++) {
                csvLine.append(row[i]);
                if (i < row.length - 1) {
                    csvLine.append(",");
                }
            }
            csvLine.append(System.lineSeparator());
        }
        return csvLine.toString();
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
        File folder = new File(directory + "\\savings");

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
        // Load Game
        List<Object> csv = loadCSV(filepath + File.separator + "game.csv");
        List<String> header = (List<String>) csv.get(0);
        List<List<String>> gameData = (List<List<String>>) csv.get(1);

        String playerOrder = gameData.get(0).get(header.indexOf("playerOrder"));
        String board = gameData.get(0).get(header.indexOf("board"));
        String amountOfGameTurns = gameData.get(0).get(header.indexOf("amountOfGameTurns"));
        String levelOneTile = gameData.get(0).get(header.indexOf("levelOneTile"));
        String levelTwoTile = gameData.get(0).get(header.indexOf("levelTwoTile"));
        String levelThreeTile = gameData.get(0).get(header.indexOf("levelThreeTile"));
        String levelFourTile = gameData.get(0).get(header.indexOf("levelFourTile"));
        String maxStepUpHeight = gameData.get(0).get(header.indexOf("maxStepUpHeight"));
        String maxStepDownHeight = gameData.get(0).get(header.indexOf("maxStepDownHeight"));
        String gameName = gameData.get(0).get(header.indexOf("gameName"));
        String lastMovedFigure = gameData.get(0).get(header.indexOf("lastMovedFigure"));
        String gameStatus = gameData.get(0).get(header.indexOf("gameStatus"));
        String victoryHeight = gameData.get(0).get(header.indexOf("victoryHeight"));

        // Load Player
        csv = loadCSV(filepath + File.separator + "player.csv");
        header = (List<String>) csv.get(0);
        List<List<String>> playerData = (List<List<String>>) csv.get(1);

        String levelOfIntelligence = playerData.get(0).get(header.indexOf("levelOfIntelligence"));
        String name = playerData.get(0).get(header.indexOf("name"));
        String color = playerData.get(0).get(header.indexOf("color"));
        String amountOfFigures = playerData.get(0).get(header.indexOf("amountOfFigures"));
        String amountOfTurns = playerData.get(0).get(header.indexOf("amountOfTurns"));
        String figures = playerData.get(0).get(header.indexOf("figures"));
        String gods = playerData.get(0).get(header.indexOf("gods"));
        String team = playerData.get(0).get(header.indexOf("team"));

        // Load Figure
        csv = loadCSV(filepath + File.separator + "figure.csv");
        header = (List<String>) csv.get(0);
        List<List<String>> figureData = (List<List<String>>) csv.get(1);

        String x = playerData.get(0).get(header.indexOf("x"));
        String y = playerData.get(0).get(header.indexOf("y"));
        String placed = playerData.get(0).get(header.indexOf("placed"));

        // Load Fields
        csv = loadCSV(filepath + File.separator + "fields.csv");
        header = (List<String>) csv.get(0);
        List<List<String>> fieldsData = (List<List<String>>) csv.get(1);

        String fieldX = playerData.get(0).get(header.indexOf("fieldX"));;
        String fieldY = playerData.get(0).get(header.indexOf("fieldY"));;
        String towerLevel = playerData.get(0).get(header.indexOf("towerLevel"));;
        String towerComplete = playerData.get(0).get(header.indexOf("towerComplete"));;
        String figure = playerData.get(0).get(header.indexOf("figure"));;
    }

    /**
     * Saves the game
     *
     * @param game
     * @return Boolean, if the saving was successful
     */
    public void saveGame(Game game){
        loadHighscore();

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

        // Rename the game
        game.setGameName(saveGameName);
        System.out.println("Saved this game: " + saveGameName);
    }

    public List<String[]> getFieldsData(Game game){
        List<String[]> fieldsData = new ArrayList<>();
        for (int i = 0; i < game.getBoard().getXSize(); i++) {
            for (int j = 0; j < game.getBoard().getYSize(); j++) {
                String towerLevel = Integer.toString(game.getBoard().getField(i, j).getTowerLevel());
                String towerComplete = "False";
                if (game.getBoard().getField(i, j).getTowerComplete()) {
                    towerComplete = "True";
                }
                fieldsData.add(new String[]{Integer.toString(i), Integer.toString(j), towerLevel, towerComplete});
            }
        }
        return fieldsData;
    }

    public List<String[]> getFiguresData(ArrayList<Player> playerList){
        List<String[]> figureData = new ArrayList<>();

        for (Player player: playerList){
            ArrayList<Figure> figureList = player.getFigure();
            for (Figure figure: figureList){
                figureData.add(new String[]{Integer.toString(figure.getX()), Integer.toString(figure.getY())});
            }
        }
        return figureData;
    }

    public List<String[]> getPlayersData(ArrayList<Player> playerList){
        List<String[]> playerData = new ArrayList<>();
        for (Player player: playerList){
            String name = player.getName();
            String color = player.getColor().toString();
            Player.PlayerType levelOfIntelligence = player.getLevelOfIntelligence();
            String gods = "Nobody"; // Muss gelöscht werden
            String levelOneTile = Integer.toString(player.getLevelOneTile());
            String levelTwoTile = Integer.toString(player.getLevelTwoTile());
            String levelThreeTile = Integer.toString(player.getLevelThreeTile());
            String levelFourTile = Integer.toString(player.getLevelFourTile());
            // String gods = player.getGods(); Muss angepasst werden.
            String team = player.getTeam();
            playerData.add(new String[]{name, color, String.valueOf(levelOfIntelligence), gods, team});
        }
        return playerData;
    }

    public List<String[]> getGameData(Game game){
        List<String[]> gameData = new ArrayList<>();
        String roundWorld = "False";
        if (game.getBoard().getRoundWorld()) {
            roundWorld = "True";
        }
        String playerOrder = Integer.toString(game.getPlayerOrder().size());
        String amountOfTurns = Integer.toString(game.getAmountOfTurns());
        gameData.add(new String[]{roundWorld, playerOrder, amountOfTurns});
        return gameData;
    }

    /**
     * @return the highscore data
     */
    public ArrayList<String> loadHighscore() {
        List<Object> csv = loadCSV((System.getProperty("user.dir") + File.separator + "savings\\highscore.csv"));
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
        extractedData.add(topOneplayerNames);
        extractedData.add(topOneLevelOfIntelligence);
        extractedData.add(topOneAmountOfTurns);
        extractedData.add(topOneTeamName);
        extractedData.add(topTwoplayerNames);
        extractedData.add(topTwoLevelOfIntelligence);
        extractedData.add(topTwoAmountOfTurns);
        extractedData.add(topTwoTeamName);
        extractedData.add(topThreeplayerNames);
        extractedData.add(topThreeLevelOfIntelligence);
        extractedData.add(topThreeAmountOfTurns);
        extractedData.add(topThreeTeamName);
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
        String filepath = (System.getProperty("user.dir") + File.separator + "savings\\highscore.csv");
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
    public void checkHighscore(Game game, String winner){
        ArrayList<String> oldHighscore = loadHighscore();
        int potentialHighscore = 10000;
        ArrayList<Player> playerList = game.getPlayerOrder();
        int position = 4;

        for (Player player: playerList) {
            if (player.getTeam().equals(winner)) {
                if (player.getAmountOfTurns() < potentialHighscore) {
                    potentialHighscore = player.getAmountOfTurns();
                }
            }
        }

        for (int k = 0; k <= 2; k++){
            if (Integer.parseInt(oldHighscore.get(2+k*3)) > potentialHighscore){
                position = k;
                break;
            }
        }

        if (Integer.parseInt(oldHighscore.get(4)) < game.getAmountOfTurns()){
            saveHighscore(game, winner, position);
        }
    }
}