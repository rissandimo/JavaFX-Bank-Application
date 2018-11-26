package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import model.BankConnection;
import util.BankConnectionValidator;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddTransactionController implements Initializable
{

    @FXML
    private JFXTextField amountField;

    @FXML
    private JFXTextField descriptionField;

    @FXML
    private ChoiceBox<String> transactionChoices = new ChoiceBox<>();

    private ObservableList<String> transactionTypes = FXCollections.observableArrayList("Transaction", "Deposit", "Withdrawal");

    private BankConnection bankConnection;

    private int clientAccountNumber;
    private String social;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        bankConnection = BankConnection.getInstance();
        transactionChoices.getItems().setAll(transactionTypes);
    }

    public void setClientInfo(int clientAccountNumber, String social)
    {
        this.clientAccountNumber = clientAccountNumber;
        this.social = social;
        handleSubmit();
    }

    @FXML
    private void handleSubmit()
    {
        double amount = Double.parseDouble(amountField.getText());
        String description = descriptionField.getText();
        String transactionType = transactionChoices.getSelectionModel().getSelectedItem();

        double accountBalance = getAccountBalance(clientAccountNumber);

        switch(transactionType)
        {
            case "Transaction":
                performTransaction(amount, description, accountBalance, "transaction");
                break;
            case "Deposit":
                performTransaction(amount, description, accountBalance, "credit");
                break;
            case "Withdrawal":
                performTransaction(amount, description, accountBalance, "debit");
                break;
        }

    }

    private double getAccountBalance(int clientAccountNumber)
    {
        String getAccountBalance = "SELECT balance from checking_account where account_number = " + clientAccountNumber;

        ResultSet account = bankConnection.executeQuery(getAccountBalance);
        double accountBalance = 0;

        try
        {
            while(account.next())
            {
                accountBalance = account.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountBalance;
    }

    // use database transactions -> all transactions succeed or all fail
    // create accounts table
    private void performTransaction(double amount, String description, double accountBalance, String type)
    {
        String addTransactionQuery = null;
        String updateCheckingQuery = null;

        if(type.equals("debit") || type.equals("transaction"))
        {
            if(BankConnectionValidator.withdrawalAmountValid(accountBalance, amount))
            {
                 addTransactionQuery = "INSERT INTO " + "transactions" +
                        " (amount, trans_date, trans_type, description, balance, chk_account_number, client_social)" +
                        " values (" + amount + ", " + "CURDATE()" + ", '" + type + "', '" +
                        description + "', " + (accountBalance - amount) + ", " + clientAccountNumber + ", " + social +  ")";

                updateCheckingQuery = "UPDATE checking_account set balance = " + (accountBalance - amount) + " where account_number = " + clientAccountNumber;
            }
        }
        else
        {
            addTransactionQuery = "INSERT INTO " + "transactions" +
                    " (amount, trans_date, trans_type, description, balance, chk_account_number, client_social)" +
                    " values (" + amount + ", " + "CURDATE()" + ", '" + type + "', '" +
                    description + "', " + (accountBalance + amount) + ", " + clientAccountNumber + ", " + social +  ")";

            updateCheckingQuery = "UPDATE checking_account set balance = " + (accountBalance + amount) + " where account_number = " + clientAccountNumber;
        }

            // both succeed or all fail - db performTransaction
            // start a performTransaction and commit at end if everything goes ok
            // if not all money goes back to original state
            bankConnection.executeStatement(addTransactionQuery);
            bankConnection.executeStatement(updateCheckingQuery);
        }

        }
