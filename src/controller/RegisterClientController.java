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

    private static final String CLIENT_SOCIAL = "social";
    private static final String CLIENT_TABLE = "clients";
    private static final String CHECKING_ACCOUNT_TABLE = "checking_account";

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        bankConnection = new BankConnection();
    }

    private void initClientCheckingAccount()
    {
        String social = socialSecurityField.getText();
        String createCheckingAccount = "INSERT INTO " + CHECKING_ACCOUNT_TABLE + " (client_social, balance) values (" +
                "'" + social + "', " + 0.0 + ")";

        bankConnection.executeStatement(createCheckingAccount);
    }

    @FXML
    private void createNewAccount()
    {
        insertNewClient();
        initClientCheckingAccount();
    }

    @FXML
    private void insertNewClient()
    {
        String clientFirstName = firstNameField.getText();
        String clientLastName = lastNameField.getText();
        String clientSocialSecurity = socialSecurityField.getText();

        if(!doesClientExist(clientSocialSecurity)) // client doesn't exist
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

    private boolean doesClientExist(String newClientSocial)
    {
        String clientQuery = "SELECT " + CLIENT_SOCIAL + " FROM " + CLIENT_TABLE;
        ResultSet clients = bankConnection.executeQuery(clientQuery);

        try
        {
            while(clients.next())
            {
                if(clients.getString("social").equals(newClientSocial))
                    return true;
            }
        }
        catch(SQLException e) {e.printStackTrace(); }
        return false;
    }


}