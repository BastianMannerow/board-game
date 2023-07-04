package tnt.remote;


import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;

public class ReadLineService extends ScheduledService<String> {
    public ReadLineService() {
        super();
        setRestartOnFailure(false);
    }

    private BufferedReader bufferedReader;

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

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }
}

