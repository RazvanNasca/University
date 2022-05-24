package socialnetwork.repository.database;

import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.RepoException;
import socialnetwork.repository.Repository;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UtilizatorDBRepository implements Repository<Long, Utilizator> {

    private String url;
    private String username;
    private String password;

    private Connection connection;

    private Validator<Utilizator> validator;

    public UtilizatorDBRepository(String url, String username, String password, Validator<Utilizator> validator) {
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
    public Utilizator findOne(Long aLong) {
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM utilizatori WHERE id = " + aLong);
            ResultSet resultSet = statement.executeQuery()){

            if(resultSet.next() == false)
                return null;

            Long id = resultSet.getLong("id");
            String firstName = resultSet.getString("nume");
            String lastName = resultSet.getString("prenume");

            Utilizator utilizator = new Utilizator(firstName, lastName);
            utilizator.setId(id);

            return utilizator;

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<Utilizator> findAll() {
        Set<Utilizator> users = new HashSet<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * from utilizatori");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("nume");
                String lastName = resultSet.getString("prenume");

                Utilizator utilizator = new Utilizator(firstName, lastName);
                utilizator.setId(id);
                users.add(utilizator);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Utilizator save(Utilizator entity)
    {
        validator.validate(entity);
        if(findOne(entity.getId()) != null)
            throw new RepoException("Id existent!\n");

        try{
            Statement s = connection.createStatement();
            s.executeUpdate("INSERT INTO utilizatori(id, nume, prenume) VALUES('"+entity.getId()+"', '"+entity.getFirstName()+"', '"+entity.getLastName()+"')");


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Utilizator delete(Long aLong)
    {
        if(findOne(aLong) == null)
            throw new RepoException("Id inexistent!\n");

        try{
            Statement s = connection.createStatement();
            s.executeUpdate("DELETE FROM utilizatori WHERE id = " + aLong);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Utilizator update(Utilizator entity) {
        return null;
    }

}

