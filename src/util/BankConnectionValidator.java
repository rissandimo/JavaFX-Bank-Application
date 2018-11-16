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
            Error.showError("Error", "Social security number can only contain digits");
            return false;
        }
        else if(social.length() != 9)
        {
            Error.showError("Error", "Social security number must be 9 digits long");
            return false;
        }
        return true;
    }
}
