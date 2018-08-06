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

    // private BankConnection bankConnection;
    public static final String CLIENT_TABLE = "clients";
    public static final String CLIENT_QUERY = "SELECT account_number, password FROM clients where account_number = ? and password = ?";

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
      //  bankConnection = new BankConnection();
    }

    @FXML
    private void handleMakeNewAccount(ActionEvent event) throws IOException
    {
        // TODO - if account exists()
        Parent root = FXMLLoader.load(getClass().getResource("../view/registerClient.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        currentStage.setScene(scene);
        currentStage.show();
    }

    @FXML
    private void handleLogIn(ActionEvent actionEvent) throws IOException
    {
        Parent viewAccountRoot = FXMLLoader.load(getClass().getResource("../view/viewAccounts.fxml"));
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(viewAccountRoot));
        currentStage.show();
    }

}
