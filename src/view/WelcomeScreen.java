package view;

import controller.AccountListener;
import controller.CreateClientAccount;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class WelcomeScreen extends Application
{
    private Stage window;
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

    private HBox getButtonsLayout()
    {
        HBox buttonsLayout = new HBox(10);
        buttonsLayout.setAlignment(Pos.BASELINE_CENTER);

        accessAccount = new Button("Access Account");
        createAccount = new Button("Create Account");
        deleteAccount = new Button("Delete Account");

        accessAccount.setOnAction(new AccountListener(this));
        createAccount.setOnAction(new AccountListener(this));
        deleteAccount.setOnAction(new AccountListener(this));

        buttonsLayout.getChildren().addAll(accessAccount, createAccount, deleteAccount);
        buttonsLayout.setPadding(new Insets(5,5,5,5));

        return buttonsLayout;
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

        return new Scene(frame,400,200);
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

        VBox buttonPanel = new VBox();
        buttonPanel.setPadding(new Insets(0,0,10,0));
        buttonPanel.setAlignment(Pos.BASELINE_CENTER);
        Button submitButton = new Button("Submit");
        buttonPanel.getChildren().add(submitButton);

        //get largest account num and pass it to CreateClientAccount constructor
        submitButton.setOnAction(new CreateClientAccount(firstName, lastName, social));

        BorderPane frame = new BorderPane();

        frame.setTop(menu);
        frame.setLeft(labels);
        frame.setRight(textFieldsPanel);
        frame.setBottom(buttonPanel);

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

        VBox buttonPanel = new VBox();
        buttonPanel.setPadding(new Insets(0,0,10,0));
        buttonPanel.setAlignment(Pos.BASELINE_CENTER);
        Button submitButton = new Button("Submit");
        buttonPanel.getChildren().add(submitButton);

        BorderPane frame = new BorderPane();

        frame.setTop(menu);
        frame.setLeft(labels);
        frame.setRight(textFieldsPanel);
        frame.setBottom(buttonPanel);

        return new Scene(frame, 400,200);
    }
}

