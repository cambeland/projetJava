package ca.ulaval.glo2004.Domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

public class Conveyor extends Equipment implements Deletable, Serializable{
    Point exitPoint;
    Point entryPoint;
    public Conveyor(Point exitPoint, Point entryPoint){
        this.entryPoint = entryPoint;
        this.exitPoint = exitPoint;
        color = Color.BLACK;
        name = "";
        capacity = Double.MAX_VALUE;
        binPackage = entryPoint.getBinPackage();
    }
    
    @Override
    public ArrayList<Point> delete(){
        binPackage = new BinPackage();
        exitPoint.validate();
        entryPoint.removeExitConveyor(this);
        exitPoint.removeEntryConveyor(this);
        return new ArrayList<Point>();
    }

    public Point getExitPoint(){
        return exitPoint;
    }

    public Point getEntryPoint(){
        return entryPoint;
    }
    
    public void validate(){
        binPackage = entryPoint.getBinPackage();
        this.exitPoint.validate();
    }
}
