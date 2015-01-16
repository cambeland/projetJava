package ca.ulaval.glo2004.Domain;

import java.awt.Color;
import java.io.Serializable;

public class StationEntryPoint extends Point implements Serializable{

    public StationEntryPoint(int posX, int posY, Station station) throws CantTakeStation{
        super(posX, posY);
        setStation(station);
        setColor(Color.white);
    }

    @Override
    public void setOutputConveyor(Conveyor conveyor) throws CantTakeConveyorOutput, ConveyorOutputAlreadySet {
        throw new CantTakeConveyorOutput("");
    }

    @Override
    public void setStation(Station station)throws CantTakeStation{
        this.station = station;
    }
    
    @Override
    public void validate(){
        if (this.inputConveyors.isEmpty()){
            this.binPackage = this.inputConveyors.get(0).getBinPackage();
        }
    }
}
