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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import model.BankConnection;

import java.io.IOException;
import java.net.URL;
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


    private ObservableList<String> transactionTypes = FXCollections.observableArrayList("Debt", "Deposit", "Withdrawal");

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
        // text boxes
        System.out.println("client account number" + getClientAccountNumber());
        double amount = Double.parseDouble(amountField.getText());
        String description = descriptionField.getText();
        String transactionType = transactionChoices.getSelectionModel().getSelectedItem();
        //LocalDate date = datePicker.getValue();


        //Adding social for load transactions
        String addTransaction = "INSERT INTO " + "transactions" +
                " (amount, trans_date, trans_type, description, balance, chk_account_number, client_social)" +
                " values (" + amount + ", " + "CURDATE()" + ", '" + transactionType + "', '" +
                description + "', " + 30.00 + ", " + clientAccountNumber + ", " + social +  ")";

        bankConnection.executeStatement(addTransaction);

    }


}
