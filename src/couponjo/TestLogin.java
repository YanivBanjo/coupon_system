package couponjo;

import couponjo.beans.ClientType;
import couponjo.facade.LoginManager;

import java.sql.SQLException;

public class TestLogin {
    public static void main(String[] args) throws SQLException {
        LoginManager loginManager = LoginManager.getInstance();
        System.out.println(loginManager.login("Coca_Cola@gmail.co.il","Z9VcTwbgSk", ClientType.COMPANY));
    }
}
