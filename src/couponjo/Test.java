package couponjo;

import couponjo.beans.CouponExperationDailyJob;
import couponjo.exceptions.LoginOperationException;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException, InterruptedException, LoginOperationException {
        testAll();
    }


    public static void testAll() throws SQLException, InterruptedException, LoginOperationException {
        CouponExperationDailyJob job = new CouponExperationDailyJob();
        Thread t1 = new Thread(job);
        t1.start();

        TestAdminFacade.run();
        TestCompanyFacade.run();
        TestCustomerFacade.run();

        Thread.sleep(30000);
        job.stop(true);

    }
}
