package gui;

import festival.model.Show;
import festival.model.User;
import festival.services.FestivalException;
import festival.services.IFestivalObserver;
import festival.services.IFestivalServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController implements Initializable, IFestivalObserver {


    public Button logOutButton;
    public DatePicker dateOfConcert;
    public Button searchButton;
    public TableView<Show> artistTable;
    public TableView<Show> allShowTable;
    public TableView<Show> allShowTableForBuyer;
    public TextField buyerName;
    public Button buyerButton;
    public Spinner<Integer> noTicketsBuyed;
    public TableView<User> usersTable;

    ObservableList<String> users = FXCollections.observableArrayList();
    ObservableList<Show> shows = FXCollections.observableArrayList();

    private IFestivalServices serviceFestival;
    private User user;

    public HomeController(){}

    public HomeController(IFestivalServices serviceFestival){this.serviceFestival = serviceFestival;}

    public void setServer(IFestivalServices s) {
        serviceFestival = s;
    }


    public void putData() throws FestivalException {
        shows.clear();
        shows.addAll(serviceFestival.getAllShows());

        usersTable.getItems().clear();
        usersTable.getItems().addAll(serviceFestival.getLoggedUsers());
    }


    public void handleLogOutButtonAction(ActionEvent actionEvent) {
        logout();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void searchButtonHandler(ActionEvent actionEvent) throws FestivalException {
        try {
            LocalDateTime date = dateOfConcert.getValue().atStartOfDay();
            List<Show> results = serviceFestival.searchArtistByDate(date);

            artistTable.getItems().clear();
            artistTable.getItems().addAll(results);
        }
        catch(FestivalException e)
        {
            System.out.println("Error " + e);
        }
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

    public void buyerSummit(ActionEvent actionEvent) {
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

                serviceFestival.saveBuyer(name, noTickets, show);
                buyerName.setText("");
                showNotification("RepairedForm successfully added! ", Alert.AlertType.INFORMATION);
            }catch (NumberFormatException ex){
                showNotification("The price must be a number! ", Alert.AlertType.ERROR);
            } catch (FestivalException e) {
                showNotification(e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    void logout() {
        try {
            serviceFestival.logout(user, this);
        } catch (FestivalException e) {
            System.out.println("Logout error " + e);
        }

    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLoggedUsers() {
        try {
            User[] users = serviceFestival.getLoggedUsers();
            usersTable.getItems().clear();
            for (User u : users) {
                usersTable.getItems().add(u);
            }
            if(usersTable.getItems().size()>0)
                usersTable.getSelectionModel().select(0);

        } catch (FestivalException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showUpdate(Show show) throws FestivalException {
        Optional<Show> showFound = shows.stream().filter(x -> x.getArtistName().equals(show.getArtistName())).findFirst();
        shows.set(shows.indexOf(showFound.get()),show);
    }

    @Override
    public void userLoggedIn(User user) throws FestivalException {
        usersTable.getItems().add(user);
        System.out.println("Friends logged in " + user.getName());

    }

    @Override
    public void userLoggedOut(User user) throws FestivalException {
        usersTable.getItems().remove(user);
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("INIT : am in lista " + users.size() + " users");

        allShowTable.getItems().clear();
        allShowTable.setItems(shows);
        allShowTableForBuyer.setItems(shows);


        System.out.println("END INIT!!!!!!!!!");
    }
}
