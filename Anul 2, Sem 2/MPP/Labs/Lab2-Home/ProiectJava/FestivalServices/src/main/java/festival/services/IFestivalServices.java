package festival.services;

import festival.model.Buyer;
import festival.model.Show;
import festival.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface IFestivalServices {

    public List<Show> getAllShows() throws FestivalException;

    public List<Buyer> getAllBuyers();

    public User Login(User user, IFestivalObserver client) throws FestivalException;

    public void logout(User user, IFestivalObserver client) throws FestivalException;

    public List<Show> searchArtistByDate(LocalDateTime date) throws FestivalException;

    public Buyer saveBuyer(String name, Integer noTickets, Show Show) throws FestivalException;

    User[] getLoggedUsers() throws FestivalException;

}
