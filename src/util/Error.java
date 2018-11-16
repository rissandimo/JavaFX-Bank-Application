package util;

import javafx.scene.control.Alert;

public class Error
{
    public static void showError(String title)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(null);
        alert.showAndWait();
    }

    public static void showError(String title, String contextText)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

    public static void showClientSuccessfullMessage(String title, String firstName, String lastName)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(firstName + " " + lastName + " added to Bank");
        alert.showAndWait();
    }

}
