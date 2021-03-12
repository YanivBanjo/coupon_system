package couponjo.beans;

import couponjo.dao.CouponDAO;
import couponjo.dbdao.CouponDBDAO;
import couponjo.utils.Print;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class CouponExpirationDailyJob implements Runnable {
    private boolean quit;
    private CouponDAO couponDAO = new CouponDBDAO();
    long millis=System.currentTimeMillis();
    java.sql.Date date=new java.sql.Date(millis);

    public CouponExpirationDailyJob() {

    }

    @Override
    public void run() {
        while (!quit) {
            try {
                Print.thread("DAILY JOB RUN, verify old coupons not in the system");
                Thread.sleep(5000);
                List<Coupon> couponList = couponDAO.getOldCoupons(date);
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

    public void stop(boolean quit) {
        Print.thread("DAILY JOB STOPPED");
        this.quit = quit;
    }

}
