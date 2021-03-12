package couponjo;

import couponjo.beans.CouponExpirationDailyJob;
import couponjo.exceptions.LoginOperationException;

import java.sql.SQLException;

public class TestAll {
    public static void run() throws SQLException, InterruptedException, LoginOperationException {
        CouponExpirationDailyJob job = new CouponExpirationDailyJob();

        new Thread(job).start();
        TestAdminFacade.run();
        TestCompanyFacade.run();
        TestCustomerFacade.run();

        Thread.sleep(30000);
        job.stop(true);

    }
}
