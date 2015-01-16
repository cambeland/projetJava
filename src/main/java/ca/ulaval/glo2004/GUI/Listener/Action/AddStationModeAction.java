package ca.ulaval.glo2004.GUI.Listener.Action;

import ca.ulaval.glo2004.GUI.AppMasterFrame;
import ca.ulaval.glo2004.GUI.AppMasterFrame.ApplicationMode;
import ca.ulaval.glo2004.GUI.Mouse.CursorStateModifier;
import ca.ulaval.glo2004.GUI.ToolBarPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddStationModeAction extends ModeAction{
    public AddStationModeAction(AppMasterFrame masterFrame, ToolBarPanel toolBarPanel){
        super(masterFrame, toolBarPanel);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        doDefaultAction(e);
        CursorStateModifier.addCursorModifier(masterFrame);
        masterFrame.setMode(ApplicationMode.ADD_STATION);
        toolBar.pointButton.setSelected(false);
    }
}
