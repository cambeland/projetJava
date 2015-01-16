package ca.ulaval.glo2004.GUI.Listener;

import ca.ulaval.glo2004.GUI.AppMasterFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewButtonListener implements ActionListener {
    private final AppMasterFrame masterFrame;

    public  NewButtonListener(AppMasterFrame masterFrame){
        this.masterFrame = masterFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        masterFrame.loadNewProject();
    }
}
