package repository.db;


import festival.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.RepoUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepoUserDB implements RepoUser {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger(RepoUserDB.class);

    public RepoUserDB(Properties props) {
        logger.info("Initializing RepoUserDB with properties: {} ",props);
        dbUtils = new JdbcUtils(props);
    }

    /*public RepoUserDB(){}*/

    @Override
    public User findOne(String s)
    {
        logger.traceEntry();
        Connection connection =  dbUtils.getConnection();
        User user = null;
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE name = (?)"))
        {
            statement.setString(1, s);
            try(ResultSet result = statement.executeQuery())
            {
                if(result.next())
                {
                    String name = result.getString("name");
                    String password = result.getString("password");
                    user = new User(name, password);
                }
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(user);
        return user;
    }

    @Override
    public List<User> findAll()
    {
        logger.traceEntry();
        Connection connection =  dbUtils.getConnection();
        List<User> users = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM users"))
        {
            try(ResultSet result = statement.executeQuery())
            {
                while(result.next())
                {
                    String name = result.getString("name");
                    String password = result.getString("password");
                    User user = new User(name, password);
                    users.add(user);
                }
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(users);
        return users;
    }

    @Override
    public User save(User user)
    {
        logger.traceEntry("savinng task{}", user);
        Connection connection =  dbUtils.getConnection();
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, password) VALUES (?,?)"))
        {
            statement.setString(1,user.getName());
            statement.setString(2,user.getPassword());

            int result = statement.executeUpdate();
            logger.trace("Saved {} instances", result);

        }
        catch (SQLException e)
        {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        return user;
    }

    @Override
    public User findByName(String name) {
        logger.traceEntry();
        Connection connection =  dbUtils.getConnection();
        User user = null;
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE name = (?)"))
        {
            statement.setString(1, name);
            try(ResultSet result = statement.executeQuery())
            {
                if(!result.next())
                    return null;

                String username = result.getString("name");
                String password = result.getString("password");
                user = new User(username, password);
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        return user;
    }
}
