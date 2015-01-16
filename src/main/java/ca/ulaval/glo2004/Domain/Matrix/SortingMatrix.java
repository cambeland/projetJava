package ca.ulaval.glo2004.Domain.Matrix;

import ca.ulaval.glo2004.Domain.Product;
import ca.ulaval.glo2004.Domain.StationExitPoint;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class SortingMatrix implements Serializable{

    private List<ProductSortingHolder> sortingMatrix;

    public SortingMatrix(){
        sortingMatrix = new ArrayList<ProductSortingHolder>();
    }

    private ProductSortingHolder find(Product product){
        ProductSortingHolder productSortingHolder = null;
        for(ProductSortingHolder i : sortingMatrix){
            if(i.product == product){
                productSortingHolder = i;
                break;
            }
        }
        return productSortingHolder;
    }

    public ProductSortingHolder addOrFind(Product product){
        //Trouver si le produit existe déjà
        ProductSortingHolder productSortingHolder = find(product);
        //Sinon le créer
        if(productSortingHolder == null){
            productSortingHolder = new ProductSortingHolder(product);
            sortingMatrix.add(productSortingHolder);
        }
        return productSortingHolder;
    }

    public void add(Product product, StationExitPoint exitPoint, double value) throws ProductAlreadyThereException{
        ProductSortingHolder productSortingHolder = addOrFind(product);
        productSortingHolder.add(new ExitProductSortingHolder(exitPoint, value));
    }

    public void set(Product product, StationExitPoint exitPoint, double value) {
        ProductSortingHolder productSortingHolder = addOrFind(product);
        productSortingHolder.setOrAdd(exitPoint, value); 
    }

    public List<Product> getProducts(){
        List<Product> p = new ArrayList<Product>();
        for(ProductSortingHolder i : sortingMatrix){
            p.add(i.product);
        }
        return p;
    }

    public List<StationExitPoint> getExits(){
        List<StationExitPoint> p = new ArrayList<StationExitPoint>();
        for(ProductSortingHolder i : sortingMatrix){
            for(ExitProductSortingHolder j: i.exitList){
                if(!p.contains(j.exitPoint)){
                    p.add(j.exitPoint);
                }
            }
        }
        return p;
    }

    public double getValue(Product product, StationExitPoint exitPoint){
        ProductSortingHolder productSortingHolder = find(product);
        if(productSortingHolder != null){
            for(ExitProductSortingHolder exitProductSortingHolder : productSortingHolder.exitList){
                if(exitProductSortingHolder.exitPoint==exitPoint){
                    return exitProductSortingHolder.value;
                }
            }
        }
        return 0;
    }

    public void verify(List<StationExitPoint> stationExitPoints) {
        for (StationExitPoint exitPoint : stationExitPoints){
            for (ProductSortingHolder i : sortingMatrix) {
                if (!i.contains(exitPoint)) {
                    i.setOrAdd(exitPoint, 0);
                }
            }
        }
    }
    public List<ProductSortingHolder> getProductSortingHolders(){
        return this.sortingMatrix;
    }
}
