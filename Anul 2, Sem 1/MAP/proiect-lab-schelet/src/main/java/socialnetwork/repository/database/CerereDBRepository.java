package socialnetwork.repository.database;

import socialnetwork.domain.CererePrietenie;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.RepoException;
import socialnetwork.repository.Repository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class CerereDBRepository implements Repository<Tuple<Long, Long>, CererePrietenie> {

    private String url;
    private String username;
    private String password;

    private Connection connection;

    private Validator<CererePrietenie> validator;

    public CerereDBRepository (String url, String username, String password, Validator<CererePrietenie> validator) {
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
    public CererePrietenie findOne(Tuple<Long, Long> aLong) {
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM FriendRequest WHERE (id1 = " + aLong.getLeft() + " AND id2 = " +  aLong.getRight() + ") OR ( id1 = " + aLong.getRight() + " AND id2 = " +  aLong.getLeft() + ")");
            ResultSet resultSet = statement.executeQuery()){

            if(resultSet.next() == false)
                return null;

            Long id1 = resultSet.getLong("id1");
            Long id2 = resultSet.getLong("id2");
            Timestamp date = resultSet.getTimestamp("datap");
            String status = resultSet.getString("status");

            CererePrietenie cerere = new CererePrietenie();
            cerere.setId(new Tuple(id1,id2));
            cerere.setDate(date.toLocalDateTime());
            cerere.setStatus(status);

            return cerere;

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<CererePrietenie> findAll() {
        Set<CererePrietenie> cereri = new HashSet<>();
        Long id1, id2;
        Timestamp date;
        String status;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * from FriendRequest");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                id1 = resultSet.getLong("id1");
                id2 = resultSet.getLong("id2");
                date = resultSet.getTimestamp("datap");
                status = resultSet.getString("status");

                CererePrietenie cerere = new CererePrietenie();
                cerere.setId(new Tuple(id1,id2));
                cerere.setDate(date.toLocalDateTime());
                cerere.setStatus(status);

                cereri.add(cerere);
            }
            return cereri;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cereri;
    }

    @Override
    public CererePrietenie save(CererePrietenie entity)
    {
        validator.validate(entity);
        if(findOne(entity.getId()) != null)
            throw new RepoException("Cererea exista deja!\n");

        try{
            Statement s = connection.createStatement();
            s.executeUpdate("INSERT INTO FriendRequest(id1, id2, status, datap) VALUES('"+entity.getId().getLeft()+"', '"+entity.getId().getRight()+"', '"+entity.getStatus()+"','"+entity.getDate()+"')");


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CererePrietenie delete(Tuple<Long, Long> aLong)
    {
        if(findOne(aLong) == null)
            throw new RepoException("Id inexistent!\n");

        try{
            Statement s = connection.createStatement();
            s.executeUpdate("DELETE FROM FriendRequest WHERE (id1 = " + aLong.getLeft() + " AND id2 = " +  aLong.getRight() + ") OR ( id1 = " + aLong.getRight() + " AND id2 = " +  aLong.getLeft() + ")");


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CererePrietenie update(CererePrietenie entity) {
        return null;
    }

}
