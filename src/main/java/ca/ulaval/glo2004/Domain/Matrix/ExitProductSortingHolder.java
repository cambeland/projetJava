package ca.ulaval.glo2004.Domain.Matrix;

import ca.ulaval.glo2004.Domain.StationExitPoint;
import java.io.Serializable;

public class ExitProductSortingHolder implements Serializable{
    public StationExitPoint exitPoint;
    public double value;

    public ExitProductSortingHolder(StationExitPoint exitPoint, double value){
        this.exitPoint = exitPoint;
        this.value = value;
    }
}
