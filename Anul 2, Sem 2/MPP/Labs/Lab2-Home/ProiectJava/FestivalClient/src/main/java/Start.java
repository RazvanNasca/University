import festival.services.IFestivalServices;
import gui.HomeController;
import gui.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start extends Application {

    private static int defaultChatPort=55555;
    private static String defaultServer="localhost";


    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
       /* Properties clientProps = new Properties();
        try {
            clientProps.load(Start.class.getResourceAsStream("/chatclient.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("chat.server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("chat.server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        IFestivalServices server = new FestivalServicesRpcProxy(serverIP, serverPort);*/

        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        IFestivalServices server=(IFestivalServices)factory.getBean("festivalService");
        System.out.println("Obtained a reference to remote chat server");
        //HomeController ctrl1 = new HomeController(server);

        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("LoginFXML.fxml"));
        Parent root = loader.load();

        LoginController ctrl =
                loader.<LoginController>getController();
        ctrl.setServer(server);


        FXMLLoader cloader = new FXMLLoader(
                getClass().getClassLoader().getResource("HomeFXML.fxml"));
        Parent croot = cloader.load();


        HomeController chatCtrl =
                cloader.<HomeController>getController();
        chatCtrl.setServer(server);

        ctrl.setHomeController(chatCtrl);
        ctrl.setParent(croot);

        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 300, 130));
        primaryStage.show();
    }

}
