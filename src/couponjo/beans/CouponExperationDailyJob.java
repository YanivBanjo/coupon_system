package couponjo.beans;

import couponjo.dao.CouponDAO;
import couponjo.exceptions.InvalidOperationException;
import couponjo.utils.Print;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class CouponExperationDailyJob implements Runnable{
    private boolean quit;
    private CouponDAO couponDAO;

    public CouponExperationDailyJob() {

    }


    @Override
    public void run() {
        System.out.println("Thread start");
        try {
            List<Coupon> couponList = couponDAO.getAllCoupons();
            if(couponList != null){
                for(Coupon c:couponList){
                    if (c.getEnd_date().getTime() - new Date().getTime() < 0) {
                        System.out.println("Coupon is no longer valid going to delete coupon and is purchase history");
                        couponDAO.deleteCoupon(c);
                        List<CustomerCouponPurchase> customerCouponPurchaseList = couponDAO.getAllCouponPurchaseByCouponId(c.getId());
                        if (customerCouponPurchaseList != null){
                            for (CustomerCouponPurchase p:customerCouponPurchaseList){
                                couponDAO.deleteCouponPurchase(p.getCustomerId(),p.getCouponId());
                            }
                        }
                    }
                }
            }

        } catch (SQLException e) {
            Print.exception(e.getMessage());
        }
    }

}
