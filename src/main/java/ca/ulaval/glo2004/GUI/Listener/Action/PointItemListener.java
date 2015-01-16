/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ulaval.glo2004.GUI.Listener.Action;
import ca.ulaval.glo2004.GUI.ToolBarPanel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
public class PointItemListener implements ItemListener {
    
    private ToolBarPanel toolBar;
    
    public PointItemListener(ToolBarPanel toolBarPanel){
        this.toolBar = toolBarPanel;
        toolBar.entryPointButton.setEnabled(false);
        toolBar.exitPointButton.setEnabled(false);
        toolBar.junctionPointToggleButton.setEnabled(false);
    }
    
    
    @Override
    public void itemStateChanged(ItemEvent e){//TODO should be an actionListener
        if (e.getStateChange()==ItemEvent.SELECTED && e.getSource()==toolBar.pointButton){
            toolBar.entryPointButton.setEnabled(true);
            toolBar.exitPointButton.setEnabled(true);
            toolBar.junctionPointToggleButton.setEnabled(true);
        }
    }

}
