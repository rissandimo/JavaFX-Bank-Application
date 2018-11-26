package util;

import javafx.scene.control.Alert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BankConnectionValidator
{
    public static boolean socialSecurityValid(String social)
    {
        Pattern pattern = Pattern.compile("[0-9]", Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(social);

        if(!matcher.find())
        {
            Message.showMessage(Message.ERROR, "Message", "Social security number can only contain digits");
            return false;
        }
        else if(social.length() != 9)
        {
            Message.showMessage(Message.ERROR, "Message", "Social security number must be 9 digits long");
            return false;
        }
        return true;
    }

    public static boolean withdrawalAmountValid(double accountBalance, double amount)
    {
        if( (accountBalance - amount) < 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Message - unsufficient funds");
            alert.setContentText("You do not have sufficient funds!");
            alert.showAndWait();
            return false;
        }
        else if(amount > 500)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Message - Limit exceeded");
            alert.setContentText("Please choose an amount not greater than $500");
            alert.showAndWait();
            return false;
        }
        else
        {
            return true;
        }

    }
}
