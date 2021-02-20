package couponjo;

import couponjo.beans.CouponExperationDailyJob;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        testAll();
    }


    public static void testAll() throws SQLException {
        Thread job = new Thread(new CouponExperationDailyJob());
        job.start();

        TestAdminFacade.run();
        TestCompanyFacade.run();
        TestCustomerFacade.run();

        
    }
}
