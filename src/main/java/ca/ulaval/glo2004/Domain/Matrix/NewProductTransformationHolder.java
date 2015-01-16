package ca.ulaval.glo2004.Domain.Matrix;

import ca.ulaval.glo2004.Domain.Product;
import java.io.Serializable;

public class NewProductTransformationHolder implements Serializable{
    public Product newProduct;
    public double value;
    public double recuperation;

    public NewProductTransformationHolder(Product newProduct, double value){
        this.newProduct = newProduct;
        this.value = value;
        this.recuperation = 100;
    }
}
