package couponjo;

import couponjo.beans.Category;
import couponjo.beans.ClientType;
import couponjo.beans.Coupon;
import couponjo.beans.Customer;
import couponjo.dao.CouponDAO;
import couponjo.dbdao.CouponDBDAO;
import couponjo.exceptions.CouponOperationException;
import couponjo.exceptions.LoginOperationException;
import couponjo.facade.CustomerFacade;
import couponjo.facade.LoginManager;
import couponjo.utils.ASCIIArtGenerator;
import couponjo.utils.Print;

import java.sql.SQLException;
import java.time.LocalDate;

public class TestCustomerFacade {
    public static void run() throws SQLException, LoginOperationException {
        ASCIIArtGenerator.print("Customer Facade");

        LoginManager loginManager = LoginManager.getInstance();
        try {
            System.out.println("check customer invalid user");
            System.out.println(loginManager.login("Yanivi.Banjo@gmail.com", "Aa123456", ClientType.CUSTOMER));
        } catch (LoginOperationException e) {
            Print.exception(e.getMessage());
        }
        Print.separation();
        System.out.println("login");
        System.out.println(loginManager.login("Yaniv.Banjo@gmail.com", "Aa123456", ClientType.CUSTOMER));

        CustomerFacade customerFacade = (CustomerFacade) loginManager.login("Yaniv.Banjo@gmail.com", "Aa123456", ClientType.CUSTOMER);
        Print.separation();

        System.out.println("purchase coupon by customer for the first time");
        try {
            System.out.println("Print coupon details verify amount will be changed after the purchase");
            Coupon.printCoupon(customerFacade.getSingleCoupon(8));
            customerFacade.purchaseCoupon(8);
            Coupon.printCoupon(customerFacade.getSingleCoupon(8));
        } catch (CouponOperationException e) {
            Print.exception(e.getMessage());

        }
        Print.separation();

        System.out.println("purchase coupon by customer for the second time");
        try {
            customerFacade.purchaseCoupon(8);
        } catch (CouponOperationException e) {
            Print.exception(e.getMessage());

        }
        Print.separation();

        System.out.println("purchase coupon no amount ");
        Coupon coupon = Coupon.createCoupon(4);
        coupon.setAmount(0);
        CouponDAO couponDAO = new CouponDBDAO();
        couponDAO.addCoupon(coupon);
        try {
            Coupon.printCoupon(customerFacade.getSingleCoupon(9));
            customerFacade.purchaseCoupon(9);
        } catch (CouponOperationException e) {
            Print.exception(e.getMessage());

        }
        Print.separation();


        System.out.println("purchase old coupon ");
        Coupon coupon1 = Coupon.createCoupon(4);
        coupon1.setStart_date(java.sql.Date.valueOf(LocalDate.now().minusDays(7)));
        coupon1.setEnd_date(java.sql.Date.valueOf(LocalDate.now().minusDays(2)));
        couponDAO.addCoupon(coupon1);
        try {
            Coupon.printCoupon(customerFacade.getSingleCoupon(10));
            customerFacade.purchaseCoupon(10);
        } catch (CouponOperationException e) {
            Print.exception(e.getMessage());

        }
        Print.separation();

        System.out.println("Get all Coupons purchase by customer login");
        Coupon.printCoupons(customerFacade.getAllCouponsPurchaseByCustomer());
        Print.separation();

        System.out.println("Get all Coupons purchase by customer with category Food");
        Coupon.printCoupons(customerFacade.getAllCouponsPurchaseByCategory(Category.FOOD));
        Print.separation();

        System.out.println("Get all Coupons purchase by customer with category Electricity");
        Coupon.printCoupons(customerFacade.getAllCouponsPurchaseByCategory(Category.ELECTRICITY));
        Print.separation();

        System.out.println("Get all Coupons purchase by customer with lower price then 20");
        Coupon.printCoupons(customerFacade.getAllCouponsPurchaseByPriceLowerThen(20));
        Print.separation();

        System.out.println("Get Customer Details");
        Customer.printCustomer(customerFacade.getCustomerDetails());
        Print.separation();

    }
}
