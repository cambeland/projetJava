package ca.ulaval.glo2004.GUI.Listener;

import ca.ulaval.glo2004.GUI.AppMasterFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RedoButtonListener implements ActionListener{
    AppMasterFrame appMaster;
    
    public RedoButtonListener( AppMasterFrame appMaster){
        this.appMaster = appMaster;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        appMaster.saveToUndo();
        appMaster.loadToRedo();
    }
    
}
