package ca.ulaval.glo2004.Controller;

import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

import ca.ulaval.glo2004.Domain.*;
import ca.ulaval.glo2004.Drawing.GraphicPanelDrawer;
import ca.ulaval.glo2004.GUI.Adapter.GraphicPanelRedrawer;
import ca.ulaval.glo2004.GUI.AppMasterFrame;
import ca.ulaval.glo2004.GUI.EditionFrame.StationEditionFrame;
import ca.ulaval.glo2004.GUI.EditionFrame.ConveyorEditionFrame;
import ca.ulaval.glo2004.GUI.EditionFrame.FactoryEntryPointEditionJFrame;
import ca.ulaval.glo2004.Domain.Point;
import java.io.Serializable;

public class GraphicalInterfaceController implements Serializable{
    private GraphicPanelDrawer graphicPanelDrawer;
    private Dimension initialDimension = new Dimension(1000, 1000);
    private Point previousSelectedPoint = null;

    public GraphicalInterfaceController(){
        graphicPanelDrawer = new GraphicPanelDrawer();
        AppMasterFrame appMasterFrame = new AppMasterFrame(this, graphicPanelDrawer);

        // The panel now listen to mouse actions
        appMasterFrame.initMouseInteractions(graphicPanelDrawer);
 
        graphicPanelDrawer.setPreferredSize(initialDimension);
        appMasterFrame.pack();
        appMasterFrame.setSize(new Dimension(2000, 2000));
        appMasterFrame.setVisible(true);
        appMasterFrame.saveNewProjet();
    }

    public void constructStation(java.awt.Point mousePoint){
        int sceneDimension = graphicPanelDrawer.getSceneDimension(graphicPanelDrawer);
        int xCM_Pos = graphicPanelDrawer.floorConvertPXtoCM(sceneDimension, (int) mousePoint.getX());
        int yCM_Pos = graphicPanelDrawer.floorConvertPXtoCM(sceneDimension,(int)mousePoint.getY());
        int nbExit = StationFactory.getNbExit();
        if(nbExit >= 0) {
            List<Drawable> objects = StationFactory.StationFactoryMethod("", xCM_Pos, yCM_Pos, nbExit);
            for (Drawable object : objects) {
                graphicPanelDrawer.add(object);
            }
        }
        graphicPanelDrawer.repaint();
    }

    public void addExitPoint(java.awt.Point mousePoint){
        StationController stationController = graphicPanelDrawer.findStation(mousePoint);
        if(stationController != null){
            addStationExitPoint(mousePoint, stationController);
        }
        else{
            addFactoryExitPoint(mousePoint);
        }
    }

    private StationExitPointController addStationExitPoint(java.awt.Point mousePoint, StationController stationController){
        int sceneDimension = graphicPanelDrawer.getSceneDimension(graphicPanelDrawer);
        int xCM_Pos = graphicPanelDrawer.floorConvertPXtoCM(sceneDimension,(int)mousePoint.getX());
        int yCM_Pos = graphicPanelDrawer.floorConvertPXtoCM(sceneDimension,(int)mousePoint.getY());
        StationExitPointController object = null;
        Line2D nearestBorder = stationController.findClosestBorderFromPointPosition(xCM_Pos, yCM_Pos);
        java.awt.Point closestPoint = stationController.getClosestPointOnBorder(nearestBorder, xCM_Pos, yCM_Pos);
        String number = stationController.getExitNumber();
        try{
            if (!stationController.getExitFull()){
                 object = new StationExitPointController(number, closestPoint.x , closestPoint.y, stationController);
                stationController.addStationExitPoint(object);
                graphicPanelDrawer.add(object);
            }
            else
                throw new RuntimeException("");
            
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Maximum number of exit reached");
        }
        graphicPanelDrawer.repaint();
        return object;
    }

    public void addFactoryEntryPoint(java.awt.Point mousePoint){
        int sceneDimension = graphicPanelDrawer.getSceneDimension(graphicPanelDrawer);
        int xCM_Pos = graphicPanelDrawer.floorConvertPXtoCM(sceneDimension, (int) mousePoint.getX());
        int yCM_Pos = graphicPanelDrawer.floorConvertPXtoCM(sceneDimension, (int) mousePoint.getY());
        if (graphicPanelDrawer.findStation(mousePoint) == null) {
            FactoryEntryPointController object = new FactoryEntryPointController("", xCM_Pos, yCM_Pos);
            graphicPanelDrawer.add(object);
            graphicPanelDrawer.repaint();
        }
    }
   
    private void addFactoryExitPoint(java.awt.Point mousePoint){
        int sceneDimension = graphicPanelDrawer.getSceneDimension(graphicPanelDrawer);
        int xCM_Pos = graphicPanelDrawer.floorConvertPXtoCM(sceneDimension,(int)mousePoint.getX());
        int yCM_Pos = graphicPanelDrawer.floorConvertPXtoCM(sceneDimension, (int) mousePoint.getY());
        if (graphicPanelDrawer.findStation(mousePoint) != null){
            addStationExitPoint(mousePoint, graphicPanelDrawer.findStation(mousePoint));
        } 
        else{
            FactoryExitPointController object = new FactoryExitPointController("", xCM_Pos, yCM_Pos);
            graphicPanelDrawer.add(object);
            graphicPanelDrawer.repaint();
        }
    }
    
    public void addJunctionPoint(java.awt.Point mousePoint){
        ConveyorController conveyor = graphicPanelDrawer.findConveyor(mousePoint);
        int sceneDimension = graphicPanelDrawer.getSceneDimension(graphicPanelDrawer);
        int xCM_Pos = graphicPanelDrawer.floorConvertPXtoCM(sceneDimension,(int)mousePoint.getX());
        int yCM_Pos = graphicPanelDrawer.floorConvertPXtoCM(sceneDimension, (int) mousePoint.getY());
        if(conveyor != null){
            addJunctionConveyorPointController(conveyor, xCM_Pos, yCM_Pos);
        }else{
            JunctionPointController object = new JunctionPointController("", xCM_Pos , yCM_Pos);
            graphicPanelDrawer.add(object);
        }
        graphicPanelDrawer.repaint();
    }

    private void addJunctionConveyorPointController(ConveyorController conveyor,int xCM_Pos ,int yCM_Pos){
        JunctionPointController object = new JunctionConveyorPointController("", xCM_Pos , yCM_Pos);
        graphicPanelDrawer.add(object);
        List<Drawable> conveyorList = new ArrayList<Drawable>();
        conveyorList.add(conveyor);
        conveyor.delete();
        graphicPanelDrawer.removeSelectedEquipment(conveyorList);
        try {
            ConveyorController conveyorController = new ConveyorController("", conveyor.getEntryPoint(), object);
            graphicPanelDrawer.add(conveyorController);
            conveyorController = new ConveyorController("", object, conveyor.getExitPoint());
            graphicPanelDrawer.add(conveyorController);
        }catch (Exception e){

        }
    }

    public void addConveyor(Point source, Point destination){
        if(source == destination){
            Toolkit.getDefaultToolkit().beep();
        } else {
            try {
                ConveyorController conveyorController = new ConveyorController("", source, destination);
                graphicPanelDrawer.add(conveyorController);
                graphicPanelDrawer.repaint();
                validate();
            }catch (CantTakeConveyorInput e){
                JOptionPane.showMessageDialog(graphicPanelDrawer, "This input point can't be an entry for the conveyor.");
            }catch (CantTakeConveyorOutput e){
                JOptionPane.showMessageDialog(graphicPanelDrawer, "This exit point can't be an input for the conveyor.");
            }catch (ConveyorInputsMaximumReached e){
                JOptionPane.showMessageDialog(graphicPanelDrawer, "This input point already has a connected conveyor.");
            }catch (ConveyorOutputAlreadySet e){
                JOptionPane.showMessageDialog(graphicPanelDrawer, "This output point already has a connected conveyor.");
            }
        }
    }

    public void selectPoint(java.awt.Point mousePoint){
        Point pointToConnectConveyor = graphicPanelDrawer.findPoint(mousePoint);
        if(previousSelectedPoint == null){
            previousSelectedPoint = pointToConnectConveyor;
            //can be set to null again if not found
        } else {
            if(pointToConnectConveyor != null){
                addConveyor(previousSelectedPoint, pointToConnectConveyor);
                previousSelectedPoint = null;
            }
        }
    }

    public void resetPreviousSelection(){
        previousSelectedPoint = null;
    }

    public void editEquipment(java.awt.Point mousePoint) {
        StationController stationController = graphicPanelDrawer.findStation(mousePoint);
        ConveyorController conveyorController = graphicPanelDrawer.findConveyor(mousePoint);
        Point factoryEntryPoint = graphicPanelDrawer.findPoint(mousePoint);
        if (stationController != null){
            StationEditionFrame stationEditionFrame = new StationEditionFrame(stationController);
            stationEditionFrame.addWindowListener(new GraphicPanelRedrawer(graphicPanelDrawer));
            stationEditionFrame.setVisible(true);
        }
        else if(conveyorController != null){
            ConveyorEditionFrame conveyorEditionFrame = new ConveyorEditionFrame(conveyorController);
            conveyorEditionFrame.addWindowListener(new GraphicPanelRedrawer(graphicPanelDrawer));
            conveyorEditionFrame.setVisible(true);
        }
        else if(factoryEntryPoint != null && factoryEntryPoint instanceof FactoryEntryPoint){
            FactoryEntryPointEditionJFrame factoryEntryPointEditionJFrame = new FactoryEntryPointEditionJFrame(factoryEntryPoint);
            factoryEntryPointEditionJFrame.addWindowListener(new GraphicPanelRedrawer(graphicPanelDrawer));
            factoryEntryPointEditionJFrame.setVisible(true);
            
        }
        validate();
    }
    
    public void moveEquipment(int dx, int dy){
        int sceneDimension = graphicPanelDrawer.getSceneDimension(graphicPanelDrawer);
        int newDx = graphicPanelDrawer.floorConvertPXtoCM(sceneDimension, dx);
        int newDy = graphicPanelDrawer.floorConvertPXtoCM(sceneDimension,dy);
        java.util.List<Drawable> list = graphicPanelDrawer.findAllSelected();
        
        for (Drawable i: list){
            if (i instanceof Movable){
                Movable pointController = (Movable) i;
                pointController.changePosition(newDx, newDy);
            }
        }
        graphicPanelDrawer.repaint();
    }

    public void setScale(double newScale){
        Dimension newPreferredSize = new Dimension((int)(initialDimension.width*newScale), (int)(initialDimension.height*newScale));
        graphicPanelDrawer.setSize((int)(initialDimension.width*newScale), (int)(initialDimension.height*newScale));
        graphicPanelDrawer.setPreferredSize(newPreferredSize);
        graphicPanelDrawer.revalidate();
        graphicPanelDrawer.repaint();
    }
    
    public GraphicPanelDrawer getGraphicPanelDrawer(){
        return graphicPanelDrawer;
    }
    
    public void setGraphicPanelDrawer(GraphicPanelDrawer drawer){
        graphicPanelDrawer = drawer;
    }
    
    public void isSelected(java.awt.Point mousePoint){
        Drawable d = graphicPanelDrawer.findDrawable(mousePoint);
        if(d!=null){
            d.toggleSelected();
        }
        else{
            graphicPanelDrawer.deselectAll();
        }
        graphicPanelDrawer.repaint();
    }
    
    public String getEquipmentInformation(java.awt.Point mousePoint){
       ConveyorController conveyorController = graphicPanelDrawer.findConveyor(mousePoint);
       StationController stationController = graphicPanelDrawer.findStation(mousePoint);
       Point point = graphicPanelDrawer.findPoint(mousePoint);
       String information = new String();
        if (conveyorController != null) {
            information = conveyorController.getBinPackageInfo();
            graphicPanelDrawer.toggleEquipmentInformation();
            graphicPanelDrawer.setEquipmentInformation(information);
            graphicPanelDrawer.setActualMousePoint(mousePoint);
        }
        if (stationController != null){
            //information = stationController.getBinPackageInfo();
            graphicPanelDrawer.toggleEquipmentInformation();
            graphicPanelDrawer.setEquipmentInformation(information);
            graphicPanelDrawer.setActualMousePoint(mousePoint);
        }
        if (point != null){
            information = point.getBinPackageInfo();
            graphicPanelDrawer.toggleEquipmentInformation();
            graphicPanelDrawer.setEquipmentInformation(information);
            graphicPanelDrawer.setActualMousePoint(mousePoint);
        }
        return information;
    }
    
    public void deleteSelectedEquipment(){
        List<Drawable> selectedDrawables = graphicPanelDrawer.findAllSelected();
        if(selectedDrawables.size() > 1){
            JOptionPane.showMessageDialog(graphicPanelDrawer, "You can only delete one element at a time");
        }
        else{
            List<Drawable> drawablesToDelete = new ArrayList<Drawable>();
            for(Drawable d : selectedDrawables){
                if(d instanceof Deletable){
                    drawablesToDelete.add(d);
                    Deletable deletable = (Deletable)d;
                    try{
                        List<Point> pointsToDelete = deletable.delete();
                        List<Drawable> drawablePointsToDelete = graphicPanelDrawer.getDrawableStationPoints(pointsToDelete);
                        for(Drawable d2 : drawablePointsToDelete){
                            drawablesToDelete.add(d2);
                        }
                        if(drawablesToDelete.size() == 0){
                            JOptionPane.showMessageDialog(graphicPanelDrawer, "Nothing to delete.");
                        }
                        graphicPanelDrawer.removeSelectedEquipment(drawablesToDelete);
                        graphicPanelDrawer.repaint();
                        validate();
                    }catch (DeleteEquipmentBeforeConveyorException e){
                        JOptionPane.showMessageDialog(graphicPanelDrawer, "Please delete connected conveyors before deleting the Equipment");
                    }      
                }
            }  
        }
    }
    
    public void validate(){
        for (Drawable i : graphicPanelDrawer.getAllDrawables()){
             if (i instanceof FactoryEntryPointController){
                 FactoryEntryPointController p = (FactoryEntryPointController) i;
                 p.validate();
             }
        }
        for (Drawable i : graphicPanelDrawer.getAllDrawables()){
            if (i instanceof StationController){
                StationController p = (StationController) i;
                p.validate();
             }
        }
    }
}
