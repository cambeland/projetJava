package ca.ulaval.glo2004.Controller;


import java.awt.*;
import java.awt.geom.Ellipse2D;

import ca.ulaval.glo2004.Domain.CantTakeStation;
import ca.ulaval.glo2004.Domain.Station;
import ca.ulaval.glo2004.Drawing.GraphicPanelDrawer;
import ca.ulaval.glo2004.Domain.StationExitPoint;
import java.io.Serializable;

public class StationExitPointController extends StationExitPoint implements Drawable, Serializable{
    private boolean selected;

    private Ellipse2D e;

    public StationExitPointController(String name, int posX, int posY, int radius, Station station) throws CantTakeStation{
        super(posX, posY, station);
        this.name = name;
        this.radius = radius;
        this.selected = false;
        
        e = new Ellipse2D.Double();
        setColor(Color.black);
    }

    public StationExitPointController(String name, int posX, int posY, Station station) throws CantTakeStation{
        this(name, posX, posY, 12, station);
    }

    @Override
    public void draw(Graphics2D g, int panelDimension){
        PointController.drawFilledEllipse(g, panelDimension, e, this);
        PointController.drawEllipse(g, panelDimension, e, this, selected);
        int panelPosX = GraphicPanelDrawer.ceilConvertCMtoPX(panelDimension, this.getPosX());
        int panelPosY = GraphicPanelDrawer.ceilConvertCMtoPX(panelDimension,  this.getPosY());
        int panelRadius = GraphicPanelDrawer.ceilConvertCMtoPX(panelDimension, this.radius);
        g.drawString(getName(), panelPosX+panelRadius*2, panelPosY+(int)(panelRadius/1.5));
    }

    @Override
    public boolean isCollision(int posX, int posY){
        Ellipse2D bounds =  new Ellipse2D.Double(this.getPosX() - radius,this.getPosY() - radius, 2 * radius, 2 * radius);
        return bounds.contains(posX, posY);
    }

    @Override
    public boolean getSelected(){
        return selected;
    }

    @Override
    public boolean toggleSelected(){
        selected = !selected;
        return selected;
    }
}
