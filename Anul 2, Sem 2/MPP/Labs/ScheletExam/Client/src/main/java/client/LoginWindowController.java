package client;

import model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.IAppServices;
import services.ServiceException;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class LoginWindowController implements Initializable
{
    IAppServices server;
    Stage stage;

    @FXML
    TextField textFieldEmail;

    @FXML
    TextField textFieldPassword;

    @FXML
    Button buttonLogin;

    public void setServer(IAppServices server) {
        this.server = server;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        buttonLogin.setOnMouseClicked(x->
        {
            try{
                User foundUser = server.userLogin(textFieldEmail.getText() , textFieldPassword.getText());
                MainMenuController controller = new MainMenuController();
                FXMLLoader loader = null;

                loader = new FXMLLoader(getClass().getClassLoader().getResource("userWindow.fxml"));

                controller.setServer(this.server);
                controller.setCurrentStage(stage);
                controller.setCurrentUser(foundUser);
                loader.setController(controller);

                stage.setScene(new Scene(loader.load()));
                stage.show();
            }
            catch (ServiceException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }
}
