package ca.ulaval.glo2004.RecyclApp;

import ca.ulaval.glo2004.Controller.*;
import javax.swing.UIManager;


public class App {
    public static void main (String[] args){
        setMacOsNativeUI();
        new GraphicalInterfaceController();
    }

    private static void setMacOsNativeUI(){
        try {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Test");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}