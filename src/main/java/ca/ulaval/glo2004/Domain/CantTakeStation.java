package ca.ulaval.glo2004.Domain;

public class CantTakeStation extends Exception {
    public CantTakeStation(String message) {
        super(message);
    }
}