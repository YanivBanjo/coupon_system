package couponjo.dbdao;

import couponjo.beans.Category;
import couponjo.beans.Coupon;
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
            resultSet.next();
            return new Coupon(resultSet.getInt(1),resultSet.getInt(2), Category.values()[resultSet.getInt(3)-1],
                    resultSet.getString(4),resultSet.getString(5), resultSet.getDate(6) , resultSet.getDate(7),
                    resultSet.getInt(8), resultSet.getDouble(9) , resultSet.getString(10));

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
            while(resultSet.next()){
               coupons.add(new Coupon(resultSet.getInt(1),resultSet.getInt(2), Category.values()[resultSet.getInt(3)-1],
                        resultSet.getString(4),resultSet.getString(5), resultSet.getDate(6) , resultSet.getDate(7),
                        resultSet.getInt(8), resultSet.getDouble(9) , resultSet.getString(10)));
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
}
