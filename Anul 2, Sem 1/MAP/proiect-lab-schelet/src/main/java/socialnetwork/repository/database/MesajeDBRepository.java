package socialnetwork.repository.database;

import socialnetwork.domain.Mesaje;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.RepoException;
import socialnetwork.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class MesajeDBRepository implements Repository<Long, Mesaje> {

    private String url;
    private String username;
    private String password;

    private Connection connection;

    private Validator<Mesaje> validator;

    public MesajeDBRepository(String url, String username, String password, Validator<Mesaje> validator) {
        try {
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        this.validator = validator;
    }

    @Override
    public Mesaje findOne(Long aLong)
    {
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM mesaje WHERE idMesaj = " + aLong);
            ResultSet resultSet = statement.executeQuery()){

            if(resultSet.next() == false)
                return null;

            Long idMesaj = resultSet.getLong("idMesaj");
            Long sursa = resultSet.getLong("sursa");
            String continut = resultSet.getString("continut");
            Timestamp data = resultSet.getTimestamp("datam");

            Mesaje mesaj = new Mesaje(sursa, continut, data.toLocalDateTime());
            mesaj.setId(idMesaj);

            /// extragem id-ul utilizatorilor care au primit mesajul
            PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM destinatie WHERE idMesaj = " + aLong);
            ResultSet resultSet2 = statement2.executeQuery();

                if (resultSet2.next() == false)
                    return null;

                Long idUtilizator = resultSet2.getLong("idUtilizator");
                mesaj.getTo().add(idUtilizator);

                while(resultSet2.next())
                {
                    idUtilizator = resultSet2.getLong("idUtilizator");
                    mesaj.getTo().add(idUtilizator);
                }

            /// extragem id-ul reply-urilor de la acest mesaj
            PreparedStatement statement3 = connection.prepareStatement("SELECT * FROM reply WHERE idM1 = " + aLong);
            ResultSet resultSet3 = statement3.executeQuery();

                if (resultSet3.next() != false)
                {
                    Long idM2 = resultSet3.getLong("idM2");
                    mesaj.getReply().add(idM2);

                    while (resultSet3.next()) {
                        idM2 = resultSet3.getLong("idM2");
                        mesaj.getReply().add(idM2);
                    }
                }

            return mesaj;

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<Mesaje> findAll()
    {
        Set<Mesaje> mesaje = new HashSet<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * from mesaje");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long idMesaj = resultSet.getLong("idMesaj");
                Mesaje mesaj = findOne(idMesaj);
                mesaje.add(mesaj);
            }
            return mesaje;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mesaje;
    }

    @Override
    public Mesaje save(Mesaje entity)
    {
       /// validator.validate(entity);
        if(findOne(entity.getId()) != null)
            throw new RepoException("Id existent!\n");

        try{
            Statement s = connection.createStatement();
            s.executeUpdate("INSERT INTO mesaje(idMesaj, sursa, continut, datam) VALUES('"+entity.getId()+"', '"+entity.getFrom()+"', '"+entity.getMesaj()+"' , '"+entity.getData()+"')");

            for(Long it : entity.getTo())
                s.executeUpdate("INSERT INTO destinatie (idMesaj, idUtilizator) VALUES('"+entity.getId()+"' , '"+it+"')");

            for(Long it : entity.getReply())
                s.executeUpdate("INSERT INTO reply (idM1, idM2) VALUES('"+entity.getId()+"' , '"+it+"')");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Mesaje delete(Long aLong) { return null; }

    @Override
    public Mesaje update(Mesaje entity) {
        if(findOne(entity.getId()) == null)
            throw new RepoException("Id inexistent!\n");

        Long id = 0L;
        for(Long it : entity.getReply())
            id = it;

        try{
            Statement s = connection.createStatement();
            s.executeUpdate("INSERT INTO reply(idM1, idM2) VALUES ( '"+entity.getId()+"' , '"+id+"')");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
