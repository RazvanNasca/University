package gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.scene.control.Alert;

public class Utils {

    private static Logger logger = LogManager.getLogger(Utils.class.getName());

    public static void showWarning(String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Festival");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();

    }

}
