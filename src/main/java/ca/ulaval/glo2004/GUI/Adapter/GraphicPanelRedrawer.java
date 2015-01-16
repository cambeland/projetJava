package ca.ulaval.glo2004.GUI.Adapter;

import ca.ulaval.glo2004.Drawing.GraphicPanelDrawer;

import java.awt.event.WindowAdapter;

public class GraphicPanelRedrawer extends WindowAdapter{
    GraphicPanelDrawer graphicPanelDrawer;

    public GraphicPanelRedrawer(GraphicPanelDrawer graphicPanelDrawer){
        this.graphicPanelDrawer = graphicPanelDrawer;

    }

    @Override
    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
        graphicPanelDrawer.repaint();
    }
}
