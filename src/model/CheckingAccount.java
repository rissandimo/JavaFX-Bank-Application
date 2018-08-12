package model;

import javafx.fxml.FXML;

import java.util.Date;

class CheckingAccount
{
    private double      accountNumber;
    private double      balance;

    public double getAccountNumber()
    {
        return accountNumber;
    }

    public double getBalance()
    {
        return balance;
    }

    private boolean depositValid(double amountToDeposit)
    {
        if(!Double.isNaN(amountToDeposit))
        {
            if(amountToDeposit > 0 && amountToDeposit <= 500)
            return true;
        }
        return false;
    }

    @FXML
    private void handleWithdrawal(double amountToWithdraw)
    {
        if(withdrawValid(amountToWithdraw))
        {
            // withdraw from balance from db
        }

    }

    private boolean withdrawValid(double amountToWithdraw)
    {
        if(!Double.isNaN(amountToWithdraw))
        {
            if(amountToWithdraw > 0 && amountToWithdraw <= 500)
                return true;
        }
        return false;
    }
}
