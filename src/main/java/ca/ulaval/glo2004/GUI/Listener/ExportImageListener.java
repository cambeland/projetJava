package ca.ulaval.glo2004.GUI.Listener;
import ca.ulaval.glo2004.Drawing.GraphicPanelDrawer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExportImageListener implements ActionListener {
    private final GraphicPanelDrawer graphicPanelDrawer;

    public ExportImageListener(GraphicPanelDrawer graphicPanelDrawer){
        this.graphicPanelDrawer =graphicPanelDrawer;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        graphicPanelDrawer.saveImage();
    }
}
