
package controller;

import model.BankConnection;
import view.WelcomeScreen;

import java.sql.*;
import javax.swing.*;

public class CreateClientController
{
    private String firstName, lastName, social;
    private int accountNumber;
    private Connection bankConnection;
    private WelcomeScreen view;

    public CreateClientController(String firstName, String lastName, String social)
    {
        System.out.println("CreateClientAccount()");
        this.view = new WelcomeScreen();
        this.bankConnection = BankConnection.createConnection();
        this.accountNumber = Utilities.getBiggestAccountNumber(bankConnection);
        this.accountNumber++; // increment account number for next client

        this.firstName = firstName;
        this.lastName = lastName;
        this.social = social;

        addClientToDatabase(accountNumber, social, bankConnection);
    }

        private void addClientToDatabase(int accountNumber, String social, Connection connection)
        {
           System.out.println("addClientToDatabse()");
           if(clientInfoValid() && (!accountExists(social) ))
           {
            addClientInfo(firstName, lastName, social, connection);
            addCheckingInfo(connection, accountNumber, 0.0, social);
            //new AccessAccountView(accountNumber);
           }
        }

    private boolean clientInfoValid()
    {
        boolean informationCorrect = false;

        social = social.replaceAll("[- ]", "");


        if(firstName.length() == 0)
        {
            JOptionPane.showMessageDialog(null, "First name invalid");
            System.out.println("first name length: " + firstName.length());
        } else if(lastName.length() == 0)
        {
            JOptionPane.showMessageDialog(null, "Last name invalid");
            System.out.println("last name length: " + lastName.length());
        } else if(social.trim().length() != 9)
        {
            JOptionPane.showMessageDialog(null, "Social Security Number invalid");
        } else
        {
            informationCorrect = true;
        }
        return informationCorrect;
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

    private boolean accountExists(String socialFromInput)
    {

        boolean accountExists = false;
        try
        {
            Statement query = bankConnection.createStatement();

            String sqlQuery = "SELECT social FROM clients";

            ResultSet resultSet = query.executeQuery(sqlQuery);

            while(resultSet.next())
            {
                String socialFromDatabase = resultSet.getString(1);

                if(socialFromDatabase.equals(socialFromInput))
                {
                    accountExists = true;
                    System.out.println("Account found for: " + resultSet.getString(1) + " " + resultSet.getString(2));
                    break;
                }
                else
                    accountExists = false;
            }
        }
        catch(SQLException e) { e.printStackTrace(); }
        return  accountExists;
    }
}
