package repository.db;

import festival.model.Show;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.RepoShow;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepoShowDB implements RepoShow {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger(RepoShowDB.class);

    public RepoShowDB(Properties props) {
        logger.info("Initializing RepoShowDB with properties: {} ",props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Show findOne(Integer id) {
        logger.traceEntry();
        Connection connection =  dbUtils.getConnection();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Show show = null;
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM shows WHERE idShow = (?)"))
        {
            statement.setInt(1, id);
            try(ResultSet result = statement.executeQuery())
            {
                while(result.next())
                {
                    int idShow = result.getInt("idShow");
                    String artistName = result.getString("artistName");
                    LocalDateTime date = LocalDateTime.parse(result.getString("date"), formatter);
                    String place = result.getString("place");
                    int remainingTickets = result.getInt("remainingTickets");
                    show = new Show(artistName, date, place, remainingTickets);
                    show.setId(idShow);
                }
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(show);
        return show;
    }

    @Override
    public List<Show> findAll() {
        logger.traceEntry();
        Connection connection =  dbUtils.getConnection();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        List<Show> shows = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM shows"))
        {
            try(ResultSet result = statement.executeQuery())
            {
                while(result.next())
                {
                    int id = result.getInt("idShow");
                    String artistName = result.getString("artistName");
                    LocalDateTime date = LocalDateTime.parse(result.getString("date"), formatter);
                    String place = result.getString("place");
                    int remainingTickets = result.getInt("remainingTickets");
                    Show show = new Show(artistName, date, place, remainingTickets);
                    show.setId(id);
                    shows.add(show);
                }
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(shows);
        return shows;
    }

    @Override
    public Show save(Show show)
    {
        logger.traceEntry("savinng task{}", show);
        Connection connection =  dbUtils.getConnection();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO show (idShow, artistName, date, place, remainingtickets) VALUES (?,?,?,?,?)"))
        {
            statement.setInt(1, show.getId());
            statement.setString(2, show.getArtistName());
            statement.setString(3, show.getData().format(formatter));
            statement.setString(4, show.getPlace());
            statement.setInt(5, show.getRemainingTickets());

            int result = statement.executeUpdate();
            logger.trace("Saved {} instances", result);

        }
        catch (SQLException e)
        {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        return show;
    }

    @Override
    public List<Show> searchArtistByDate(LocalDateTime date) {
        logger.traceEntry();
        Connection connection =  dbUtils.getConnection();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDate = date.format(formatter);
        formattedDate += "%";

        List<Show> shows = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM shows WHERE date LIKE (?) "))
        {
            statement.setString(1, formattedDate);
            try(ResultSet result = statement.executeQuery())
            {
                while(result.next())
                {
                    int id = result.getInt("idShow");
                    String artistName = result.getString("artistName");
                    LocalDateTime showDate = LocalDateTime.parse(result.getString("date"), formatter1);
                    String place = result.getString("place");
                    int remainingTickets = result.getInt("remainingTickets");
                    Show show = new Show(artistName, showDate, place, remainingTickets);
                    show.setId(id);
                    shows.add(show);
                }
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(shows);
        return shows;
    }

}
