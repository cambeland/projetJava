package ca.ulaval.glo2004.Domain.Matrix;

import ca.ulaval.glo2004.Domain.Product;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class TransformationMatrix implements Serializable{
    
    private List<ProductTransformationHolder> transformationMatrix;
            
    public TransformationMatrix(){
        transformationMatrix = new ArrayList<ProductTransformationHolder>();
    }

    private ProductTransformationHolder find(Product product){
        ProductTransformationHolder productTransformationHolder = null;
        for(ProductTransformationHolder i : transformationMatrix){
            if(i.product == product){
                productTransformationHolder = i;
                break;
            }
        }
        return productTransformationHolder;
    }

    public ProductTransformationHolder addOrFind(Product product){
        //Trouver si le produit existe déjà
        ProductTransformationHolder productTransformationHolder = find(product);
        //Sinon le créer
        if(productTransformationHolder == null){
            productTransformationHolder = new ProductTransformationHolder(product);
            transformationMatrix.add(productTransformationHolder);
        }
        return productTransformationHolder;
    }

    public void add(Product product, Product newProduct, double value) throws ProductAlreadyThereException{
        ProductTransformationHolder productTransformationHolder = addOrFind(product);
        productTransformationHolder.add(new NewProductTransformationHolder(newProduct, value));
    }

    public void set(Product product, Product newProduct, double value) {
        ProductTransformationHolder productTransformationHolder = addOrFind(product);
        productTransformationHolder.setOrAdd(newProduct, value);
    }
    
    public List<Product> getProducts(){
        List<Product> p = new ArrayList<Product>();
        for(ProductTransformationHolder i : transformationMatrix){
            p.add(i.product);
        }
        return p;
    }
    
    public List<Product> getNewProducts(){
        List<Product> p = new ArrayList<Product>();
        for(ProductTransformationHolder i : transformationMatrix){
            for(NewProductTransformationHolder j: i.transformationList){
                if(!p.contains(j.newProduct)){
                    p.add(j.newProduct);
                }
            }
        }
        return p;
    }

    public double getValue(Product product, Product newProduct){
        ProductTransformationHolder productTransformationHolder = find(product);
        if(productTransformationHolder != null){
            for(NewProductTransformationHolder newProductTransformation: productTransformationHolder.transformationList){
                if(newProductTransformation.newProduct==newProduct){
                    return newProductTransformation.value;
                }
            }
        }
        return 0;
    }
}
