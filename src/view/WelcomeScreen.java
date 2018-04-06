package view;

import controller.CreateClientController;
import controller.DeleteClientAccount;
import controller.WelcomeScreenController;
import model.BankConnection;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.*;

import java.sql.*;

public class WelcomeScreen extends Application
{
    public Button accessAccount, createAccount, deleteAccount;
    private Connection bankConnection;
    private DeleteClientAccount deleteAccountController;
    private Stage window;
    private TextField acctNumText, textFirstName, textLastName, textSocial;
    public static TextArea results;

    public WelcomeScreen()
    {
        this.bankConnection = BankConnection.createConnection();
        this.deleteAccountController = new DeleteClientAccount(3);

        //Results
        results = new TextArea();
        results.setPrefColumnCount(30);
        results.setPrefRowCount(400);
    }

    public static void main(String[] args)
    {
        launch(args);
    }


    public void start(Stage primaryStage)
    {
        window = primaryStage;
        window.setTitle("Bank");

        HBox buttonsMenuLayout = getButtonsLayout();

        BorderPane frame = new BorderPane();

        frame.setTop(buttonsMenuLayout);

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

    private Button createButton(String nameOfButton)
    {
        Button button = new Button(nameOfButton);
        button.setOnAction(new WelcomeScreenController(this));
        return button;
    }


    public Scene getAccessAccountScene()
    {
        //Menu
        HBox accessAccountButtonLayout = getButtonsLayout();

        //Login layout
        VBox loginLayout = new VBox(10);
        loginLayout.setAlignment(Pos.BASELINE_CENTER);
        loginLayout.setPadding(new Insets(5, 5, 5, 5));

        //Label and text
        Label acctNumLabel = new Label("Account Number");
        acctNumText = new TextField();
        acctNumText.setMaxWidth(200);

        //Button
        Button submit = new Button("Submit");
        //  submit.setOnAction( e -> accessAccount());

        loginLayout.getChildren().addAll(acctNumLabel, acctNumText, submit);

        BorderPane frame = new BorderPane();

        frame.setTop(accessAccountButtonLayout);
        frame.setCenter(loginLayout);

        return new Scene(frame, 400, 200);
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
        window.close(); // close frame
        new CreateClientController(textFirstName.getText(), textLastName.getText(), textSocial.getText());
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

    public int getAcctNum()
    {
        return Integer.parseInt(acctNumText.getText());
    }

    public String getTextFirstName()
    {
        return textFirstName.getText();
    }

    public String getTextLastName()
    {
        return textLastName.getText();
    }

    public String getTextSocial()
    {
        return textSocial.getText();
    }

    public static TextArea getResults()
    {
        return results;
    }
}
