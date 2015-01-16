package ca.ulaval.glo2004.Controller;


import java.awt.*;
import java.awt.geom.Ellipse2D;

import ca.ulaval.glo2004.Domain.CantTakeStation;
import ca.ulaval.glo2004.Domain.Station;
import ca.ulaval.glo2004.Domain.StationEntryPoint;
import java.io.Serializable;

public class StationEntryPointController extends StationEntryPoint implements Drawable, Serializable{
    private boolean selected;

    private Ellipse2D e;
    
    public StationEntryPointController(String name, int posX, int posY, int radius, Station station) throws CantTakeStation {
        super(posX, posY, station);
        this.radius = radius;
        this.selected = false;

        e = new Ellipse2D.Double();
        setColor(Color.white);
    }

    public StationEntryPointController(String name, int posX, int posY, Station station) throws CantTakeStation{
        this(name, posX, posY, 12, station);
    }

    @Override
    public void draw(Graphics2D g, int panelDimension){
        PointController.drawFilledEllipse(g, panelDimension, e, this);
        PointController.drawEllipse(g, panelDimension, e, this, selected);
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
