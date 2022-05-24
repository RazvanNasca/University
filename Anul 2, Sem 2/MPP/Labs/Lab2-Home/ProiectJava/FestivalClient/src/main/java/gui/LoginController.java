package gui;

import festival.model.User;
import festival.services.FestivalException;
import festival.services.IFestivalServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginController extends UnicastRemoteObject {

    public TextField usernameField;
    public PasswordField passwordField;
    public Label messageToUser;
    public Button signInButton;

    private IFestivalServices serviceFestival;

    public LoginController() throws RemoteException {
    }

    public void setServer(IFestivalServices s){
        serviceFestival = s;
    }

    Parent mainPageParent;
    public void setParent(Parent p){
        mainPageParent = p;
    }

    private HomeController chatCtrl;
    private User crtUser;
    public void setUser(User user) {
        this.crtUser = user;
    }

    @FXML
    public void initialize() throws IOException {

        usernameField.setText("");
        passwordField.setText("");

        usernameField.setPromptText("username");
        passwordField.setPromptText("password");

    }

    public void handleSubmitButtonAction(ActionEvent actionEvent) {

        String nume = usernameField.getText();
        String passwd = passwordField.getText();
        this.crtUser = new User(nume, passwd);

        try{

            serviceFestival.Login(crtUser, chatCtrl);

            Stage stage=new Stage();
            stage.setTitle("Chat Window for " + crtUser.getId());
            stage.setScene(new Scene(mainPageParent));

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    chatCtrl.logout();
                    System.exit(0);
                }
            });

            stage.show();
            chatCtrl.setUser(crtUser);
            chatCtrl.putData();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        }
        catch (FestivalException e)
        {
            messageToUser.setText(e.getMessage());
            messageToUser.setTextFill(Color.DARKRED);

            passwordField.setText("");
        }
    }

    public void setHomeController(HomeController chatController) {
        this.chatCtrl = chatController;
    }

}
