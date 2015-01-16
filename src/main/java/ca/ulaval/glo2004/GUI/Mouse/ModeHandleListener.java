package ca.ulaval.glo2004.GUI.Mouse;

import ca.ulaval.glo2004.GUI.AppMasterFrame;
import ca.ulaval.glo2004.GUI.AppMasterFrame.ApplicationMode;
import ca.ulaval.glo2004.Drawing.GraphicPanelDrawer;
import ca.ulaval.glo2004.GUI.StateBarPanel;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ModeHandleListener extends MouseAdapter {
    
    private final AppMasterFrame masterFrame;
    private final StateBarPanel stateBarPanel;
    private Point mousePt;
    
    public ModeHandleListener(AppMasterFrame masterFrame, StateBarPanel stateBarPanel){
        this.masterFrame = masterFrame;
        this.stateBarPanel = stateBarPanel;
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
        masterFrame.actualMousePoint = e.getPoint();
        Point mousePoint = e.getPoint();
        if(e.getClickCount()==2){
            processDoubleClick(mousePoint);
        } else{
            //single click is executed before double click
            processSingleClick(mousePoint);
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e){
        Point mousePoint = e.getPoint();
        updateCursorLocation(mousePoint);
        ApplicationMode actualMode = masterFrame.getActualMode();
        switch (actualMode) {
            case ADD_CONVEYOR:
                break;
            case ADD_ENTRY_POINT:
                break;
            case ADD_EXIT_POINT:
                break;
            case ADD_JUNCTION_POINT:
                break;
            case ADD_STATION:
                break;
            case SELECT:
                masterFrame.getGraphicalInterfaceController().getEquipmentInformation(mousePoint);
                break;
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e){
        mousePt = e.getPoint();
    }
    
    @Override
    public void mouseDragged(MouseEvent e){
        masterFrame.saveToUndo();
        Point mousePoint = e.getPoint();
        int dx = mousePoint.x - mousePt.x;
        int dy = mousePoint.y - mousePt.y;
        mousePt = mousePoint;
        masterFrame.getGraphicalInterfaceController().moveEquipment(dx, dy);
    }
    
    private void updateCursorLocation(Point mousePoint){
        //TODO refactor first line
        int panelDimension = GraphicPanelDrawer.getSceneDimension(masterFrame.getGraphicalInterfaceController().getGraphicPanelDrawer());
        double posXinM = (GraphicPanelDrawer.convertPXtoCMnoGrid(panelDimension, (int)mousePoint.x)/100);
        double posYinM = (GraphicPanelDrawer.convertPXtoCMnoGrid(panelDimension, (int)mousePoint.y)/100);
        stateBarPanel.setDimensionText(posXinM,posYinM);
    }

    private void processSingleClick(Point mousePoint){
        ApplicationMode actualMode = masterFrame.getActualMode();
        switch (actualMode) {
            case ADD_CONVEYOR:
                masterFrame.saveToUndo();
                masterFrame.getGraphicalInterfaceController().selectPoint(mousePoint);
                break;
            case ADD_ENTRY_POINT:
                masterFrame.saveToUndo();
                masterFrame.getGraphicalInterfaceController().addFactoryEntryPoint(mousePoint);
                break;
            case ADD_EXIT_POINT:
                masterFrame.saveToUndo();
                masterFrame.getGraphicalInterfaceController().addExitPoint(mousePoint);
                break;
            case ADD_JUNCTION_POINT:
                masterFrame.saveToUndo();
                masterFrame.getGraphicalInterfaceController().addJunctionPoint(mousePoint);
                break;
            case ADD_STATION:
                masterFrame.saveToUndo();
                masterFrame.getGraphicalInterfaceController().constructStation(mousePoint);
                break;
            case SELECT:
                masterFrame.getGraphicalInterfaceController().isSelected(mousePoint);
                break;
        }
    }

    private void processDoubleClick(Point mousePoint){
        ApplicationMode actualMode = masterFrame.getActualMode();
        switch (actualMode) {
            case ADD_CONVEYOR:
                break;
            case ADD_ENTRY_POINT:
                break;
            case ADD_EXIT_POINT:
                break;
            case ADD_JUNCTION_POINT:
                break;
            case ADD_STATION:
                break;
            case SELECT:
                masterFrame.saveToUndo();//TODO Why the fucking hell
                masterFrame.getGraphicalInterfaceController().editEquipment(mousePoint);
                break;
        }
    }
}
