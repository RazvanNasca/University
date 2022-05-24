package repository;


import festival.model.Show;

import java.time.LocalDateTime;
import java.util.List;

public interface RepoShow extends CrudRepo<Integer, Show> {

    List<Show> searchArtistByDate(LocalDateTime date);

}
