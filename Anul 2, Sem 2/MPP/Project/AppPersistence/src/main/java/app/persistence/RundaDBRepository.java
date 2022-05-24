package app.persistence;

import app.model.Runda;
import app.persistence.interfaces.IRundaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Component
public class RundaDBRepository implements IRundaRepository
{
    private JdbcUtils dbUtils;

    @Autowired
    public RundaDBRepository(Properties props) {
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Runda findOne(Integer integer) {
        return null;
    }

    @Override
    public List<Runda> findAll() {
        return null;
    }

    @Override
    public Runda save(Runda entity) {
        Connection connection =  dbUtils.getConnection();
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO Runde (idRunda, idJoc) VALUES (?,?)"))
        {
            statement.setInt(1, entity.getId());
            statement.setInt(2, entity.getGame().getId());

            int result = statement.executeUpdate();

        }
        catch (SQLException e)
        {
            System.err.println("Error DB " + e);
        }

        return entity;
    }
}
