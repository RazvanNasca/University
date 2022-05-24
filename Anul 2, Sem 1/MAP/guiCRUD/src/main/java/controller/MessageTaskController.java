package controller;


import domain.MessageTask;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import services.MessageTaskService;
import utils.events.MessageTaskChangeEvent;
import utils.events.TaskStatusEvent;
import utils.observer.Observer;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MessageTaskController implements Observer<MessageTaskChangeEvent> {
    MessageTaskService service;
    ObservableList<MessageTask> model = FXCollections.observableArrayList();


    @FXML
    TableView<MessageTask> tableView;
    @FXML
    TableColumn<MessageTask,String> tableColumnDesc;
    @FXML
    TableColumn<MessageTask,String> tableColumnFrom;
    @FXML
    TableColumn<MessageTask,String> tableColumnTo;
    @FXML
    TableColumn<MessageTask,String> tableColumnData;

    public void setMessageTaskService(MessageTaskService messageTaskService) {
        service = messageTaskService;
        service.addObserver(this);
        initModel();
    }

    @FXML
    public void initialize() {
        // TODO
    }

    private void initModel() {
        // TODO
    }



    public void handleDeleteMessage(ActionEvent actionEvent) {
        // TODO
    }

    @Override
    public void update(MessageTaskChangeEvent messageTaskChangeEvent) {

        initModel();
    }

    @FXML
    public void handleUpdateMessage(ActionEvent ev) {
        // TODO
    }

    @FXML
    public void handleAddMessage(ActionEvent ev) {

        showMessageTaskEditDialog(null);
    }

    public void showMessageTaskEditDialog(MessageTask messageTask) {
        // TODO
    }



}
