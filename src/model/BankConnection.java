package model;

import javax.swing.*;
import java.sql.*;

public class BankConnection
{
    private final static String CONNECTION_URL = "jdbc:mysql://localhost:3306/bank";
    private static Statement statement = null;
    private Connection connection = null;

    private final static String USERNAME = "root";
    private final static String PASSWORD  = "";

    public BankConnection()
    {
        createConnection();
        setupClientTable();
    }

    public void createConnection()
    {
        try
        {
            connection = DriverManager.getConnection(CONNECTION_URL, USERNAME,PASSWORD);
            System.out.println("Connection successfull");
        }
        catch(SQLException e)
        {
            System.out.println("Unable to create connection");
            e.printStackTrace();
        }
    }

    void createDatabase()
    {
        try
        {
            String createDatabaseStatement = "CREATE DATABASE IF NOT EXISTS bank";
            Statement statement = connection.createStatement();
            statement.execute(createDatabaseStatement);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    void setupClientTable()
    {
        String tableName = "clients";
        String createClientTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName + " ( " +
           " first_name VARCHAR(15) NOT NULL, " +
           " last_name VARCHAR(15) NOT NULL, " +
           " social VARCHAR(9) NOT NULL	PRIMARY KEY )";
        //TODO - check if table exists already
        try
        {
        statement = connection.createStatement();
        statement.execute(createClientTableQuery);
        }
        catch(SQLException e)
        {
            System.out.println("Unable to create client table");
        }
    }

    public boolean executeCreateClientStatement(String statement)
    {
        try
        {
            Statement statementToExecute = connection.createStatement();
            statementToExecute.execute(statement);
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    ResultSet executeQuery(String queryToExecute)
    {
        ResultSet resultSet;
        try
        {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(queryToExecute);
            return resultSet;
        }
        catch(SQLException e)
        {
            System.out.println("Unable to execute query");
            e.printStackTrace();
            return null;
        }
    }

}
