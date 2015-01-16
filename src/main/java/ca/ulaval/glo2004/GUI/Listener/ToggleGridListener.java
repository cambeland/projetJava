package ca.ulaval.glo2004.GUI.Listener;

import ca.ulaval.glo2004.Drawing.GraphicPanelDrawer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToggleGridListener implements ActionListener {
    private final GraphicPanelDrawer graphicPanelDrawer;

    public ToggleGridListener(GraphicPanelDrawer graphicPanelDrawer){
        this.graphicPanelDrawer =graphicPanelDrawer;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        graphicPanelDrawer.toggleGrid();
    }
}
