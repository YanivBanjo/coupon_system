package couponjo.facade;

import couponjo.dao.CompanyDAO;
import couponjo.dbdao.CompanyDBDAO;

import java.sql.SQLException;

public class CompanyFacade extends ClientFacade{
    private CompanyDAO companyDAO = new CompanyDBDAO();

    @Override
    boolean login(String email, String password) throws SQLException {
        return companyDAO.isCompanyExist(email, password);
    }
}
