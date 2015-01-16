package ca.ulaval.glo2004.Utils;

import javax.swing.*;
import java.awt.*;

import ca.ulaval.glo2004.Domain.Equipment;

public class ColorProvider {
    public static ColorHolder defaultColorChoice = new ColorHolder("Black", Color.black);
    public static ColorHolder[] colors = {new ColorHolder("White", Color.white),
            new ColorHolder("Light Gray", Color.lightGray),
            new ColorHolder("Gray", Color.gray),
            new ColorHolder("Dark Gray", Color.darkGray),
            defaultColorChoice,
            new ColorHolder("Red", Color.red),
            new ColorHolder("Pink", Color.pink),
            new ColorHolder("Yellow", Color.yellow),
            new ColorHolder("Orange", Color.orange),
            new ColorHolder("Green", Color.green),
            new ColorHolder("Magenta", Color.magenta),
            new ColorHolder("Cyan", Color.cyan),
            new ColorHolder("Blue", Color.blue)};

    public static void selectEquipmentColor(JComboBox colorComboBox, Equipment equipmentController){
        colorComboBox.setSelectedItem(ColorProvider.defaultColorChoice);
        for(ColorHolder i : ColorProvider.colors){
            if(i.color.getRGB() == equipmentController.getColor().getRGB()){
                colorComboBox.setSelectedItem(i);
            }
        }
    }
}
