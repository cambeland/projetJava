package ca.ulaval.glo2004.Domain;

import java.util.List;

public interface Deletable {
    public List<Point> delete() throws DeleteEquipmentBeforeConveyorException;
}
