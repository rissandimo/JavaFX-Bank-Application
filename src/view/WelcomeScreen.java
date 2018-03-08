package view;

import controller.AccountListener;
import controller.CreateClientAccount;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.BankConnection;

public class WelcomeScreen extends Application
{
    public Scene acessAccountScene, createAccountScene, deleteAccountScene;
    public Stage window;
    public Button accessAccount, createAccount, deleteAccount;

    public void start(Stage primaryStage)
    {
       window = primaryStage;
       setDisplay();
    }

    private void setDisplay()
    {
        window.setTitle("Bank");

        HBox buttonsLayout = getButtonsLayout(); //

        BorderPane frame = new BorderPane();

        frame.setTop(buttonsLayout);

        Scene scene = new Scene(frame,400,200);

        window.setScene(scene);
        window.show();
    }

    HBox getButtonsLayout()
    {
        HBox buttonsLayout = new HBox(10);
        buttonsLayout.setAlignment(Pos.BASELINE_CENTER);

        accessAccount = createButton("Access Account");
        createAccount = new Button("Create Account");
        deleteAccount = new Button("Delete Account");

        accessAccount.setOnAction(new AccountListener(this));
        createAccount.setOnAction(new AccountListener(this));
        deleteAccount.setOnAction(new AccountListener(this));

        buttonsLayout.getChildren().addAll(accessAccount, createAccount, deleteAccount);
        buttonsLayout.setPadding(new Insets(5,5,5,5));

        return buttonsLayout;
    }

    public Button createButton(String buttonName)
    {
        AccountListener accountListener = new AccountListener(this);
        Button button = new Button(buttonName);
        button.setOnAction(accountListener);

        return button;
    }

    public void setScene(Scene sceneToSet)
    {
        System.out.println("method reached");
        window.setScene(sceneToSet);
    }

    public Scene getAccessAccountScene()
    {
        //Menu
        HBox menu = getButtonsLayout();

        //Log in
        VBox logIn = new VBox(10);
        logIn.setAlignment(Pos.BASELINE_CENTER);
        logIn.setPadding(new Insets(5,5,5,5));

        Label acctNumLabel = new Label("Account Number");
        TextField acctNumText = new TextField();
        acctNumText.setMaxWidth(200);
        Button submit = new Button("Submit");

        logIn.getChildren().addAll(acctNumLabel, acctNumText, submit);

        //frame
        BorderPane frame = new BorderPane();

        frame.setTop(menu);
        frame.setCenter(logIn);

        this.acessAccountScene = new Scene(frame,400,200);

        return acessAccountScene;
    }

    public Scene getCreateAccountScene()
    {
        VBox labels = new VBox(10);
        labels.setPadding(new Insets(5,5,5,10));
        HBox menu = getButtonsLayout();

        Label labelFirstName = new Label("First Name: ");
        Label labelLastName = new Label("Last Name: ");
        Label labelSocial = new Label("Social: ");

        labels.getChildren().addAll(labelFirstName,labelLastName,labelSocial);

        VBox textFieldsPanel = new VBox(10);
        textFieldsPanel.setPadding(new Insets(0,10,0,10));


        TextField textFirstName = new TextField();
        TextField textLastName = new TextField();
        TextField textSocial = new TextField();

        String firstName, lastName, social;
        firstName = textFirstName.getText();
        lastName = textLastName.getText();
        social = textSocial.getText();

        textFieldsPanel.getChildren().addAll(textFirstName, textLastName, textSocial);

        Button submit = new Button("Submit");
        //get largest account num and pass it to CreateClientAccount constructor
        submit.setOnAction(new CreateClientAccount(firstName, lastName, social));

        BorderPane frame = new BorderPane();

        frame.setTop(menu);
        frame.setLeft(labels);
        frame.setRight(textFieldsPanel);

        return new Scene(frame, 400,200);
    }

    public Scene getDeleteAccountScene()
    {
        VBox labels = new VBox(10);
        labels.setPadding(new Insets(5,5,5,10));
        HBox menu = getButtonsLayout();

        Label labelFirstName = new Label("First Name: ");
        Label labelLastName = new Label("Last Name: ");
        Label labelAccount = new Label("Account: ");

        labels.getChildren().addAll(labelFirstName,labelLastName,labelAccount);

        VBox textFieldsPanel = new VBox(10);
        textFieldsPanel.setPadding(new Insets(0,10,0,10));


        TextField textFirstName = new TextField();
        TextField textLastName = new TextField();
        TextField textAccount = new TextField();

        textFieldsPanel.getChildren().addAll(textFirstName, textLastName, textAccount);

        BorderPane frame = new BorderPane();

        frame.setTop(menu);
        frame.setLeft(labels);
        frame.setRight(textFieldsPanel);

        return new Scene(frame, 400,200);
    }
    }

