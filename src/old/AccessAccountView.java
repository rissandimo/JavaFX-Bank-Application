package old;

import model.BankConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AccessAccountView extends Application
{
    private TextArea results;
    private int accountNumber;
    private Connection bankConnection;

    public AccessAccountView(int accountNumber)
    {
        //bankConnection = BankConnection.createConnection();
        this.accountNumber = accountNumber;
    }

    public void start(Stage window)
    {

        Scene displayScreen = getDisplayScreenScene();
        window.setScene(displayScreen);
        window.show();
    }

    private Scene getDisplayScreenScene()
    {
        BorderPane frame = new BorderPane();

        HBox buttonsPanel = createButtonsPanel();

        TextField input = new TextField();
        input.setPrefColumnCount(10);

        Button submitButton = new Button("Submit");

        results = new TextArea();
        results.setPrefColumnCount(40);
        results.setPrefRowCount(30);

        HBox bottomPanel = new HBox();
        bottomPanel.setAlignment(Pos.BASELINE_CENTER);
        bottomPanel.getChildren().addAll(input, submitButton);
        frame.setTop(buttonsPanel);
        frame.setCenter(results);
        frame.setBottom(bottomPanel);

        return new Scene(frame, 600, 500);
    }

    private HBox createButtonsPanel()
    {
        HBox buttonsPanel = new HBox();
        buttonsPanel.setAlignment(Pos.BASELINE_CENTER);

        Button depositButton = new Button("Deposit");
        depositButton.setOnAction( e -> results.appendText("Enter an amount to handleDeposit \n"));

        Button withdrawalButton = new Button("Withdrawal");
        withdrawalButton.setOnAction( e -> results.appendText("Enter an amount to handleWithdrawal \n"));

        Button displayBalance = new Button("Check Account");
        displayBalance.setOnAction( e -> showBalance());

        Button logOutButton = new Button("Log Out");

        buttonsPanel.getChildren().addAll(depositButton, withdrawalButton, displayBalance, logOutButton);

        return buttonsPanel;
    }

    private void showBalance()
    {
        String balanceQuery = "SELECT account_balance from checking_account where account_number = ?";
        try
        {
        PreparedStatement preparedStatement = bankConnection.prepareStatement(balanceQuery);
        preparedStatement.setInt(1, accountNumber);

        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) { results.appendText("Balance: " + resultSet.getDouble(1)); }
        resultSet.first();

        }
        catch(SQLException e) {e.printStackTrace(); }
    }
}
