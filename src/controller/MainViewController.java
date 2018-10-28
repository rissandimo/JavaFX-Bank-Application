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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable
{

    @FXML
    private TextField accountNumberField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

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
        String firstName, lastName, accountNumberS = "";
        try
        {
             firstName = firstNameField.getText();
             lastName = lastNameField.getText();
             accountNumberS = accountNumberField.getText();
            boolean credentialsNotEntered = firstName.isEmpty() || lastName.isEmpty() || accountNumberS.isEmpty();

            if(credentialsNotEntered)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error - empty fields");
                alert.setContentText("All fields must be entered");
                alert.showAndWait();

            }
            else
            {
                int accountNumberI = Integer.parseInt(accountNumberField.getText());
                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                //get the controller
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("../view/viewAccounts.fxml").openStream());
                ViewAccountsController viewAccountsController =  loader.getController();
                //set the data
                viewAccountsController.setClientInfo(accountNumberI, firstName, lastName);
                Scene scene = new Scene(root);
                //load the scene
                currentStage.setScene(scene);
                currentStage.setTitle("Transaction List");
                currentStage.show();
            }
        }
        catch(NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error - empty fields");
            alert.setContentText("All fields must be entered");
            alert.showAndWait();
        }
    }

}
