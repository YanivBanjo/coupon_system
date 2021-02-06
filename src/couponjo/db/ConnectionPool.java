package couponjo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPool {
    private static final int NUM_OF_CONS = 2;
    private static ConnectionPool instance = null;
    private static Stack<Connection> connections = new Stack<>();

    private ConnectionPool() throws SQLException {
        for (int i = 0; i < NUM_OF_CONS; i++) {
            Connection connection = DriverManager.getConnection(DBManager.url, DBManager.username,
                    DBManager.password);
            connections.push(connection);
        }

    }

    public static ConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws InterruptedException {
        synchronized (connections) {
            if (connections.isEmpty()) {
                connections.wait();
            }
            return connections.pop();
        }
    }

    public void returnConnection(Connection connection) {
        synchronized (connections) {
            connections.push(connection);
            connections.notify();
        }
    }

    public void closeAllConnection() throws InterruptedException {
        synchronized (connections) {
            while (connections.size() < NUM_OF_CONS) {
                connections.wait();
            }
            connections.removeAllElements();
        }

    }
}
