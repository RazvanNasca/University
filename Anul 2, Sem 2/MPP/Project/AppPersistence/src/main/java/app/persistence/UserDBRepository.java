package app.persistence;

import app.model.User;
import app.persistence.interfaces.IUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class UserDBRepository implements IUserRepository
{

    private JdbcUtils dbUtils;

    @Autowired
    public UserDBRepository(Properties props) {
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public User findOne(String s)
    {
        Connection connection =  dbUtils.getConnection();
        User user = null;
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = (?)"))
        {
            statement.setString(1, s);
            try(ResultSet result = statement.executeQuery())
            {
                if(result.next())
                {
                    String name = result.getString("username");
                    String password = result.getString("password");
                    user = new User(name, password);
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error DB " + e);
        }
        return user;
    }

    @Override
    public List<User> findAll()
    {
        Connection connection =  dbUtils.getConnection();
        List<User> users = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM users"))
        {
            try(ResultSet result = statement.executeQuery())
            {
                while(result.next())
                {
                    String name = result.getString("username");
                    String password = result.getString("password");
                    User user = new User(name, password);
                    users.add(user);
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error DB " + e);
        }
        return users;
    }

    @Override
    public User save(User user)
    {
        Connection connection =  dbUtils.getConnection();
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?,?)"))
        {
            statement.setString(1,user.getUsername());
            statement.setString(2,user.getPassword());

            int result = statement.executeUpdate();

        }
        catch (SQLException e)
        {
            System.err.println("Error DB " + e);
        }

        return user;
    }

    public User getUser(String username, String password) {
        Connection connection = dbUtils.getConnection();
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE Username = (?) AND Password = (?)")) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    String name = result.getString("Username");
                    String pass = result.getString("Password");
                    user = new User(name, pass);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return user;
    }
}