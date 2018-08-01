package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import model.BankConnection;

import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        bankConnection = new BankConnection();
    }

    @FXML
    private void insertNewClient()
    {
        String clientFirstName = firstNameField.getText();
        String clientLastName = lastNameField.getText();
        String clientSocialSecurity = socialSecurityField.getText();



        String createClientStatement = "INSERT INTO clients values ("  +
                "'" + clientFirstName + "', '" + clientLastName + "', '" + clientSocialSecurity + "'" + ")";
        if(bankConnection.executeCreateClientStatement(createClientStatement))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Account successfully created");
            alert.setHeaderText(null);
            alert.setContentText(clientFirstName + " " + clientLastName + " added to Bank");
            alert.showAndWait();
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


}
