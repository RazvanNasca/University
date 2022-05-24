package controller;

import javafx.fxml.Initializable;

import javafx.stage.Stage;
import model.Product;
import model.User;
import service.Observer;

import java.net.URL;
import java.util.ResourceBundle;


public class HomeController implements Initializable, Observer {

    private Stage stage = new Stage();

    public void setStage(Stage stage1)
    {
        this.stage = stage1;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void update(Product prod) {

    }

    @Override
    public void userLoggedIn(User user) {

    }

    @Override
    public void userLoggedOut(User user) {

    }
}
