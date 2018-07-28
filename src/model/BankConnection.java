package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BankConnection
{
    private final static String CONNECTION = "jdbc:mysql://localhost::3306/bank";
    private final static String USERNAME = "administrator";
    private final static String PASSWORD  = "adminPassword";

    public static Connection createConnection()
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(CONNECTION, USERNAME,PASSWORD);
            return connection;
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return null;
    }
}
