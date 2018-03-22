package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.BankConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteClientAccount implements EventHandler<ActionEvent>
{

    private Connection bankConnection;
    private PreparedStatement preparedStatement;
    private int acccount_number;

    public static void main(String[] args)
    {
        new DeleteClientAccount();
    }

    public DeleteClientAccount()
    {
        System.out.println("DeleteClientAccount()");
    }

    public DeleteClientAccount(int account_number)
    {
        this.acccount_number = account_number;
        bankConnection = new BankConnection().createConnection();
    }

    private void removeClient(int account_number) throws SQLException
    {

        removeCheckingDeposit(account_number);

        removeCheckingWithdrawal(account_number);

        removeCheckingAccount(account_number);

        removeClientFromDatabase(account_number);

        System.out.println("Client removed");

    }

    public void removeCheckingAccount(int account_number) throws SQLException
    {
        String removeClientCK_ACCT = "DELETE FROM checking_account where account_number = ?";

        preparedStatement = bankConnection.prepareStatement(removeClientCK_ACCT);

        preparedStatement.setInt(1, account_number);

        preparedStatement.execute();
    }

    public void removeCheckingDeposit(int account_number) throws SQLException
    {
        String removeClientCK_DEPOSIT = "DELETE FROM checking_deposit where account_number = ?";

        preparedStatement = bankConnection.prepareStatement(removeClientCK_DEPOSIT);

        preparedStatement.setInt(1, account_number);

        preparedStatement.execute();
    }

    public void removeCheckingWithdrawal(int account_number) throws SQLException
    {
        String removeClientCK_WITHDRAWAL = "DELETE FROM checking_withdrawl where account_number = ?";

        preparedStatement = bankConnection.prepareStatement(removeClientCK_WITHDRAWAL);

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


    @Override
    public void handle(ActionEvent event)
    {
        System.out.println("handle()");
    }
}
