
package controller;

import model.BankConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateClientController
{
    private String firstName, lastName, social;
    private int accountNumber;

    public CreateClientController(String firstName, String lastName, String social)
    {
        Connection bankConnection = BankConnection.createConnection();

        this.accountNumber = Utilities.getBiggestAccountNumber(bankConnection);
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
            }
        }
        catch (SQLException e) { e.printStackTrace(); }}

    private void addCheckingInfo(Connection bankConnection, int ACCOUNT_NUMBER, double balance, String social) {
        try {
            String checkingStatement = "INSERT INTO checking_account (account_number, account_balance, social) values(?,?,?)";

            PreparedStatement preparedStatement = bankConnection.prepareStatement(checkingStatement);

            preparedStatement.setInt(1, ACCOUNT_NUMBER);
            preparedStatement.setDouble(2, 0.0);
            preparedStatement.setString(3, social);

            preparedStatement.execute();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
