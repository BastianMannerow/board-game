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
     * @param filepath
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
     * @param filepath
     * @param data
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
    public void loadGame(){
        System.out.println("Hallihallo");
    }

    public void saveGame(){
        System.out.println("Hallihallo");
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
        List<String[]> csvData = new ArrayList<>();

        // Getting the data
        String playerOneName = playerList.get(0).getName();
        String playerTwoName = playerList.get(1).getName();
        String playerOneIntelligence = playerList.get(0).getLevelOfIntelligence();
        String playerTwoIntelligence = playerList.get(1).getLevelOfIntelligence();
        String amountOfTurns = Integer.toString(game.getAmountOfTurns());

        // Saving in rows
        csvData.add(new String[]{playerOneName, playerOneIntelligence});
        csvData.add(new String[]{playerTwoName, playerTwoIntelligence});
        csvData.add(new String[]{amountOfTurns, ""});
        saveCSV(filepath, csvData);
    }
}