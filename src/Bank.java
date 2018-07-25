import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Bank extends Application
{
    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("view/mainView.fxml"));
        window.setScene(new Scene(root, 700,600));
        window.show();

    }

    public static void main(String[] args)
{
    launch(args);

}
}
