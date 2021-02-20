package couponjo.dbdao;

import couponjo.beans.Category;
import couponjo.beans.Coupon;
import couponjo.beans.CustomerCouponPurchase;
import couponjo.dao.CouponDAO;
import couponjo.db.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CouponDBDAO implements CouponDAO {
    private static final String ADD_COUPON = "INSERT INTO `couponjo`.`coupons` (`company_id`, `category_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_COUPON = "UPDATE `couponjo`.`coupons` SET `company_id` = ?, `category_id` = ?, `title` = ?, `description` = ?, `start_date` = ?, `end_date` = ?, `amount` = ?, `price` = ?, `image` = ? WHERE (`id` = ?)";
    private static final String DELETE_COUPON = "DELETE FROM `couponjo`.`coupons` WHERE (`id` = ?)";
    private static final String GET_ONE_COUPON = "SELECT * FROM `couponjo`.`coupons` WHERE (`id` = ?)";
    private static final String GET_ALL_COUPONS = "SELECT * FROM `couponjo`.`coupons`";
    private static final String ADD_COUPONS_PURCHASE = "INSERT INTO `couponjo`.`customers_coupons` (`customer_id`, `coupon_id`) VALUES (?, ?)";
    private static final String DELETE_COUPONS_PURCHASE = "DELETE FROM `couponjo`.`customers_coupons` WHERE (`customer_id` = ?) and (`coupon_id` = ?)";
    private static final String GET_COUPONS_PURCHASE_BY_CUSTOMER_ID = "select * FROM `couponjo`.`customers_coupons` WHERE (`customer_id` = ?)";
    private static final String GET_COUPONS_PURCHASE_BY_COUPON_ID = "select * FROM `couponjo`.`customers_coupons` WHERE (`coupon_id` = ?)";
    private static final String GET_COUPONS_BY_COMPANY_ID = "select * FROM `couponjo`.`coupons` WHERE (`company_id` = ?)";
    private static final String GET_COUPONS_BY_CATEGORY_COMPANY_ID = "select * FROM `couponjo`.`coupons` WHERE (`company_id` = ?) and (`category_id` = ?)";
    private static final String GET_COUPONS_PRICE_LOWER_THEN = "select * FROM `couponjo`.`coupons` WHERE (`company_id` = ?) and (`price` < ?)";
    private static final String GET_COUPON_BY_TITLE = "select * FROM `couponjo`.`coupons` WHERE (`title` = ?)";

    private static Connection connection;

    @Override
    public void addCoupon(Coupon coupon) throws SQLException {
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(ADD_COUPON);
            statement.setInt(1, coupon.getCompanyId());
            statement.setInt(2, coupon.getCategory().ordinal());
            statement.setString(3, coupon.getTitle());
            statement.setString(4, coupon.getDescription());
            statement.setDate(5, (java.sql.Date) coupon.getStart_date());
            statement.setDate(6, (java.sql.Date) coupon.getEnd_date());
            statement.setInt(7, coupon.getAmount());
            statement.setDouble(8, coupon.getPrice());
            statement.setString(9, coupon.getImage());
            statement.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    @Override
    public void updateCoupon(Coupon coupon) throws SQLException {
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(UPDATE_COUPON);
            statement.setInt(1, coupon.getCompanyId());
            statement.setInt(2, coupon.getCategory().ordinal());
            statement.setString(3, coupon.getTitle());
            statement.setString(4, coupon.getDescription());
            statement.setDate(5, (java.sql.Date) coupon.getStart_date());
            statement.setDate(6, (java.sql.Date) coupon.getEnd_date());
            statement.setInt(7, coupon.getAmount());
            statement.setDouble(8, coupon.getPrice());
            statement.setString(9, coupon.getImage());
            statement.setInt(10, coupon.getId());
            statement.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    @Override
    public void deleteCoupon(Coupon coupon) throws SQLException {
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(DELETE_COUPON);
            statement.setInt(1, coupon.getId());
            statement.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    @Override
    public Coupon getSingleCoupon(int id) throws SQLException {
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(GET_ONE_COUPON);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Coupon(resultSet.getInt(1), resultSet.getInt(2), Category.values()[resultSet.getInt(3) - 1],
                        resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getDate(7),
                        resultSet.getInt(8), resultSet.getDouble(9), resultSet.getString(10));
            }
            ;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return null;
    }

    @Override
    public Coupon getCouponByTitle(String title) throws SQLException {
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(GET_COUPON_BY_TITLE);
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Coupon(resultSet.getInt(1), resultSet.getInt(2), Category.values()[resultSet.getInt(3) - 1],
                        resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getDate(7),
                        resultSet.getInt(8), resultSet.getDouble(9), resultSet.getString(10));
            }
            ;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return null;
    }

    @Override
    public List<Coupon> getAllCoupons() throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                coupons.add(new Coupon(resultSet.getInt(1), resultSet.getInt(2), Category.values()[resultSet.getInt(3) - 1],
                        resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getDate(7),
                        resultSet.getInt(8), resultSet.getDouble(9), resultSet.getString(10)));
            }
            return coupons;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return null;
    }

    @Override
    public List<Coupon> getAllCouponsByCompanyId(int id) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(GET_COUPONS_BY_COMPANY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                coupons.add(new Coupon(resultSet.getInt(1), resultSet.getInt(2), Category.values()[resultSet.getInt(3) - 1],
                        resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getDate(7),
                        resultSet.getInt(8), resultSet.getDouble(9), resultSet.getString(10)));
            }
            return coupons;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return null;
    }

    @Override
    public List<Coupon> getAllCouponsWithPriceLowerThen(int companyId, int price) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(GET_COUPONS_PRICE_LOWER_THEN);
            statement.setInt(1, companyId);
            statement.setInt(2, price);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                coupons.add(new Coupon(resultSet.getInt(1), resultSet.getInt(2), Category.values()[resultSet.getInt(3) - 1],
                        resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getDate(7),
                        resultSet.getInt(8), resultSet.getDouble(9), resultSet.getString(10)));
            }
            return coupons;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return null;
    }

    @Override
    public List<Coupon> getAllCouponsByCategoryAndCompanyId(int companyId, int categoryId) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(GET_COUPONS_BY_CATEGORY_COMPANY_ID);
            statement.setInt(1, companyId);
            statement.setInt(2, categoryId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                coupons.add(new Coupon(resultSet.getInt(1), resultSet.getInt(2), Category.values()[resultSet.getInt(3) - 1],
                        resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getDate(7),
                        resultSet.getInt(8), resultSet.getDouble(9), resultSet.getString(10)));
            }
            return coupons;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return null;
    }

    @Override
    public void addCouponPurchase(int customerId, int couponId) throws SQLException {
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(ADD_COUPONS_PURCHASE);
            statement.setInt(1, customerId);
            statement.setInt(2, couponId);
            statement.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    @Override
    public void deleteCouponPurchase(int customerId, int couponId) throws SQLException {
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(DELETE_COUPONS_PURCHASE);
            statement.setInt(1, customerId);
            statement.setInt(2, couponId);
            statement.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    @Override
    public List<CustomerCouponPurchase> getAllCouponPurcaseByCustomerId(int customerId) throws SQLException {
        List<CustomerCouponPurchase> purchaseList = new ArrayList<>();
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(GET_COUPONS_PURCHASE_BY_CUSTOMER_ID);
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                purchaseList.add(new CustomerCouponPurchase(resultSet.getInt(1), resultSet.getInt(2)));
            }
            return purchaseList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return null;
    }

    @Override
    public List<CustomerCouponPurchase> getAllCouponPurchaseByCouponId(int couponId) throws SQLException {
        List<CustomerCouponPurchase> purchaseList = new ArrayList<>();
        try {
            // STEP 2 - open connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            // STEP 3 - Run SQL Statement
            PreparedStatement statement = connection.prepareStatement(GET_COUPONS_PURCHASE_BY_COUPON_ID);
            statement.setInt(1, couponId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                purchaseList.add(new CustomerCouponPurchase(resultSet.getInt(1), resultSet.getInt(2)));
            }
            return purchaseList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // STEP 5 - close connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return null;
    }
}
