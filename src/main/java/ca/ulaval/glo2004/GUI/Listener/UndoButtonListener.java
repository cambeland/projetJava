package ca.ulaval.glo2004.GUI.Listener;

import ca.ulaval.glo2004.GUI.AppMasterFrame;
import ca.ulaval.glo2004.GUI.ToolBarPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UndoButtonListener implements ActionListener{
    
    AppMasterFrame appMaster;
    
    public UndoButtonListener( AppMasterFrame appMaster){
        this.appMaster = appMaster;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        appMaster.saveToRedo();
        appMaster.loadToUndo();
    }
}
