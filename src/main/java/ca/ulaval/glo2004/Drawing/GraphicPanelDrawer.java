package ca.ulaval.glo2004.Drawing;

import ca.ulaval.glo2004.Controller.Drawable;
import ca.ulaval.glo2004.Controller.StationController;
import ca.ulaval.glo2004.Controller.ConveyorController;
import ca.ulaval.glo2004.Domain.Point;

import java.awt.*;
import java.io.Serializable;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GraphicPanelDrawer extends JPanel implements Serializable{
    private List<Drawable> drawables;
    protected static int sceneSizeInCM = 2000;
    private static final int gridRepetitionInCM = 50;
    private final int gridSquareSizeInCM = 4;
    private boolean gridIsOn = false;
    private static boolean magneticGridIsOn = false;
    private final Color gridColor = new Color(128, 128, 128);
    private final Color defaultColor = new Color(0, 0, 0);
    private boolean equipmentInformationIsOn;
    private String equipmentInformation;
    private java.awt.Point actualMousePoint;

    public GraphicPanelDrawer(){
        drawables = new LinkedList<Drawable>();
    }
    
    public void add(Drawable drawable){
        if(!drawables.contains(drawable)) {
            drawables.add(drawable);
        } else{
            throw new RuntimeException("Drawable already there.");
        }
    }
    
    public void setEquipmentInformation(String information){
        this.equipmentInformation = information;
    }
    
    public void setActualMousePoint(java.awt.Point mousePoint){
        this.actualMousePoint = mousePoint;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int panelDimension = getSceneDimension(this);
        Graphics2D g2 = (Graphics2D)g;
        if(gridIsOn)paintGrid(g2);
        if(equipmentInformationIsOn)paintEquipmentInformation(g2);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(defaultColor);
        g2.setStroke(new BasicStroke(2f));
        for (Drawable i : drawables) {
            i.draw(g2, panelDimension);
        }
    } 

    private void paintGrid(Graphics2D g){
        final int gridSquareMiddleInCM = gridSquareSizeInCM/2;
        int panelDimension = getSceneDimension(this);
        Rectangle r = new Rectangle();
        g.setColor(gridColor);
        for(int i = 0-gridSquareMiddleInCM; i<sceneSizeInCM; i = i +gridRepetitionInCM){
            for(int j = 0-gridSquareMiddleInCM; j < sceneSizeInCM; j = j +gridRepetitionInCM){
                r.setRect(GraphicPanelDrawer.floorConvertCMtoPX(panelDimension, i),
                        GraphicPanelDrawer.floorConvertCMtoPX(panelDimension, j),
                        GraphicPanelDrawer.floorConvertCMtoPX(panelDimension, gridSquareSizeInCM),
                        GraphicPanelDrawer.floorConvertCMtoPX(panelDimension, gridSquareSizeInCM));
                g.draw(r);
            }
        }
    }
    
    public void paintEquipmentInformation(Graphics2D g){
        int i = 0;
        int size = 0;
        String longString = "";
        for (String line  : equipmentInformation.split("\n")){
            if (size<line.length()){
                size = line.length();
                longString = line;
            }
            g.drawString(line, actualMousePoint.x, actualMousePoint.y+(g.getFontMetrics().getHeight()*i));
            i++;
        }
//        g.drawRect(actualMousePoint.x, actualMousePoint.y, g.getFontMetrics().stringWidth(longString), g.getFontMetrics().getHeight()*i);
    }
    

    public boolean toggleGrid(){
        gridIsOn = !gridIsOn;
        repaint();
        return gridIsOn;
    }

    public boolean toggleMagneticGrid(){
        magneticGridIsOn = !magneticGridIsOn;
        return magneticGridIsOn;
    }

    public boolean toggleEquipmentInformation(){
        equipmentInformationIsOn = !equipmentInformationIsOn;
        repaint();
        return equipmentInformationIsOn;
    }

    public StationController findStation(java.awt.Point mousePoint){
        int xCM_Pos = this.floorConvertPXtoCM(getSceneDimension(this),(int)mousePoint.getX());
        int yCM_Pos = this.floorConvertPXtoCM(getSceneDimension(this),(int)mousePoint.getY());
        for (Drawable i : drawables) {
            if(i.isCollision(xCM_Pos, yCM_Pos)){
                if (i instanceof StationController) {
                    return (StationController)i;
                }
            }
        }
        return null;
    }
    
    public ConveyorController findConveyor(java.awt.Point mousePoint){
        int xCM_Pos = this.floorConvertPXtoCM(getSceneDimension(this),(int)mousePoint.getX());
        int yCM_Pos = this.floorConvertPXtoCM(getSceneDimension(this),(int)mousePoint.getY());
        for (Drawable i : drawables) {
            if(i.isCollision(xCM_Pos, yCM_Pos)){
                if (i instanceof ConveyorController) {
                    return (ConveyorController)i;
                }
            }
        }
        return null;
    }

    public Point findPoint(java.awt.Point mousePoint){
        int xCM_Pos = this.floorConvertPXtoCM(getSceneDimension(this),(int)mousePoint.getX());
        int yCM_Pos = this.floorConvertPXtoCM(getSceneDimension(this),(int)mousePoint.getY());
        for (Drawable i : drawables) {
            if(i.isCollision(xCM_Pos, yCM_Pos)){
                if (i instanceof Point) {
                    return (Point)i;
                }
            }
        }
        return null;
    }

    public Drawable findDrawable(java.awt.Point mousePoint){
        int xCM_Pos = this.floorConvertPXtoCM(getSceneDimension(this),(int)mousePoint.getX());
        int yCM_Pos = this.floorConvertPXtoCM(getSceneDimension(this),(int)mousePoint.getY());
        for (Drawable i : drawables) {
            if(i.isCollision(xCM_Pos, yCM_Pos)){
                    return i;
            }
        }
        return null;
    }
    
    public List<Drawable> findAllSelected(){
        List<Drawable> list = new LinkedList();
        for (Drawable i : drawables){
            if(i.getSelected()){
                list.add(i);
            }
        }
        return list;
    }
    
    public void deselectAll(){
        for (Drawable i : drawables){
            if(i.getSelected()){
                i.toggleSelected();
            }
        }
    }

    public static int getSceneDimension(GraphicPanelDrawer graphicPanelDrawer){
        int sceneDimensionX     = (int) Math.ceil(graphicPanelDrawer.getSize().getWidth());
        int sceneDimensionY     = (int) Math.ceil(graphicPanelDrawer.getSize().getHeight());

        int sceneDimension = sceneDimensionX;
        if(sceneDimensionY>sceneDimensionX)
            sceneDimension = sceneDimensionY;
        return sceneDimension;
    }

    static public int floorConvertCMtoPX(int panelDimension, int cmPos){
        return (int) Math.floor(panelDimension * cmPos / sceneSizeInCM);
    }

    static public int ceilConvertCMtoPX(int panelDimension, int cmPos){
        return (int) Math.ceil(panelDimension * cmPos / sceneSizeInCM);
    }

    static public int floorConvertPXtoCM(int panelDimension, int pxPos){
        if((magneticGridIsOn)){
            int posEnCm =  ((int) Math.floor((double)pxPos / (double)panelDimension * sceneSizeInCM));
            return (int) Math.round(((float)posEnCm / (float)gridRepetitionInCM)) *gridRepetitionInCM;
        }
        return (int) Math.floor((double)pxPos / (double)panelDimension * sceneSizeInCM);
    }

    static public float convertPXtoCMnoGrid(int panelDimension, int pxPos){
        return (float)((double)pxPos / (double)panelDimension * sceneSizeInCM);
    }
    
    public void saveImage(){
        //Code est adapté de http://stackoverflow.com/questions/6575578/convert-a-graphics2d-to-an-image-or-bufferedimage
        BufferedImage imageToExport = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = imageToExport.getGraphics();
        this.printAll(g);
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", ".png");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION){
            File saveFile = fileChooser.getSelectedFile();
            String file_name = saveFile.getPath();
            if (!file_name.endsWith(".png")){
                file_name += ".png";
                saveFile= new File(file_name);
            }
            try{
                ImageIO.write(imageToExport, "png", saveFile);
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "An error has occurred. Please try again later.");
            }
        }
    }
    
    public void removeSelectedEquipment(List<Drawable> drawablesToDelete){
        for(Drawable d : drawablesToDelete){
            drawables.remove(d);
        }    
    }
    
    public List<Drawable> getDrawableStationPoints(List<Point> pointToRemove){
        List<Drawable> drawableToRemove = new ArrayList<>();
        for (Point p : pointToRemove){
            for (Drawable d : drawables) {
               if(d == p){
                    drawableToRemove.add(d);
               }
            }
        }  
        return drawableToRemove;
    }
    
    public List<Drawable> getAllDrawables(){
        return drawables;
    }
 }
