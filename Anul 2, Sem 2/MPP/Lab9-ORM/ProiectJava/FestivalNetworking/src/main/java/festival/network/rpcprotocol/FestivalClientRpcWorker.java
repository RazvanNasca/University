package festival.network.rpcprotocol;

import festival.model.Show;
import festival.model.User;
import festival.services.FestivalException;
import festival.services.IFestivalObserver;
import festival.services.IFestivalServices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class FestivalClientRpcWorker implements Runnable, IFestivalObserver {

    private IFestivalServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public FestivalClientRpcWorker(IFestivalServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(connected){
            try {
                Object request = input.readObject();
                Response response = handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    @Override
    public void showUpdate(Show show) throws FestivalException {
        Response resp = new Response.Builder().type(ResponseType.UPDATED_SHOW).data(show).build();
        System.out.println("show updated  " + show);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            throw new FestivalException("Sending error: "+e);
        }
    }

    @Override
    public void userLoggedIn(User user) throws FestivalException {
        Response resp = new Response.Builder().type(ResponseType.USER_LOGGED_IN).data(user).build();
        System.out.println("User logged in "+ user);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void userLoggedOut(User user) throws FestivalException {
        Response resp = new Response.Builder().type(ResponseType.USER_LOGGED_OUT).data(user).build();
        System.out.println("User logged out "+ user);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Response okResponse=new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request){
        Response response=null;
        if (request.type() == RequestType.LOGIN){
            System.out.println("Login request ..." + request.type());
            User user = (User)request.data();
            try {
                server.Login(user, this);
                return okResponse;
            } catch (FestivalException e) {
                connected = false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.type() == RequestType.LOGOUT){
            System.out.println("Logout request");
            // LogoutRequest logReq=(LogoutRequest)request;
            User user = (User)request.data();
            try {
                server.logout(user, this);
                connected=false;
                return okResponse;

            } catch (FestivalException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.type() == RequestType.SEND_SHOW){
            System.out.println("SendShowRequest ...");
            Show show = (Show)request.data();
//            try {
//                server.
//                return okResponse;
//            } catch (FestivalException e) {
//                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
//            }
        }

        if (request.type()== RequestType.GET_LOGGED_USERS){
            System.out.println("GetLoggedUsers Request ...");
            User user = (User)request.data();
            try {
                User[] users = server.getLoggedUsers();
                return new Response.Builder().type(ResponseType.GET_LOGGED_USERS).data(users).build();
            } catch (FestivalException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        return response;
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response " + response);
        synchronized(output) {
            output.writeObject(response);
            output.flush();
        }
    }

}
