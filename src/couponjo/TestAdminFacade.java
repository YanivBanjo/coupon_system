package couponjo;

import couponjo.beans.ClientType;
import couponjo.beans.Company;
import couponjo.beans.Coupon;
import couponjo.beans.Customer;
import couponjo.dao.CouponDAO;
import couponjo.dao.CustomerDAO;
import couponjo.db.DBInit;
import couponjo.dbdao.CouponDBDAO;
import couponjo.dbdao.CustomerDBDAO;
import couponjo.exceptions.InvalidOperationException;
import couponjo.exceptions.LoginOperationException;
import couponjo.facade.AdminFacade;
import couponjo.facade.LoginManager;
import couponjo.utils.ASCIIArtGenerator;
import couponjo.utils.Print;

import java.sql.SQLException;

public class TestAdminFacade {
    public static void run() throws SQLException, LoginOperationException {
        DBInit.createCouponjoSystem();
        ASCIIArtGenerator.print("COUPONJO");
        ASCIIArtGenerator.print("Admin Facade");

        LoginManager loginManager = LoginManager.getInstance();
        try {
            System.out.println("check admin invalid user");
            System.out.println(loginManager.login("admini@admin.com", "admin", ClientType.ADMINISTRATOR));
        } catch (LoginOperationException e) {
            Print.exception(e.getMessage());
        }

        Print.separation();
        System.out.println("login");
        System.out.println(loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR));

        AdminFacade adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
        Print.separation();

        Company company1 = Company.createCompany("cocaCola");
        Company company2 = Company.createCompany("Spring");
        Company company3 = Company.createCompany("IKEA");
        Company company4 = Company.createCompany("Burger");
        company4.setPassword("Aa123456");

        try {
            adminFacade.addCompany(company1);
            System.out.println("Company " + company1.getName() + " created");
            adminFacade.addCompany(company2);
            System.out.println("Company " + company2.getName() + " created");
            adminFacade.addCompany(company3);
            System.out.println("Company " + company3.getName() + " created");
            adminFacade.addCompany(company4);
            System.out.println("Company " + company4.getName() + " created");
            System.out.println("try to create " + company1.getName() + " again");
            adminFacade.addCompany(company1);

        } catch (InvalidOperationException e) {
            Print.exception(e.getMessage());

        }
        Print.separation();

        try {
            System.out.println("update company " + company1.getName() + " password");
            System.out.println("BEFORE");
            Company.printCompany(adminFacade.getSingleCompany(1));
            company1.setPassword("Aa123456");
            adminFacade.updateCompany(company1);
            System.out.println("AFTER");
            Company.printCompany(adminFacade.getSingleCompany(1));
        } catch (InvalidOperationException e) {
            Print.exception(e.getMessage());

        }
        Print.separation();

        try {
            System.out.println("update company " + company1.getName() + " name to Pepsi");
            company1.setName("Pepsi");
            System.out.println("BEFORE DB UPDATE");
            Company.printCompany(company1);
            adminFacade.updateCompany(company1);
            System.out.println("AFTER");
            Company.printCompany(adminFacade.getSingleCompany(1));
        } catch (InvalidOperationException e) {
            Print.exception(e.getMessage());

        }
        Print.separation();

        try {
            company1.setName("cocaCola");
            company1.setId(999);
            System.out.println("update company " + company1.getName() + " code to 999");
            adminFacade.updateCompany(company1);
            System.out.println("AFTER");
            Company.printCompany(adminFacade.getSingleCompany(1));

        } catch (InvalidOperationException e) {
            Print.exception(e.getMessage());

        }
        Print.separation();

        System.out.println("Get all companies");
        Company.printCompanies(adminFacade.getAllCompanies());
        Print.separation();

        System.out.println("Get single company");
        Company.printCompany(adminFacade.getSingleCompany(1));
        Print.separation();

        System.out.println("Going to delete company with no coupons");
        try {
            adminFacade.deleteCompany(company2);
        } catch (InvalidOperationException e) {
            Print.exception(e.getMessage());
        }
        Print.separation();

        System.out.println("Going to delete company with coupons and purchase");
        try {
            CustomerDAO customer = new CustomerDBDAO();
            Customer customer1 = Customer.createCustomer("Leni", "Banjo");
            Customer customer2 = Customer.createCustomer("Ela", "Banjo");
            customer.addCustomer(customer1);
            customer.addCustomer(customer2);
            Coupon coupon1 = Coupon.createCoupon(3);
            Coupon coupon2 = Coupon.createCoupon(1);
            Coupon coupon3 = Coupon.createCoupon(3);
            Coupon coupon4 = Coupon.createCoupon(4);
            Coupon coupon5 = Coupon.createCoupon(4);
            CouponDAO couponDAO = new CouponDBDAO();
            couponDAO.addCoupon(coupon1);
            couponDAO.addCoupon(coupon2);
            couponDAO.addCoupon(coupon3);
            couponDAO.addCoupon(coupon4);
            couponDAO.addCoupon(coupon5);
            couponDAO.addCouponPurchase(1, 1);
            adminFacade.deleteCompany(company3);
        } catch (InvalidOperationException e) {
            Print.exception(e.getMessage());

        }
        Print.separation();

        Customer customer1 = Customer.createCustomer("Yaniv", "Banjo");
        Customer customer2 = Customer.createCustomer("Shmuel", "Banjo");
        Customer customer3 = Customer.createCustomer("Avia", "Banjo");
        customer1.setPassword("Aa123456");
        try {
            adminFacade.addCustomer(customer1);
            System.out.println("Add new customer " + customer1.getFirstName() + " Succeeded");
            adminFacade.addCustomer(customer2);
            System.out.println("Add new customer " + customer2.getFirstName() + " Succeeded");
            adminFacade.addCustomer(customer3);
            System.out.println("Add new customer " + customer3.getFirstName() + " Succeeded");
            System.out.println("going to add same customer " + customer1.getFirstName() + " with the same email");
            adminFacade.addCustomer(customer1);
        } catch (InvalidOperationException e) {
            Print.exception(e.getMessage());

        }
        Print.separation();

        try {
            System.out.println("change customer code for  " + customer1.getFirstName());
            customer1.setId(777);
            adminFacade.updateCustomer(customer1);
        } catch (InvalidOperationException e) {
            Print.exception(e.getMessage());

        }
        Print.separation();
        System.out.println("Get all customers");
        Customer.printCustomers(adminFacade.getAllCustomers());
        Print.separation();
        try {
            System.out.println("Going to delete customer " + customer2.getFirstName() + " without purchases");
            adminFacade.deleteCustomer(customer2);
        } catch (InvalidOperationException e) {
            e.printStackTrace();
        }
        Print.separation();

        try {
            System.out.println("Going to delete customer " + customer3.getFirstName() + " with purchases");
            CouponDAO couponDAO = new CouponDBDAO();
            Coupon coupon1 = Coupon.createCoupon(1);
            Coupon coupon2 = Coupon.createCoupon(1);
            Coupon coupon3 = Coupon.createCoupon(1);
            couponDAO.addCoupon(coupon1);
            couponDAO.addCoupon(coupon2);
            couponDAO.addCoupon(coupon3);
            couponDAO.addCouponPurchase(5, 4);
            couponDAO.addCouponPurchase(5, 5);
            couponDAO.addCouponPurchase(3, 4);
            couponDAO.addCouponPurchase(2, 4);
            couponDAO.addCouponPurchase(5, 6);
            adminFacade.deleteCustomer(customer3);
        } catch (InvalidOperationException e) {
            e.printStackTrace();
        }
        Print.separation();

        System.out.println("Get all customers");
        Customer.printCustomers(adminFacade.getAllCustomers());
        Print.separation();

        System.out.println("Get single customer");
        Customer.printCustomer(adminFacade.getSingleCustomer(1));
        Print.separation();

    }
}
