package couponjo;

import couponjo.dao.CompanyDAO;
import couponjo.dao.CouponDAO;
import couponjo.dao.CustomerDAO;
import couponjo.db.DBInit;
import couponjo.dbdao.CompanyDBDAO;
import couponjo.dbdao.CouponDBDAO;
import couponjo.dbdao.CustomerDBDAO;
import couponjo.utils.ASCIIArtGenerator;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws  SQLException {

//        Class.forName("com.mysql.cj.jdbc.Driver");
        DBInit.createCouponjoSystem();
        ASCIIArtGenerator.print("COUPONJO");
        ASCIIArtGenerator.print("CompanyDAO");
        CompanyDAO companyDAO = new CompanyDBDAO();
        System.out.println(companyDAO.getSingleCompany(1));
        companyDAO.getAllCompanies().forEach(System.out::println);

        ASCIIArtGenerator.print("CustomerDAO");
        CustomerDAO customerDAO = new CustomerDBDAO();
        System.out.println(customerDAO.getSingleCustomer(4));
        customerDAO.getAllCustomer().forEach(System.out::println);

        ASCIIArtGenerator.print("CouponDAO");
        CouponDAO couponDAO = new CouponDBDAO();
        System.out.println(couponDAO.getSingleCoupon(9));
        couponDAO.getAllCoupons().forEach(System.out::println);
    }
}
