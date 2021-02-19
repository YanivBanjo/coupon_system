package couponjo.dao;

import couponjo.beans.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {
    boolean isCustomerExist(String email ,String password) throws SQLException;

    void addCustomer(Customer customer) throws SQLException;

    void updateCustomer(Customer customer) throws SQLException;

    void deleteCustomer(Customer customer) throws SQLException;

    Customer getSingleCustomer(int id) throws SQLException;

    Customer getCustomerByEmail(String email) throws SQLException;

    List<Customer> getAllCustomer() throws SQLException;
}
