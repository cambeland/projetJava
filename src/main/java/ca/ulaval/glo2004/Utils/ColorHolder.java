package ca.ulaval.glo2004.Utils;

import java.awt.*;

public class ColorHolder{
    public Color color;
    public String name;

    public ColorHolder(String name,Color color){
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString(){
        return name;
    }
}