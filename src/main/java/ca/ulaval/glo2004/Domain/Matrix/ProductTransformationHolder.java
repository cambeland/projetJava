package ca.ulaval.glo2004.Domain.Matrix;


import ca.ulaval.glo2004.Domain.Product;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class ProductTransformationHolder implements Serializable{
    public Product product;
    protected List<NewProductTransformationHolder> transformationList;
    
    public ProductTransformationHolder(Product product){
        this.product = product;
        transformationList = new ArrayList<NewProductTransformationHolder>();
    }

    public ProductTransformationHolder(Product product, List<NewProductTransformationHolder> transformationList){
        this.product = product;
        this.transformationList = transformationList;
    }

    public void add(NewProductTransformationHolder newProductTransformationHolder) throws ProductAlreadyThereException{
        for(NewProductTransformationHolder i: transformationList){
            if(i.newProduct==newProductTransformationHolder.newProduct){
                throw new ProductAlreadyThereException("Product already there.");
            }
        }
        transformationList.add(newProductTransformationHolder);
    }

    public void setOrAdd(Product product, double value){
        NewProductTransformationHolder newProductTransformationHolder = null;
        for(NewProductTransformationHolder i: transformationList){
            if(i.newProduct==product){
                newProductTransformationHolder = i;
                break;
            }
        }
        if((newProductTransformationHolder == null) && (value != 0) ){
            transformationList.add(new NewProductTransformationHolder(product, value));
        } else if (newProductTransformationHolder != null) {
            newProductTransformationHolder.value = value;
        }
    }
}
