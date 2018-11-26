package util;

import javafx.scene.control.Alert;


public class Message
{

    public static final Alert.AlertType ERROR = Alert.AlertType.ERROR;
    public static final Alert.AlertType WARNING = Alert.AlertType.WARNING;
    public static final Alert.AlertType CONFIRMATION = Alert.AlertType.CONFIRMATION;

    public static void showMessage(Alert.AlertType alertType, String title, String contextText)
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

    public static Alert showMessageAndReturnAlertReference(Alert.AlertType alertType, String title, String contextText)
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
        return alert;
    }

    public static void showClientCreationSuccessMessage(String title, String firstName, String lastName)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(firstName + " " + lastName + " added to Bank");
        alert.showAndWait();
    }

}
