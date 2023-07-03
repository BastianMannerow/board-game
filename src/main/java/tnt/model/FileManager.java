package tnt.model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.File;

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
    public List<String[]> loadCSV(String filepath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                data.add(row);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
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
        try (FileWriter writer = new FileWriter(filepath)) {
            for (String[] row : data) {
                StringBuilder csvLine = new StringBuilder();
                for (int i = 0; i < row.length; i++) {
                    csvLine.append(row[i]);
                    if (i < row.length - 1) {
                        csvLine.append(",");
                    }
                }
                csvLine.append(System.lineSeparator());
                writer.append(csvLine.toString());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
        File folder = new File("user.dir\\savings");

        ArrayList<String> savedGames = new ArrayList<>();

        if (folder.isDirectory()) {
            File[] subfolders = folder.listFiles(File::isDirectory);
            if (subfolders != null) {
                for (File subfolder : subfolders) {
                    savedGames.add(subfolder.getName());
                }
            }
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
        List<String[]> gameData = loadCSV(filepath + File.separator + "game.csv");
        // gameData.add(new String[]{roundWorld, playerOrder, amountOfTurns, levelOneTile, levelTwoTile, levelThreeTile, levelFourTile});
        // game.setPlayerOrder(gameData.get(0)[1]); // Muss noch anders gesaved werden!
        game.setAmountOfTurns(Integer.parseInt(gameData.get(0)[2]));
        game.setLevelOneTile(Integer.parseInt(gameData.get(0)[3]));
        game.setLevelTwoTile(Integer.parseInt(gameData.get(0)[4]));
        game.setLevelThreeTile(Integer.parseInt(gameData.get(0)[5]));
        game.setLevelFourTile(Integer.parseInt(gameData.get(0)[6]));

        // Load Player
        List<String[]> playerData = loadCSV(filepath + File.separator + "player.csv");

        // Load Figure
        List<String[]> figureData = loadCSV(filepath + File.separator + "figure.csv");

        // Load Fields
        List<String[]> fieldsData = loadCSV(filepath + File.separator + "fields.csv");

    }

    /**
     * Saves the game
     *
     * @param game
     */
    public void saveGame(Game game){
        String saveGameName = game.getGameName();
        // If new game, create new saving folder
        if(saveGameName.equals("newGame")){
            String lastGame = getSavedGames().get(getSavedGames().size()-1);
            String lastTwoChars = lastGame.substring(lastGame.length() - 2);
            int number = Integer.parseInt(lastTwoChars) + 1;
            saveGameName = "Game ".concat(Integer.toString(number));
        }

        // If no, delete old game and save new one
        else{
            String filepath = System.getProperty("user.dir") + File.separator + "savings" + File.separator + saveGameName;
            File folder = new File(filepath);
            deleteFolder(folder);
        }

        // Save Game Information
        List<String[]> gameData = new ArrayList<>();
        String roundWorld = "False";
        if (game.getBoard().getRoundWorld()) {
            roundWorld = "True";
        }
        String playerOrder = Integer.toString(game.getPlayerOrder().size());
        String amountOfTurns = Integer.toString(game.getAmountOfTurns());
        String levelOneTile = Integer.toString(game.getLevelOneTile());
        String levelTwoTile = Integer.toString(game.getLevelTwoTile());
        String levelThreeTile = Integer.toString(game.getLevelThreeTile());
        String levelFourTile = Integer.toString(game.getLevelFourTile());
        gameData.add(new String[]{roundWorld, playerOrder, amountOfTurns, levelOneTile, levelTwoTile, levelThreeTile, levelFourTile});

        String filepath = System.getProperty("user.dir") + File.separator + "savings" + File.separator + saveGameName + File.separator + "game.csv";
        saveCSV(filepath, gameData);

        // Save Player Information
        List<String[]> playerData = new ArrayList<>();
        ArrayList<Player> playerList = game.getPlayerOrder();
        for (Player player: playerList){
            String name = player.getName();
            String color = player.getColor().toString();
            Player.PlayerType levelOfIntelligence = player.getLevelOfIntelligence();
            String gods = "Nobody"; // Muss gelöscht werden
            // String gods = player.getGods(); Muss angepasst werden.
            String team = player.getTeam();
            playerData.add(new String[]{name, color, String.valueOf(levelOfIntelligence), gods, team});
        }

        filepath = System.getProperty("user.dir") + File.separator + "savings" + File.separator + saveGameName + File.separator + "player.csv";
        saveCSV(filepath, playerData);

        // Save Figure Information
        List<String[]> figureData = new ArrayList<>();

        for (Player player: playerList){
            ArrayList<Figure> figureList = player.getFigure();
            for (Figure figure: figureList){
                figureData.add(new String[]{Integer.toString(figure.getX()), Integer.toString(figure.getY())});
            }
        }

        filepath = System.getProperty("user.dir") + File.separator + "savings" + File.separator + saveGameName + File.separator + "figure.csv";
        saveCSV(filepath, figureData);

        // Save Field Information
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

        filepath = System.getProperty("user.dir") + File.separator + "savings" + File.separator + saveGameName + File.separator + "fields.csv";
        saveCSV(filepath, fieldsData);
    }

    /**
     * @return the old highscore
     */
    public void loadHighscore() {
        List<String[]> data = loadCSV(System.getProperty("user.dir\\savings\\highscore.csv"));
        String playerOneName = data.get(0)[0];
        String playerOneIntelligence = data.get(0)[1];
        String playerTwoName = data.get(1)[0];
        String playerTwoIntelligence = data.get(1)[1];
        String amountOfTurns = data.get(1)[0];
    }

    /**
     * Saves the new highscore.
     *
     * @param game The game object
     * @param playerList The player who won as list (even if it's just one)
     */
    public void saveHighscore(Game game, ArrayList<Player> playerList){
        String filepath = System.getProperty("user.dir\\savings\\highscore.csv");
        List<String[]> data = new ArrayList<>();

        // Getting the data
        String playerOneName = playerList.get(0).getName();
        String playerTwoName = playerList.get(1).getName();
        Player.PlayerType playerOneIntelligence = playerList.get(0).getLevelOfIntelligence();
        Player.PlayerType playerTwoIntelligence = playerList.get(1).getLevelOfIntelligence();
        String amountOfTurns = Integer.toString(game.getAmountOfTurns());

        // Saving in rows
        data.add(new String[]{playerOneName, String.valueOf(playerOneIntelligence)});
        data.add(new String[]{playerTwoName, String.valueOf(playerTwoIntelligence)});
        data.add(new String[]{amountOfTurns, ""});
        saveCSV(filepath, data);
    }
}