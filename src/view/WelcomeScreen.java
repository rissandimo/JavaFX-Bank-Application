package view;

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
    private Stage window;
    public static TextArea results;
    private Connection bankConnection;
    private DeleteClientAccount deleteAccountController;
    private TextField acctNumText;

    public WelcomeScreen()
    {
        this.bankConnection = BankConnection.createConnection();
        this.deleteAccountController = new DeleteClientAccount();
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

    private HBox getButtonsLayout()
    {
        HBox buttonsLayout = new HBox(10);
        buttonsLayout.setAlignment(Pos.BASELINE_CENTER);

        accessAccount = createButton("Access Account");
        createAccount = createButton("Create Account");
        deleteAccount = createButton("Delete Account");

        buttonsLayout.getChildren().addAll(accessAccount, createAccount, deleteAccount);
        buttonsLayout.setPadding(new Insets(5, 5, 5, 5));

        return buttonsLayout;
    }

    private Button createButton(String buttonName)
    {
        Button button = new Button(buttonName);

        button.setOnAction(new MenuAccountListener(this));

        return button;
    }


    public Scene getAccessAccountScene()
    {
        HBox menuPanel = getButtonsLayout();
        VBox loginPanel = getLogin();

        BorderPane frame = new BorderPane();
        frame.setTop(menuPanel);
        frame.setCenter(loginPanel);

        return new Scene(frame, 400, 200);
    }

    private VBox getLogin()
    {
        VBox loginPanel = new VBox(10);
        loginPanel.setAlignment(Pos.BASELINE_CENTER);
        loginPanel.setPadding(new Insets(5, 5, 5, 5));

        Label acctNumLabel = new Label("Account Number");
        acctNumText = new TextField();
        acctNumText.setMaxWidth(200);
        Button submit = new Button("Submit");
        submit.setOnAction( e -> accessAccount());

        loginPanel.getChildren().addAll(acctNumLabel, acctNumText, submit);

        return loginPanel;
    }

    private void accessAccount()
    {
        checkClientInfo();

        if(accountExists()) new AccessAccountView(Integer.parseInt(acctNumText.getText()));
    }

    private boolean accountExists()
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
                accountExists = accountNumberFromDatabase.equals(accountFromInput);
            }
        }
        catch(SQLException e) { e.printStackTrace(); }
        return accountExists;
    }

    private void checkClientInfo()
    {
        int accountNumber = Integer.parseInt(acctNumText.getText());

        if (accountExists())
        {
            window.close();
            new AccessAccountView(accountNumber);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "No account found for: " + accountNumber);
        }
    }

    public Scene getCreateAccountScene()
    {
        //Panels
        HBox menu = getButtonsLayout();
        VBox labels = getLabels();
        VBox textFields = getTextFields();
        VBox buttonPanel = getButtons();

        //add panels to frame
        BorderPane frame = new BorderPane();
        frame.setTop(menu);
        frame.setLeft(labels);
        frame.setRight(textFields);
        frame.setBottom(buttonPanel);

        return new Scene(frame, 400, 200);
    }

    private VBox getLabels()
    {
        VBox labels = new VBox(10);
        labels.setPadding(new Insets(5, 0, 0, 10));
        Label labelFirstName = new Label("First Name: ");
        Label labelLastName = new Label("Last Name: ");
        Label labelSocial = new Label("Social: ");

        labels.getChildren().addAll(labelFirstName, labelLastName, labelSocial);

        return labels;
    }

    private VBox getTextFields()
    {
        VBox textFields = new VBox(10);
        textFields.setPadding(new Insets(0, 10, 0, 0));

        textFirstName = new TextField();
        textLastName = new TextField();
        textSocial = new TextField();

        textFields.getChildren().addAll(textFirstName, textLastName, textSocial);

        return textFields;
    }

    private VBox getButtons()
    {
        VBox buttonPanel = new VBox(10);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction( e -> createAccount());

        buttonPanel.setPadding(new Insets(0,0,10,0));
        buttonPanel.setAlignment(Pos.BASELINE_CENTER);
        buttonPanel.getChildren().add(submitButton);

        return buttonPanel;
    }

    private void createAccount()
    {
        if( !accountExists() ) new AccessAccountView(Integer.parseInt(acctNumText.getText()));
        else JOptionPane.showMessageDialog(null, "Account already exists");
    }

    public Scene getDeleteAccountScene()
    {
        HBox bottomPanel = getBottomPanel();
        HBox menuPanel = getMenu();

        BorderPane frame = new BorderPane();
        frame.setTop(menuPanel);
        frame.setCenter(results);
        frame.setBottom(bottomPanel);

        return new Scene(frame, 600, 400);
    }

    private HBox getBottomPanel()
    {
        Label accountLabel = new Label("Account number: ");

        //Results
        results = new TextArea();
        results.setPrefColumnCount(30);
        results.setPrefRowCount(400);

        // Text field
        TextField accountTextField = new TextField();
        accountTextField.setPrefColumnCount(10);

        Button submit = new Button("Submit");
        submit.setOnAction( e -> deleteAccount(Integer.parseInt(accountTextField.getText() )));

        HBox bottomPanel = new HBox();
        bottomPanel.setAlignment(Pos.BASELINE_CENTER);
        bottomPanel.getChildren().addAll(accountLabel, accountTextField, submit);

        return bottomPanel;
    }

    private HBox getMenu()
    {
        HBox menu = getButtonsLayout();

        Button showAccountButton = new Button("Show Accounts");
        showAccountButton.setOnAction(e -> deleteAccountController.displayClients());

        menu.getChildren().add(showAccountButton);
        return menu;
    }

    private void deleteAccount(int account_number)
    {
        try { new DeleteClientAccount(account_number); }
        catch(NumberFormatException e)
        {JOptionPane.showMessageDialog(null, "Incorrect account number"); }
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
