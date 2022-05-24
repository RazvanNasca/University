package app.client;

import app.client.gui.LoginController;
import app.client.gui.MainWindowController;
import app.services.AppException;
import app.services.IAppServices;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class StartClientFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        IAppServices server = (IAppServices)factory.getBean("appService");
        System.out.println("Obtained a reference to remote app server");

        initView(primaryStage, server);
        primaryStage.show();
    }

    private void initView(Stage primaryStage, IAppServices server) throws IOException, AppException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/loginView.fxml"));
        AnchorPane root = loader.load();
        primaryStage.setScene(new Scene(root));

        LoginController loginController = loader.getController();
        loginController.setService(server, primaryStage);
    }
}
