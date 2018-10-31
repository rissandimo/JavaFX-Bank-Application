package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.BankConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterClientController implements Initializable
{

    @FXML
    private JFXTextField firstNameField;

    @FXML
    private JFXTextField lastNameField;

    @FXML
    private JFXTextField socialSecurityField;

    private BankConnection bankConnection;

    private static final String CLIENT_FIRST_NAME = "first_name";
    private static final String CLIENT_LAST_NAME = "last_name";
    private static final String CLIENT_SOCIAL = "social";
    private static final String CLIENT_TABLE = "clients";
    private static final String TRANSACTIONS_TABLE = "transactions";
    private static final String CHECKING_ACCOUNT_TABLE = "checking_account";
    private int checkingAccountNum = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        bankConnection = BankConnection.getInstance();
    }

    private void initClientCheckingAccount()
    {
        String social = socialSecurityField.getText();
        String initCheckingAccount = "INSERT INTO " + CHECKING_ACCOUNT_TABLE + " (client_social, balance) values (" +
                "'" + social + "', " + 0.0 + ")";

        bankConnection.executeStatement(initCheckingAccount);
    }

    @FXML
    private void createNewAccount(ActionEvent event)
    {
        if(insertNewClient())
        {
            initClientCheckingAccount();
            initTransaction(event);
        }
    }

    private void initTransaction(ActionEvent event)
    {
        String social = socialSecurityField.getText();
        ResultSet chkAcctNumResults = bankConnection.executeQuery("SELECT account_number from checking_account" +
                    " join clients" +
                    " where client_social = " + "'" + socialSecurityField.getText() + "'");

            try
            {
                if (chkAcctNumResults != null) {
                    while(chkAcctNumResults.next())
                    {
                        checkingAccountNum = chkAcctNumResults.getInt("account_number");
                    }
                }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }

            String initTransactionStatement =  "INSERT INTO " + TRANSACTIONS_TABLE + "(amount, trans_date, trans_type, description, balance, chk_account_number, client_social) values" +
                    "(0.0, CURDATE(), 'open account', 'open checking', " + 0.0 + ", " + checkingAccountNum + ", " + social + ")";


            bankConnection.executeStatement(initTransactionStatement);

            loadViewAccounts(event);
    }

    private void loadViewAccounts(ActionEvent event)
    {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();

            Parent viewAccountsRoot = fxmlLoader.load((getClass().getResource("../view/viewAccounts.fxml").openStream())); // <- Parent
            Stage currentStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            ViewAccountsController viewAccountsController = fxmlLoader.getController();
            currentStage.setTitle("Transaction List");
            viewAccountsController.setClientInfo(checkingAccountNum, firstName, lastName);
            currentStage.setScene(new Scene(viewAccountsRoot));
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private boolean insertNewClient()
    {
        String clientFirstName = firstNameField.getText();
        String clientLastName = lastNameField.getText();
        String clientSocialSecurity = socialSecurityField.getText();

       if(!socialSecurityValid(clientSocialSecurity)) // not valid
       {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Social security is invalid");
           alert.setHeaderText(null);
           alert.setContentText(null);
           alert.showAndWait();
           return false;
       }


            if(!doesClientExist(
                    clientFirstName,
                    clientLastName,
                    clientSocialSecurity)) // client doesn't exist
            {
                String createClientStatement = "INSERT INTO clients values ("  +
                        "'" + clientFirstName + "', '" + clientLastName + "', '" + clientSocialSecurity + "'" + ")";

                if(bankConnection.executeCreateClientStatement(createClientStatement)) // add client to db
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Account successfully created");
                    alert.setHeaderText(null);
                    alert.setContentText(clientFirstName + " " + clientLastName + " added to Bank");
                    alert.showAndWait();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Account error");
                alert.setHeaderText(null);
                alert.setContentText("That social security number already exists.");
                alert.showAndWait();
            }
            return true;
    }

    private boolean socialSecurityValid(String social)
    {
        Pattern pattern = Pattern.compile("[0-9]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(social);
        return matcher.find();
    }

    private boolean doesClientExist(
            String firstName,
            String lastName,
            String newClientSocial)
    {
        String clientQuery = "SELECT " + CLIENT_FIRST_NAME + ", " + CLIENT_LAST_NAME + ", " + CLIENT_SOCIAL + " FROM " + CLIENT_TABLE;
        ResultSet clients = bankConnection.executeQuery(clientQuery);

        try
        {
            while(clients.next())
            if (clients.getString("first_name").equals(firstName)) {
                if (clients.getString("last_name").equals(lastName)) {
                    if (clients.getString("social").equals(newClientSocial)) {
                        return true;
                    }
                }
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

}
