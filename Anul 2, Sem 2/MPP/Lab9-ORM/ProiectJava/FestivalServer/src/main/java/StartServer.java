import java.io.IOException;
import java.util.Properties;


import festival.network.utils.AbstractServer;
import festival.network.utils.FestivalRpcConcurrentServer;
import festival.server.serviceFestival;
import festival.services.IFestivalServices;
import repository.db.RepoBuyerDB;
import repository.db.RepoShowDB;
import repository.orm.RepoUserORM;


public class StartServer {

    private static int defaultPort=55555;
    public static void main(String[] args) {
        Properties serverProps=new Properties();
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
        RepoUserORM repoUser = new RepoUserORM();
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
        }
    }

}
