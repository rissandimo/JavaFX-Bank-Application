package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import model.BankConnection;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterClientController implements Initializable
{

    @FXML
    private JFXTextField firstNameField;

    @FXML
    private JFXTextField lastNameField;

    @FXML
    private JFXTextField socialSecurityField;

    private BankConnection bankConnection;

    public static final String CLIENT_FIRST_NAME = "first_name";
    public static final String CLIENT_LAST_NAME = "last_name";
    private static final String CLIENT_SOCIAL = "social";
    private static final String CLIENT_TABLE = "clients";
    private static final String TRANSACTIONS_TABLE = "transactions";
    private static final String CHECKING_ACCOUNT_TABLE = "checking_account";

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        bankConnection = new BankConnection();
    }

    private void initClientCheckingAccount()
    {
        String social = socialSecurityField.getText();
        String initCheckingAccount = "INSERT INTO " + CHECKING_ACCOUNT_TABLE + " (client_social, balance) values (" +
                "'" + social + "', " + 0.0 + ")";

        bankConnection.executeStatement(initCheckingAccount);
    }

    @FXML
    private void createNewAccount()
    {
        insertNewClient();
        initClientCheckingAccount();
        initTransaction();
    }

    private void initTransaction()
    {
        System.out.println("social = " + socialSecurityField.getText());

            ResultSet chkAcctNumResults = bankConnection.executeQuery("SELECT account_number from checking_account" +
                    " join clients" +
                    " where client_social = " + "'" + socialSecurityField.getText() + "'");
            int checkingAccountNum = 0;
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

            String initTransactionStatement =  "INSERT INTO " + TRANSACTIONS_TABLE + "(amount, trans_date, trans_type, description, balance, chk_account_number) values" +
                    "(0.0, CURDATE(), 'open account', 'open checking', " + 0.0 + ", " + checkingAccountNum + ")";


            bankConnection.executeStatement(initTransactionStatement);
    }


    @FXML
    private void insertNewClient()
    {
        String clientFirstName = firstNameField.getText();
        String clientLastName = lastNameField.getText();
        String clientSocialSecurity = socialSecurityField.getText();

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
            alert.setContentText("Client " + clientFirstName + " " + clientLastName + " exists.");
            alert.showAndWait();
        }
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
            if (clients != null) {
                while(clients.next())
                {
                    if (clients.getString("firstName").equals(firstName))
                        if(clients.getString("lastName").equals(lastName))
                            if(clients.getString("social").equals(newClientSocial))
                                return true;
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println("doesClientExist() -> Error adding client");
            e.printStackTrace();
        }
        return false;
    }


}
