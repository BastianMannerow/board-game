package tnt.remote;


import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;

/**
 * Service for reading the lines comming from connection partner
 */
public class ReadLineService extends ScheduledService<String> {

    private BufferedReader bufferedReader;

    /**
     * constructor for the readline service
     */
    public ReadLineService() {
        super();
        setRestartOnFailure(false);
    }
    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            @Override
            protected String call() throws IOException {
                String line = bufferedReader.readLine();
                if (line == null) {
                    throw new EOFException("End of socket reached");
                }
                return line;
            }
        };
    }

    /**
     * setter for the buffered reader
     * @param bufferedReader the reader to be set
     */
    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }
}

