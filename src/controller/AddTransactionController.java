package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import model.BankConnection;

import java.net.URL;
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

    public void setClientInfo(int clientAccountNumber)
    {
        this.clientAccountNumber = clientAccountNumber;
    }

    @FXML
    private void handleSubmit(ActionEvent event)
    {
        System.out.println("client account number" + getClientAccountNumber());
        double amount = Double.parseDouble(amountField.getText());
        String description = descriptionField.getText();
        String transactionType = transactionChoices.getSelectionModel().getSelectedItem();

        String addTransaction = "INSERT INTO " + "transactions" +
                " (amount, trans_date, trans_type, description, balance, chk_account_number)" +
                " values (" + amount +  ", " + "CURDATE()"  + ", '" + transactionType + "', '" +
                description + "', " + 30.00 + ", " + clientAccountNumber + "" + ")";

        bankConnection.executeStatement(addTransaction);

        Stage currentStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        currentStage.close();
    }

}
