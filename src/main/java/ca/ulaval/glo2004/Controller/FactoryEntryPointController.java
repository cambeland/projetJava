package ca.ulaval.glo2004.Controller;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import ca.ulaval.glo2004.Domain.FactoryEntryPoint;
import java.io.Serializable;

public class FactoryEntryPointController extends FactoryEntryPoint implements Drawable, Movable, Serializable{
    protected boolean selected;
    protected Ellipse2D e;

    public FactoryEntryPointController(String name, int posX, int posY, int radius){
        super(posX,posY);
        this.radius = radius;

        e = new Ellipse2D.Double();
        setColor(Color.green);
    }

    public FactoryEntryPointController(String name, int posX, int posY){
        this(name, posX, posY, 25);
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

    @Override
    public void changePosition(int dx, int dy){
        this.setPosX(getPosX()+dx);
        this.setPosY(getPosY()+dy);
    }
}
