package couponjo.facade;

import couponjo.beans.Company;
import couponjo.beans.Coupon;
import couponjo.dao.CompanyDAO;
import couponjo.dbdao.CompanyDBDAO;
import couponjo.exceptions.InvalidOperationException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyFacade extends ClientFacade{
    private int companyId;

    public CompanyFacade() {
    }

    public CompanyFacade(int companyId) {
        this.companyId = companyId;
    }

    @Override
    boolean login(String email, String password) throws SQLException {
        //companyId = get company id from db
        return companyDAO.isCompanyExist(email, password);
    }

    public void addCoupon(Coupon coupon) throws SQLException, InvalidOperationException {
        List<Coupon> couponList = couponDAO.getAllCoupons();
        for (Coupon c:couponList){
            if(coupon.getCompanyId() == c.getCompanyId() && coupon.getTitle().equals(c.getTitle())){
                throw new InvalidOperationException("Coupon title is already exist and can't added");
            }
        }
        couponDAO.addCoupon(coupon);
    }
}
