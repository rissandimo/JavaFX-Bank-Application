package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AccessAccountView extends Application
{

    private TextArea results;
    private Stage window;
    private int accountNumber;

    public AccessAccountView(int accountNumber)
    {
        System.out.println("AccessAccountView()");
        this.accountNumber = accountNumber;
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage window)
    {
        System.out.println("AccessAccountView - start()");
        this.window = window;
        createFrame(window);
    }


    private void createFrame(Stage window)
    {
        System.out.println("createFrame()");
        Scene displayScreen = getDisplayScreenScene();

        window.setScene(displayScreen);
        window.show();

        results.appendText("Please make a selection \n");
    }

    private Scene getDisplayScreenScene()
    {
        BorderPane frame = new BorderPane();

        //Buttons
        HBox buttonsPanel = new HBox();
        buttonsPanel.setAlignment(Pos.BASELINE_CENTER);

        Button depositButton = new Button("Deposit");
        depositButton.setOnAction( e -> results.appendText("Enter an amount to deposit \n"));

        Button withdrawalButton = new Button("Withdrawal");
        withdrawalButton.setOnAction( e -> results.appendText("Enter an amount to withdrawal \n"));

        Button checkAcctButton = new Button("Check Account");

        Button logOutButton = new Button("Log Out");

        buttonsPanel.getChildren().addAll(depositButton, withdrawalButton, checkAcctButton, logOutButton);

        // Text area
        results = new TextArea();
        results.setPrefColumnCount(40);
        results.setPrefRowCount(30);

                                                        //Bottom Panel
        HBox bottomPanel = new HBox();
        bottomPanel.setAlignment(Pos.BASELINE_CENTER);

        //Text field
        TextField input = new TextField();
        input.setPrefColumnCount(10);

        //Button

        Button submitButton = new Button("Submit");

        bottomPanel.getChildren().addAll(input, submitButton);
        frame.setTop(buttonsPanel);
        frame.setCenter(results);
        frame.setBottom(bottomPanel);

        return new Scene(frame, 600, 500);
    }


}
