import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarsDBRepository implements CarRepository{

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger(CarsDBRepository.class);

    public CarsDBRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public List<Car> findByManufacturer(String manufacturerN) {
        logger.traceEntry();
        Connection connection =  dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM Masini WHERE manufacturer = (?)"))
        {
            statement.setString(1, manufacturerN);
            try(ResultSet result = statement.executeQuery())
            {
                while(result.next())
                {
                    int id = result.getInt("id");
                    String manufacturer = result.getString("manufacturer");
                    String model = result.getString("model");
                    int year = result.getInt("year");
                    Car car = new Car(manufacturer,model,year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(cars);
        return cars;
    }

    @Override
    public List<Car> findBetweenYears(int min, int max) {
        logger.traceEntry();
        Connection connection =  dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM Masini WHERE year > (?) AND year < (?)"))
        {
            statement.setInt(1, min);
            statement.setInt(2, max);
            try(ResultSet result = statement.executeQuery())
            {
                while(result.next())
                {
                    int id = result.getInt("id");
                    String manufacturer = result.getString("manufacturer");
                    String model = result.getString("model");
                    int year = result.getInt("year");
                    Car car = new Car(manufacturer,model,year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(cars);
        return cars;
    }

    @Override
    public void add(Car elem) {
        logger.traceEntry("savinng task{}", elem);
        Connection connection =  dbUtils.getConnection();
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO Masini (manufacturer, model, year) VALUES (?,?,?)"))
        {
            statement.setString(1, elem.getManufacturer());
            statement.setString(2,elem.getModel());
            statement.setInt(3,elem.getYear());

            int result = statement.executeUpdate();
            logger.trace("Saved {} instances", result);
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
    }

    @Override
    public void update(Integer integer, Car elem) {

    }

    @Override
    public Iterable<Car> findAll() {
        logger.traceEntry();
        Connection connection =  dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM Masini"))
        {
            try(ResultSet result = statement.executeQuery())
            {
                while(result.next())
                {
                    int id = result.getInt("id");
                    String manufacturer = result.getString("manufacturer");
                    String model = result.getString("model");
                    int year = result.getInt("year");
                    Car car = new Car(manufacturer,model,year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(cars);
        return cars;
    }
}
