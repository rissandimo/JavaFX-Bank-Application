package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.WelcomeScreen;

public class WelcomeScreenController implements EventHandler<ActionEvent>
{
    WelcomeScreen view;

    public WelcomeScreenController(WelcomeScreen view)
    {
        this.view = view;
    }

        public void handle(ActionEvent event)
        {
            System.out.println("handle()");
               if(event.getSource() == view.getAccessAccount()) view.setScene(view.getAccessAccountScene());
               if(event.getSource() == view.getCreateAccount()) view.setScene(view.getCreateAccountScene());
               if(event.getSource() == view.getDeleteAccount()) view.setScene(view.getDeleteAccountScene());

        }
}
