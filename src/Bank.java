import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Bank extends Application
{
    private Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("view/mainView.fxml"));
        window.setScene(new Scene(root));
        window.setTitle("Login Screen");
        window.show();
    }

    public static void main(String[] args) { launch(args); }
}
