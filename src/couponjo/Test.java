package couponjo;

import couponjo.beans.Category;
import couponjo.beans.Company;
import couponjo.beans.Coupon;
import couponjo.beans.Customer;
import couponjo.dao.CompanyDAO;
import couponjo.dao.CouponDAO;
import couponjo.dao.CustomerDAO;
import couponjo.db.DBInit;
import couponjo.dbdao.CompanyDBDAO;
import couponjo.dbdao.CouponDBDAO;
import couponjo.dbdao.CustomerDBDAO;
import couponjo.utils.ASCIIArtGenerator;
import couponjo.utils.PasswordUtils;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class Test {
    public static void main(String[] args) throws SQLException {

//        Class.forName("com.mysql.cj.jdbc.Driver");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DBInit.createCouponjoSystem();
        ASCIIArtGenerator.print("COUPONJO");
        ASCIIArtGenerator.print("CompanyDAO");
        CompanyDAO companyDAO = new CompanyDBDAO();

        System.out.println("*******************  add company Electro ************************");
        Company c1 = new Company("Electro","electro@gmail.com", PasswordUtils.generate(10));
        companyDAO.addCompany(c1);
        System.out.println(companyDAO.getSingleCompany(10));

        System.out.println("*******************  update company to ElectroBanjo ************************");
        Company toUpdate = companyDAO.getSingleCompany(10);
        toUpdate.setName("ElectroBanjo");
        toUpdate.setEmail("ElectroBanjo@gmail.com");
        toUpdate.setPassword("greatPassword1!");
        companyDAO.updateCompany(toUpdate);
        System.out.println(companyDAO.getSingleCompany(10));

        System.out.println("*******************  is company ElectroBanjo exist ************************");
        System.out.println(companyDAO.isCompanyExist("ElectroBanjo@gmail.com","greatPassword1!"));

        System.out.println("*******************  delete company ElectroBanjo ************************");
        companyDAO.deleteCompany(companyDAO.getSingleCompany(10));
        companyDAO.getAllCompanies().forEach(System.out::println);

        System.out.println("*******************  is company ElectroBanjo exist ************************");
        System.out.println(companyDAO.isCompanyExist("ElectroBanjo@gmail.com","greatPassword1!"));



        ASCIIArtGenerator.print("CustomerDAO");
        CustomerDAO customerDAO = new CustomerDBDAO();
        System.out.println("*******************  add customer Avner ************************");
        Customer customer1 = new Customer("Avner","Katz","Avner.katz@gmail.com", PasswordUtils.generate(10));
        customerDAO.addCustomer(customer1);
        System.out.println(customerDAO.getSingleCustomer(7));

        System.out.println("*******************  update customer to Moshe ************************");
        Customer toUpdateCustomer = customerDAO.getSingleCustomer(7);
        toUpdateCustomer.setFirstName("Moshe");
        toUpdateCustomer.setEmail("Moshe.katz@gmail.com");
        toUpdateCustomer.setPassword("greatPassword1!");
        customerDAO.updateCustomer(toUpdateCustomer);
        System.out.println(customerDAO.getSingleCustomer(7));

        System.out.println("*******************  is customer Moshe exist ************************");
        System.out.println(customerDAO.isCustomerExist("Moshe.katz@gmail.com","greatPassword1!"));

        System.out.println("*******************  delete customer Moshe ************************");
        customerDAO.deleteCustomer(customerDAO.getSingleCustomer(7));
        customerDAO.getAllCustomer().forEach(System.out::println);

        System.out.println("*******************  is customer Moshe exist ************************");
        System.out.println(customerDAO.isCustomerExist("Moshe.katz@gmail.com","greatPassword1!"));


        ASCIIArtGenerator.print("CouponDAO");
        CouponDAO couponDAO = new CouponDBDAO();


        System.out.println("*******************  add coupon ************************");
        Coupon coupon1 = new Coupon(9, Category.VACATION,"Super Clasico","don't miss it",
                Date.valueOf(LocalDate.now().plusDays(2)),Date.valueOf(LocalDate.now().plusDays(2)),7,8.5,"imagin");
        couponDAO.addCoupon(coupon1);
        System.out.println(couponDAO.getSingleCoupon(10));

        System.out.println("*******************  update coupon ************************");
        Coupon toUpdateCoupon = couponDAO.getSingleCoupon(10);
        toUpdateCoupon.setAmount(70);
        toUpdateCoupon.setDescription("5 last tickets");
        couponDAO.updateCoupon(toUpdateCoupon);
        System.out.println(couponDAO.getSingleCoupon(10));

        System.out.println("*******************  delete coupon ************************");
        couponDAO.deleteCoupon(couponDAO.getSingleCoupon(10));
        couponDAO.getAllCoupons().forEach(System.out::println);

        couponDAO.addCouponPurchase(1,1);
        couponDAO.addCouponPurchase(2,2);
        couponDAO.addCouponPurchase(5,4);
        couponDAO.addCouponPurchase(3,2);
        couponDAO.addCouponPurchase(1,4);

        couponDAO.deleteCouponPurchase(1,1);
        couponDAO.deleteCouponPurchase(1,4);

    }
}
