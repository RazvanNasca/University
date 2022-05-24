package app.client.gui;

import app.model.Mana;
import app.model.ManaDTO;
import app.services.IAppServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class ClasamentController {
    Stage dialogStage;
    ObservableList<ManaDTO> clasament = FXCollections.observableArrayList();
    @FXML
    TableView<ManaDTO> tableClasament;
    @FXML
    TableColumn<ManaDTO, String> columnUser;
    @FXML
    TableColumn<ManaDTO, Integer> columnNrCarti;

    public void setClasament(Stage stage, List<ManaDTO> cl) {
        dialogStage = stage;
        clasament.setAll(cl);
    }

    public void initialize() {
        columnUser.setCellValueFactory(new PropertyValueFactory<>("username"));
        columnNrCarti.setCellValueFactory(new PropertyValueFactory<>("punctaj"));
        tableClasament.setItems(clasament);
    }

    public void handeClose(){
        dialogStage.close();
    }
}
