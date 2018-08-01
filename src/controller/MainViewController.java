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
import javafx.stage.Stage;
import model.BankConnection;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable
{

    @FXML
    private TextField accountNumberField;

    @FXML
    private TextField passwordField;

    private BankConnection bankConnection;
    public static final String CLIENT_TABLE = "clients";
    public static final String CLIENT_QUERY = "SELECT account_number, password FROM clients where account_number = ? and password = ?";

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        bankConnection = new BankConnection();
    }

    @FXML
    private void handleRegisterNewClient(ActionEvent event) throws IOException
    {
        // TODO - if account exists()
        Parent root = FXMLLoader.load(getClass().getResource("../view/registerClient.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 600);
        currentStage.setScene(scene);
        currentStage.show();
    }


    @FXML
    private void handleSignIn(ActionEvent event)
    {
        int accountNumber = Integer.parseInt(accountNumberField.getText());
        String password = passwordField.getText();

        if(accountNumber == 0 || password.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Invalid fields");
            alert.setContentText("Account number and/or password field are empty");
            alert.showAndWait();
        }

    }



/*    @FXML
    public void handleSignIn(ActionEvent event) throws IOException
    {
        int accountNumber = Integer.parseInt(accountNumberField.getText());
        String password = passwordField.getText();
        if(clientCredentialsCorrect(accountNumber, password))
        {
            changeScene(event);
        }
    }*/

/*    private boolean clientCredentialsCorrect(int accountNumber, String password)
    {
        try
        {
            PreparedStatement preparedStatement = bankConnection.prepareStatement(CLIENT_QUERY);
            preparedStatement.setInt(1, accountNumber);
            preparedStatement.setString(2, password);
            ResultSet clientResultSet = preparedStatement.executeQuery();

            while(clientResultSet.next())
            {
                if(clientResultSet.getInt(1) == accountNumber)
                    if(clientResultSet.getString(2).equals(passwordField))
                        return true;
            }
        }
        catch(SQLException e) {e.printStackTrace(); }
        return false;
    }*/




}
