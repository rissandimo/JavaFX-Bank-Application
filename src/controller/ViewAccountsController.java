package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ViewAccountsController
{
    //private Connection bankConnection = BankConnection.createConnection();

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField amountField;

    @FXML
    private ChoiceBox typeChoice;

    @FXML
    private TableView<String> tableView;


    @FXML
    public void newTransaction()
    {
     /*   String description = descriptionField.getText();
        double amount = Double.parseDouble(amountField.getText());*/
        String choice = (String) typeChoice.getValue();

        if(choice.equals("Deposit"))
            System.out.println("deposit");
        else if(choice.equals("Withdrawal"))
            System.out.println("withdrawal");
        else
            System.out.println("purchase");
    }


    public void initialize()
    {
        System.out.println("Controller started");
    }


}
