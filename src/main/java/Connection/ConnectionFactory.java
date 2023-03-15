package Connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/***
 * The ConnectionFactory class has the role of ensuring the connection with the Orders Management relational database
 */
public class ConnectionFactory {
    /***
     * the logger
     */
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    /***
     * the driver
     */
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    /***
     * the port
     */
    private static final String DBURL = "jdbc:mysql://localhost:3306/ordersmanagement";
    /***
     * user
     */
    private static final String USER = "root";
    /***
     * password
     */
    private static final String PASS = "root";
    /***
     * connection
     */
    private static ConnectionFactory singleInstance = new ConnectionFactory();

    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /***
     * creates the database connection
     * @return object of type Connection
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database");
            e.printStackTrace();
        }
        return connection;
    }

    /***
     *
     * @return object of type Connection
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /***
     * ose the connection given as parameter
     * @param connection the connection to be closed
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
            }
        }
    }

    /***
     * closes the Statement type object given as a parameter
     * @param statement the Statement type object that is to be closed
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
            }
        }
    }

    /***
     * closes the ResultSet object given as a parameter
     * @param resultSet result set to be closed
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
            }
        }
    }
}
