package ca.ulaval.glo2004.Controller;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import ca.ulaval.glo2004.Domain.Point;
import ca.ulaval.glo2004.Drawing.GraphicPanelDrawer;

abstract public class PointController{

    protected static void drawFilledEllipse(Graphics2D g, int panelDimension, Ellipse2D e, Point p){
        e.setFrame(GraphicPanelDrawer.floorConvertCMtoPX(panelDimension, p.getPosX() - p.getRadius()),
                GraphicPanelDrawer.floorConvertCMtoPX(panelDimension, p.getPosY() - p.getRadius()),
                GraphicPanelDrawer.floorConvertCMtoPX(panelDimension, 2 * p.getRadius()),
                GraphicPanelDrawer.floorConvertCMtoPX(panelDimension, 2 * p.getRadius()));
        g.setColor(p.getColor());
        g.fill(e);
    }

    protected static void drawEllipse(Graphics2D g, int panelDimension, Ellipse2D e, Point p, boolean selected){
        e.setFrame(GraphicPanelDrawer.floorConvertCMtoPX(panelDimension, p.getPosX() - p.getRadius() - 6),
                GraphicPanelDrawer.floorConvertCMtoPX(panelDimension, p.getPosY() - p.getRadius() - 6),
                GraphicPanelDrawer.floorConvertCMtoPX(panelDimension, 2 * p.getRadius() + 10),
                GraphicPanelDrawer.floorConvertCMtoPX(panelDimension, 2 * p.getRadius() + 10));
        if(selected) {
            g.setColor(Color.LIGHT_GRAY);
        } else {
            g.setColor(p.getColor());
        }
        g.draw(e);
    }
}
