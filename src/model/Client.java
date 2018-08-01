package model;

public class Client
{
    private String firstName;
    private String lastName;
    private long    accountNumber;
    private String password;
    private String socialSecurityNumber;
    private CheckingAccount checkingAccount;

    public Client(String firstName, String lastName, String socialSecurityNumber)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;
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
