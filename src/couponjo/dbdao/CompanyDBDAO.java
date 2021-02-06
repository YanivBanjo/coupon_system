package couponjo.dbdao;

import couponjo.beans.Company;
import couponjo.dao.CompanyDAO;
import couponjo.db.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDBDAO implements CompanyDAO {
    private static final String ADD_COMPANY = "INSERT INTO `couponjo`.`companies` (`name`, `email`, `password`) VALUES (?, ?, ?)";
    private static final String UPDATE_COMPANY = "UPDATE `couponjo`.`companies` SET `name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?)";
    private static final String DELETE_COMPANY = "DELETE FROM `couponjo`.`companies` WHERE (`id` = ?);";
    private static final String GET_ONE_COMPANY = "SELECT * FROM `couponjo`.`companies` WHERE (`id` = ?);";
    private static final String GET_ALL_COMPANIES = "SELECT * FROM `couponjo`.`companies`";

    private static Connection connection;

    @Override
    public void addCompany(Company company) throws SQLException {
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(ADD_COMPANY);
            statement.setString(1, company.getName());
            statement.setString(2, company.getEmail());
            statement.setString(3, company.getPassword());
            statement.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    @Override
    public void updateCompany(Company company) throws SQLException {
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(UPDATE_COMPANY);
            statement.setString(1, company.getName());
            statement.setString(2, company.getEmail());
            statement.setString(3, company.getPassword());
            statement.setInt(4, company.getId());
            statement.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    @Override
    public void deleteCompany(Company company) throws SQLException {
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(DELETE_COMPANY);
            statement.setInt(1, company.getId());
            statement.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    @Override
    public Company getSingleCompany(int id) throws SQLException {
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(GET_ONE_COMPANY);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return new Company(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return null;
    }

    @Override
    public List<Company> getAllCompanies() throws SQLException {
        List<Company> companies = new ArrayList<>();
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COMPANIES);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                companies.add(new Company(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4)));
            }
            return companies;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return null;
    }
}
