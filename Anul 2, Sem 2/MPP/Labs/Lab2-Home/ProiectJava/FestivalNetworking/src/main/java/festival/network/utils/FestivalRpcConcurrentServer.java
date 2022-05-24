package festival.network.utils;

import festival.network.rpcprotocol.FestivalClientRpcReflectionWorker;
import festival.services.IFestivalServices;

import java.net.Socket;

public class FestivalRpcConcurrentServer extends AbsConcurrentServer{

    private IFestivalServices chatServer;
    public FestivalRpcConcurrentServer(int port, IFestivalServices chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Chat- ChatRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        // ChatClientRpcWorker worker=new ChatClientRpcWorker(chatServer, client);
        FestivalClientRpcReflectionWorker worker=new FestivalClientRpcReflectionWorker(chatServer, client);

        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }

}
