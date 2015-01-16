package ca.ulaval.glo2004.Domain;
import java.io.Serializable;
import java.util.*;

public class BinPackage implements Serializable{
    private List<ProductHolder> wasteBundle;
    
    public BinPackage(){
       wasteBundle = new ArrayList<ProductHolder>();
    }
    
    public void addProductHolder(ProductHolder productHolder){
       if(!this.find(productHolder.getProduct())){
           wasteBundle.add(productHolder);
       }
    }
    
    public boolean find(Product product){
        for(ProductHolder i : wasteBundle){
            if(i.getProduct() == product){
                return true;
            }
        }
        return false;
    }
    
    public List<ProductHolder> getWasteBundleCopy(){
        List wasteBundleCopy = new ArrayList<ProductHolder>();
        for(ProductHolder product: wasteBundle){
            wasteBundleCopy.add(product);
        }
        return wasteBundleCopy;
    }
   
    public List<ProductHolder> getWasteBundle(){
        return wasteBundle;
    }
    
    public double getPurity(ProductHolder product){
        double totalQuantity = 0;
        for(ProductHolder p : wasteBundle){
            totalQuantity+=p.getQuantity();
        }
        double purity = product.getQuantity()/totalQuantity*100;
        return purity;
    }
    
    
    public String getText(){
        String wasteContent = "";
        if (!wasteBundle.isEmpty()){
            for(ProductHolder p : wasteBundle){
                wasteContent += (String.format("\n%10s\t: %.1fkg/h \tRecuperation: %.1f%%\tPurity: %.1f%%", p.getProductName(),p.getQuantity(),p.getRecuperation(), this.getPurity(p)));
            }
        }
        return wasteContent;
    }

    public void addBinPackage(final BinPackage binToAdd)
    {
        for(ProductHolder pAdd : binToAdd.getWasteBundle())
        {
            Boolean productIsInBin = false;
            for(ProductHolder pThere : wasteBundle)
            {
                if(pAdd.getProductName().equals(pThere.getProductName()))
                {
                    pThere.setQuantity(pAdd.getQuantity() + pThere.getQuantity());
                    pThere.setRecuperation(pAdd.getRecuperation()+pThere.getRecuperation());
                    productIsInBin = true;
                }
            }
            if(!productIsInBin)
            {
                wasteBundle.add(new ProductHolder(new Product(pAdd.getProductName()), pAdd.getQuantity(), pAdd.getRecuperation()));
            }
        }
    }
    
    public BinPackage copyBinPackage(){
        BinPackage binPacakgeCopy = new BinPackage();
        for(ProductHolder product : wasteBundle){
            ProductHolder productHolder = new ProductHolder(product.getProduct(), product.getQuantity(), product.getRecuperation());
            binPacakgeCopy.addProductHolder(productHolder);
        }
        return binPacakgeCopy;
    }
    
    public void updateQuantity(Product product, double quantity){
        for(ProductHolder i : wasteBundle){
            if(i.getProduct() == product){
                double quantityToUpdate = i.getQuantity() + quantity;
                i.setQuantity(quantityToUpdate);
            }
        }
    }
    
    public void replaceProduct(ProductHolder product){
        for(ProductHolder i : wasteBundle){
            if(i.getProduct() == product.getProduct()){
                wasteBundle.remove(i);
                ProductHolder p = new ProductHolder(product.getProduct(), product.getQuantity(), product.getRecuperation());
                wasteBundle.add(product);
            }
        }
    }
}
