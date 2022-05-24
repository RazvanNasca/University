package app.persistence;

import app.model.Mana;
import app.model.Propunere;
import app.model.User;
import app.persistence.interfaces.IPropunereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class PropunereDBRepository implements IPropunereRepository
{

    private JdbcUtils dbUtils;

    @Autowired
    public PropunereDBRepository(Properties props) {
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Propunere findOne(Integer integer) {
        return null;
    }

    @Override
    public List<Propunere> findAll() {
        return null;
    }

    @Override
    public Propunere save(Propunere entity) {
        Connection connection =  dbUtils.getConnection();
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO CaractereInitiale (sirCaractere, idUser, idJoc) VALUES (?,?,?)"))
        {
            statement.setString(1, entity.getSirCaractere());
            statement.setString(2, entity.getUser().getUsername());
            statement.setInt(3, entity.getJoc().getId());

            int result = statement.executeUpdate();

        }
        catch (SQLException e)
        {
            System.err.println("Error DB " + e);
        }

        return entity;
    }

    @Override
    public Map<String, String> getPropuneriByJoc(int idJoc) {
        Connection connection =  dbUtils.getConnection();
        Map <String, String> propuneri = new HashMap<String, String>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM CaractereInitiale WHERE idJoc = (?)"))
        {
            statement.setInt(1, idJoc);
            try(ResultSet result = statement.executeQuery())
            {
                while(result.next())
                {
                    String sirCaractere = result.getString("sirCaractere");
                    String username = result.getString("idUser");

                    propuneri.put(username, sirCaractere);
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error DB " + e);
        }
        return propuneri;
    }

}
