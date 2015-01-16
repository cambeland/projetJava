package ca.ulaval.glo2004.Controller;

import java.awt.*;
import java.util.ArrayList;
import java.awt.geom.Line2D;
import ca.ulaval.glo2004.Utils.SerializableBufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import ca.ulaval.glo2004.Domain.Station;
import ca.ulaval.glo2004.Drawing.GraphicPanelDrawer;
import java.io.Serializable;

public class StationController extends Station implements Drawable, Serializable{

    private boolean selected;
    private Rectangle r;
    private SerializableBufferedImage image;

    public StationController(String name, int posX, int posY, int sizeX, int sizeY){
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.image = null;
        this.selected = false;
        setName(name);
        this.r = new Rectangle();
    }

    public StationController(String name, int posX, int posY){
        this(name, posX, posY, 100, 100);
    }

    @Override
    public void draw(Graphics2D g, int panelDimension){
        g.setColor(getColor());
        if(selected){
            g.setColor(Color.LIGHT_GRAY);
        }
        int panelPosX = GraphicPanelDrawer.ceilConvertCMtoPX(panelDimension, posX);
        int panelPosY = GraphicPanelDrawer.ceilConvertCMtoPX(panelDimension, posY);
        int width = GraphicPanelDrawer.floorConvertCMtoPX(panelDimension, sizeX);
        int height = GraphicPanelDrawer.floorConvertCMtoPX(panelDimension, sizeY);
        r.setRect(panelPosX, panelPosY, width, height);
        drawImage(g, panelPosX, panelPosY, width, height);
        g.draw(r);
        g.drawString(getName(), panelPosX, panelPosY);
    }
    
    private void drawImage(Graphics2D g, int panelPosX, int panelPosY, int width, int height){
        if (this.image == null){
            if (this.getImagePath() != null){
                try {
                    image = new SerializableBufferedImage(ImageIO.read(new File(this.getImagePath())));
                } catch (IOException e){
                    throw new RuntimeException(e.toString());
                }
            }
        } else{
            g.drawImage(this.image.getBImage(), panelPosX, panelPosY, width, height, null);
        }
    }
    
    @Override
    public boolean isCollision(int posX, int posY){
        boolean detected = false;
        if (posX >= this.posX && posX <= this.posX + this.sizeX
                && posY >= this.posY && posY <= this.posY + this.sizeY){
            detected = true;
        }
        return detected;
    }

    public Line2D findClosestBorderFromPointPosition(double pointPosX, double pointPosY){
        ArrayList<Line2D> borders = new ArrayList<>();
        Line2D westSegment = new Line2D.Float(posX, posY, posX, posY + sizeY);
        Line2D northSegment = new Line2D.Float(posX, posY, posX + sizeX, posY);
        Line2D eastSegment = new Line2D.Float(posX + sizeX, posY, posX + sizeX, posY + sizeY);
        Line2D southSegment = new Line2D.Float(posX, posY + sizeY, posX + sizeX, posY + sizeY);
        borders.add(westSegment);
        borders.add(northSegment);
        borders.add(eastSegment);
        borders.add(southSegment);
        double minimumDistance = Double.POSITIVE_INFINITY;
        Line2D closestSegment = eastSegment;
        for (Line2D segment : borders){
            if (segment.ptSegDist(pointPosX, pointPosY) < minimumDistance){
                minimumDistance = segment.ptSegDist(pointPosX, pointPosY);
                closestSegment = segment;
            }
        }
        return closestSegment;
    }

    public Point getClosestPointOnBorder(Line2D segment, double pointPosX, double pointPosY){
        double deltaX = segment.getX2() - segment.getX1();
        double deltaY = segment.getY2() - segment.getY1();

        double u = ((pointPosX - segment.getX1()) * deltaX + (pointPosY - segment.getY1()) * deltaY) / (deltaX * deltaX + deltaY * deltaY);

        final Point closestPoint;
        if (u < 0){
            closestPoint = new Point((int) segment.getX1(), (int) segment.getY1());
        } else if (u > 1){
            closestPoint = new Point((int) segment.getX2(), (int) segment.getY2());
        } else{
            closestPoint = new Point((int) Math.round(segment.getX1() + u * deltaX), (int) Math.round(segment.getY1() + u * deltaY));
        }
       return closestPoint;
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
    public void setImagePath(String imagePath){
        this.image = null;
        this.imagePath = imagePath;
    }
    public boolean getExitFull(){
        return this.stationExitPoints.size() == this.outputNumber;
    }
    public String getExitNumber(){
        return String.valueOf(this.stationExitPoints.size()+1);
    }
}
