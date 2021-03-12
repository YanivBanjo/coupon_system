package couponjo.facade;

import couponjo.beans.Company;
import couponjo.beans.Coupon;
import couponjo.beans.Customer;

import couponjo.beans.CustomerCouponPurchase;
import couponjo.exceptions.InvalidOperationException;
import couponjo.utils.Print;

import java.sql.SQLException;
import java.util.List;

public class AdminFacade extends ClientFacade {

    public AdminFacade() {
    }

    @Override
    boolean login(String email, String password) {
        return (email.equals("admin@admin.com") && password.equals("admin"));
    }

    public void addCompany(Company company) throws SQLException, InvalidOperationException {
        Company byName = getCompanyByName(company.getName());
        Company byEmail = getCompanyByEmail(company.getEmail());
        if (byName == null && byEmail == null) {
            companyDAO.addCompany(company);
        } else {
            throw new InvalidOperationException("Company can't be added, name/email is already exist");
        }
    }

    public void deleteCompany(Company company) throws SQLException, InvalidOperationException {
        Company toDelete = getCompanyByName(company.getName());
        if (toDelete == null) {
            throw new InvalidOperationException("company doesn't exist");
        }
        List<Coupon> couponList = getAllCouponsByCompanyId(toDelete.getId());
        Coupon.printCoupons(couponList);
        for (Coupon c : couponList) {
            deletePurchases(getCouponPurchaseByCouponId(c.getId()), c.getTitle());
        }
        deleteCoupons(couponList);

        companyDAO.deleteCompany(toDelete);
        System.out.println("Company deleted");
    }

    public void updateCompany(Company company) throws SQLException, InvalidOperationException {
        Company toCompare = getCompanyByName(company.getName());
        if (toCompare == null) {
            throw new InvalidOperationException("company doesn't exist");
        }
        company.setId(toCompare.getId());
        companyDAO.updateCompany(company);
    }

    public List<Company> getAllCompanies() throws SQLException {
        return companyDAO.getAllCompanies();
    }

    public Company getSingleCompany(int id) throws SQLException {
        return companyDAO.getSingleCompany(id);
    }

    public Company getCompanyByName(String name) throws SQLException {
        return companyDAO.getCompanyByName(name);
    }

    public Company getCompanyByEmail(String email) throws SQLException {
        return companyDAO.getCompanyByEmail(email);
    }

    public void addCustomer(Customer customer) throws SQLException, InvalidOperationException {
        Customer byEmail = getCustomerByEmail(customer.getEmail());
        if (byEmail == null) {
            customerDAO.addCustomer(customer);
        } else {
            throw new InvalidOperationException("Customer can't be added, email is already taken");
        }
    }

    public void updateCustomer(Customer customer) throws SQLException, InvalidOperationException {
        Customer customerByEmail = getCustomerByEmail(customer.getEmail());
        if (customerByEmail != null) {
            if (customer.getId() == customerByEmail.getId()) {
                customerDAO.updateCustomer(customer);
            } else {
                throw new InvalidOperationException("Customer code can't be update");
            }
        } else {
            throw new InvalidOperationException("Customer " + customer.getEmail() + " dosen't exist");
        }
    }

    public void deleteCustomer(Customer customer) throws SQLException, InvalidOperationException {
        Customer customerByEmail = getCustomerByEmail(customer.getEmail());
        if (customerByEmail != null) {
            List<CustomerCouponPurchase> customerCouponPurchaseList = getCouponPurchaseByCustomerId(customerByEmail.getId());
            deletePurchases(customerCouponPurchaseList, customerByEmail.getEmail());
            customerDAO.deleteCustomer(customerByEmail);
        } else {
            throw new InvalidOperationException("Customer " + customer.getEmail() + " dosen't exist");
        }
    }

    public List<Customer> getAllCustomers() throws SQLException {
        return customerDAO.getAllCustomer();
    }

    public Customer getSingleCustomer(int id) throws SQLException {
        return customerDAO.getSingleCustomer(id);
    }

    private Customer getCustomerByEmail(String email) throws SQLException {
        return customerDAO.getCustomerByEmail(email);
    }

    private List<Coupon> getAllCouponsByCompanyId(int id) throws SQLException {
        return couponDAO.getAllCouponsByCompanyId(id);
    }

    private List<CustomerCouponPurchase> getCouponPurchaseByCouponId(int id) throws SQLException {
        return couponDAO.getAllCouponPurchaseByCouponId(id);
    }

    private List<CustomerCouponPurchase> getCouponPurchaseByCustomerId(int id) throws SQLException {
        return couponDAO.getAllCouponPurchaseByCustomerId(id);
    }

    private void deletePurchases(List<CustomerCouponPurchase> purchaseList, String name) {
        if (purchaseList.size() > 0) {
            System.out.println("Going to delete customer purchases for " + name);
            purchaseList.forEach(purchase -> {
                try {
                    couponDAO.deleteCouponPurchase(purchase.getCustomerId(), purchase.getCouponId());
                } catch (SQLException e) {
                    Print.exception(e.getMessage());

                }
            });
        } else {
            System.out.println("For Coupon named " + name + " ,No purchases found to delete");
        }
    }

    private void deleteCoupons(List<Coupon> couponList) {
        if (couponList.size() > 0) {
            couponList.forEach(coupon -> {
                try {
                    System.out.println("Deleting Company Coupon " + coupon.getTitle());
                    couponDAO.deleteCoupon(coupon);
                } catch (SQLException e) {
                    Print.exception(e.getMessage());

                }
            });
        } else {
            System.out.println("No coupons found to delete");
        }
    }
}
