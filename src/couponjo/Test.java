package couponjo;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        testAll();
    }


    public static void testAll() throws SQLException {
        TestAdminFacade.run();
        TestCompanyFacade.run();
        TestCustomerFacade.run();
    }
}
