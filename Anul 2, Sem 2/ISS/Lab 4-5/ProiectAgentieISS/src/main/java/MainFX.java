import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
public class MainFX extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(getClass().getResource("/LoginFXML.fxml")); //URL
        GridPane root = loader.load();
        LoginController controller = loader.getController();
        Scene scene = new Scene(root, 400, 500);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false);
        controller.setStage(stage);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
