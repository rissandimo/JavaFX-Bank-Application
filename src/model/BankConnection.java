package model;

import java.sql.*;

public class BankConnection
{
    //DB - TABLES
    private static final String CHECKING_ACCOUNT_TABLE = "checking_account";
    private static final String CLIENT_TABLE = "clients";
    private static final String TRANSACTIONS_TABLE = "transactions";

    private final static String CONNECTION_URL = "jdbc:mysql://localhost:3306/bank";
    private static Statement statement = null;
    private Connection connection = null;

    private final static String USERNAME = "root";
    private final static String PASSWORD  = "";

    public BankConnection()
    {
        createConnection();
        setUpClientTable();
        setupCheckingAcctTable();
        setupTransactionsTable();
    }

    private void createConnection()
    {
        try
        {
            connection = DriverManager.getConnection(CONNECTION_URL, USERNAME,PASSWORD);
            System.out.println("Connection successful");
        }
        catch(SQLException e)
        {
            System.out.println("Unable to create connection");
            e.printStackTrace();
        }
    }


    private void setupCheckingAcctTable()
    {
        String createClientTableQuery = "CREATE TABLE IF NOT EXISTS " + CHECKING_ACCOUNT_TABLE + " ( " +
           " account_number INT NOT NULL AUTO_INCREMENT PRIMARY KEY , " +
           " balance DOUBLE NOT NULL, " +
           " client_social VARCHAR(9) NOT NULL," +
            "FOREIGN KEY (client_social) REFERENCES clients (social)" +
                ")";
        //TODO - check if table exists already
        try
        {
        statement = connection.createStatement();
        statement.execute(createClientTableQuery);
        }
        catch(SQLException e)
        {
            System.out.println("Unable to create checking account table");
            e.printStackTrace();
        }
    }

    private void setUpClientTable()
    {

        String createClientTableQuery = "CREATE TABLE IF NOT EXISTS " + CLIENT_TABLE + " ( " +
                " first_name VARCHAR(15) NOT NULL, " +
                " last_name VARCHAR(15) NOT NULL, " +
                " social VARCHAR(9) NOT NULL PRIMARY KEY )";
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

    private void setupTransactionsTable()
    {
        String createTransactionsTable = "CREATE TABLE IF NOT EXISTS " + TRANSACTIONS_TABLE + " (" +
                "trans_id INT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "amount DOUBLE NOT NULL, " +
                "trans_date DATE NOT NULL, " +
                "trans_type VARCHAR(30) NOT NULL, " +
                "description VARCHAR(100) NOT NULL, " +
                "chk_account_number INT, " +
                "FOREIGN KEY (chk_account_number) REFERENCES checking_account(account_number)" +
                "ON DELETE CASCADE)";

        try
        {
        Statement statement = connection.createStatement();
        statement.execute(createTransactionsTable);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
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

    public void executeStatement(String statementToExecute)
    {
        try
        {
        Statement statement = connection.createStatement();
        statement.execute(statementToExecute);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String queryToExecute)
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
