package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AddTransactionController implements Initializable
{

    @FXML
    private ChoiceBox<String> transactionChoices = new ChoiceBox<>();

    private ObservableList<String> transactionTypes = FXCollections.observableArrayList("Debt", "Deposit", "Withdrawal");

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        transactionChoices.getItems().setAll(transactionTypes);
    }




}
