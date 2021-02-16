package couponjo.facade;

import couponjo.beans.Company;
import couponjo.beans.Customer;
import couponjo.dao.CompanyDAO;
import couponjo.dbdao.CompanyDBDAO;
import couponjo.exceptions.InvalidOperationException;

import java.sql.SQLException;
import java.util.List;

public class AdminFacade extends ClientFacade{

    @Override
    boolean login(String email, String password) {
        return (email=="admin@admin.com" && password == "admin");
    }

    public void addCompany(Company company) throws SQLException, InvalidOperationException {
        List<Company> companies = getAllCompanies();
        for (Company c:companies){
            if (c.getName().equals(company.getName()) || c.getEmail().equals(company.getEmail())){
                throw new InvalidOperationException("Company can't be added, name/email is already taken");
            }
        }
        companyDAO.addCompany(company);
    }

//    public void deleteCompany(Company company) throws SQLException, InvalidOperationException {
//        if(company.getId() > 0){
//            couponDAO.getCouponPhurcaseByCustomerId(company.getId());
//        } else {
//            throw new InvalidOperationException("In order to update company please provide company id");
//        }
//        companyDAO.deleteCompany(company);
//    }

    public void updateCompany(Company company) throws SQLException, InvalidOperationException {
        if(company.getId() > 0){
            Company toCompare = getSingleCompany(company.getId());
            if(!toCompare.getName().equals(company.getName()) || !toCompare.getPassword().equals(company.getPassword())){
                throw new InvalidOperationException("company name and password can be update by company user only");
            }
        } else {
            throw new InvalidOperationException("In order to update company please provide company id");
        }
        companyDAO.updateCompany(company);
    }

    public List<Company> getAllCompanies() throws SQLException {
            return companyDAO.getAllCompanies();
    }
    public Company getSingleCompany(int id) throws SQLException {
        return companyDAO.getSingleCompany(id);
    }


    public void addCustomer(Customer customer) throws SQLException, InvalidOperationException {
        List<Customer> customers = getAllCustomers();
        for (Customer c:customers){
            if (c.getEmail().equals(customer.getEmail())){
                throw new InvalidOperationException("Customer can't be added, email is already taken");
            }
        }
        customerDAO.addCustomer(customer);
    }
    public void updateCustomer(Customer customer) throws SQLException, InvalidOperationException {
        if(customer.getId() > 0){
            Customer toCompare = getSingleCustomer(customer.getId());
            if(!toCompare.getPassword().equals(customer.getPassword())){
                throw new InvalidOperationException("Customer password can be update by customer user only");
            }
        } else {
            throw new InvalidOperationException("In order to update company please provide company id");
        }
        customerDAO.updateCustomer(customer);
    }

//    public void deleteCustomer (){
//
//    }
    public List<Customer> getAllCustomers() throws SQLException {
        return customerDAO.getAllCustomer();
    }
    public Customer getSingleCustomer(int id) throws SQLException {
        return customerDAO.getSingleCustomer(id);
    }
}
