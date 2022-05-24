package app.persistence;

import app.model.Joc;
import app.model.User;
import app.persistence.interfaces.IJocRepository;
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
public class JocDBRepository implements IJocRepository
{
    private JdbcUtils dbUtils;

    @Autowired
    public JocDBRepository(Properties props) {
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Joc findOne(Integer integer) {
        Connection connection =  dbUtils.getConnection();
        Joc joc = null;
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM Jocuri WHERE idJoc = (?)"))
        {
            statement.setInt(1, integer);
            try(ResultSet result = statement.executeQuery())
            {
                if(result.next())
                {
                    int idJoc = result.getInt("idJoc");
                    joc = new Joc();
                    joc.setId(idJoc);
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error DB " + e);
        }
        return joc;
    }

    @Override
    public List<Joc> findAll() {
        Connection connection =  dbUtils.getConnection();
        List<Joc> jocuri = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM Jocuri"))
        {
            try(ResultSet result = statement.executeQuery())
            {
                while(result.next())
                {
                    int idJoc = result.getInt("idJoc");
                    Joc joc = new Joc();
                    joc.setId(idJoc);
                    jocuri.add(joc);
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error DB " + e);
        }
        return jocuri;
    }

    @Override
    public Joc save(Joc entity) {
        Connection connection =  dbUtils.getConnection();
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO Jocuri (idJoc) VALUES (?)"))
        {
            statement.setInt(1, entity.getId());

            int result = statement.executeUpdate();

        }
        catch (SQLException e)
        {
            System.err.println("Error DB " + e);
        }

        return entity;
    }
}
