package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.WelcomeScreen;

public class AccountListener implements EventHandler<ActionEvent>
{

    private WelcomeScreen welcomeScreen;

    public AccountListener(WelcomeScreen welcomeScreen)
    {
        this.welcomeScreen = welcomeScreen;
    }

        @Override
        public void handle(ActionEvent event)
        {
            if(event.getSource() == welcomeScreen.accessAccount)
                welcomeScreen.setScene(welcomeScreen.getAccessAccountScene());
            else if(event.getSource() == welcomeScreen.createAccount)
                welcomeScreen.setScene(welcomeScreen.getCreateAccountScene());
            else if(event.getSource() == welcomeScreen.deleteAccount)
                welcomeScreen.setScene(welcomeScreen.getDeleteAccountScene());
        }

}
