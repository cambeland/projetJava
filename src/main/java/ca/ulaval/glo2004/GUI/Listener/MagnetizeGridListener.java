package ca.ulaval.glo2004.GUI.Listener;

import ca.ulaval.glo2004.Drawing.GraphicPanelDrawer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MagnetizeGridListener implements ActionListener {
    private final GraphicPanelDrawer graphicPanelDrawer;

    public MagnetizeGridListener(GraphicPanelDrawer graphicPanelDrawer){
        this.graphicPanelDrawer =graphicPanelDrawer;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        graphicPanelDrawer.toggleMagneticGrid();
    }
}
