package ca.ulaval.glo2004.Domain.Matrix;

import java.io.Serializable;

public class ProductAlreadyThereException extends Exception implements Serializable{
    public ProductAlreadyThereException(String message) {
        super(message);
    }
}
