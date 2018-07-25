package model;

public class Client
{
    private String firstName;
    private String lastName;
    private CheckingAccount checkingAccount;

    public Client(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        checkingAccount = new CheckingAccount();

    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void printCheckingAccount()
    {
        ///
    }
}
