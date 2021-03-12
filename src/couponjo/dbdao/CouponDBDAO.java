package couponjo.dbdao;

import couponjo.beans.Category;
import couponjo.beans.Coupon;
import couponjo.beans.CustomerCouponPurchase;
import couponjo.dao.CouponDAO;
import couponjo.utils.DBUtils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponDBDAO implements CouponDAO {
    private static final String ADD_COUPON = "INSERT INTO `couponjo`.`coupons` (`company_id`, `category_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_COUPON = "UPDATE `couponjo`.`coupons` SET `company_id` = ?, `category_id` = ?, `title` = ?, `description` = ?, `start_date` = ?, `end_date` = ?, `amount` = ?, `price` = ?, `image` = ? WHERE (`id` = ?)";
    private static final String DELETE_COUPON = "DELETE FROM `couponjo`.`coupons` WHERE (`id` = ?)";
    private static final String GET_ONE_COUPON = "SELECT * FROM `couponjo`.`coupons` WHERE (`id` = ?)";
    private static final String GET_ALL_COUPONS = "SELECT * FROM `couponjo`.`coupons`";
    private static final String GET_OLD_COUPONS_BY_DATE = "SELECT * FROM `couponjo`.`coupons` WHERE (`end_date` < ?)";
    private static final String ADD_COUPONS_PURCHASE = "INSERT INTO `couponjo`.`customers_coupons` (`customer_id`, `coupon_id`) VALUES (?, ?)";
    private static final String DELETE_COUPONS_PURCHASE = "DELETE FROM `couponjo`.`customers_coupons` WHERE (`customer_id` = ?) and (`coupon_id` = ?)";
    private static final String GET_COUPONS_PURCHASE_BY_CUSTOMER_ID = "select * FROM `couponjo`.`customers_coupons` WHERE (`customer_id` = ?)";
    private static final String GET_COUPONS_PURCHASE_BY_COUPON_ID = "select * FROM `couponjo`.`customers_coupons` WHERE (`coupon_id` = ?)";
    private static final String GET_COUPONS_BY_COMPANY_ID = "select * FROM `couponjo`.`coupons` WHERE (`company_id` = ?)";
    private static final String GET_COUPONS_BY_CATEGORY_COMPANY_ID = "select * FROM `couponjo`.`coupons` WHERE (`company_id` = ?) and (`category_id` = ?)";
    private static final String GET_COUPONS_PRICE_LOWER_THEN = "select * FROM `couponjo`.`coupons` WHERE (`company_id` = ?) and (`price` < ?)";
    private static final String GET_COUPON_BY_TITLE = "select * FROM `couponjo`.`coupons` WHERE (`title` = ?)";

    @Override
    public void addCoupon(Coupon coupon) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, coupon.getCompanyId());
        map.put(2, coupon.getCategory().ordinal() + 1);
        map.put(3, coupon.getTitle());
        map.put(4, coupon.getDescription());
        map.put(5, coupon.getStart_date());
        map.put(6, coupon.getEnd_date());
        map.put(7, coupon.getAmount());
        map.put(8, coupon.getPrice());
        map.put(9, coupon.getImage());

        DBUtils.runQuery(ADD_COUPON, map);
    }

    @Override
    public void updateCoupon(Coupon coupon) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, coupon.getCompanyId());
        map.put(2, coupon.getCategory().ordinal() + 1);
        map.put(3, coupon.getTitle());
        map.put(4, coupon.getDescription());
        map.put(5, coupon.getStart_date());
        map.put(6, coupon.getEnd_date());
        map.put(7, coupon.getAmount());
        map.put(8, coupon.getPrice());
        map.put(9, coupon.getImage());
        map.put(10, coupon.getId());

        DBUtils.runQuery(UPDATE_COUPON, map);
    }

    @Override
    public void deleteCoupon(Coupon coupon) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, coupon.getId());

        DBUtils.runQuery(DELETE_COUPON, map);
    }

    @Override
    public Coupon getSingleCoupon(int id) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);

        ResultSet resultSet = DBUtils.runQueryWithResult(GET_ONE_COUPON, map);
        if (resultSet.next()) {
            return new Coupon(resultSet.getInt(1), resultSet.getInt(2), Category.values()[resultSet.getInt(3) - 1],
                    resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getDate(7),
                    resultSet.getInt(8), resultSet.getDouble(9), resultSet.getString(10));
        }
        return null;
    }

    @Override
    public Coupon getCouponByTitle(String title) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, title);

        ResultSet resultSet = DBUtils.runQueryWithResult(GET_COUPON_BY_TITLE, map);
        if (resultSet.next()) {
            return new Coupon(resultSet.getInt(1), resultSet.getInt(2), Category.values()[resultSet.getInt(3) - 1],
                    resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getDate(7),
                    resultSet.getInt(8), resultSet.getDouble(9), resultSet.getString(10));
        }
        return null;
    }

    @Override
    public List<Coupon> getAllCoupons() throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        List<Coupon> coupons = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_ALL_COUPONS, map);

        while (resultSet.next()) {
            coupons.add(new Coupon(resultSet.getInt(1), resultSet.getInt(2), Category.values()[resultSet.getInt(3) - 1],
                    resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getDate(7),
                    resultSet.getInt(8), resultSet.getDouble(9), resultSet.getString(10)));
        }
        return coupons;
    }

    @Override
    public List<Coupon> getOldCoupons(Date date) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, date);

        List<Coupon> coupons = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_OLD_COUPONS_BY_DATE, map);

        while (resultSet.next()) {
            coupons.add(new Coupon(resultSet.getInt(1), resultSet.getInt(2), Category.values()[resultSet.getInt(3) - 1],
                    resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getDate(7),
                    resultSet.getInt(8), resultSet.getDouble(9), resultSet.getString(10)));
        }
        return coupons;
    }

    @Override
    public List<Coupon> getAllCouponsByCompanyId(int id) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);
        List<Coupon> coupons = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_COUPONS_BY_COMPANY_ID, map);

        while (resultSet.next()) {
            coupons.add(new Coupon(resultSet.getInt(1), resultSet.getInt(2), Category.values()[resultSet.getInt(3) - 1],
                    resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getDate(7),
                    resultSet.getInt(8), resultSet.getDouble(9), resultSet.getString(10)));
        }
        return coupons;
    }

    @Override
    public List<Coupon> getAllCouponsWithPriceLowerThen(int companyId, int price) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyId);
        map.put(2, price);
        List<Coupon> coupons = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_COUPONS_PRICE_LOWER_THEN, map);

        while (resultSet.next()) {
            coupons.add(new Coupon(resultSet.getInt(1), resultSet.getInt(2), Category.values()[resultSet.getInt(3) - 1],
                    resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getDate(7),
                    resultSet.getInt(8), resultSet.getDouble(9), resultSet.getString(10)));
        }
        return coupons;
    }

    @Override
    public List<Coupon> getAllCouponsByCategoryAndCompanyId(int companyId, int categoryId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyId);
        map.put(2, categoryId+1);
        List<Coupon> coupons = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_COUPONS_BY_CATEGORY_COMPANY_ID, map);

        while (resultSet.next()) {
            coupons.add(new Coupon(resultSet.getInt(1), resultSet.getInt(2), Category.values()[resultSet.getInt(3) - 1],
                    resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getDate(7),
                    resultSet.getInt(8), resultSet.getDouble(9), resultSet.getString(10)));
        }
        return coupons;
    }

    @Override
    public void addCouponPurchase(int customerId, int couponId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        map.put(2, couponId);

        DBUtils.runQuery(ADD_COUPONS_PURCHASE, map);
    }

    @Override
    public void deleteCouponPurchase(int customerId, int couponId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        map.put(2, couponId);

        DBUtils.runQuery(DELETE_COUPONS_PURCHASE, map);
    }

    @Override
    public List<CustomerCouponPurchase> getAllCouponPurchaseByCustomerId(int customerId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        List<CustomerCouponPurchase> purchaseList = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_COUPONS_PURCHASE_BY_CUSTOMER_ID, map);

        while (resultSet.next()) {
            purchaseList.add(new CustomerCouponPurchase(resultSet.getInt(1), resultSet.getInt(2)));
        }
        return purchaseList;
    }

    @Override
    public List<CustomerCouponPurchase> getAllCouponPurchaseByCouponId(int couponId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, couponId);
        List<CustomerCouponPurchase> purchaseList = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_COUPONS_PURCHASE_BY_COUPON_ID, map);

        while (resultSet.next()) {
            purchaseList.add(new CustomerCouponPurchase(resultSet.getInt(1), resultSet.getInt(2)));
        }
        return purchaseList;

    }
}
