package util;

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
            Message.showMessage(Message.ERROR, "Message - insufficient funds", "You do not have sufficient funds!");
            return false;
        }
        else if(amount > 500)
        {
            Message.showMessage(Message.ERROR, "Limit exceeded", "Please choose an amount up to $500");
            return false;
        }
        else
        {
            return true;
        }

    }
}
