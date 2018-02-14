package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.geometry.*;



public class AccessAccount extends Application
{
    Stage window;
    private Button createAcccount;
    private Button accessAccount;
    private Button deleteAccount;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.window = primaryStage;
        window.setTitle("Access Account");

        createAcccount = new Button("Create Account");
        accessAccount = new Button("Create Account");
        deleteAccount = new Button("Delete Account");

        HBox verticalLayout = new HBox(10);
        verticalLayout.setPadding(new Insets(10,10, 10,10));

        verticalLayout.getChildren().addAll(createAcccount, accessAccount, deleteAccount);

        Scene mainWindow = new Scene(verticalLayout,380,300);
        window.setScene(mainWindow);

        window.show();

        createFrame();
    }

    private void createFrame()
    {

    }
}
