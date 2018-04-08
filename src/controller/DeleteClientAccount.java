package controller;

import java.sql.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.BankConnection;
import view.WelcomeScreen;

public class DeleteClientAccount implements EventHandler<ActionEvent>
{

    private Connection bankConnection;
    private PreparedStatement preparedStatement;

    public static void main(String[] args)
    {
        new DeleteClientAccount(3);
    }

    public DeleteClientAccount(int account_number)
    {
        bankConnection = BankConnection.createConnection();

        try { removeClient(account_number); }
        catch(SQLException e) {e.printStackTrace();}
    }

    private void removeClient(int account_number) throws SQLException
    {

        removeCheckingDeposit(account_number);

        removeCheckingWithdrawal(account_number);

        removeCheckingAccount(account_number);

        removeClientFromDatabase(account_number);
    }

    private void removeCheckingDeposit(int account_number) throws SQLException
    {
        String removeClientCK_DEPOSIT = "DELETE FROM checking_deposit where account_number = ?";

        preparedStatement = bankConnection.prepareStatement(removeClientCK_DEPOSIT);

        preparedStatement.setInt(1, account_number);

        preparedStatement.execute();
    }

    private void removeCheckingWithdrawal(int account_number) throws SQLException
    {
        String removeClientCK_WITHDRAWAL = "DELETE FROM checking_withdrawl where account_number = ?";

        preparedStatement = bankConnection.prepareStatement(removeClientCK_WITHDRAWAL);

        preparedStatement.setInt(1, account_number);

        preparedStatement.execute();

    }

    private void removeCheckingAccount(int account_number) throws SQLException
    {
        String removeClientCK_ACCT = "DELETE FROM checking_account where account_number = ?";

        preparedStatement = bankConnection.prepareStatement(removeClientCK_ACCT);

        preparedStatement.setInt(1, account_number);

        preparedStatement.execute();
    }

    private void removeClientFromDatabase(int account_number) throws SQLException
    {
        String removeClient = "DELETE FROM clients where account_number = ?";

        preparedStatement = bankConnection.prepareStatement(removeClient);

        preparedStatement.setInt(1, account_number);

        preparedStatement.execute();
    }

    public void displayClients()
    {
        try
        {
            Statement statement = bankConnection.createStatement();

            String clientStatement = "SELECT first_name, last_name, account_number FROM clients";

            ResultSet resultSet = statement.executeQuery(clientStatement);

            clearResults();

            WelcomeScreen.results.appendText("Enter account number to delete \n \n");

            while(resultSet.next())
            {
                WelcomeScreen.results.appendText(resultSet.getString(1) + ", " +
                        resultSet.getString(2) + "-" +
                        "Account #: " +
                        resultSet.getString(3) + "\n");
            }

        }
        catch(SQLException e) { e.printStackTrace(); }
    }

    private void clearResults()
    {
        WelcomeScreen.results.setText("");
    }

    @Override
    public void handle(ActionEvent event)
    {
        System.out.println("handle()");
    }
}
