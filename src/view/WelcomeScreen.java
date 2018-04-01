package view;

import controller.CreateClientAccount;
import controller.DeleteClientAccount;
import controller.MenuAccountListener;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.BankConnection;

import javax.swing.*;
import java.sql.*;

public class WelcomeScreen extends Application
{
    public Button accessAccount, createAccount, deleteAccount;
    private TextField textFirstName, textLastName, textSocial;
    public Stage window;
    public static TextArea results;
    private Connection bankConnection;
    private DeleteClientAccount deleteAccountController;
    TextField acctNumText;

    public WelcomeScreen()
    {
        this.bankConnection = new BankConnection().createConnection();
        this.deleteAccountController = new DeleteClientAccount();

        //Results
        results = new TextArea();
        results.setPrefColumnCount(30);
        results.setPrefRowCount(400);
    }

    public static void main(String[] args)
    {
        launch(args);
    }


    public void start(Stage primaryStage) throws Exception
    {
        System.out.println("start()");
        window = primaryStage;
        window.setTitle("Bank");

        HBox buttonsLayout = getButtonsLayout();

        BorderPane frame = new BorderPane();

        frame.setTop(buttonsLayout);

        Scene scene = new Scene(frame, 400, 200);
        window.setScene(scene);
        window.show();
    }

    public TextField getTextFirstName()
    {
        return textFirstName;
    }

    public TextField getTextLastName()
    {
        return textLastName;
    }

    public TextField getTextSocial()
    {
        return textSocial;
    }

    private HBox getButtonsLayout()
    {
        //Buttons Layout
        HBox buttonsLayout = new HBox(10);
        buttonsLayout.setAlignment(Pos.BASELINE_CENTER);

        accessAccount = new Button("Access Account");
        createAccount = new Button("Create Account");
        deleteAccount = new Button("Delete Account");

        accessAccount.setOnAction(new MenuAccountListener(this));
        createAccount.setOnAction(new MenuAccountListener(this));
        deleteAccount.setOnAction(new MenuAccountListener(this));


        buttonsLayout.getChildren().addAll(accessAccount, createAccount, deleteAccount);
        buttonsLayout.setPadding(new Insets(5, 5, 5, 5));

        return buttonsLayout;
    }


    public Scene getAccessAccountScene()
    {
        //Menu
        HBox menu = getButtonsLayout();

        //Log in
        VBox logIn = new VBox(10);
        logIn.setAlignment(Pos.BASELINE_CENTER);
        logIn.setPadding(new Insets(5, 5, 5, 5));

        Label acctNumLabel = new Label("Account Number");
        acctNumText = new TextField();
        acctNumText.setMaxWidth(200);
        Button submit = new Button("Submit");
        submit.setOnAction( e -> accessAccount());

        logIn.getChildren().addAll(acctNumLabel, acctNumText, submit);

        //frame
        BorderPane frame = new BorderPane();

        frame.setTop(menu);
        frame.setCenter(logIn);

        return new Scene(frame, 400, 200);
    }

    private void accessAccount()
    {
        checkClientInfo();
        boolean accountExists = doesAccountExist();

        if(accountExists) new AccessAccountView(Integer.parseInt(acctNumText.getText()));
    }

    private boolean doesAccountExist()
    {

        boolean accountExists = false;
        try
        {
            Statement query = bankConnection.createStatement();

            String sqlQuery = "SELECT first_name, last_name, account_number FROM clients";

            ResultSet resultSet = query.executeQuery(sqlQuery);

            while(resultSet.next())
            {

                String accountNumberFromDatabase = resultSet.getString(3);
                String accountFromInput = acctNumText.getText();

                if(accountNumberFromDatabase.equals(accountFromInput))
                {
                    accountExists = true;
                    System.out.println("Account found for: " + resultSet.getString(1) + " " + resultSet.getString(2));
                    break;
                }
                else
                    accountExists = false;
            }
        }
        catch(SQLException e) { e.printStackTrace(); }
        return  accountExists;
    }


    private void checkClientInfo()
    {
        String accountNumber = acctNumText.getText();
        int accountNumberInteger = Integer.parseInt(accountNumber);

        boolean accountExists = doesAccountExist();
        if (accountExists)
        {
            window.close(); // close the frame
            new AccessAccountView(accountNumberInteger);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "No account found for: " + accountNumberInteger);
        }
    }

    public Scene getCreateAccountScene()
    {
        VBox labels = new VBox(10);
        labels.setPadding(new Insets(5, 0, 0, 10));
        Label labelFirstName = new Label("First Name: ");
        Label labelLastName = new Label("Last Name: ");
        Label labelSocial = new Label("Social: ");

        labels.getChildren().addAll(labelFirstName, labelLastName, labelSocial);

        HBox menu = getButtonsLayout();

        VBox textFields = new VBox(10);
        textFields.setPadding(new Insets(0, 10, 0, 0));

        textFirstName = new TextField();
        textLastName = new TextField();
        textSocial = new TextField();

        textFields.getChildren().addAll(textFirstName, textLastName, textSocial);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction( e -> createAccount());

        VBox buttonPanel = new VBox(10);
        buttonPanel.setPadding(new Insets(0,0,10,0));
        buttonPanel.setAlignment(Pos.BASELINE_CENTER);
        buttonPanel.getChildren().add(submitButton);


        BorderPane frame = new BorderPane();

        frame.setTop(menu);
        frame.setLeft(labels);
        frame.setRight(textFields);
        frame.setBottom(buttonPanel);

        return new Scene(frame, 400, 200);
    }

    private void createAccount()
    {
        if( !doesAccountExist() ) new AccessAccountView(Integer.parseInt(acctNumText.getText()));
        else JOptionPane.showMessageDialog(null, "Account already exists");
    }

    public Scene getDeleteAccountScene()
    {

        //Menu
        VBox labels = new VBox(10);
        labels.setPadding(new Insets(5, 0, 0, 10));
        HBox menu = getButtonsLayout();

        Button showAccountButton = new Button("Show Accounts");
        showAccountButton.setOnAction(e -> deleteAccountController.displayClients());

        menu.getChildren().add(showAccountButton); // add "show accounts button" to menu

                                                                        //Bottom Panel

        Label accountLabel = new Label("Account number: ");

        // Text field
        TextField accountTextField = new TextField();
        accountTextField.setPrefColumnCount(10);

        //Button
        Button submit = new Button("Submit");
        submit.setOnAction( e -> deleteAccount(accountTextField.getText() ));

        HBox bottomPanel = new HBox();
        bottomPanel.setAlignment(Pos.BASELINE_CENTER);
        bottomPanel.getChildren().addAll(accountLabel, accountTextField, submit);


        BorderPane frame = new BorderPane();

        frame.setTop(menu);
        frame.setCenter(results);
        frame.setBottom(bottomPanel);

        return new Scene(frame, 600, 400);
    }

    private void deleteAccount(String account_number)
    {
        try
        {
             int accountNumber = Integer.parseInt(account_number);
             new DeleteClientAccount(accountNumber);
        }
        catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Incorrect account number");
        }

    }

    private void clearResults()
    {
        results.setText("");
    }

    public Button getAccessAccount()
    {
        return accessAccount;
    }

    public Button getCreateAccount()
    {
        return createAccount;
    }

    public Button getDeleteAccount()
    {
        return deleteAccount;
    }

    public void setScene(Scene scene)
    {
        window.setScene(scene);
    }

}
