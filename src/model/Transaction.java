package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class Transaction
{
    private SimpleObjectProperty<Date> date;
    private SimpleStringProperty description;
    private SimpleStringProperty type;
    private SimpleDoubleProperty amount;
    private SimpleDoubleProperty balance;
    private SimpleIntegerProperty transactionID;

    public Transaction(Date date, String description, String type, double amount, double balance, int transactionId)
    {
        this.date = new SimpleObjectProperty<>(date);
        this.date = new SimpleObjectProperty<>();
        this.description = new SimpleStringProperty(description);
        this.type = new SimpleStringProperty(type);
        this.amount = new SimpleDoubleProperty(amount);
        this.balance = new SimpleDoubleProperty(balance);
        this.transactionID = new SimpleIntegerProperty(transactionId);
    }

    public Date getDate()
    {
        return date.get();
    }

    public SimpleObjectProperty<Date> dateProperty()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date.set(date);
    }

    public String getDescription()
    {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description.set(description);
    }

    public String getType()
    {
        return type.get();
    }

    public SimpleStringProperty typeProperty()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type.set(type);
    }

    public double getAmount()
    {
        return amount.get();
    }

    public SimpleDoubleProperty amountProperty()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount.set(amount);
    }

    public double getBalance()
    {
        return balance.get();
    }

    public SimpleDoubleProperty balanceProperty()
    {
        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance.set(balance);
    }

    public int getTransactionID()
    {
        return transactionID.get();
    }

    public SimpleIntegerProperty transactionIDProperty()
    {
        return transactionID;
    }

    public void setTransactionID(int transactionID)
    {
        this.transactionID.set(transactionID);
    }
}