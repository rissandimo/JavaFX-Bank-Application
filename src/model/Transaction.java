package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
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

    public String getSocial()
    {
        return social.get();
    }

    public SimpleStringProperty socialProperty()
    {
        return social;
    }

    public void setSocial(String social)
    {
        this.social.set(social);
    }

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

}