package client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import model.AppUser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import services.*;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;

public class MainController extends UnicastRemoteObject implements Initializable, IObserver {


    public Button logOutButton;
    public Button buyerButton;
    public TableView<AppUser> usersTable;

    ObservableList<String> users = FXCollections.observableArrayList();

    private IServices service;
    private AppUser user;

    public MainController() throws RemoteException { }

    public MainController(IServices serviceFestival) throws RemoteException {
        this.service = serviceFestival;
    }

    public void setServer(IServices s) {
        service = s;
    }


    public void putData() throws MyException {
       /* shows.clear();
        shows.addAll(service.getAllShows());

        usersTable.getItems().clear();
        usersTable.getItems().addAll(service.getLoggedUsers());*/
    }


    public void handleLogOutButtonAction(ActionEvent actionEvent) {
        logout();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }


    private void showNotification(String message, Alert.AlertType type){
        Alert alert=new Alert(type);
        alert.setTitle("Notification");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean checkString(String s){
        return s != null && !s.isEmpty();
    }

    /*public void buyerSummit(ActionEvent actionEvent) {
        int selectedRequest = allShowTableForBuyer.getSelectionModel().getSelectedIndex();
        if (selectedRequest<0){
            showNotification("You must select a request first! ", Alert.AlertType.ERROR);
            return;
        }
        String name = buyerName.getText();
        int noTickets =  noTicketsBuyed.getValue();
        if (checkString(name) && noTickets > 0){
            try{
                Show show = allShowTableForBuyer.getSelectionModel().getSelectedItem();

                if(show.getRemainingTickets() < noTickets)
                    throw new FestivalException("Insufficient tickets!");

                service.saveBuyer(name, noTickets, show);
                buyerName.setText("");
                showNotification("RepairedForm successfully added! ", Alert.AlertType.INFORMATION);
            }catch (NumberFormatException ex){
                showNotification("The price must be a number! ", Alert.AlertType.ERROR);
            } catch (FestivalException e) {
                showNotification(e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }*/

    void logout() {
        try {
            service.logout(user, this);
        } catch (MyException e) {
            System.out.println("Logout error " + e);
        }

    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public void setLoggedUsers() {
        try {
            AppUser[] users = service.getLoggedUsers();
            usersTable.getItems().clear();
            for (AppUser u : users) {
                usersTable.getItems().add(u);
            }
            if(usersTable.getItems().size()>0)
                usersTable.getSelectionModel().select(0);

        } catch (MyException e) {
            e.printStackTrace();
        }
    }

   /* @Override
    public void showUpdate(Show show) throws FestivalException {
        Optional<Show> showFound = shows.stream().filter(x -> x.getArtistName().equals(show.getArtistName())).findFirst();
        shows.set(shows.indexOf(showFound.get()),show);
    }*/

    @Override
    public void userLoggedIn(AppUser user) throws MyException {
        usersTable.getItems().add(user);
        System.out.println("Friends logged in " + user.getName());

    }

    @Override
    public void userLoggedOut(AppUser user) throws MyException {
        usersTable.getItems().remove(user);
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("INIT : am in lista " + users.size() + " users");

        /*allShowTable.getItems().clear();
        allShowTable.setItems(shows);
        allShowTableForBuyer.setItems(shows);
*/

        System.out.println("END INIT!!!!!!!!!");
    }
}
