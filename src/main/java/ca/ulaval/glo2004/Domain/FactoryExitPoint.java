package ca.ulaval.glo2004.Domain;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class FactoryExitPoint extends Point implements Deletable, Serializable{
    public FactoryExitPoint(int posX, int posY) {
        super(posX, posY);
    }

    @Override
    public void setOutputConveyor(Conveyor conveyor) throws CantTakeConveyorOutput, ConveyorOutputAlreadySet {
        throw new CantTakeConveyorOutput("");
    }
    
    @Override
    public List<Point> delete() throws DeleteEquipmentBeforeConveyorException{
        
        if(!inputConveyors.isEmpty() || outputConveyor != null){
            throw new DeleteEquipmentBeforeConveyorException("");
        }
        binPackage = new BinPackage();
        return new ArrayList<Point>();
    }
    
    public void validate(){
        if (!this.inputConveyors.isEmpty()){
            this.binPackage = this.inputConveyors.get(0).getBinPackage();
        }
    }
}
