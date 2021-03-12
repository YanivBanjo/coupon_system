package couponjo;

import couponjo.beans.*;
import couponjo.exceptions.InvalidOperationException;
import couponjo.exceptions.LoginOperationException;
import couponjo.facade.CompanyFacade;
import couponjo.facade.LoginManager;
import couponjo.utils.ASCIIArtGenerator;
import couponjo.utils.Print;

import java.sql.SQLException;

public class TestCompanyFacade {
    public static void run() throws SQLException, LoginOperationException {
        ASCIIArtGenerator.print("Company Facade");
        LoginManager loginManager = LoginManager.getInstance();
        try {
            System.out.println("check company invalid user");
            System.out.println(loginManager.login("iBurger@gmail.co.il", "Aa123456", ClientType.COMPANY));
        } catch (LoginOperationException e) {
            Print.exception(e.getMessage());
        }
        Print.separation();
        System.out.println("login");
        System.out.println(loginManager.login("Burger@gmail.co.il", "Aa123456", ClientType.COMPANY));

        CompanyFacade companyFacade = (CompanyFacade) loginManager.login("Burger@gmail.co.il", "Aa123456", ClientType.COMPANY);
        Print.separation();

        System.out.println("Get Company coupons");
        Coupon.printCoupons(companyFacade.getAllCouponsByCompanyId());
        Print.separation();

        try {
            System.out.println("add coupon with the same title");
            Coupon coupon = companyFacade.getSingleCoupon(5);
            Coupon newCoupon = Coupon.createCoupon(5);
            newCoupon.setTitle(coupon.getTitle());
            System.out.println("Going to add coupon: " + newCoupon);
            companyFacade.addCoupon(newCoupon);
        } catch (InvalidOperationException e) {
            Print.exception(e.getMessage());

        }
        Print.separation();

        try {
            System.out.println("add coupon with the same title of other company");
            Coupon coupon = companyFacade.getSingleCoupon(4);
            Coupon newCoupon = Coupon.createCoupon(4);
            Coupon newCoupon1 = Coupon.createCoupon(4);
            Coupon newCoupon2 = Coupon.createCoupon(4);
            newCoupon.setTitle(coupon.getTitle());
            System.out.println("Going to add coupon: " + newCoupon);
            companyFacade.addCoupon(newCoupon);
            companyFacade.addCoupon(newCoupon1);
            companyFacade.addCoupon(newCoupon2);
        } catch (InvalidOperationException e) {
            Print.exception(e.getMessage());

        }
        Print.separation();

        try {
            System.out.println("update coupon with different companyid");
            Coupon coupon = companyFacade.getSingleCoupon(4);
            coupon.setCompanyId(999);
            System.out.println("Going to add coupon: " + coupon);
            companyFacade.updateCoupon(coupon);
        } catch (InvalidOperationException e) {
            Print.exception(e.getMessage());

        }
        Print.separation();

        try {
            System.out.println("update coupon with different id");
            Coupon coupon = companyFacade.getSingleCoupon(5);
            coupon.setId(999);
            System.out.println("Going to update coupon from id 4 to 999: " + coupon);
            companyFacade.updateCoupon(coupon);
            System.out.println("get coupon 4");
            Coupon.printCoupon(companyFacade.getSingleCoupon(5));
            System.out.println("get coupon 999");
            System.out.println(companyFacade.getSingleCoupon(999));
        } catch (InvalidOperationException e) {
            Print.exception(e.getMessage());

        }
        Print.separation();

        try {

            Coupon coupon = companyFacade.getSingleCoupon(4);
            System.out.println("going to delete coupon: " + coupon);
            companyFacade.deleteCoupon(coupon);
        } catch (InvalidOperationException e) {
            Print.exception(e.getMessage());

        }
        Print.separation();

        System.out.println("Get Company coupons");
        Coupon.printCoupons(companyFacade.getAllCouponsByCompanyId());
        Print.separation();

        System.out.println("Get coupons by category "+Category.values()[1]);
        Coupon.printCoupons(companyFacade.getAllCouponsByCategoryAndCompanyId(1));
        Print.separation();

        System.out.println("Get coupons by price lower then 25");
        Coupon.printCoupons(companyFacade.getAllCouponsWithPriceLowerThen(25));
        Print.separation();

    }
}
