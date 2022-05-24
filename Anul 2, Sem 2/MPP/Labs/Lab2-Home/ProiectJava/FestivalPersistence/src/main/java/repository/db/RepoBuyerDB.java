package repository.db;


import festival.model.Buyer;
import festival.model.Show;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.RepoBuyer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepoBuyerDB implements RepoBuyer {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger(RepoBuyerDB.class);

    public RepoBuyerDB(Properties props) {
        logger.info("Initializing RepoBuyerDB with properties: {} ",props);
        dbUtils = new JdbcUtils(props);
    }

    /*public RepoBuyerDB(){

    }*/

    @Override
    public Buyer findOne(Integer integer)
    {
        logger.traceEntry();
        Connection connection =  dbUtils.getConnection();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Buyer buyer = null;
        try(PreparedStatement statement = connection.prepareStatement("select * from Buyers B " +
                "inner join BuyerShow BS on B.idBuyer = BS.idBuyer " +
                "inner join Shows S on S.idShow = BS.idShow " +
                "where B.idbuyer = (?)"))
        {
            statement.setInt(1, integer);
            try(ResultSet result = statement.executeQuery())
            {
                if(result.next())
                {
                    int idShow = result.getInt("idShow");
                    String artistName = result.getString("artistName");
                    LocalDateTime date = LocalDateTime.parse(result.getString("date"), formatter);
                    String place = result.getString("place");
                    int remainingTickets = result.getInt("remainingTickets");
                    Show show = new Show(artistName, date, place, remainingTickets);
                    show.setId(idShow);

                    int idBuyer = result.getInt("id");
                    String name = result.getString("name");
                    int noTickets = result.getInt("noTickets");

                    buyer = new Buyer(name, noTickets, show);
                    buyer.setId(idBuyer);
                }
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(buyer);
        return buyer;
    }

    @Override
    public List<Buyer> findAll() {
        logger.traceEntry();
        Connection connection =  dbUtils.getConnection();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<Buyer> buyers = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("select * from Buyers B " +
                "inner join BuyerShow BS on B.idBuyer = BS.idBuyer " +
                "inner join Shows S on S.idShow = BS.idShow "))
        {
            try(ResultSet result = statement.executeQuery())
            {
                while (result.next())
                {
                    int idShow = result.getInt("idShow");
                    String artistName = result.getString("artistName");
                    LocalDateTime date = LocalDateTime.parse(result.getString("date"), formatter);
                    String place = result.getString("place");
                    int remainingTickets = result.getInt("remainingTickets");
                    Show show = new Show(artistName, date, place, remainingTickets);
                    show.setId(idShow);

                    int idBuyer = result.getInt("idBuyer");
                    String name = result.getString("name");
                    int noTickets = result.getInt("noTickets");

                    Buyer buyer = new Buyer(name, noTickets, show);
                    buyer.setId(idBuyer);
                    buyers.add(buyer);
                }
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(buyers);
        return buyers;
    }

    @Override
    public Buyer save(Buyer buyer) {
        logger.traceEntry("savinng task{}", buyer);
        Connection connection =  dbUtils.getConnection();
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO buyers (idBuyer, noTickets, name) VALUES (?,?,?)"))
        {
            statement.setInt(1, buyer.getId());
            statement.setInt(2, buyer.getNoTickets());
            statement.setString(3,buyer.getName());

            int result = statement.executeUpdate();
            logger.trace("Saved {} instances", result);

            try(PreparedStatement statement2 = connection.prepareStatement("UPDATE shows SET remainingTickets = remainingTickets - (?) WHERE idShow = (?)"))
            {
                statement2.setInt(1,buyer.getNoTickets());
                statement2.setInt(2,buyer.getShow().getId());

                int result2 = statement2.executeUpdate();
                logger.trace("Saved {} instances", result2);
            }
            catch (SQLException e)
            {
                logger.error(e);
                System.err.println("Error DB " + e);
            }

            try(PreparedStatement statement2 = connection.prepareStatement("INSERT INTO BuyerShow (idShow, idBuyer) VALUES (?,?)"))
            {
                statement2.setInt(1,buyer.getShow().getId());
                statement2.setInt(2, buyer.getId());

                int result2 = statement2.executeUpdate();
                logger.trace("Saved {} instances", result2);
            }
            catch (SQLException e)
            {
                logger.error(e);
                System.err.println("Error DB " + e);
            }

        }
        catch (SQLException e)
        {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        return buyer;
    }
}
