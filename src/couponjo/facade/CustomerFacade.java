package couponjo.facade;

import couponjo.beans.Category;
import couponjo.beans.Coupon;
import couponjo.beans.Customer;
import couponjo.beans.CustomerCouponPurchase;
import couponjo.exceptions.CouponOperationException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerFacade extends ClientFacade {
    private int customerId;

    public CustomerFacade() {
    }

    @Override
    boolean login(String email, String password) throws SQLException {
        Customer customer = customerDAO.getCustomerByEmail(email);
        if (customer != null) {
            this.customerId = customer.getId();
            return customerDAO.isCustomerExist(email,password);
        }
        return false;
    }

    public void purchaseCoupon(int couponId) throws SQLException, CouponOperationException {
        List<CustomerCouponPurchase> customerCouponPurchaseList = couponDAO.getAllCouponPurchaseByCustomerId(customerId);
        for (CustomerCouponPurchase p : customerCouponPurchaseList) {
            if (couponId == p.getCouponId()) {
                throw new CouponOperationException("Coupon was previously purchase");
            }
        }
        couponDAO.addCouponPurchase(customerId, couponId);
        Coupon coupon = couponDAO.getSingleCoupon(couponId);
        if (coupon.getEnd_date().getTime() - new Date().getTime() < 0) {
            throw new CouponOperationException("Coupon is no longer valid");
        }

        if (coupon.getAmount() == 0) {
            throw new CouponOperationException("Sorry no more coupons left");
        }
        coupon.setAmount(coupon.getAmount() - 1);
        couponDAO.updateCoupon(coupon);
    }

    public Coupon getSingleCoupon(int couponId) throws SQLException, CouponOperationException {
        Coupon coupon = couponDAO.getSingleCoupon(couponId);
        if (coupon == null) {
            throw new CouponOperationException("No such Coupon exist");
        }
        return coupon;
    }

    public List<Coupon> getAllCouponsPurchaseByCustomer() throws SQLException {
        List<CustomerCouponPurchase> customerCouponPurchaseList = couponDAO.getAllCouponPurchaseByCustomerId(customerId);
        List<Coupon> couponList = new ArrayList<>();
        for (CustomerCouponPurchase p : customerCouponPurchaseList) {
            couponList.add(couponDAO.getSingleCoupon(p.getCouponId()));
        }
        return couponList;
    }

    public List<Coupon> getAllCouponsPurchaseByCategory(Category category) throws SQLException {
        List<Coupon> couponList = new ArrayList<>();
        for (Coupon c : getAllCouponsPurchaseByCustomer()) {
            if (c.getCategory().equals(category)) {
                couponList.add(c);
            }
        }
        return couponList;
    }

    public List<Coupon> getAllCouponsPurchaseByPriceLowerThen(double price) throws SQLException {
        List<Coupon> couponList = new ArrayList<>();
        for (Coupon c : getAllCouponsPurchaseByCustomer()) {
            if (c.getPrice() < price) {
                couponList.add(c);
            }
        }
        return couponList;
    }

    public Customer getCustomerDetails() throws SQLException {
        return customerDAO.getSingleCustomer(customerId);
    }
}
