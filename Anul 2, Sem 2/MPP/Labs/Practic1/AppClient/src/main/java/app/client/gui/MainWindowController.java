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
import java.util.stream.Collectors;

public class MainWindowController extends UnicastRemoteObject implements IAppObserver, Serializable {

    IAppServices service;
    Stage dialogStage;
    User user = null;

    ObservableList<ManaDTO> joc = FXCollections.observableArrayList();

    @FXML
    Label labelMessages;

    @FXML
    TableView<ManaDTO> tableJucatori;
    @FXML
    TableColumn<ManaDTO, String> columnUser;
    @FXML
    TableColumn<ManaDTO, String> columnCuvinte;

    @FXML
    TextField textFieldCuvant;

    @FXML
    TextField textFieldLitera;

    @FXML
    Button btnStartJoc;
    @FXML
    Button btnTrimite;
    @FXML
    Button btnLogout;

    public MainWindowController() throws RemoteException {
    }

    public void setService(IAppServices serviceAll, String username, String password, Stage dialogStage) throws AppException {
        this.service = serviceAll;
        this.dialogStage = dialogStage;
        user = service.login(username, password, this);
        dialogStage.setTitle("Welcome, " + user.getUsername() + "!");
        dialogStage.setOnCloseRequest(e -> {
            service.logout(user);
            Platform.exit();
            System.exit(0);
        });
    }

    @FXML
    public void initialize() {
        columnUser.setCellValueFactory(new PropertyValueFactory<>("username"));
        columnCuvinte.setCellValueFactory(new PropertyValueFactory<>("cuvInitial"));
        tableJucatori.setItems(joc);
    }

    public void showLogin() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleLogout() {
        service.logout(user);
        dialogStage.close();
        showLogin();
    }

    public void handleStartJoc(){
        try{
            String cuvant = textFieldCuvant.getText();
            service.startJoc(user, cuvant);

            joc.clear();
            labelMessages.setText("Waiting for the other players...");
            btnStartJoc.setDisable(true);
            btnLogout.setDisable(true);

        } catch (AppException ex){
            labelMessages.setText(ex.getMessage());
        }
    }

    public void handleTrimiteMana(){
        try{
            ManaDTO alegere = tableJucatori.getSelectionModel().getSelectedItem();
            if(alegere == null)
                throw new AppException("Trebuie sa alegi un jucator!");
            String litera = textFieldLitera.getText();

            service.trimiteMana(user, alegere.getUsername(), litera);
            btnTrimite.setDisable(true);
            labelMessages.setText("Waiting for the other players..");

        }catch (AppException e){
            labelMessages.setText(e.getMessage());
        }
    }

    @Override
    public void jocNou(List<ManaDTO> usernames) throws RemoteException {
        Platform.runLater(()->{
            joc.setAll(usernames);
            labelMessages.setText("");
            btnTrimite.setDisable(false);
        });
    }

    @Override
    public void sfRunda(List<ManaDTO> mana) throws RemoteException {
        Platform.runLater(()->{
            List<ManaDTO> jucatori = new ArrayList<>(joc);

            for(ManaDTO m : mana){
                for(ManaDTO mm : jucatori){
                    if(m.getUsername().equals(mm.getUsername())){

                        String cuvInit = mm.getCuvInitial();
                        char[] str = cuvInit.toCharArray();

                        for(int i = 0; i < m.getCuvInitial().length(); i++)
                            if(m.getLitera().charAt(0) == m.getCuvInitial().charAt(i))
                                str[i] = m.getCuvInitial().charAt(i);

                        cuvInit = String.valueOf(str);
                        mm.setCuvInitial(cuvInit);
                    }
                }
            }
            joc.setAll(jucatori);
            btnTrimite.setDisable(false);
            labelMessages.setText("");
        });
    }

    @Override
    public void clasament(List<ManaDTO> clasament) throws RemoteException {
        Platform.runLater(()->{

            labelMessages.setText("");
            btnStartJoc.setDisable(false);
            btnLogout.setDisable(false);
            btnTrimite.setDisable(true);
            joc.clear();

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/clasamentView.fxml"));
                AnchorPane root = loader.load();

                //Stage
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                Scene scene = new Scene(root);
                stage.setScene(scene);

                ClasamentController clasamentController = loader.getController();
                clasamentController.setClasament(stage, clasament);
                stage.showAndWait();
            } catch (IOException ex) {
                labelMessages.setPrefHeight(20 * ex.getMessage().split("\n").length);
                labelMessages.setText(ex.getMessage());
            }
        });
    }
}
