package map.seminar13.sample;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

public class Controller {
    @FXML
    ProgressBar progressBar;

    @FXML
    ProgressIndicator indicatorBar;

    @FXML
    Label labelStatus;

    Task loadingDataTask = new Task<Void>() {
        @Override public Void call() {
            final int max = 100000000;
            for (int i=1; i<=max; i++) {
                if (isCancelled()) {
                    updateMessage("Cancelled!");
                    break;
                }
                updateProgress(i, max);
                updateMessage("loading...");
            }
            return null;
        }
    };

    @FXML public void initialize()
    {
        progressBar.setProgress(0);
        indicatorBar.setProgress(0);
       // progressBar.progressProperty().bind(loadingDataTask.progressProperty());
        indicatorBar.progressProperty().bind(loadingDataTask.progressProperty());
        labelStatus.textProperty().bind(loadingDataTask.messageProperty());
        new Thread(loadingDataTask).start();
    }


}
