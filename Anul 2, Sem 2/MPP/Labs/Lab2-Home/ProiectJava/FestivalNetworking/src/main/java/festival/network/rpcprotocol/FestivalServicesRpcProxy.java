package festival.network.rpcprotocol;

import festival.model.Buyer;
import festival.model.Show;
import festival.model.User;
import festival.services.FestivalException;
import festival.services.IFestivalObserver;
import festival.services.IFestivalServices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FestivalServicesRpcProxy implements IFestivalServices {

    private String host;
    private int port;

    private IFestivalObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public FestivalServicesRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }

    @Override
    public List<Show> getAllShows() throws FestivalException {

        Request req=new Request.Builder().type(RequestType.GET_SHOWS).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new FestivalException(err);
        }
        List<Show> shows=(List<Show>)response.data();
        return shows;
    }

    @Override
    public List<Buyer> getAllBuyers() {
        return null;
    }

    @Override
    public User Login(User user, IFestivalObserver client) throws FestivalException {
        initializeConnection();
        Request req = new Request.Builder().type(RequestType.LOGIN).data(user).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.OK){
            this.client=client;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new FestivalException(err);
        }
        return null;
    }

    @Override
    public void logout(User user, IFestivalObserver client) throws FestivalException {
        Request req=new Request.Builder().type(RequestType.LOGOUT).data(user).build();
        sendRequest(req);
        Response response=readResponse();
        closeConnection();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new FestivalException(err);
        }
    }

    @Override
    public List<Show> searchArtistByDate(LocalDateTime date) throws FestivalException {
        Request req=new Request.Builder().type(RequestType.GET_ORDERD_SHOWS).data(date).build();
        sendRequest(req);
        Response response=readResponse();
            if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new FestivalException(err);
        }
        List<Show> shows=(List<Show>)response.data();
        return shows;
    }

    @Override
    public Buyer saveBuyer(String name, Integer noTickets, Show Show) throws FestivalException {
        Buyer buyer = new Buyer(name, noTickets, Show);
        Request req = new Request.Builder().type(RequestType.BUY_TICKET).data(buyer).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new FestivalException(err);
        }
        return buyer;
    }

    @Override
    public User[] getLoggedUsers() throws FestivalException {
        Request req = new Request.Builder().type(RequestType.GET_LOGGED_USERS).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new FestivalException(err);
        }
        User[] users = (User[])response.data();
        return users;
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request)throws FestivalException {
        try {
            synchronized(output) {
                output.writeObject(request);
                output.flush();
            }
        } catch (IOException e) {
            throw new FestivalException("Error sending object "+e);
        }

    }

    private Response readResponse() throws FestivalException {
        Response response=null;
        try{

            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() throws FestivalException {
        try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(Response response){
        if (response.type()== ResponseType.USER_LOGGED_IN){

            User user = (User)response.data();
            System.out.println("User logged in " + user);
            try {
                client.userLoggedIn(user);
            } catch (FestivalException | RemoteException e) {
                e.printStackTrace();
            }
        }
        if (response.type() == ResponseType.USER_LOGGED_OUT){
            User user = (User)response.data();
            System.out.println("User logged out " + user);
            try {
                client.userLoggedOut(user);
            } catch (FestivalException | RemoteException e) {
                e.printStackTrace();
            }
        }

        if (response.type()== ResponseType.UPDATED_SHOW){
            Show show = (Show)response.data();
            try {
                client.showUpdate(show);
            } catch (FestivalException | RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isUpdate(Response response){
        return response.type() == ResponseType.USER_LOGGED_OUT || response.type() == ResponseType.USER_LOGGED_IN || response.type() == ResponseType.UPDATED_SHOW;
    }
    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (isUpdate((Response)response)){
                        handleUpdate((Response)response);
                    }else{

                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }

}
