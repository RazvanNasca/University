package client;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;
import model.AppUser;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.*;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginController extends UnicastRemoteObject {

    public TextField usernameField;
    public PasswordField passwordField;
    public Label messageToUser;

    private IServices service;

    public LoginController() throws RemoteException {
    }

    public void setServer(IServices s){
        service = s;
    }

    Parent mainPageParent;
    public void setParent(Parent p){
        mainPageParent = p;
    }

    private MainController currentCtrl;
    private AppUser crtUser;
    public void setUser(AppUser user) {
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
        this.crtUser = new AppUser(nume, passwd);

        try{

            service.Login(crtUser, currentCtrl);

            Stage stage=new Stage();
            stage.setTitle("Chat Window for " + crtUser.getName());
            stage.setScene(new Scene(mainPageParent));

            stage.setOnCloseRequest(new EventHandler<javafx.stage.WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    currentCtrl.logout();
                    System.exit(0);
                }
            });

            stage.show();
            currentCtrl.setUser(crtUser);
            currentCtrl.putData();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        }
        catch (MyException e)
        {
            messageToUser.setText(e.getMessage());
            messageToUser.setTextFill(Color.DARKRED);

            passwordField.setText("");
        }
    }

    public void setHomeController(MainController currentController) {
        this.currentCtrl = currentController;
    }

}
