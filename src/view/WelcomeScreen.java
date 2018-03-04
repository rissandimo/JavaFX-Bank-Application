package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WelcomeScreen extends Application
{
    private Button accessAccount, createAccount, deleteAccount;
    private Stage window;

    public static void main(String[]args)
    {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception
    {
        window = primaryStage;
        primaryStage.setTitle("Bank");

        HBox buttonsLayout = getButtonsLayout();

        BorderPane frame = new BorderPane();

        frame.setTop(buttonsLayout);

        Scene scene = new Scene(frame,400,200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox getButtonsLayout()
    {
        //Buttons Layout
        HBox buttonsLayout = new HBox(10);
        buttonsLayout.setAlignment(Pos.BASELINE_CENTER);

        accessAccount = new Button("Access Account");
        createAccount = new Button("Create Account");
        deleteAccount = new Button("Delete Account");

        ButtonListener buttonListener = new ButtonListener();

        accessAccount.setOnAction(buttonListener);
        createAccount.setOnAction(buttonListener);
        deleteAccount.setOnAction(buttonListener);

        buttonsLayout.getChildren().addAll(accessAccount, createAccount, deleteAccount);
        buttonsLayout.setPadding(new Insets(5,5,5,5));

        return buttonsLayout;
    }

    private Scene getAccessAccountScene()
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

    private Scene getCreateAccountScene()
    {
        VBox labels = new VBox();
        HBox menu = getButtonsLayout();

        Label labelFirstName = new Label("First Name: ");
        Label labelLasttName = new Label("Last Name: ");
        Label labelSocial = new Label("Social: ");

        labels.getChildren().addAll(labelFirstName,labelLasttName,labelSocial);

        VBox textFields = new VBox();

        TextField textFirstName = new TextField();
        TextField textLastName = new TextField();
        TextField textSocial = new TextField();

        textFields.getChildren().addAll(textFirstName, textLastName, textSocial);

        BorderPane frame = new BorderPane();

        frame.setTop(menu);
        frame.setLeft(labels);
        frame.setRight(textFields);

        return new Scene(frame, 400,200);
    }

    class ButtonListener implements EventHandler<ActionEvent>
    {

        public void handle(ActionEvent event)
        {
            if(event.getSource() == accessAccount) window.setScene(getAccessAccountScene());
            if(event.getSource() == createAccount) window.setScene(getCreateAccountScene());
        }
    }

}
