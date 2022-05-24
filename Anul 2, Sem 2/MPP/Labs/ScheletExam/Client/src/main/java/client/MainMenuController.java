package client;

import model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import services.IAppObserver;
import services.IAppServices;

import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;

public class MainMenuController extends UnicastRemoteObject implements Initializable, Serializable, IAppObserver
{
    private User currentUser;
    private IAppServices server;
    private Stage currentStage;

    public void setCurrentUser(User currentAdmin) {
        this.currentUser = currentAdmin;
    }

    public void setServer(IAppServices server) {
        this.server = server;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    @FXML
    Button buttonLogout;

    @FXML
    Label labelUsername;

    public MainMenuController() throws RemoteException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelUsername.setText(currentUser.getUsername());
        buttonLogout.setOnMouseClicked(x->
        {

        });
    }
}
