package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.UserType;
import service.IService;
import service.ServiceException;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginController {

    public TextField usernameField;
    public PasswordField passwordField;
    public Label messageToUser;
    public Button signInButton;
    public ComboBox<String> comboBox;

    private Stage stage = new Stage();

    public void setStage(Stage stage1)
    {
        this.stage = stage1;
    }


    private IService service;

    public LoginController() throws RemoteException {
    }

    public void setServer(IService s){
        service = s;
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

    public void handleSubmitButtonAction(ActionEvent actionEvent) throws IOException {

        String nume = usernameField.getText();
        String passwd = passwordField.getText();
        String type = comboBox.getTypeSelector();

        if(type.equals("agent"))
            this.crtUser = new User(nume, passwd, UserType.AGENT);
        else
            this.crtUser = new User(nume, passwd, UserType.ADMIN);

        User user = service.login(crtUser);

        if(user != null)
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/HomeFXML.fxml"));
            GridPane root = loader.load();
            HomeController controller = loader.getController();
            Scene scene = new Scene(root, 650, 300);
            Stage stage = new Stage();
            stage.setTitle("Home");
            stage.setResizable(false);
            stage.setScene(scene);
            controller.setStage(stage);
            stage.show();
        }
        else
        {
            messageToUser.setText("User not found!");
            messageToUser.setTextFill(Color.DARKRED);

            passwordField.setText("");
        }

    }

    public void setHomeController(HomeController chatController) {
        this.chatCtrl = chatController;
    }

}
