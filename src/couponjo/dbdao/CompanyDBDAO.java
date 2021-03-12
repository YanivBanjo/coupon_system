package couponjo.dbdao;

import couponjo.beans.Company;
import couponjo.dao.CompanyDAO;
import couponjo.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyDBDAO implements CompanyDAO {
    private static final String ADD_COMPANY = "INSERT INTO `couponjo`.`companies` (`name`, `email`, `password`) VALUES (?, ?, ?)";
    private static final String UPDATE_COMPANY = "UPDATE `couponjo`.`companies` SET `name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?)";
    private static final String DELETE_COMPANY = "DELETE FROM `couponjo`.`companies` WHERE (`id` = ?)";
    private static final String GET_ONE_COMPANY = "SELECT * FROM `couponjo`.`companies` WHERE (`id` = ?)";
    private static final String GET_ALL_COMPANIES = "SELECT * FROM `couponjo`.`companies`";
    private static final String IS_COMPANY_EXIST = "SELECT * FROM `couponjo`.`companies` WHERE (`email` = ?) and (`password` = ?)";
    private static final String GET_COMPANY_BY_NAME = "SELECT * FROM `couponjo`.`companies` WHERE (`name` = ?)";
    private static final String GET_COMPANY_BY_EMAIL = "SELECT * FROM `couponjo`.`companies` WHERE (`email` = ?)";

    @Override
    public boolean isCompanyExist(String email, String password) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);

        ResultSet resultSet = DBUtils.runQueryWithResult(IS_COMPANY_EXIST, map);
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    @Override
    public void addCompany(Company company) throws SQLException {

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, company.getName());
        map.put(2, company.getEmail());
        map.put(3, company.getPassword());

        DBUtils.runQuery(ADD_COMPANY, map);
    }

    @Override
    public void updateCompany(Company company) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, company.getName());
        map.put(2, company.getEmail());
        map.put(3, company.getPassword());
        map.put(4, company.getId());

        DBUtils.runQuery(UPDATE_COMPANY, map);
    }

    @Override
    public void deleteCompany(Company company) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, company.getId());

        DBUtils.runQuery(DELETE_COMPANY, map);
    }

    @Override
    public Company getSingleCompany(int id) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);

        ResultSet resultSet = DBUtils.runQueryWithResult(GET_ONE_COMPANY, map);
        if (resultSet.next()) {
            return new Company(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4));
        }
        return null;
    }

    @Override
    public Company getCompanyByName(String name) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, name);

        ResultSet resultSet = DBUtils.runQueryWithResult(GET_COMPANY_BY_NAME, map);
        if (resultSet.next()) {
            return new Company(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4));
        }
        return null;
    }

    @Override
    public Company getCompanyByEmail(String email) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);

        ResultSet resultSet = DBUtils.runQueryWithResult(GET_COMPANY_BY_EMAIL, map);
        if (resultSet.next()) {
            return new Company(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4));
        }
        return null;
    }

    @Override
    public List<Company> getAllCompanies() throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        List<Company> companies = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_ALL_COMPANIES, map);

        while (resultSet.next()) {
            companies.add(new Company(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4)));
        }
        return companies;
    }
}
