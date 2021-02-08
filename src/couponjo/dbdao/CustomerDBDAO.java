package couponjo.dbdao;

import couponjo.beans.Customer;
import couponjo.dao.CustomerDAO;
import couponjo.db.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDBDAO implements CustomerDAO {
    private static final String ADD_CUSTOMER = "INSERT INTO `couponjo`.`customers` (`first_name`, `last_name`, `email`, `password`) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_CUSTOMER = "UPDATE `couponjo`.`customers` SET `first_name` = ?, `last_name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?)";
    private static final String DELETE_CUSTOMER = "DELETE FROM `couponjo`.`customers` WHERE (`id` = ?);";
    private static final String GET_ONE_CUSTOMER = "SELECT * FROM `couponjo`.`customers` WHERE (`id` = ?);";
    private static final String GET_ALL_CUSTOMER = "SELECT * FROM `couponjo`.`customers`";
    private static final String IS_CUSTOMER_EXIST = "SELECT * FROM `couponjo`.`customers` WHERE (`email` = ?) and (`password` = ?)";

    private static Connection connection;

    @Override
    public boolean isCustomerExist(String email, String password) throws SQLException {
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(IS_CUSTOMER_EXIST);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;    }

    @Override
    public void addCustomer(Customer customer) throws SQLException {
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(ADD_CUSTOMER);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPassword());
            statement.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    @Override
    public void updateCustomer(Customer customer) throws SQLException {
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPassword());
            statement.setInt(5, customer.getId());
            statement.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    @Override
    public void deleteCustomer(Customer customer) throws SQLException {
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER);
            statement.setInt(1, customer.getId());
            statement.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    @Override
    public Customer getSingleCustomer(int id) throws SQLException {
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(GET_ONE_CUSTOMER);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4),resultSet.getString(5));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return null;    }

    @Override
    public List<Customer> getAllCustomer() throws SQLException {
       List<Customer> customers = new ArrayList<>();
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(GET_ALL_CUSTOMER);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                customers.add(new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4),resultSet.getString(5)));
            }
            return customers;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return null;
    }
}
