package controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.BankConnection;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class ViewAccountsController implements Initializable
{

    private static final String CLIENT_TABLE = "checking_account";
    private static final String SHOW_ACCOUNT_DETAILS_QUERY = "SELECT * FROM " + "transactions";

    @FXML
    private TableColumn<Transaction, Date> dateColumn;

    @FXML
    private TableColumn<Transaction, String>descriptionColumn;

    @FXML
    private TableColumn<Transaction, String>typeColumn;

    @FXML
    private TableColumn<Transaction, Double> amountColumn;

    @FXML
    private TableColumn<Transaction, Double> balanceColumn;

    @FXML
    private TableColumn<Transaction, Integer> transactionColumn;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField amountField;

    @FXML
    private ChoiceBox typeChoice;

    @FXML
    private TableView<Transaction> tableView;

    private ObservableList<Transaction> transactionList = FXCollections.observableArrayList();

    class Transaction
    {
        private SimpleObjectProperty<Date> date;
        private SimpleStringProperty description;
        private SimpleStringProperty type;
        private SimpleDoubleProperty amount;
        private SimpleDoubleProperty balance;
        private SimpleIntegerProperty transactionID;

        Transaction(Date date, String description, String type, double amount, double balance, int transactionId)
        {
            this.date = new SimpleObjectProperty<>();
            this.description = new SimpleStringProperty(description);
            this.type = new SimpleStringProperty(type);
            this.amount = new SimpleDoubleProperty(amount);
            this.balance = new SimpleDoubleProperty(balance);
            this.transactionID = new SimpleIntegerProperty(transactionId);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        loadTransactions();
    }

    private void loadTransactions()
    {
        BankConnection bankConnection = new BankConnection();
        ResultSet transactions = bankConnection.executeQuery(SHOW_ACCOUNT_DETAILS_QUERY);

        try
        {
            while (transactions.next())
            {
                int transactionId = transactions.getInt("trans_id");
                double amount = transactions.getDouble("amount");
                Date transDate = transactions.getDate("trans_date");
                String transactionType = transactions.getString("trans_type");
                String description = transactions.getString("description");
                double balance = transactions.getDouble("balance");

                Transaction transaction = new Transaction(transDate, description, transactionType, amount, balance, transactionId);

                transactionList.add(transaction);

                tableView.getItems().setAll(transactionList);
            }
        }
        catch(SQLException e) {e.printStackTrace(); }
    }
}
