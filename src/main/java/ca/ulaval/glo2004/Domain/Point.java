package ca.ulaval.glo2004.Domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Point implements Serializable{
    private int posX;
    private int posY;
    protected Station station;
    protected int radius;
    protected String name;
    private Color color;
    protected List<Conveyor> inputConveyors;
    protected Conveyor outputConveyor;
    protected BinPackage binPackage;

    public Point(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
        this.inputConveyors = new ArrayList<Conveyor>();
        this.station = null;
        binPackage = new BinPackage();
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color newColor){
        this.color = newColor;
    }

    public void setOutputConveyor(Conveyor conveyor) throws CantTakeConveyorOutput, ConveyorOutputAlreadySet {
        if(conveyor == null){
            throw new NullPointerException();
        }
        if(this.outputConveyor != null){
            throw new ConveyorOutputAlreadySet("");
        }
        this.outputConveyor = conveyor;
    }

    public void addInputConveyor(Conveyor conveyor) throws CantTakeConveyorInput, ConveyorInputsMaximumReached {
        if(conveyor == null){
            throw new NullPointerException();
        }
        if(this.inputConveyors.size() == 1 ){
            throw new ConveyorInputsMaximumReached("");
        }
        this.inputConveyors.add(conveyor);
    }
    
    public void removeEntryConveyor(Conveyor conveyor){
        ArrayList<Conveyor> conveyorToRemove = new ArrayList<>();
        for(Conveyor i : inputConveyors){
            if(i == conveyor){
                conveyorToRemove.add(i);
            }
        }
        for(Conveyor j : conveyorToRemove){
            inputConveyors.remove(j);
        }
        if(inputConveyors.isEmpty() && outputConveyor == null){
           binPackage = new BinPackage();
        }
//        Update des BinPackages pour junctionPoint suite à un delete.
//        BinPackage resultBin = new BinPackage();
//        else{
//            for(Conveyor i : inputConveyors){
//                resultBin.addBinPackage(i.getBinPackage());
//            }        
//            binPackage = resultBin.copyBinPackage();
//        }
    }
    
    public void removeExitConveyor(Conveyor conveyor){
        outputConveyor = null;
    }
    
    public void changePosition(int dx, int dy){
        this.setPosX(getPosX()+dx);
        this.setPosY(getPosY()+dy);
    }

    public final int getPosX(){
        return posX;
    }

    public final int getPosY(){
        return posY;
    }

    public void setPosX(int posX){
        this.posX = posX;
    }

    public void setPosY(int posY){
        this.posY = posY;
    }

    public void changePositionWithStationImpact(int dx, int dy){
        if(station!=null){
            station.changePosition(dx, dy);
        }else{
            changePosition(dx,dy);
        }
    }

    public final String getName(){
        return this.name;
    }

    public final int getRadius(){
        return this.radius;
    }

    public String getBinPackageInfo(){
        return binPackage.getText();
    }

    public BinPackage getBinPackage(){
        return binPackage;
    }

    public void setBinPackage(BinPackage binPackage){
        this.binPackage = binPackage;
    }
    
    public Conveyor getOutputConveyor(){
        return outputConveyor;
    }
    
    public List<Conveyor> getInputConveyors(){
        return inputConveyors;
    }

    public void setStation(Station sation)throws CantTakeStation{
        throw new CantTakeStation("");
    }

    public Station getStation(){
        return station;
    }
    
    public void validate(){
        this.binPackage = this.inputConveyors.get(0).getBinPackage();
    }
}
