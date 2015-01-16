package ca.ulaval.glo2004.Controller;

import java.awt.*;

public interface Drawable{
    void draw(Graphics2D g, int panelDimension);
    public boolean isCollision(int posX, int posY);
    public boolean toggleSelected();
    public boolean getSelected();
}

