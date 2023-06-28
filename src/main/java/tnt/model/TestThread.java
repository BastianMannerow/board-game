package tnt.model;


import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * A test class for multithreading
 */
public class TestThread extends Service<Integer> {
    ExecuteStuff ex;

    public TestThread(ExecuteStuff ex) {
        this.ex = ex;
    }

    @Override
    protected Task createTask() {
        return new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                for (int i = 0; i<1000; i++){
                    for (int j = 0; j<10000000; j++){
                        if (isCancelled()){
                            return 0;
                        }
                    }
                }
                ex.doSmth();
                return 5;
            }
        };
    }
}