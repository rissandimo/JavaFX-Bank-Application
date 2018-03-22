package controller;

import model.BankConnection;
import view.WelcomeScreen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateClientAccount
{

    private String firstName, lastName, social;
    WelcomeScreen view;

    private Connection bankConnection;
    private int accountnumber;

/*    public static void main(String[] args)
    {
        new CreateClientAccount("John", "Smith", "123456789");
    }*/

    public CreateClientAccount(String firstName, String lastName, String social)
    {

        this.bankConnection = new BankConnection().createConnection();

        this.view = view;
        this.accountnumber = getBiggestAccountNumber(bankConnection);
        this.accountnumber++; // increment account number for next client

        System.out.println("connection established");

        this.firstName = firstName;
        this.lastName = lastName;
        this.social = social;

        addClientToDatabase(accountnumber, bankConnection);

        // add info to other tables

        //show the main screen

    }

        private void addClientToDatabase(int accountNumber, Connection connection)
        {
        addClientInfo(firstName, lastName, social, connection);
        addCheckingInfo(connection, accountNumber, 0.0, social);

        }

    private void addClientInfo(String firstName, String lastName, String social, Connection bankConnection)
    {
        try
        {

            String createClientStatement = "INSERT INTO clients (first_name, last_name, social, account_number) values (?, ?, ?, ?)";

            try (PreparedStatement preparedStatementClient = bankConnection.prepareStatement(createClientStatement))
            {
                preparedStatementClient.setString(1, firstName);
                preparedStatementClient.setString(2, lastName);
                preparedStatementClient.setString(3, social);
                preparedStatementClient.setInt(4, accountnumber);

                preparedStatementClient.execute();
                System.out.println("client info added");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void addCheckingInfo(Connection bankConnection, int ACCOUNT_NUMBER, double balance, String social)
    {

        try
        {
            String checkingStatement = "INSERT INTO checking_account (account_number, account_balance, social) values(?,?,?)";

            PreparedStatement preparedStatement = bankConnection.prepareStatement(checkingStatement);

            preparedStatement.setInt(1, ACCOUNT_NUMBER);
            preparedStatement.setDouble(2,balance);
            preparedStatement.setString(3, social);

            preparedStatement.execute();
            System.out.println("checking info added");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    private int getBiggestAccountNumber(Connection bankConnection)
    {
        System.out.println("getBiggestAccountNumber()");

        int largestAccountNumber = 0;

        try
        {
            String largestAcctNum = "SELECT MAX(account_number) from clients";

            PreparedStatement preparedStatement = bankConnection.prepareStatement(largestAcctNum);


            ResultSet resultlargestAcctNum = preparedStatement.executeQuery();


            while(resultlargestAcctNum.next())
            {
                largestAccountNumber = resultlargestAcctNum.getInt(1);
                System.out.println("Largest account# found: " + largestAccountNumber);
                return largestAccountNumber;
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return largestAccountNumber;
    }

}
