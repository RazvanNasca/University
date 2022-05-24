package festival.services;

import festival.model.Show;
import festival.model.User;

public interface IFestivalObserver {

    void showUpdate(Show show) throws FestivalException;
    void userLoggedIn(User user) throws FestivalException;
    void userLoggedOut(User user) throws FestivalException;

}
