package couponjo.facade;

import couponjo.dao.CompanyDAO;
import couponjo.dao.CouponDAO;
import couponjo.dao.CustomerDAO;
import couponjo.dbdao.CompanyDBDAO;
import couponjo.dbdao.CouponDBDAO;
import couponjo.dbdao.CustomerDBDAO;

import java.sql.SQLException;

public abstract class ClientFacade {
    protected CompanyDAO companyDAO = new CompanyDBDAO();
    protected CustomerDAO customerDAO = new CustomerDBDAO();
    protected CouponDAO couponDAO = new CouponDBDAO();

    abstract boolean login(String email, String password) throws SQLException;

}
