package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import model.BankConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddTransactionController implements Initializable
{

    @FXML
    private JFXTextField amountField;

    @FXML
    private JFXTextField descriptionField;

    @FXML
    private ChoiceBox<String> transactionChoices = new ChoiceBox<>();

    @FXML
    private DatePicker datePicker;


    private ObservableList<String> transactionTypes = FXCollections.observableArrayList("Transaction", "Deposit", "Withdrawal");

    private BankConnection bankConnection;

    private int clientAccountNumber;
    private String social;

    private int getClientAccountNumber()
    {
        return clientAccountNumber;
    }

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
                transaction(amount, description, accountBalance);
                break;
            case "Deposit":
                deposit(accountBalance, amount);
                break;
            case "Withdrawal":
                withdrawal(accountBalance, amount);
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

    private void deposit(double accountBalance, double amount)
    {
        accountBalance += amount;
        String updateChecking = "UPDATE checking_account set balance = " + accountBalance + " where account_number = " + clientAccountNumber;

        bankConnection.executeStatement(updateChecking);
    }

    private void withdrawal(double accountBalance, double amount)
    {
        if( (accountBalance - amount) < 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error - unsufficient funds");
            alert.setContentText("You do not have sufficient funds!");
            alert.showAndWait();
        }
        else if(amount > 500)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error - Limit exceeded");
            alert.setContentText("Please choose an amount not greater than $500");
            alert.showAndWait();
        }
        else
        {
            accountBalance -= amount;
            String updateChecking = "UPDATE checking_account set balance = " + accountBalance + " where account_number = " + clientAccountNumber;
            bankConnection.executeStatement(updateChecking);
        }

    }

    private void transaction(double amount, String description, double accountBalance)
    {
        accountBalance += amount;
        String addTransaction = "INSERT INTO " + "transactions" +
                " (amount, trans_date, trans_type, description, balance, chk_account_number, client_social)" +
                " values (" + amount + ", " + "CURDATE()" + ", '" + "transaction" + "', '" +
                description + "', " + accountBalance + ", " + clientAccountNumber + ", " + social +  ")";

        String updateChecking = "UPDATE checking_account set balance = " + accountBalance + " where account_number = " + clientAccountNumber;


        bankConnection.executeStatement(addTransaction);
        bankConnection.executeStatement(updateChecking);
    }

}
