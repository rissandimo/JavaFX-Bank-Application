package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.BankConnection;
import model.Transaction;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewAccountsController implements Initializable
{

    private int clientAccountNumber;

    private String social;
    private String firstName;
    private String lastName;

    @FXML
    private GridPane viewAccountsWindow;

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
    private void handleClose()
    {
        ((Stage)viewAccountsWindow.getScene().getWindow()).close();
    }


    @FXML
    private void handleNewTransaction()
    {

        //Set up dialog for new transaction
        Dialog<ButtonType> dialogWindow = new Dialog<>();
        dialogWindow.initOwner(viewAccountsWindow.getScene().getWindow());
        dialogWindow.setTitle("Add new transaction");

        //get access to controller
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../view/addTransaction.fxml"));
        try
        {
            dialogWindow.getDialogPane().setContent(fxmlLoader.load());
        }
        catch (IOException e)
        {
            System.out.println("Unable to load add transaction view");
            e.printStackTrace();
        }

        //Set up dialog buttons
        dialogWindow.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialogWindow.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        //Check which button was chosen
        Optional<ButtonType> choice = dialogWindow.showAndWait();
        if(choice.isPresent() && choice.get() == ButtonType.OK) // -> AddTransactionController -> handleSubmit()
        {
            AddTransactionController addTransactionController = fxmlLoader.getController();
            addTransactionController.setClientInfo(clientAccountNumber, social);

            reloadView();
            loadTransactions(clientAccountNumber, firstName, lastName);
        }
        else
        {
            System.out.println("Cancel pressed");
        }

    }

    @FXML
    private void handleTransactionDelete()
    {
        Transaction transactionToDelete = tableView.getSelectionModel().getSelectedItem();

        //no transaction chosen
        if(transactionToDelete == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No transaction deleted");
            alert.setContentText("Please choose a transaction to delete");
            alert.showAndWait();
            return;
        }

        Alert alertConfirmation = new Alert(Alert.AlertType.WARNING);
        alertConfirmation.setHeaderText("Delete transaction confirmation");
        alertConfirmation.setContentText(
                "Are you sure you want to delete the following transaction: ? \n" +
                transactionToDelete.getDescription());

        Optional<ButtonType> deleteTransaction = alertConfirmation.showAndWait();
        if(deleteTransaction.isPresent() && deleteTransaction.get() == ButtonType.OK)
        {
            boolean deleteSuccessfull = BankConnection.getInstance().deleteTransaction(transactionToDelete);
            if(deleteSuccessfull)
            {
                transactionList.remove(transactionToDelete);
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Deletion cancelled");
            alert.setContentText(transactionToDelete.getDescription() + " -  has not been deleted");
            alert.showAndWait();
        }




    }

    private void reloadView()
    {
        tableView.getItems().clear();
    }

    public void setClientInfo(int clientAccountNumber, String firstName, String lastName)
    {
        this.clientAccountNumber = clientAccountNumber;
        this.firstName = firstName;
        this.lastName = lastName;

        loadTransactions(clientAccountNumber, firstName, lastName);
    }

}
