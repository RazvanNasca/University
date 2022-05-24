import client.LoginController;
import client.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IServices;

public class ClientStart extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        IServices server=(IServices)factory.getBean("appServices");
        System.out.println("Obtained a reference to remote server");
        //MainController ctrl1 = new MainController(server);

        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("loginWindows.fxml"));
        Parent root = loader.load();

        LoginController ctrl =
                loader.<LoginController>getController();
        ctrl.setServer(server);


        FXMLLoader cloader = new FXMLLoader(
                getClass().getClassLoader().getResource("HomeFXML.fxml"));
        Parent croot = cloader.load();


        MainController mainCtrl =
                cloader.<MainController>getController();
        mainCtrl.setServer(server);

        ctrl.setHomeController(mainCtrl);
        ctrl.setParent(croot);

        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 300, 130));
        primaryStage.show();
    }
}
