package couponjo.beans;

import couponjo.dao.CouponDAO;
import couponjo.dbdao.CouponDBDAO;
import couponjo.utils.Print;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class CouponExperationDailyJob implements Runnable {
    private boolean quit = true;
    private CouponDAO couponDAO = new CouponDBDAO();

    public CouponExperationDailyJob() {

    }


    @Override
    public void run() {
        while (quit) {
            try {
                Thread.sleep(10000);
                Print.thread("DAILY JOB RUN: verify old coupons not in the system ");
                List<Coupon> couponList = couponDAO.getAllCoupons();
                if (couponList != null) {
                    for (Coupon c : couponList) {
                        if (c.getEnd_date().getTime() - new Date().getTime() < 0) {
                            Print.thread("Coupon is no longer valid going to delete coupon and is purchase history");
                            Print.thread(c.toString());
                            List<CustomerCouponPurchase> customerCouponPurchaseList = couponDAO.getAllCouponPurchaseByCouponId(c.getId());
                            if (customerCouponPurchaseList != null) {
                                for (CustomerCouponPurchase p : customerCouponPurchaseList) {
                                    couponDAO.deleteCouponPurchase(p.getCustomerId(), p.getCouponId());
                                }
                            }
                            couponDAO.deleteCoupon(c);
                        }
                    }
                }

            } catch (SQLException | InterruptedException e) {
                Print.exception(e.getMessage());
            }
        }

    }

}
