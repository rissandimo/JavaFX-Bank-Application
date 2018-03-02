package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class WelcomeScreen extends Application
{

    public static void main(String[]args)
    {
        launch(args);
    }

    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Bank");

        HBox buttonsLayout = new HBox(10);

        Button accessAccount = new Button("Access Account");
        Button createAccount = new Button("Create Account");
        Button deleteAccount = new Button("Delete Account");

        buttonsLayout.getChildren().addAll(accessAccount, createAccount, deleteAccount);
        buttonsLayout.setPadding(new Insets(5,5,5,5));

        Scene mainWindow = new Scene(buttonsLayout, 330, 200);
        primaryStage.setScene(mainWindow);
        primaryStage.show();
    }


}
