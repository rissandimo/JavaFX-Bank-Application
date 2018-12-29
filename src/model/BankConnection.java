package model;

import java.sql.*;

public final class BankConnection
{
    //TABLES
    private static final String CHECKING_ACCOUNT_TABLE = "checking_account";
    private static final String CLIENT_TABLE = "clients";
    private static final String TRANSACTIONS_TABLE = "transactions";

    //CONNECTION
    private final static String CONNECTION_URL = "jdbc:mysql://localhost:3306/bank";
    private static Statement statement = null;
    private static Connection connection = null;
    private static BankConnection bankConnection = null;

    //USER LOGIN
    private final static String USERNAME = "root";
    private final static String PASSWORD = "";

    private BankConnection()
    {
        createConnection();
        setUpClientTable();
        setupCheckingAcctTable();
        setupTransactionsTable();
    }

    public static BankConnection getInstance()
    {
        if(bankConnection == null)
        {
            bankConnection = new BankConnection();
        }
        return bankConnection;
    }

    private void createConnection()
    {
        try
        {
            connection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
            System.out.println("Connection successful");
        } catch(SQLException e)
        {
            System.err.println("Unable to create connection");
        }
    }


    private void setupCheckingAcctTable()
    {
        String createClientTableQuery = "CREATE TABLE IF NOT EXISTS " + CHECKING_ACCOUNT_TABLE + " ( " + " account_number INT NOT NULL AUTO_INCREMENT PRIMARY KEY , " + " balance DOUBLE NOT NULL, " + " client_social VARCHAR(9) NOT NULL," + "FOREIGN KEY (client_social) REFERENCES clients (social)" + ")";

        try
        {
            statement = connection.createStatement();
            statement.execute(createClientTableQuery);
        } catch(SQLException e)
        {
            System.err.println("Unable to create checking account table");
            e.printStackTrace();
        }
    }

    private void setUpClientTable()
    {

        String createClientTableQuery = "CREATE TABLE IF NOT EXISTS " + CLIENT_TABLE + " ( " + " first_name VARCHAR(15) NOT NULL, " + " last_name VARCHAR(15) NOT NULL, " + " social VARCHAR(9) NOT NULL PRIMARY KEY )";
        //TODO - check if table exists already
        try
        {
            statement = connection.createStatement();
            statement.execute(createClientTableQuery);
        } catch(SQLException e)
        {
            System.out.println("Unable to create client table");
        }
    }

    private void setupTransactionsTable()
    {
        String createTransactionsTable = "CREATE TABLE IF NOT EXISTS " + TRANSACTIONS_TABLE + " (" + "trans_id INT NULL AUTO_INCREMENT PRIMARY KEY, " + "amount DOUBLE NOT NULL, " + "trans_date DATE NOT NULL, " + "trans_type VARCHAR(30) NOT NULL, " + "description VARCHAR(100) NOT NULL, " + "balance DOUBLE NOT NULL, " + "chk_account_number INT NOT NULL, " + "client_social VARCHAR(9) NOT NULL," + "FOREIGN KEY (chk_account_number) REFERENCES checking_account(account_number)," + "FOREIGN KEY (client_social) REFERENCES clients(social)" + "ON DELETE CASCADE)";

        try
        {
            Statement statement = connection.createStatement();
            statement.execute(createTransactionsTable);
        } catch(SQLException e)
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
        } catch(SQLException e)
        {
            return false;
        }
    }

    /**
     * Executes sql against the database.
     * @param statementToExecute the sql statement.
     */
    public void executeStatement(String statementToExecute)
    {
        try
        {
            Statement statement = connection.createStatement();
            statement.execute(statementToExecute);
        } catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Executes a select on the database
     * @param queryToExecute the select statemetn
     * @return the resultset
     */
    public ResultSet executeQuery(String queryToExecute)
    {
        ResultSet resultSet;
        try
        {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(queryToExecute);
            return resultSet;
        } catch(SQLException e)
        {
            System.out.println("Execute Query() ->Unable to execute query");
            e.printStackTrace();
            return null;
        }
    }

    //queries client info from db
    public ResultSet executeClientQuery(String queryToExecute, int accountNumber, String firstName, String lastName)
    {
        ResultSet resultSet;
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(queryToExecute);
            preparedStatement.setInt(1, accountNumber);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch(SQLException e)
        {
            System.out.println("executeClientQuery() -> Unable to execute query");
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteTransaction(Transaction transactionToDelete)
    {
        String deleteStatement = "DELETE FROM transactions where description = ?";
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = connection.prepareStatement(deleteStatement);
            preparedStatement.setString(1, transactionToDelete.getDescription());
            int rowsDeleted = preparedStatement.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
            return true;
        } catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
