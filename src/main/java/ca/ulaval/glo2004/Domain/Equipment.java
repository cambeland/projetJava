package ca.ulaval.glo2004.Domain;

import java.awt.Color;
import java.io.Serializable;

public abstract class Equipment implements Serializable{
    protected Color color;
    protected String name;
    protected BinPackage binPackage;
    protected double capacity;
    
    public double getCapacity(){
        return capacity;
    }

    public void setCapacity(double newCapacity){
        this.capacity = newCapacity;
    }
    
    public Color getColor(){
        return color;
    }
    
    public void setColor(Color newColor){
        this.color = newColor;
    }
    
    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
    
    public BinPackage getBinPackage(){
        return binPackage;
    }
}
