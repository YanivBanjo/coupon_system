package couponjo.dao;

import couponjo.beans.Company;

import java.sql.SQLException;
import java.util.List;

public interface CompanyDAO {
    void addCompany(Company company) throws SQLException;

    void updateCompany(Company company) throws SQLException;

    void deleteCompany(Company company) throws SQLException;

    Company getSingleCompany(int id) throws SQLException;

    List<Company> getAllCompanies() throws SQLException;
}
