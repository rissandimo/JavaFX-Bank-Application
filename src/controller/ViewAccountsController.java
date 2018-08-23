package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.BankConnection;
import model.Transaction;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class ViewAccountsController implements Initializable
{

    private int clientAccountNumber;

    private String social;

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
    private TableView<Transaction> tableView;

    private ObservableList<Transaction> transactionList = FXCollections.observableArrayList();

    private BankConnection bankConnection;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        bankConnection = BankConnection.getInstance();
        initColumns();
    }

    private void initColumns()
    {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
    }

    public void loadTransactions(int clientAccountNumber, String firstName, String lastName)
    {
        // social is needed to join client table -> needed to retrieve first and last name for login
        String SHOW_ACCOUNT_DETAILS_QUERY =  "SELECT amount, trans_date, trans_type," +
                " description, balance, client_social FROM " + "transactions" +
                " join clients" +
                " on transactions.client_social = clients.social" +
                " where chk_account_number = ? AND" +
                " first_name = ? AND" +
                " last_name = ?";
        ResultSet transactions = bankConnection.executeClientQuery(SHOW_ACCOUNT_DETAILS_QUERY, clientAccountNumber, firstName, lastName);

        try
        {
            while (transactions.next())
            {
                double amount = transactions.getDouble("amount");
                Date transDate = transactions.getDate("trans_date");
                String transactionType = transactions.getString("trans_type");
                String description = transactions.getString("description");
                double balance = transactions.getDouble("balance");
                social = transactions.getString("client_social");

                Transaction transaction = new Transaction(amount, transDate, transactionType, description, balance, social);

                transactionList.add(transaction);
                System.out.println("transaction added");

                //TODO - reload table view after adding new transaction
            }


        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        tableView.setItems(transactionList);
    }


    @FXML
    private void handleNewTransaction()
    {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("../view/addTransaction.fxml").openStream());
            AddTransactionController addTransactionController = fxmlLoader.getController();
            addTransactionController.setClientInfo(clientAccountNumber, social);

            Stage stage = new Stage();
            stage.setTitle("Add New Transaction");
            stage.setScene(new Scene(root));
            stage.show();

            tableView.getItems().setAll(transactionList); //update transaction list

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void setClientInfo(int clientAccountNumber, String firstName, String lastName)
    {
        this.clientAccountNumber = clientAccountNumber;
        loadTransactions(clientAccountNumber, firstName, lastName);
    }

}
