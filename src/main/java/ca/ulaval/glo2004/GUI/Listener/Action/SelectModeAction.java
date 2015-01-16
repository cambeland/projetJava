package ca.ulaval.glo2004.GUI.Listener.Action;

import java.awt.event.ActionEvent;

import ca.ulaval.glo2004.GUI.AppMasterFrame;
import ca.ulaval.glo2004.GUI.AppMasterFrame.ApplicationMode;
import ca.ulaval.glo2004.GUI.Mouse.CursorStateModifier;
import ca.ulaval.glo2004.GUI.ToolBarPanel;

public class SelectModeAction extends ModeAction{
    public SelectModeAction(AppMasterFrame masterFrame, ToolBarPanel toolBarPanel){
        super(masterFrame, toolBarPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        doDefaultAction(e);
        CursorStateModifier.selectCursorModifier(masterFrame);
        masterFrame.setMode(ApplicationMode.SELECT);
        toolBar.pointButton.setSelected(false);
    }
}
