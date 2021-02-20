package couponjo.facade;

import couponjo.exceptions.InvalidOperationException;

import java.sql.SQLException;

public class CustomerFacade extends ClientFacade {
    private int customerId;

    @Override
    boolean login(String email, String password) throws SQLException {
        return customerDAO.isCustomerExist(email, password);
    }

    public void purchaseCoupon(int customerId, int couponId) throws SQLException, InvalidOperationException {
        //add logic
//        CustomerCouponPurchase customerPurchase = couponDAO.getCouponPurcaseByCustomerId(customerId);
//        if (customerPurchase.getCouponId() == couponId){
//            throw new InvalidOperationException("Coupon can only be purchse once");
//        }
//        couponDAO.addCouponPurchase(customerId,couponId);
    }


}
