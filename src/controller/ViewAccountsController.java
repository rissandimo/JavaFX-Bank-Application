package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.BankConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewAccountsController
{

    public static final String CLIENT_TABLE = "checking_account";
    public static final String SHOW_ACCOUNT_DETAILS_QUERY = "SELECT * FROM " + CLIENT_TABLE;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField amountField;

    @FXML
    private ChoiceBox typeChoice;

    @FXML
    private TableView<String> tableView;


}
