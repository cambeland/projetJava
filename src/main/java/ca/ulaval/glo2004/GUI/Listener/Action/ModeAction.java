package ca.ulaval.glo2004.GUI.Listener.Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ca.ulaval.glo2004.GUI.AppMasterFrame;
import ca.ulaval.glo2004.GUI.ToolBarPanel;

public abstract class ModeAction implements ActionListener {
    protected final AppMasterFrame masterFrame;
    protected ToolBarPanel toolBar;

    public ModeAction(AppMasterFrame masterFrame, ToolBarPanel toolBarPanel){
        this.masterFrame = masterFrame;
        this.toolBar = toolBarPanel;
    }

    protected void doDefaultAction(ActionEvent e){
        if(!(e.getSource()==toolBar.pointButton)){
            toolBar.entryPointButton.setEnabled(false);
            toolBar.exitPointButton.setEnabled(false);
            toolBar.junctionPointToggleButton.setEnabled(false);
        }
    }
}
