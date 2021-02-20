package couponjo;

import couponjo.beans.*;
import couponjo.dao.CouponDAO;
import couponjo.db.DBInit;
import couponjo.dbdao.CouponDBDAO;
import couponjo.exceptions.InvalidOperationException;
import couponjo.facade.AdminFacade;
import couponjo.facade.CompanyFacade;
import couponjo.facade.LoginManager;

import java.sql.SQLException;

public class TestCompanyFacade {
    public static void main(String[] args) throws SQLException {

        LoginManager loginManager = LoginManager.getInstance();
        System.out.println(loginManager.login("Burger@gmail.co.il", "Aa123456", ClientType.COMPANY));

        CompanyFacade companyFacade = (CompanyFacade) loginManager.login("Burger@gmail.co.il", "Aa123456", ClientType.COMPANY);

        //COMPANY
        System.out.println("Get Company coupons");
        companyFacade.getAllCouponsByCompanyId().forEach(System.out::println);
        System.out.println("***********************************************************");

        try {
            System.out.println("add coupon with the same title");
            Coupon coupon = companyFacade.getSingleCoupon(5);
            Coupon newCoupon = Coupon.createCoupon(5);
            newCoupon.setTitle(coupon.getTitle());
            System.out.println("Going to add coupon: " + newCoupon);
            companyFacade.addCoupon(newCoupon);
        } catch (InvalidOperationException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("***********************************************************");

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
            System.out.println(e.getMessage());
        }
        System.out.println("***********************************************************");

        try {
            System.out.println("update coupon with different companyid");
            Coupon coupon = companyFacade.getSingleCoupon(4);
            coupon.setCompanyId(999);
            System.out.println("Going to add coupon: " + coupon);
            companyFacade.updateCoupon(coupon);
        } catch (InvalidOperationException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("***********************************************************");

        try {
            System.out.println("update coupon with different id");
            Coupon coupon = companyFacade.getSingleCoupon(5);
            coupon.setId(999);
            System.out.println("Going to update coupon from id 4 to 999: " + coupon);
            companyFacade.updateCoupon(coupon);
            System.out.println("get coupon 4: " + companyFacade.getSingleCoupon(5));
            System.out.println("get coupon 999: " + companyFacade.getSingleCoupon(999));
        } catch (InvalidOperationException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("***********************************************************");

        try {

            Coupon coupon = companyFacade.getSingleCoupon(4);
            System.out.println("going to delete coupon: " + coupon);
            companyFacade.deleteCoupon(coupon);
        } catch (InvalidOperationException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("***********************************************************");

        System.out.println("Get Company coupons");
        companyFacade.getAllCouponsByCompanyId().forEach(System.out::println);
        System.out.println("***********************************************************");

        System.out.println("Get coupons by category");
        companyFacade.getAllCouponsByCategoryAndCompanyId(1).forEach(System.out::println);
        System.out.println("***********************************************************");

        System.out.println("Get coupons by price lower then 25");
        companyFacade.getAllCouponsWithPriceLowerThen(25).forEach(System.out::println);
        System.out.println("***********************************************************");

    }
}
