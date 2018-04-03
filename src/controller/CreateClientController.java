package controller;

import model.BankConnection;
import view.AccessAccountView;
import view.WelcomeScreen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateClientController
{
    private String firstName, lastName, social;
    private WelcomeScreen view;

    private int accountNumber;

    public CreateClientController(String firstName, String lastName, String social)
    {
        Connection bankConnection = BankConnection.createConnection();

        this.accountNumber = getBiggestAccountNumber(bankConnection);
        this.accountNumber++; // increment account number for next client

        this.firstName = firstName;
        this.lastName = lastName;
        this.social = social;

        addClientToDatabase(accountNumber, bankConnection);
    }

        private void addClientToDatabase(int accountNumber, Connection connection)
        {
        addClientInfo(firstName, lastName, social, connection);
        addCheckingInfo(connection, accountNumber, 0.0, social);

        //new AccessAccountView(accountNumber);
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
                preparedStatementClient.setInt(4, accountNumber);

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

        int largestAccountNumber = 0;

        try
        {
            String largestAcctNum = "SELECT MAX(account_number) from clients";

            PreparedStatement preparedStatement = bankConnection.prepareStatement(largestAcctNum);


            ResultSet resultLargestAcctNum = preparedStatement.executeQuery();


            while(resultLargestAcctNum.next())
            {
                largestAccountNumber = resultLargestAcctNum.getInt(1);
                System.out.println("Largest account# found: " + largestAccountNumber);
                return largestAccountNumber;
            }

        }
        catch(SQLException e) { e.printStackTrace(); }

        return largestAccountNumber;
    }
}
