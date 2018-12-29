package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class Transaction
{
    private SimpleDoubleProperty amount;
    private SimpleObjectProperty<Date> date;
    private SimpleStringProperty type;
    private SimpleStringProperty description;
    private SimpleDoubleProperty balance;
    private SimpleStringProperty social;

    public Transaction(double amount, Date date, String type, String description, double balance, String social)
    {
        this.amount = new SimpleDoubleProperty(amount);
        this.date = new SimpleObjectProperty<>(date);
        this.type = new SimpleStringProperty(type);
        this.description = new SimpleStringProperty(description);
        this.balance = new SimpleDoubleProperty(balance);
        this.social = new SimpleStringProperty(social);
    }

    public String getDescription()
    {
        return description.get();
    }

    public double getAmount()
    {
        return amount.get();
    }


    public Date getDate()
    {
        return date.get();
    }

    public String getType()
    {
        return type.get();
    }

    public double getBalance()
    {
        return balance.get();
    }



}