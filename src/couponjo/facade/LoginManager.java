package couponjo.facade;

import couponjo.beans.ClientType;

import java.sql.SQLException;

public class LoginManager{
    private static LoginManager instance = null;

    private LoginManager() {
    }

    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    public ClientFacade login(String email, String password, ClientType clientType) throws SQLException {
        ClientFacade cf;
        switch (clientType) {
            case ADMINISTRATOR:
                cf = new AdminFacade();
                return (cf.login(email,password) == true) ? cf : null;
            case COMPANY:
                cf = new CompanyFacade();
                return (cf.login(email,password) == true) ? cf : null;
            case CUSTOMER:
                cf = new CustomerFacade();
                return (cf.login(email,password) == true) ? cf : null;
        }
        return null;
    }
}
