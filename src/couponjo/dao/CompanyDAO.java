package couponjo.dao;

import couponjo.beans.Company;

import java.sql.SQLException;
import java.util.List;

public interface CompanyDAO {
    boolean isCompanyExist(String email ,String password) throws SQLException;

    void addCompany(Company company) throws SQLException;

    void updateCompany(Company company) throws SQLException;

    void deleteCompany(Company company) throws SQLException;

    Company getSingleCompany(int id) throws SQLException;

    Company getCompanyByName(String name) throws SQLException;

    Company getCompanyByEmail(String email) throws SQLException;

    List<Company> getAllCompanies() throws SQLException;
}
