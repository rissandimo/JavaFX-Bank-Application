package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.BankConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainViewController implements Initializable
{

    @FXML
    private TextField accountNumberField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    private BankConnection bankConnection;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        bankConnection = BankConnection.getInstance();
    }

    @FXML
    private void handleMakeNewAccount(ActionEvent event) throws IOException
    {
        // TODO - if account exists()
        Parent root = FXMLLoader.load(getClass().getResource("../view/registerClient.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        currentStage.setTitle("Make New Account");
        currentStage.setScene(scene);
        currentStage.show();
    }

    @FXML
    private void handleLogIn(ActionEvent actionEvent) throws IOException
    {
        String firstNameEntered, lastNameEntered, accountNumberEnteredS;
        try
        {
            firstNameEntered = firstNameField.getText();
            lastNameEntered = lastNameField.getText();
            accountNumberEnteredS = accountNumberField.getText();
            int accountNumberEntered = Integer.parseInt(accountNumberField.getText());
            boolean credentialsNotEntered = firstNameEntered.isEmpty() || lastNameEntered.isEmpty() || accountNumberEnteredS.isEmpty();

            if(credentialsNotEntered)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Message - empty fields");
                alert.setContentText("All fields must be entered");
                alert.showAndWait();

            }
            else
            {
                if(validateLogin(firstNameEntered, lastNameEntered, accountNumberEntered))
                {
                    Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                    FXMLLoader loader = new FXMLLoader();
                    Pane root = loader.load(getClass().getResource("../view/viewAccounts.fxml").openStream());
                    ViewAccountsController viewAccountsController =  loader.getController();

                    viewAccountsController.setClientInfo(accountNumberEntered, firstNameEntered, lastNameEntered);
                    Scene scene = new Scene(root);

                    currentStage.setScene(scene);
                    currentStage.setTitle("Transaction List");
                    currentStage.show();
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Message - No account found");
                    alert.setContentText("Client does not exists");
                    alert.showAndWait();
                }

            }
        }
        catch(NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Message - empty fields");
            alert.setContentText("All fields must be entered");
            alert.showAndWait();
        }
    }

    private boolean validateLogin(String firstNameEntered, String lastNameEntered, int accountNumberEntered)
    {
        String accountQuery = "SELECT first_name, last_name, account_number from clients" +
                " join checking_account" +
                " where clients.social = checking_account.client_social";

        ResultSet accountResults = bankConnection.executeQuery(accountQuery);
        try
        {
            while(accountResults != null && accountResults.next())
            {
                String firstNameDB = accountResults.getString("first_name");
                String lastNameDB = accountResults.getString("last_name");
                int acctNumberDB = Integer.parseInt(accountResults.getString("account_number"));

                if(firstNameEntered.equals(firstNameDB) &&
                        lastNameEntered.equals(lastNameDB) &&
                        accountNumberEntered == acctNumberDB)
                {
                    return true;
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

}
