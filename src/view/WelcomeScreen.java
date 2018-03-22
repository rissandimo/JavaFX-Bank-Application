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

import java.sql.*;

public class WelcomeScreen extends Application
{
    public Button accessAccount, createAccount, deleteAccount;
    private TextField textFirstName, textLastName, textSocial;
    public Stage window;
    public static TextArea results;
    private Connection bankConnection;
    private DeleteClientAccount deleteAccountController;

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

        // load accounts
       // displayClients();

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
        TextField acctNumText = new TextField();
        acctNumText.setMaxWidth(200);
        Button submit = new Button("Submit");

        logIn.getChildren().addAll(acctNumLabel, acctNumText, submit);

        //frame
        BorderPane frame = new BorderPane();

        frame.setTop(menu);
        frame.setCenter(logIn);

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

        VBox buttonPanel = new VBox(10);
        buttonPanel.setPadding(new Insets(0,0,10,0));
        buttonPanel.setAlignment(Pos.BASELINE_CENTER);
        buttonPanel.getChildren().add(submitButton);

        submitButton.setOnAction( e -> createAccount());

        BorderPane frame = new BorderPane();

        frame.setTop(menu);
        frame.setLeft(labels);
        frame.setRight(textFields);
        frame.setBottom(buttonPanel);

        return new Scene(frame, 400, 200);
    }

    public void createAccount()
    {
        new CreateClientAccount(textFirstName.getText(), textLastName.getText(), textSocial.getText());
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
        submit.setOnAction( e -> new DeleteClientAccount(Integer.parseInt(accountTextField.getText())));

        HBox bottomPanel = new HBox();
        bottomPanel.setAlignment(Pos.BASELINE_CENTER);
        bottomPanel.getChildren().addAll(accountLabel, accountTextField, submit);


        BorderPane frame = new BorderPane();

        frame.setTop(menu);
        frame.setCenter(results);
        frame.setBottom(bottomPanel);

        return new Scene(frame, 600, 400);
    }


    public Scene old_getDeleteAccountScene()
    {
        VBox labels = new VBox(10);
        labels.setPadding(new Insets(5, 0, 0, 10));
        HBox menu = getButtonsLayout();

        Label labelFirstName = new Label("First Name: ");
        Label labelLastName = new Label("Last Name: ");
        Label labelAccount = new Label("Account: ");

        labels.getChildren().addAll(labelFirstName, labelLastName, labelAccount);

        VBox textFieldsPanel = new VBox(10);
        textFieldsPanel.setPadding(new Insets(0, 10, 0, 0));


        TextField textFirstName = new TextField();
        TextField textLastName = new TextField();
        TextField textAccount = new TextField();

        textFieldsPanel.getChildren().addAll(textFirstName, textLastName, textAccount);

        BorderPane frame = new BorderPane();

        frame.setTop(menu);
        frame.setLeft(labels);
        frame.setRight(textFieldsPanel);

        return new Scene(frame, 400, 200);
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
