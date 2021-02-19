package couponjo;

import couponjo.beans.Category;
import couponjo.beans.ClientType;
import couponjo.beans.Coupon;
import couponjo.exceptions.InvalidOperationException;
import couponjo.facade.CompanyFacade;
import couponjo.facade.LoginManager;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class TestLogin {
    public static void main(String[] args) throws SQLException {
        LoginManager loginManager = LoginManager.getInstance();
        System.out.println(loginManager.login("Coca_Cola@gmail.co.il","Z9VcTwbgSk", ClientType.COMPANY));

        CompanyFacade companyFacade = (CompanyFacade) loginManager.login("Coca_Cola@gmail.co.il","Z9VcTwbgSk", ClientType.COMPANY);
        Coupon c1 = new Coupon(9, Category.VACATION,"Super Clasico2","don't miss it",
                Date.valueOf(LocalDate.now().plusDays(2)),Date.valueOf(LocalDate.now().plusDays(2)),7,8.5,"imagin");
        try {
            companyFacade.addCoupon(c1);
            companyFacade.addCoupon(c1);
        } catch (InvalidOperationException e) {
            System.out.println(e.getMessage());
        }

    }
}
