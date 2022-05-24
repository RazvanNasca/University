import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class StartServer {

    private static int defaultPort=55555;
    public static void main(String[] args) {

        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");

        /*Properties serverProps=new Properties();
        try {
            serverProps.load(StartServer.class.getResourceAsStream("/chatserver.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }
        RepoBuyerDB repoBuyer = new RepoBuyerDB(serverProps);
        RepoShowDB repoShow = new RepoShowDB(serverProps);
        RepoUserDB repoUser = new RepoUserDB(serverProps);
        IFestivalServices serverImpl = new serviceFestival(repoBuyer,repoShow, repoUser);
        int serverPort=defaultPort;
        try {
            serverPort = Integer.parseInt(serverProps.getProperty("chat.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: " + serverPort);

        AbstractServer server = new FestivalRpcConcurrentServer(serverPort, serverImpl);
        try {
            server.start();
        } catch (festival.network.utils.ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }*/
    }

}
