package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BankConnection
{
    public BankConnection()
    {
        System.out.println("BankConnection()");
    }

    public static Connection createConnection()
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root","");
        }
        catch(SQLException sqlException) {sqlException.printStackTrace();}
        return connection;
    }
}
