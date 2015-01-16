package ca.ulaval.glo2004.Controller;


import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class StationFactory{
    public static List<Drawable> StationFactoryMethod(String name, int posX, int posY, int nbExits){
        List<Drawable> objects = new ArrayList<Drawable>();
        StationController station = new StationController(name, posX, posY);
        objects.add(station);

        try{
            StationEntryPointController entryPoint = new StationEntryPointController("", posX, posY + station.getSizeY()/2, station);
            station.addStationEntryPoint(entryPoint);
            objects.add(entryPoint);
            
            for(int i = 1; i <= nbExits; i++){
                StationExitPointController exitPoint = new StationExitPointController(String.valueOf(i), posX + station.getSizeX() , posY + (station.getSizeY()/(nbExits+1))*(i), station);
                station.addStationExitPoint(exitPoint);
                objects.add(exitPoint);
            }
        }catch (Exception e){
            
        }
        return objects;
    }

    public static int getNbExit(){
        int nbExit = -1;
        try{
            nbExit = Integer.parseInt(JOptionPane.showInputDialog(null, "Desired number of exit?","1"));
            if (nbExit>6){
                throw new MaximumNumberOfExitReached("");
            }
        } catch (MaximumNumberOfExitReached ex){
            nbExit = 5;
            JOptionPane.showMessageDialog(null, "Maximum number of exits reached. Station created with maximum number of exits.");
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Try next time with a valid number.");
        }
        return nbExit;
    }
}

