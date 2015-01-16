package ca.ulaval.glo2004.Domain.Matrix;


import ca.ulaval.glo2004.Domain.Product;
import ca.ulaval.glo2004.Domain.StationExitPoint;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class ProductSortingHolder implements Serializable{
    public Product product;
    protected List<ExitProductSortingHolder> exitList;

    public ProductSortingHolder(Product product){
        this.product = product;
        exitList = new ArrayList<ExitProductSortingHolder>();
    }

    public ProductSortingHolder(Product product, List<ExitProductSortingHolder> exitList){
        this.product = product;
        this.exitList = exitList;
    }

    public void add(ExitProductSortingHolder exitProductSortingHolder) throws ProductAlreadyThereException{
        for(ExitProductSortingHolder i: exitList){
            if(i.exitPoint==exitProductSortingHolder.exitPoint){
                throw new ProductAlreadyThereException("Product already there.");
            }
        }
        exitList.add(exitProductSortingHolder);
    }

    public void setOrAdd(StationExitPoint exitPoint, double value){
        ExitProductSortingHolder exitProductSortingHolder = null;
        for(ExitProductSortingHolder i: exitList){
            if(i.exitPoint==exitPoint){
                exitProductSortingHolder = i;
                break;
            }
        }
        if((exitProductSortingHolder == null)){
            exitList.add(new ExitProductSortingHolder(exitPoint, value));
        } else if (exitProductSortingHolder != null) {
            exitProductSortingHolder.value = value;
        }
    }

    public boolean contains(StationExitPoint exitPoint){
        for(ExitProductSortingHolder i: exitList){
            if(i.exitPoint==exitPoint){
                return true;
            }
        }
        return false;
    }
}
