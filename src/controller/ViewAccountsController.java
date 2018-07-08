package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.BankConnection;

import java.sql.*;

public class ViewAccountsController
{
    Connection connection = BankConnection.createConnection();

    @FXML
    ListView listView;

    public void initialize()
    {
        System.out.println("Controller started");
    }

}
