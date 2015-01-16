package ca.ulaval.glo2004.Controller;

import ca.ulaval.glo2004.Domain.CantTakeConveyorInput;
import ca.ulaval.glo2004.Domain.Conveyor;
import ca.ulaval.glo2004.Domain.ConveyorInputsMaximumReached;
import java.awt.*;

public class JunctionConveyorPointController extends JunctionPointController {
    public JunctionConveyorPointController(String name, int posX, int posY){
        super(name, posX,posY, 8);
        setColor(Color.black);
    }
    @Override
    public void addInputConveyor(Conveyor conveyor) throws CantTakeConveyorInput, ConveyorInputsMaximumReached {
        if(conveyor == null){
            throw new NullPointerException();
        }
        if(this.inputConveyors.size() == 1 ){
            throw new ConveyorInputsMaximumReached("");
        }
        this.inputConveyors.add(conveyor);
    }
    
}
