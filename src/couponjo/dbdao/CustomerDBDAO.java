package couponjo.dbdao;

import couponjo.beans.Customer;
import couponjo.dao.CustomerDAO;
import couponjo.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDBDAO implements CustomerDAO {
    private static final String ADD_CUSTOMER = "INSERT INTO `couponjo`.`customers` (`first_name`, `last_name`, `email`, `password`) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_CUSTOMER = "UPDATE `couponjo`.`customers` SET `first_name` = ?, `last_name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?)";
    private static final String DELETE_CUSTOMER = "DELETE FROM `couponjo`.`customers` WHERE (`id` = ?)";
    private static final String GET_ONE_CUSTOMER = "SELECT * FROM `couponjo`.`customers` WHERE (`id` = ?)";
    private static final String GET_ALL_CUSTOMER = "SELECT * FROM `couponjo`.`customers`";
    private static final String IS_CUSTOMER_EXIST = "SELECT * FROM `couponjo`.`customers` WHERE (`email` = ?) and (`password` = ?)";
    private static final String GET_CUSTOMER_BY_EMAIL = "SELECT * FROM `couponjo`.`customers` WHERE (`email` = ?)";

    @Override
    public boolean isCustomerExist(String email, String password) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);

        ResultSet resultSet = DBUtils.runQueryWithResult(IS_CUSTOMER_EXIST, map);
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    @Override
    public void addCustomer(Customer customer) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customer.getFirstName());
        map.put(2, customer.getLastName());
        map.put(3, customer.getEmail());
        map.put(4, customer.getPassword());

        DBUtils.runQuery(ADD_CUSTOMER, map);
    }

    @Override
    public void updateCustomer(Customer customer) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customer.getFirstName());
        map.put(2, customer.getLastName());
        map.put(3, customer.getEmail());
        map.put(4, customer.getPassword());
        map.put(5, customer.getId());

        DBUtils.runQuery(UPDATE_CUSTOMER, map);
    }

    @Override
    public void deleteCustomer(Customer customer) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customer.getId());

        DBUtils.runQuery(DELETE_CUSTOMER, map);
    }

    @Override
    public Customer getSingleCustomer(int id) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);

        ResultSet resultSet = DBUtils.runQueryWithResult(GET_ONE_CUSTOMER, map);
        if (resultSet.next()) {
            return new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5));
        }
        return null;
    }

    @Override
    public Customer getCustomerByEmail(String email) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);

        ResultSet resultSet = DBUtils.runQueryWithResult(GET_CUSTOMER_BY_EMAIL, map);
        if (resultSet.next()) {
            return new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5));
        }
        return null;
    }

    @Override
    public List<Customer> getAllCustomer() throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        List<Customer> customers = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_ALL_CUSTOMER, map);

        while (resultSet.next()) {
            customers.add(new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5)));
        }
        return customers;
    }
}
