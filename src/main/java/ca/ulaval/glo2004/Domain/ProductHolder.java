package ca.ulaval.glo2004.Domain;

import java.io.Serializable;

public class ProductHolder implements Serializable{
    private Product product;
    private double quantity;
    private double recuperation;
    
    public ProductHolder(Product product, double quantity)
    {
        this.product = product;
        this.quantity = quantity;
        if (quantity == 0){
            this.recuperation = 0;
        }
        else{
            this.recuperation = 100;
        }
    }
    
    public ProductHolder(Product product, double quantity, double recuperation)
    {
        this.product = product;
        this.quantity = quantity;
        if (quantity == 0){
            this.recuperation = 0;
        }
        else{
            this.recuperation = recuperation;
        }
    }
           
    public double getQuantity()
    {
        return this.quantity;
    }
    
    public String getProductName()
    {
        return this.product.toString();
    }
    
    public void setQuantity(double quantity)
    {
        this.quantity = quantity;
    }
    
    public void setName(Product product)
    {
        this.product = product;
    }
    public Product getProduct(){
        return this.product;
    }
    
    public void setRecuperation(double recuperation){
        this.recuperation = recuperation;
    }
    
    public double getRecuperation(){
        return this.recuperation;
    }
}
