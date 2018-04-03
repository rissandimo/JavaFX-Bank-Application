package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Utilities
{
    public static int getBiggestAccountNumber(Connection bankConnection)
    {
        int largestAccountNumber = 0;

        try
        {
            String largestAcctNum = "SELECT MAX(account_number) from clients";

            PreparedStatement preparedStatement = bankConnection.prepareStatement(largestAcctNum);


            ResultSet resultLargestAcctNum = preparedStatement.executeQuery();


            while(resultLargestAcctNum.next())
            {
                largestAccountNumber = resultLargestAcctNum.getInt(1);
                System.out.println("Largest account# found: " + largestAccountNumber);
                return largestAccountNumber;
            }

        }
        catch(SQLException e) { e.printStackTrace(); }

        return largestAccountNumber;
    }
}
