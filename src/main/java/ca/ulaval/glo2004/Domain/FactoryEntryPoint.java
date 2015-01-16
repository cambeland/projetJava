package ca.ulaval.glo2004.Domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FactoryEntryPoint extends Point implements Deletable, Serializable{

    public FactoryEntryPoint(int posX, int posY) {
        super(posX, posY);
        setColor(Color.green);
    }
    
    @Override
    public void addInputConveyor(Conveyor conveyor) throws CantTakeConveyorInput {
        throw new CantTakeConveyorInput("");
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
    public void validate(){
        if (this.outputConveyor!=null){
            this.outputConveyor.validate();
        }
    }
}
