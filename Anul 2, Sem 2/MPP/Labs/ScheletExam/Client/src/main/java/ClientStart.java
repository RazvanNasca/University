import client.LoginWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IAppServices;

public class ClientStart extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");

        IAppServices server = (IAppServices) factory.getBean("appServices");

        FXMLLoader loader = null;
        loader = new FXMLLoader(getClass().getClassLoader().getResource("loginWindow.fxml"));
        LoginWindowController controller = new LoginWindowController();
        controller.setServer(server);
        controller.setStage(primaryStage);
        loader.setController(controller);

        primaryStage.setScene(new Scene(loader.load()));

        primaryStage.show();
    }
}
