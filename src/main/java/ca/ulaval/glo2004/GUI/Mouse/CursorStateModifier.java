package ca.ulaval.glo2004.GUI.Mouse;

import ca.ulaval.glo2004.GUI.AppMasterFrame;

import java.awt.Cursor;

public class CursorStateModifier {

    public static void addCursorModifier(AppMasterFrame masterFrame){
        Cursor addStationCursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
            masterFrame.setCursor(addStationCursor);
    }

    public static void addConveyorCursorModifier(AppMasterFrame masterFrame){
        Cursor addConveyorCursor = new Cursor(Cursor.E_RESIZE_CURSOR);
        masterFrame.setCursor(addConveyorCursor);
    }

    public static void selectCursorModifier(AppMasterFrame masterFrame){
        Cursor addConveyorCursor = new Cursor(Cursor.DEFAULT_CURSOR);
        masterFrame.setCursor(addConveyorCursor);
    }

}
