package ca.ulaval.glo2004.GUI.Listener.Action;

import java.awt.event.ActionEvent;

import ca.ulaval.glo2004.GUI.AppMasterFrame;
import ca.ulaval.glo2004.GUI.ToolBarPanel;

public class DeleteEquipmentAction extends ModeAction{
    public DeleteEquipmentAction(AppMasterFrame masterFrame, ToolBarPanel toolBarPanel){
        super(masterFrame, toolBarPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        masterFrame.saveToUndo();
        masterFrame.getGraphicalInterfaceController().deleteSelectedEquipment();
    }
}

