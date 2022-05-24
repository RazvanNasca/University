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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;

public class FestivalClientRpcReflectionWorker implements Runnable, IFestivalObserver {

    private IFestivalServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public FestivalClientRpcReflectionWorker(IFestivalServices server, Socket connection)
    {
        this.server = server;
        this.connection = connection;
        try{
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
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

    private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request){
        Response response=null;
        String handlerName = "handle" + (request).type();
        System.out.println("HandlerName " + handlerName);
        try {
            Method method = this.getClass().getDeclaredMethod(handlerName, Request.class);
            response = (Response)method.invoke(this,request);
            System.out.println("Method " + handlerName + " invoked");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return response;
    }

    private Response handleGET_ORDERD_SHOWS(Request request){
        System.out.println("Get ordered shows ..." + request.type());
        LocalDateTime date = (LocalDateTime) request.data();
        try {
            List<Show> shows = server.searchArtistByDate(date);
            return new Response.Builder().type(ResponseType.GET_SHOWS).data(shows).build();
        } catch (FestivalException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleBUY_TICKET(Request request){
        System.out.println("Buy ticket ..." + request.type());
        Buyer buyer = (Buyer)request.data();
        try {
            server.saveBuyer(buyer.getName(), buyer.getNoTickets(), buyer.getShow());
            return okResponse;
        } catch (FestivalException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_SHOWS(Request request){
        System.out.println("Get shows ..." + request.type());
        try {
            List<Show> shows = server.getAllShows();
            return new Response.Builder().type(ResponseType.GET_SHOWS).data(shows).build();
        } catch (FestivalException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGIN(Request request){
        System.out.println("Login request ..." + request.type());
        User user = (User)request.data();
        try {
            server.Login(user, this);
            return okResponse;
        } catch (FestivalException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGOUT(Request request){
        System.out.println("Logout request...");
        User user = (User)request.data();
        try {
            server.logout(user, this);
            connected=false;
            return okResponse;

        } catch (FestivalException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_LOGGED_USERS(Request request){
        System.out.println("GetLoggedUsers Request ...");
        User user = (User)request.data();
        try {
            User[] users = server.getLoggedUsers();
            return new Response.Builder().type(ResponseType.GET_LOGGED_USERS).data(users).build();
        } catch (FestivalException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response " + response);
        synchronized(output) {
            output.writeObject(response);
            output.flush();
        }
    }

}
