package ca.ulaval.glo2004.Domain;

import java.awt.Color;
import java.io.Serializable;

public class StationExitPoint extends Point implements Serializable{
   
    public StationExitPoint(int posX, int posY, Station station) throws CantTakeStation{
        super(posX, posY);
        setStation(station);
        setColor(Color.black);
    }

    @Override
    public void addInputConveyor(Conveyor conveyor) throws CantTakeConveyorInput {
        throw new CantTakeConveyorInput("");
    }

    @Override
    public void setStation(Station station)throws CantTakeStation{
        this.station = station;
    }
    
    @Override
    public void validate(){
        if (this.outputConveyor != null){
            this.outputConveyor.validate();
        }
    }
}