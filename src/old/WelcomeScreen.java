package old;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.*;

public class WelcomeScreen extends Application
{
    private Button accessAccount, createAccount, deleteAccount;
    private DeleteClientAccount deleteAccountController;
    public static Stage window;
    private TextField textFirstName;
    private TextField textLastName;
    private TextField textSocial;
    public static TextArea results;

    public WelcomeScreen()
    {
        this.deleteAccountController = new DeleteClientAccount();
    }

    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("viewAccounts.fxml"));
        window = primaryStage;
        window.setTitle("Bank");

        HBox buttonsMenuLayout = getCreateButtonsMenuPanel();

        BorderPane frame = new BorderPane();

        frame.setTop(buttonsMenuLayout);

        Scene scene = new Scene(root, 700, 600);
        window.setScene(scene);
        window.show();
    }

    private HBox getCreateButtonsMenuPanel()
    {
        HBox buttonsPanel = new HBox(10);
        buttonsPanel.setAlignment(Pos.BASELINE_CENTER);

        accessAccount = createButton("Access Account");
        createAccount = createButton("Create Account");
        deleteAccount = createButton("Delete Account");

        buttonsPanel.getChildren().addAll(accessAccount, createAccount, deleteAccount);
        buttonsPanel.setPadding(new Insets(5, 5, 5, 5));

        return buttonsPanel;
    }

    private Button createButton(String nameOfButton)
    {
        Button button = new Button(nameOfButton);
        button.setOnAction(new WelcomeScreenController(this));
        return button;
    }

    public Scene getAccessAccountScene()
    {
        HBox buttonsPanel = getCreateButtonsMenuPanel();

        VBox loginPanel = getLoginLayout();

        BorderPane frame = new BorderPane();
        frame.setTop(buttonsPanel);
        frame.setCenter(loginPanel);

        return new Scene(frame, 400, 200);
    }

    private VBox getLoginLayout()
    {
        VBox loginPanel = new VBox(10);
        loginPanel.setAlignment(Pos.BASELINE_CENTER);
        loginPanel.setPadding(new Insets(5, 5, 5, 5));

        Label acctNumLabel = new Label("Account Number");
        TextField acctNumText = new TextField();
        acctNumText.setMaxWidth(200);

        Button submit = new Button("Log In");
        submit.setOnAction(e ->
        {
            window.close();
            new AccessAccountView(Integer.parseInt(acctNumText.getText()));
        });


        loginPanel.getChildren().addAll(acctNumLabel, acctNumText, submit);

        return loginPanel;
    }

    public Scene getCreateAccountScene()
    {
        HBox menuButtonsPanel = getCreateButtonsMenuPanel();

        VBox labelsPanel = getCreateLabelsPanel();

        VBox textFieldsPanel = getCreateTextFieldsPanel();

        VBox buttonPanel = getCreateButtonsPanel();

        BorderPane frame = new BorderPane();
        frame.setTop(menuButtonsPanel);
        frame.setLeft(labelsPanel);
        frame.setRight(textFieldsPanel);
        frame.setBottom(buttonPanel);

        return new Scene(frame, 400, 200);
    }

    private VBox getCreateLabelsPanel()
    {
        VBox labels = new VBox(10);
        labels.setPadding(new Insets(5, 0, 0, 10));
        Label labelFirstName = new Label("First Name: ");
        Label labelLastName = new Label("Last Name: ");
        Label labelSocial = new Label("Social: ");

        labels.getChildren().addAll(labelFirstName, labelLastName, labelSocial);

        return labels;
    }

    private VBox getCreateTextFieldsPanel()
    {
        VBox textFields = new VBox(10);
        textFields.setPadding(new Insets(0, 10, 0, 0));

        textFirstName = new TextField();
        textLastName = new TextField();
        textSocial = new TextField();

        textFields.getChildren().addAll(textFirstName, textLastName, textSocial);

        return textFields;
    }

    private VBox getCreateButtonsPanel()
    {
        VBox buttonPanel = new VBox(10);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction( e -> createNewAccount(
                textFirstName.getText(), textLastName.getText(), textSocial.getText()));

        buttonPanel.setPadding(new Insets(0,0,10,0));
        buttonPanel.setAlignment(Pos.BASELINE_CENTER);
        buttonPanel.getChildren().add(submitButton);

        return buttonPanel;
    }

    private void createNewAccount(String firstName, String lastname, String social)
    {
        window.close();
     //   new CreateClientController(firstName, lastname, social);
    }

    public Scene getDeleteAccountScene()
    {
        HBox buttonsLayout = getButtonsLayout();

        HBox bottomPanel = getDeleteSceneBottomPanel();

        BorderPane frame = new BorderPane();

        frame.setTop(buttonsLayout);
        frame.setCenter(results);
        frame.setBottom(bottomPanel);

        return new Scene(frame, 600, 400);
    }

    private HBox getButtonsLayout()
    {
        HBox deleteSceneButtonLayout = getCreateButtonsMenuPanel();
        VBox labels = new VBox(10);
        labels.setPadding(new Insets(5, 0, 0, 10));

        Button showAccountButton = new Button("Show Accounts");
        showAccountButton.setOnAction(e -> deleteAccountController.displayClients());

        deleteSceneButtonLayout.getChildren().add(showAccountButton); // add "show accounts button" to deleteSceneButtonLayout
        return deleteSceneButtonLayout;
    }

    private HBox getDeleteSceneBottomPanel()
    {
        HBox bottomPanel = new HBox();
        Label accountLabel = new Label("Account number: ");

        TextField accountTextField = new TextField();
        accountTextField.setPrefColumnCount(10);

        Button submit = new Button("Submit");
        submit.setOnAction( e -> deleteAccount(accountTextField.getText() ));

        bottomPanel.setAlignment(Pos.BASELINE_CENTER);
        bottomPanel.getChildren().addAll(accountLabel, accountTextField, submit);

        results = new TextArea();
        results.setPrefColumnCount(30);
        results.setPrefRowCount(400);

        return bottomPanel;
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

    public Button getAccessAccountButton()
    {
        return accessAccount;
    }

    public Button getCreateAccountButton()
    {
        return createAccount;
    }

    public Button getDeleteAccountButton()
    {
        return deleteAccount;
    }

    public void setScene(Scene scene)
    {
        window.setScene(scene);
    }
}