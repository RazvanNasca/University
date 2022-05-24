package app.client.gui;

import app.model.*;
import app.services.AppException;
import app.services.IAppObserver;
import app.services.IAppServices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class MainWindowController extends UnicastRemoteObject implements IAppObserver, Serializable
{
    IAppServices service;
    Stage dialogStage;
    User user = null;

    /*ObservableList<ManaDTO> game = FXCollections.observableArrayList();*/

    @FXML
    Label labelMessages;

    /*@FXML
    TableView<ManaDTO> playersTable;
    @FXML
    TableColumn<ManaDTO, String> columnUser;
    @FXML
    TableColumn<ManaDTO, String> columnChars;
*/
    @FXML
    TextField word;
    @FXML
    TextField chosenChar;

    @FXML
    Button startButton;
    @FXML
    Button sendButton;
    @FXML
    Button logoutButton;

    public MainWindowController() throws RemoteException { }

    public void setService(IAppServices serviceAll, String username, String password, Stage dialogStage) throws AppException
    {
        this.service = serviceAll;
        this.dialogStage = dialogStage;
        user = service.login(username, password, this);
        dialogStage.setTitle("Window for " + user.getUsername() + "!");
        dialogStage.setOnCloseRequest(e -> {
            service.logout(user);
            Platform.exit();
            System.exit(0);
        });
    }

    @FXML
    public void initialize()
    {
      /*  columnUser.setCellValueFactory(new PropertyValueFactory<>("username"));
        columnChars.setCellValueFactory(new PropertyValueFactory<>("obiect"));
        playersTable.setItems(game);*/
    }

    public void showLogin()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/loginView.fxml"));
            AnchorPane root = loader.load();

            //Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Welcome!");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            LoginController welcomeController = loader.getController();
            welcomeController.setService(service, dialogStage);
            dialogStage.show();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void handleLogout()
    {
        service.logout(user);
        dialogStage.close();
        showLogin();
    }

    public void handleStart()
    {
        try
        {
            String chosenWord = word.getText();
            service.startGame(user, chosenWord);
            //game.clear();
            labelMessages.setText("Waiting...");
            startButton.setDisable(true);
            logoutButton.setDisable(true);
        } catch (AppException ex){
            labelMessages.setText(ex.getMessage());
        }
    }

    public void handleSendGuess()
    {
       /* try
        {
            ManaDTO guess = playersTable.getSelectionModel().getSelectedItem();
            if(guess == null)
                throw new AppException("Choose a player!");
            Character litera = chosenChar.getCharacters().charAt(0);
            service.sendGuess(user, guess.getUsername(), litera);
            sendButton.setDisable(true);
            labelMessages.setText("Waiting...");
        }catch (AppException e)
        {
            labelMessages.setText(e.getMessage());
        }*/
    }

}
