package couponjo;

import couponjo.beans.ClientType;
import couponjo.beans.Company;
import couponjo.beans.Coupon;
import couponjo.beans.Customer;
import couponjo.dao.CouponDAO;
import couponjo.db.DBInit;
import couponjo.dbdao.CouponDBDAO;
import couponjo.exceptions.InvalidOperationException;
import couponjo.facade.AdminFacade;
import couponjo.facade.LoginManager;

import java.sql.SQLException;

public class TestAdminFacad {
    public static void main(String[] args) throws SQLException {
        DBInit.createCouponjoSystem();

        LoginManager loginManager = LoginManager.getInstance();
        System.out.println(loginManager.login("admin@admin.com","admin", ClientType.ADMINISTRATOR));

        AdminFacade adminFacade = (AdminFacade) loginManager.login("admin@admin.com","admin", ClientType.ADMINISTRATOR);

        Company company1 = Company.createCompany("cocaCola");
        Company company2 = Company.createCompany("Spring");
        Company company3 = Company.createCompany("IKEA");
        try {
            adminFacade.addCompany(company1);
            System.out.println("Company "+company1.getName()+" created");
            adminFacade.addCompany(company2);
            System.out.println("Company "+company2.getName()+" created");
            adminFacade.addCompany(company3);
            System.out.println("Company "+company3.getName()+" created");
            System.out.println("try to create "+company1.getName()+" again");
            adminFacade.addCompany(company1);

        } catch (InvalidOperationException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("***********************************************************");

        try {
            System.out.println("update company "+company1.getName()+" name to Pepsi");
            company1.setName("Pepsi");
            adminFacade.updateCompany(company1);
        } catch (InvalidOperationException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("***********************************************************");

        try {
            company1.setName("cocaCola");
            company1.setId(999);
            System.out.println("update company "+company1.getName()+" code");
            adminFacade.updateCompany(company1);
        } catch (InvalidOperationException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("***********************************************************");

        System.out.println("Get all companies");
        adminFacade.getAllCompanies().forEach(System.out::println);
        System.out.println("***********************************************************");

        System.out.println("Get single companies");
        System.out.println(adminFacade.getSingleCompany(1));
        System.out.println("***********************************************************");

        Customer customer1 = Customer.createCustomer("Yaniv","Banjo");
        Customer customer2 = Customer.createCustomer("Shmuel","Banjo");
        Customer customer3 = Customer.createCustomer("Avia","Banjo");
        try {
            adminFacade.addCustomer(customer1);
            System.out.println("Add new customer "+ customer1.getFirstName() +" Succeeded");
            adminFacade.addCustomer(customer2);
            System.out.println("Add new customer "+ customer2.getFirstName() +" Succeeded");
            adminFacade.addCustomer(customer3);
            System.out.println("Add new customer "+ customer3.getFirstName() +" Succeeded");
            System.out.println("going to add same customer "+ customer1.getFirstName() +" with the same email");
            adminFacade.addCustomer(customer1);
        } catch (InvalidOperationException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("***********************************************************");

        try {
            System.out.println("change customer code for  "+ customer1.getFirstName() );
            customer1.setId(777);
            adminFacade.updateCustomer(customer1);
        } catch (InvalidOperationException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("***********************************************************");

        System.out.println("Get all customers");
        adminFacade.getAllCustomers().forEach(System.out::println);
        System.out.println("***********************************************************");

        System.out.println("Get single customer");
        System.out.println(adminFacade.getSingleCustomer(1));
        System.out.println("***********************************************************");

        System.out.println("Going to delete company with no coupons");
        try {
            adminFacade.deleteCompany(company2);
        } catch (InvalidOperationException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("***********************************************************");

        System.out.println("Going to delete company with coupons and purchase");
        try {
            Customer.createCustomer("Leni","Banjo");
            Customer.createCustomer("Ela","Banjo");
            Coupon coupon1 = Coupon.createCoupon(3);
            Coupon coupon2 = Coupon.createCoupon(1);
            Coupon coupon3 = Coupon.createCoupon(3);
            CouponDAO couponDAO = new CouponDBDAO();
            couponDAO.addCoupon(coupon1);
            couponDAO.addCoupon(coupon2);
            couponDAO.addCoupon(coupon3);
            couponDAO.addCouponPurchase(1,1);
            adminFacade.deleteCompany(company3);
        } catch (InvalidOperationException e) {
            System.out.println(e.getMessage());
        }

    }
}