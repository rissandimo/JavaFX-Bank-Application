package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.BankConnection;
import util.BankConnectionValidator;
import util.Message;

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
    private AnchorPane registerClientPane;

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

    private void initCheckingAccount()
    {
        String social = socialSecurityField.getText();
        String initCheckingAccount = "INSERT INTO " + CHECKING_ACCOUNT_TABLE + " (client_social, balance) values (" +
                "'" + social + "', " + 0.0 + ")";

        bankConnection.executeStatement(initCheckingAccount);
    }

    @FXML
    private void createNewAccount()
    {
        if(insertNewClient())
        {
            initCheckingAccount();
            initTransaction();
        }
    }

    private void getAcctNumFromChkAcct(String social)
    {
        ResultSet chkAcctNumResults = bankConnection.executeQuery("SELECT account_number from checking_account" +
                " join clients" +
                " where client_social = " + "'" + social + "'");

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
    }

    private void initTransaction()
    {
        String social = socialSecurityField.getText();

        //get acct num for given social
        getAcctNumFromChkAcct(social);

            String initTransactionStatement =  "INSERT INTO " + TRANSACTIONS_TABLE + "(amount, trans_date, trans_type, description, balance, chk_account_number, client_social) values" +
                    "(0.0, CURDATE(), 'open account', 'open checking', " + 0.0 + ", " + checkingAccountNum + ", " + social + ")";

            bankConnection.executeStatement(initTransactionStatement);

            switchSceneToViewAccount();
    }

    private void switchSceneToViewAccount()
    {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();

            Parent viewAccountScene = fxmlLoader.load((getClass().getResource("../view/viewAccounts.fxml").openStream()));
            ViewAccountsController viewAccountsController = fxmlLoader.getController();
            Stage currentStage = (Stage) registerClientPane.getScene().getWindow();
            currentStage.setTitle("Transaction List");
            viewAccountsController.setClientInfo(checkingAccountNum, firstName, lastName);
            currentStage.setScene(new Scene(viewAccountScene));
            currentStage.show();

        }
        catch (IOException e) { e.printStackTrace();
        }

    }

    @FXML
    private boolean insertNewClient()
    {
        String clientFirstName = firstNameField.getText();
        String clientLastName = lastNameField.getText();
        String clientSocialSecurity = socialSecurityField.getText();


        //sql injection attack - can happen
        //Use prepare statements instead
        String createClientStatement = "INSERT INTO clients values ("  +
                "'" + clientFirstName + "', '" + clientLastName + "', '" + clientSocialSecurity + "'" + ")";

       if(!BankConnectionValidator.socialSecurityValid(clientSocialSecurity)) // social not valid
       {
           return false;
       }

       //create unique index on table - when using spring b/c of multithreading
        // one user accessing this -> ok to do it this way
        if(!doesClientExist(clientFirstName, clientLastName, clientSocialSecurity)) // client exist
        {
            if(bankConnection.executeCreateClientStatement(createClientStatement)) // add client to db
            {
                Message.showClientCreationSuccessMessage("Client added successfully",clientFirstName, clientLastName);
            }
        }
        else
        {
            Message.showMessage(Message.ERROR, "Account error", "That social security number already exists.");
            return false;
        }

       return true;
    }

    //extract to class
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
            //check if client exists by full name and social
            while(clients.next())
            if (clients.getString("first_name").equals(firstName)) {
                if (clients.getString("last_name").equals(lastName)) {
                    if (clients.getString("social").equals(newClientSocial)) {
                        return true;
                    }
                }
            }//check if clients exists only by social
            else if(clients.getString("social").equals(newClientSocial))
            {
                return true;
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

}
