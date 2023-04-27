package tnt.model;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The FileManager is responsible for permanent savings as csv-files.
 */
public class FileManager {

    /**
     * loads an old game, which is saved as a csv
     * @return boolean if the loading was successful/maybe there is no csv
     */
    public void loadCSV() {
        // Void muss noch zu return geändert werden
    }

    /**
     * @return the old highscore
     */
    public void getHighscore() {
        ArrayList<String> highscore = new ArrayList<>();
        // zuganzahl = int;
        // spielername = String;
        // human = boolean;
        // return (zuganzahl, spielername, human);
    }

    /**
     * @param highscore sets a new highscore
     */
    public void setHighscore(){
        ArrayList<String> highscore = new ArrayList<>();
    }

}
