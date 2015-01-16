package ca.ulaval.glo2004.Controller;


import ca.ulaval.glo2004.Domain.*;
import ca.ulaval.glo2004.Drawing.GraphicPanelDrawer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.io.Serializable;

public class ConveyorController extends Conveyor implements Drawable, Movable, Serializable {
    private Point source;
    private Point destination;
    boolean selected;

    public ConveyorController(String name, Point source, Point destination) throws CantTakeConveyorInput, CantTakeConveyorOutput, ConveyorInputsMaximumReached, ConveyorOutputAlreadySet {
        super(destination, source);
        this.source = source;
        this.destination = destination;
        selected = false;
        source.setOutputConveyor(this);
        destination.addInputConveyor(this);
        this.binPackage = this.source.getBinPackage();
        this.destination.setBinPackage(binPackage.copyBinPackage());
    }

    @Override
    public void draw(Graphics2D g, int panelDimension){
        //Code est adapté de source http://stackoverflow.com/questions/3010803/draw-arrow-on-line-algorithm
        int sourceX = GraphicPanelDrawer.ceilConvertCMtoPX(panelDimension, source.getPosX());
        int sourceY = GraphicPanelDrawer.ceilConvertCMtoPX(panelDimension, source.getPosY());
        int destinationX = GraphicPanelDrawer.ceilConvertCMtoPX(panelDimension, destination.getPosX());
        int destinationY = GraphicPanelDrawer.ceilConvertCMtoPX(panelDimension, destination.getPosY());

        g.setColor(this.color);
        if(selected){
            g.setColor(Color.LIGHT_GRAY);
        }
        g.drawLine(sourceX, sourceY, destinationX, destinationY);

        int arrowLength = 10; //can be adjusted
        int dx = destinationX - sourceX;
        int dy = destinationY - sourceY;

        double theta = Math.atan2(dy, dx);

        double rad = Math.toRadians(35); //35 angle, can be adjusted
        double x = destinationX - arrowLength * Math.cos(theta + rad);
        double y = destinationY - arrowLength * Math.sin(theta + rad);

        double phi2 = Math.toRadians(-35);//-35 angle, can be adjusted
        double x2 = destinationX - arrowLength * Math.cos(theta + phi2);
        double y2 = destinationY - arrowLength * Math.sin(theta + phi2);
        if(this.destination.getColor() != Color.BLACK){
            int[] arrowYs = new int[3];
            arrowYs[0] = destinationY;
            arrowYs[1] = (int) y;
            arrowYs[2] = (int) y2;

            int[] arrowXs = new int[3];
            arrowXs[0] = destinationX;
            arrowXs[1] = (int) x;
            arrowXs[2] = (int) x2;

            g.fillPolygon(arrowXs, arrowYs, 3);
        }
    }

    @Override
    public boolean isCollision(int mousePosX, int mousePosY){
        //Code est adapté de http://stackoverflow.com/questions/1797209/how-to-select-a-line
        boolean detected = false;
        int boxX = mousePosX - 4;
        int boxY = mousePosY - 4;
        int width = 8;
        int height = 8;
        java.awt.geom.Point2D pointSource = new java.awt.geom.Point2D.Double(source.getPosX(), source.getPosY());
        java.awt.geom.Point2D pointDestination = new java.awt.geom.Point2D.Double(destination.getPosX(), destination.getPosY());
        java.awt.geom.Line2D.Double line = new Line2D.Double(pointSource, pointDestination);
        if(line.intersects(boxX, boxY, width, height)){
            detected = true;
        }
        return detected;
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
        this.source.changePositionWithStationImpact(dx, dy);
        this.destination.changePositionWithStationImpact(dx, dy);
    }
    
    public String getBinPackageInfo(){
        return binPackage.getText();
    }
}
