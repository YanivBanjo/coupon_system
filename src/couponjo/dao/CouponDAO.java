package couponjo.dao;

import couponjo.beans.Coupon;
import couponjo.beans.CustomerCouponPurchase;

import java.sql.SQLException;
import java.util.List;

public interface CouponDAO {
    void addCoupon(Coupon coupon) throws SQLException;

    void updateCoupon(Coupon coupon) throws SQLException;

    void deleteCoupon(Coupon coupon) throws SQLException;

    Coupon getSingleCoupon(int id) throws SQLException;

    List<Coupon> getAllCoupons() throws SQLException;

    void addCouponPurchase(int customerId,int couponId) throws SQLException;

    void deleteCouponPurchase(int customerId,int couponId) throws SQLException;

    CustomerCouponPurchase getCouponPhurcaseByCustomerId(int customerId) throws SQLException;
}
