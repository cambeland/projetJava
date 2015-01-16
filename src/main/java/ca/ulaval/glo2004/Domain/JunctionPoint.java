package ca.ulaval.glo2004.Domain;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JunctionPoint extends Point implements Deletable, Serializable{
    private List<Conveyor> incomingConveyorsList; //inutilisé?
    
    public JunctionPoint(int posX, int posY){
        super(posX, posY);
        setColor(Color.yellow);
    }

    @Override
    public void addInputConveyor(Conveyor conveyor) throws CantTakeConveyorInput, ConveyorInputsMaximumReached {
        if(conveyor == null ){
            throw new NullPointerException();
        }
        if(!this.inputConveyors.contains(conveyor) ){
            this.inputConveyors.add(conveyor);
        }
    }
    
    @Override
    public List<Point> delete() throws DeleteEquipmentBeforeConveyorException{
        
        if(!inputConveyors.isEmpty() || outputConveyor != null){
            throw new DeleteEquipmentBeforeConveyorException("");
        }
        binPackage = new BinPackage();
        return new ArrayList<Point>();
    }
    
    @Override
    public void setBinPackage(BinPackage binPackage){
        this.binPackage = binPackage;
        if(inputConveyors.size() > 1){
            Conveyor conveyor = inputConveyors.get(inputConveyors.size() - 1);
            this.binPackage.addBinPackage(conveyor.getBinPackage());    
        }
    }
    @Override
    public void validate(){
        if (!inputConveyors.isEmpty()){
            this.binPackage = inputConveyors.get(0).getBinPackage().copyBinPackage();
            if(inputConveyors.size() > 1){
                Conveyor conveyor = inputConveyors.get(inputConveyors.size() - 1);
                this.binPackage.addBinPackage(conveyor.getBinPackage().copyBinPackage());
            }
            this.outputConveyor.validate();
        }
    }
}

