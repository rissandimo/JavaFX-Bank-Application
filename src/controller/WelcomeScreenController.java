package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.WelcomeScreen;

public class WelcomeScreenController implements EventHandler<ActionEvent>
{
    private WelcomeScreen view;

    public WelcomeScreenController(WelcomeScreen view)
    {
        this.view = view;
    }

    public void handle(ActionEvent event)
    {
        if(event.getSource() == view.getAccessAccountButton()) view.setScene(view.getAccessAccountScene());
        if(event.getSource() == view.getCreateAccountButton()) view.setScene(view.getCreateAccountScene());
        if(event.getSource() == view.getDeleteAccountButton()) view.setScene(view.getDeleteAccountScene());
    }
}
