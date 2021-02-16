package couponjo.facade;

import couponjo.beans.Coupon;
import couponjo.beans.Customer;
import couponjo.beans.CustomerCouponPurchase;
import couponjo.dao.CouponDAO;
import couponjo.dao.CustomerDAO;
import couponjo.dbdao.CouponDBDAO;
import couponjo.dbdao.CustomerDBDAO;
import couponjo.exceptions.InvalidOperationException;

import java.sql.SQLException;

public class CustomerFacade extends ClientFacade {
    private CustomerDAO customerDAO = new CustomerDBDAO();
    private CouponDAO couponDAO = new CouponDBDAO();

    @Override
    boolean login(String email, String password) throws SQLException {
        return  customerDAO.isCustomerExist(email, password);
    }

    public void purchaseCoupon (int customerId, int couponId) throws SQLException, InvalidOperationException {
        //add logic
        CustomerCouponPurchase customerPurchase = couponDAO.getCouponPhurcaseByCustomerId(customerId);
        if (customerPurchase.getCouponId() == couponId){
            throw new InvalidOperationException("Coupon can only be purchse once");
        }
        couponDAO.addCouponPurchase(customerId,couponId);
    }


}
