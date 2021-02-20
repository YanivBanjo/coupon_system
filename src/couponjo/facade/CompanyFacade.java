package couponjo.facade;

import couponjo.beans.Category;
import couponjo.beans.Company;
import couponjo.beans.Coupon;
import couponjo.beans.CustomerCouponPurchase;
import couponjo.exceptions.InvalidOperationException;

import java.sql.SQLException;
import java.util.List;

public class CompanyFacade extends ClientFacade {
    private int companyId;

    public CompanyFacade() {
    }

    @Override
    boolean login(String email, String password) throws SQLException {
        Company company = companyDAO.getCompanyByEmail(email);
        this.companyId = company.getId();
        return companyDAO.isCompanyExist(email, password);
    }

    public void addCoupon(Coupon coupon) throws SQLException, InvalidOperationException {
        List<Coupon> couponList = getAllCouponsByCompanyId();
        for (Coupon c : couponList) {
            if (coupon.getTitle().equals(c.getTitle())) {
                throw new InvalidOperationException("Coupon title is already exist and can't be added");
            }
        }
        couponDAO.addCoupon(coupon);
    }

    public void updateCoupon(Coupon coupon) throws SQLException, InvalidOperationException {
        Coupon getCoupon = getCouponByTitle(coupon.getTitle());
        if (getCoupon != null) {
            coupon.setId(getCoupon.getId());
            if (coupon.getCompanyId() != getCoupon.getCompanyId()) {
                throw new InvalidOperationException("Coupon CompanyId can't be changed");
            }
        } else {
            throw new InvalidOperationException("Coupon doesn't exist and can't be update");
        }
        coupon.setCategory(Category.ELECTRICITY);////need to fix
        couponDAO.updateCoupon(coupon);
    }

    public void deleteCoupon(Coupon coupon) throws SQLException, InvalidOperationException {
        Coupon todelete = getCouponByTitle(coupon.getTitle());
        if (todelete != null) {
            deletePurchases(getCouponPurchaseByCouponId(todelete.getId()), todelete.getTitle());
            couponDAO.deleteCoupon(todelete);
        } else {
            throw new InvalidOperationException("Coupon doesn't exist and can't be deleted");
        }

    }

    public Coupon getSingleCoupon(int id) throws SQLException {
        return couponDAO.getSingleCoupon(id);
    }

    private Coupon getCouponByTitle(String title) throws SQLException {
        return couponDAO.getCouponByTitle(title);
    }

    private List<Coupon> getAllCoupons() throws SQLException {
        return couponDAO.getAllCoupons();
    }

    public List<Coupon> getAllCouponsByCompanyId() throws SQLException {
        return couponDAO.getAllCouponsByCompanyId(companyId);
    }

    public List<Coupon> getAllCouponsByCategoryAndCompanyId(int categoryId) throws SQLException {
        return couponDAO.getAllCouponsByCategoryAndCompanyId(companyId, categoryId);
    }

    public List<Coupon> getAllCouponsWithPriceLowerThen(int price) throws SQLException {
        return couponDAO.getAllCouponsWithPriceLowerThen(companyId, price);
    }

    private List<CustomerCouponPurchase> getCouponPurchaseByCouponId(int id) throws SQLException {
        return couponDAO.getAllCouponPurchaseByCouponId(id);
    }

    private void deletePurchases(List<CustomerCouponPurchase> purchaseList, String name) {
        if (purchaseList.size() > 0) {
            System.out.println("Going to delete customer purchases for coupon " + name);
            purchaseList.forEach(purchase -> {
                try {
                    couponDAO.deleteCouponPurchase(purchase.getCustomerId(), purchase.getCouponId());
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            });
        } else {
            System.out.println("For Coupon named " + name + " ,No purchases found to delete");
        }
    }
}
