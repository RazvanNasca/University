package socialnetwork.repository.database;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.RepoException;
import socialnetwork.repository.Repository;

import java.sql.*;
import java.time.DateTimeException;
import java.util.HashSet;
import java.util.Set;

public class PrietenieDBRepository implements Repository<Tuple<Long, Long>, Prietenie> {

    private String url;
    private String username;
    private String password;

    private Connection connection;

    private Validator<Prietenie> validator;

    public PrietenieDBRepository (String url, String username, String password, Validator<Prietenie> validator) {
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
    public Prietenie findOne(Tuple<Long, Long> aLong) {
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM prietenie WHERE (id1 = " + aLong.getLeft() + " AND id2 = " +  aLong.getRight() + ") OR ( id1 = " + aLong.getRight() + " AND id2 = " +  aLong.getLeft() + ")");
            ResultSet resultSet = statement.executeQuery()){

            if(resultSet.next() == false)
                return null;

            Long id1 = resultSet.getLong("id1");
            Long id2 = resultSet.getLong("id2");
            Timestamp date = resultSet.getTimestamp("datap");

            Prietenie prietenie = new Prietenie();
            prietenie.setId(new Tuple(id1,id2));
            prietenie.setDate(date.toLocalDateTime());

            return prietenie;

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<Prietenie> findAll() {
        Set<Prietenie> prietenii = new HashSet<>();
        Long id1, id2;
        Timestamp date;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * from prietenie");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                id1 = resultSet.getLong("id1");
                id2 = resultSet.getLong("id2");
                date = resultSet.getTimestamp("datap");

                Prietenie prietenie = new Prietenie();
                prietenie.setId(new Tuple(id1,id2));
                prietenie.setDate(date.toLocalDateTime());

                prietenii.add(prietenie);
            }
            return prietenii;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prietenii;
    }

    @Override
    public Prietenie save(Prietenie entity)
    {
        validator.validate(entity);
        if(findOne(entity.getId()) != null)
            throw new RepoException("Id existent!\n");

        try{
            Statement s = connection.createStatement();
            s.executeUpdate("INSERT INTO prietenie(id1, id2, datap) VALUES( '"+entity.getId().getLeft()+"' , '"+entity.getId().getRight()+"' , '"+entity.getDate()+"' )");


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Prietenie delete(Tuple<Long, Long> aLong)
    {
        if(findOne(aLong) == null)
            throw new RepoException("Id inexistent!\n");

        try{
            Statement s = connection.createStatement();
            s.executeUpdate("DELETE FROM prietenie WHERE (id1 = " + aLong.getLeft() + "AND id2 = " +  aLong.getRight() + ") OR ( id1 = " + aLong.getRight() + "AND id2 = " +  aLong.getLeft() + ")");


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Prietenie update(Prietenie entity) {
        return null;
    }

}
