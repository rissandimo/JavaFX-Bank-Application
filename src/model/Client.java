package model;

public class Client
{
    private String firstName;
    private String lastName;
    private int checkingAccountNumber;

    public Client(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        CheckingAccount checkingAccount = new CheckingAccount();
        //this.checkingAccountNumber = checkingAccount.generateAccountNumber();
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }
}
