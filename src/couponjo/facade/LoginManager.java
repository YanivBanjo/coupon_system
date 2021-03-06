package couponjo.facade;

import couponjo.beans.ClientType;
import couponjo.exceptions.LoginOperationException;

import java.sql.SQLException;

public class LoginManager {
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

    public ClientFacade login(String email, String password, ClientType clientType) throws SQLException, LoginOperationException {
        ClientFacade cf;
        switch (clientType) {
            case ADMINISTRATOR:
                cf = new AdminFacade();
                if (cf.login(email, password)) {
                    return cf;
                }
                throw new LoginOperationException("Email or Password are incorrect");
            case COMPANY:
                cf = new CompanyFacade();
                if (cf.login(email, password)) {
                    return cf;
                }
                throw new LoginOperationException("Email or Password are incorrect");
            case CUSTOMER:
                cf = new CustomerFacade();
                if (cf.login(email, password)) {
                    return cf;
                }
                throw new LoginOperationException("Email or Password are incorrect");
        }
        throw new LoginOperationException("Incorrect client type");
    }
}
