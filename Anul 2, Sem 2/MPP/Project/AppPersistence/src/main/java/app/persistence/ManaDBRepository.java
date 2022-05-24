package app.persistence;

import app.model.Joc;
import app.model.Mana;
import app.model.Runda;
import app.model.User;
import app.persistence.interfaces.IManaRepository;
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
public class ManaDBRepository implements IManaRepository
{
    private JdbcUtils dbUtils;

    @Autowired
    public ManaDBRepository(Properties props) {
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Mana findOne(Integer integer) {
        return null;
    }

    @Override
    public List<Mana> findAll() {
        return null;
    }

    @Override
    public Mana save(Mana entity) {
        Connection connection =  dbUtils.getConnection();
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO Maini (idRunda, idUser, propunere, puncte, idUserGhicit,) VALUES (?,?,?,?,?)"))
        {
            statement.setInt(1, entity.getRunda().getId());
            statement.setString(2, entity.getUser().getUsername());
            statement.setString(3, entity.getPropunere());
            statement.setInt(4, entity.getPuncte());
            statement.setString(5, entity.getUserGhicit().getUsername());

            int result = statement.executeUpdate();

        }
        catch (SQLException e)
        {
            System.err.println("Error DB " + e);
        }

        return entity;
    }


    @Override
    public Mana findOneByRundaAndUser(Runda runda, User user) {
        Connection connection =  dbUtils.getConnection();
        Mana mana = null;
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM Maini WHERE idRunda = (?) AND idUser = (?)"))
        {
            statement.setInt(1, runda.getId());
            statement.setString(2, user.getUsername());
            try(ResultSet result = statement.executeQuery())
            {
                if(result.next())
                {
                    int idRunda = result.getInt("idRunda");
                    String username = result.getString("idUser");

                    String propunere = result.getString("propunere");
                    int puncte = result.getInt("puncte");

                    String userGhicit = result.getString("idUserGhicit");

                    Runda runda1 = null;
                    PreparedStatement statementRunda = connection.prepareStatement("SELECT * FROM Runda WHERE idRunda = (?) ");
                    statementRunda.setInt(1, idRunda);
                    ResultSet resultRunda = statementRunda.executeQuery();

                    if(resultRunda.next())
                    {
                        int idJoc = resultRunda.getInt("idJoc");
                        Joc joc = new Joc();
                        joc.setId(idJoc);
                        runda1 = new Runda(joc);
                        runda1.setId(idRunda);
                    }

                    User user1 = null;
                    PreparedStatement statementUser = connection.prepareStatement("SELECT * FROM Users WHERE username = (?) ");
                    statementUser.setString(1, username);
                    ResultSet resultUser = statementUser.executeQuery();

                    if(resultUser.next())
                    {
                        String name = resultUser.getString("username");
                        String password = resultUser.getString("password");
                        user1 = new User(name, password);
                    }

                    User userGhicit1 = null;
                    PreparedStatement statementUser1 = connection.prepareStatement("SELECT * FROM Users WHERE username = (?) ");
                    statementUser1.setString(1, username);
                    ResultSet resultUser1 = statementUser1.executeQuery();

                    if(resultUser1.next())
                    {
                        String name = resultUser1.getString("username");
                        String password = resultUser1.getString("password");
                        userGhicit1 = new User(name, password);
                    }

                    mana = new Mana(runda1, user1, propunere, puncte, userGhicit1);

                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error DB " + e);
        }
        return mana;
    }

    @Override
    public List<Mana> findAllByRundaAndUser(Runda runda, User user) {
        Connection connection =  dbUtils.getConnection();
        List<Mana> maini = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM Maini "))
        {
            try(ResultSet result = statement.executeQuery())
            {
                while(result.next())
                {
                    int idRunda = result.getInt("idRunda");
                    String username = result.getString("idUser");

                    String propunere = result.getString("propunere");
                    int puncte = result.getInt("puncte");

                    String userGhicit = result.getString("idUserGhicit");

                    Runda runda1 = null;
                    PreparedStatement statementRunda = connection.prepareStatement("SELECT * FROM Runda WHERE idRunda = (?) ");
                    statementRunda.setInt(1, idRunda);
                    ResultSet resultRunda = statementRunda.executeQuery();

                    if(resultRunda.next())
                    {
                        int idJoc = resultRunda.getInt("idJoc");
                        Joc joc = new Joc();
                        joc.setId(idJoc);
                        runda1 = new Runda(joc);
                        runda1.setId(idRunda);
                    }

                    User user1 = null;
                    PreparedStatement statementUser = connection.prepareStatement("SELECT * FROM Users WHERE username = (?) ");
                    statementUser.setString(1, username);
                    ResultSet resultUser = statementUser.executeQuery();

                    if(resultUser.next())
                    {
                        String name = resultUser.getString("username");
                        String password = resultUser.getString("password");
                        user1 = new User(name, password);
                    }

                    User userGhicit1 = null;
                    PreparedStatement statementUser1 = connection.prepareStatement("SELECT * FROM Users WHERE username = (?) ");
                    statementUser1.setString(1, username);
                    ResultSet resultUser1 = statementUser1.executeQuery();

                    if(resultUser1.next())
                    {
                        String name = resultUser1.getString("username");
                        String password = resultUser1.getString("password");
                        userGhicit1 = new User(name, password);
                    }

                    Mana mana = new Mana(runda1, user1, propunere, puncte, userGhicit1);

                    maini.add(mana);

                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error DB " + e);
        }
        return maini;
    }

    @Override
    public List<Mana> getHandByGameAndUser(int nrJoc, String username) {
        Connection connection =  dbUtils.getConnection();
        List<Mana> maini = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM Maini WHERE idUser = (?)"))
        {
            statement.setString(1, username);
            try(ResultSet result = statement.executeQuery())
            {
                while(result.next())
                {
                    int idRunda = result.getInt("idRunda");
                    String username1 = result.getString("idUser");

                    String propunere = result.getString("propunere");
                    int puncte = result.getInt("puncte");

                    String userGhicit = result.getString("idUserGhicit");

                    Runda runda1 = null;
                    PreparedStatement statementRunda = connection.prepareStatement("SELECT * FROM Runde WHERE idRunda = (?) AND idJoc = (?)");
                    statementRunda.setInt(1, idRunda);
                    statementRunda.setInt(2, nrJoc);
                    ResultSet resultRunda = statementRunda.executeQuery();

                    if(resultRunda.next())
                    {
                        int idJoc = resultRunda.getInt("idJoc");
                        Joc joc = new Joc();
                        joc.setId(idJoc);
                        runda1 = new Runda(joc);
                        runda1.setId(idRunda);
                    }

                    if(runda1 != null)
                    {

                        User user1 = null;
                        PreparedStatement statementUser = connection.prepareStatement("SELECT * FROM Users WHERE username = (?) ");
                        statementUser.setString(1, username1);
                        ResultSet resultUser = statementUser.executeQuery();

                        if (resultUser.next()) {
                            String name = resultUser.getString("username");
                            String password = resultUser.getString("password");
                            user1 = new User(name, password);
                        }

                        User userGhicit1 = null;
                        PreparedStatement statementUser1 = connection.prepareStatement("SELECT * FROM Users WHERE username = (?) ");
                        statementUser1.setString(1, userGhicit);
                        ResultSet resultUser1 = statementUser1.executeQuery();

                        if (resultUser1.next()) {
                            String name = resultUser1.getString("username");
                            String password = resultUser1.getString("password");
                            userGhicit1 = new User(name, password);
                        }

                        Mana mana = new Mana(runda1, user1, propunere, puncte, userGhicit1);

                        maini.add(mana);
                    }

                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error DB " + e);
        }
        return maini;
    }
}
