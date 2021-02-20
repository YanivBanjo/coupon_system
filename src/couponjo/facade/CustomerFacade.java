package couponjo.facade;

import couponjo.beans.Category;
import couponjo.beans.Coupon;
import couponjo.beans.Customer;
import couponjo.beans.CustomerCouponPurchase;
import couponjo.dao.CouponDAO;
import couponjo.exceptions.InvalidOperationException;

import java.sql.SQLException;
import java.time.LocalDate;
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
        customerId = customer.getId();
        return customerDAO.isCustomerExist(email, password);
    }

    public void purchaseCoupon(int couponId) throws SQLException, InvalidOperationException {
        List<CustomerCouponPurchase> customerCouponPurchaseList = couponDAO.getAllCouponPurcaseByCustomerId(customerId);
        for (CustomerCouponPurchase p : customerCouponPurchaseList) {
            if (couponId == p.getCouponId()) {
                throw new InvalidOperationException("Coupon was previously purchase");
            }
        }
        couponDAO.addCouponPurchase(customerId, couponId);
        Coupon coupon = couponDAO.getSingleCoupon(couponId);
        if (coupon.getEnd_date().getTime() - new Date().getTime() < 0) {
            throw new InvalidOperationException("Coupon is no longer valid");
        }

        if (coupon.getAmount() == 0) {
            throw new InvalidOperationException("Sorry no more coupons left");
        }
        coupon.setAmount(coupon.getAmount() - 1);
        couponDAO.updateCoupon(coupon);
    }

    public Coupon getSingleCoupon(int couponId) throws SQLException, InvalidOperationException {
        Coupon coupon = couponDAO.getSingleCoupon(couponId);
        if (coupon == null) {
            throw new InvalidOperationException("No such Coupon exist");
        }
        return coupon;
    }

    public List<Coupon> getAllCouponsPurchaseByCustomer() throws SQLException {
        List<CustomerCouponPurchase> customerCouponPurchaseList = couponDAO.getAllCouponPurcaseByCustomerId(customerId);
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
