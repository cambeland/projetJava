package ca.ulaval.glo2004.Domain;

import ca.ulaval.glo2004.Controller.Movable;
import ca.ulaval.glo2004.Domain.Matrix.SortingMatrix;
import ca.ulaval.glo2004.Domain.Matrix.TransformationMatrix;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Station extends Equipment implements Imageable, Deletable, Movable, Serializable{
    
    protected int posX;
    protected int posY;
    protected int sizeX;
    protected int sizeY;
    protected StationEntryPoint stationEntryPoint;
    protected List<StationExitPoint> stationExitPoints;
    private List<BinPackage> outputBinPackage;
    protected int outputNumber;
    private TransformationMatrix transformationMatrix;
    private SortingMatrix sortingMatrix;
    protected String imagePath;

    
    public Station(){
        this.transformationMatrix = new TransformationMatrix();
        this.stationExitPoints = new ArrayList<StationExitPoint>();
        this.outputBinPackage = new ArrayList<BinPackage>();
        color = Color.BLACK;
        name = "";
        capacity = Double.MAX_VALUE;
        outputNumber = 1;



        transformationMatrix = new TransformationMatrix();

        sortingMatrix = new SortingMatrix();
    }
    
    public void setOutputNumber(int outputNumber) {
        this.outputNumber = outputNumber;
    }
    
    public void setOutputBinPackage(BinPackage binPackage){
        for(StationExitPoint exit: stationExitPoints)
        {    
            BinPackage binOutputPackage = new BinPackage();
            for (Product product : sortingMatrix.getProducts()) 
            {
                for (ProductHolder productHolder : binPackage.getWasteBundle()) 
                {
                    if (productHolder.getProduct() == product) {
                        double valueSorting = sortingMatrix.getValue(product, exit);
                        double realValue = valueSorting * productHolder.getQuantity() /100;
                        double recup = productHolder.getRecuperation() *valueSorting/100;
                        if (realValue > 0){
                            ProductHolder productHolderOutput = new ProductHolder(product, realValue, recup);
                            binOutputPackage.addProductHolder(productHolderOutput);
                        }
                    }
                }
            }
        exit.setBinPackage(binOutputPackage);
        }
        for (StationExitPoint exit : stationExitPoints){
            exit.validate();
        }
    }
    
    public int getOutputNumber() {
        return outputNumber;
    }
    
    public TransformationMatrix getTransformationMatrix(){
        BinPackage binPackage;
        if(stationEntryPoint==null){
            binPackage = new BinPackage();
        }
        else {
            binPackage = stationEntryPoint.getBinPackage();
        }
        
        for (ProductHolder i : binPackage.getWasteBundle()){
            transformationMatrix.addOrFind(i.getProduct());
        }
        return transformationMatrix;
    }

    public SortingMatrix getSortingMatrix(){

        for (Product i : transformationMatrix.getProducts()){
            sortingMatrix.addOrFind(i);
        }
        for (Product i : transformationMatrix.getNewProducts()){
            sortingMatrix.addOrFind(i);     
        }
        sortingMatrix.verify(stationExitPoints);

        return sortingMatrix;
    }

    public void addStationEntryPoint(StationEntryPoint entryPoint){
        if (stationEntryPoint == null) {
            stationEntryPoint = entryPoint;
        }
        //TODO else throw error
    }

    public void addStationExitPoint(StationExitPoint exitPoint){
        stationExitPoints.add(exitPoint);
    }

    protected void moveStation(int dx, int dy){
        this.posX = posX+dx;
        this.posY = posY+dy;
        if(stationEntryPoint!=null){
            stationEntryPoint.changePosition(dx, dy);
        }
        for(Point point: stationExitPoints){
            point.changePosition(dx, dy);
        }
    }

    public void updateExitPoints(){
        BinPackage binPackage = stationEntryPoint.binPackage.copyBinPackage();
        //Todo corrections
        List<Product> newProducts = transformationMatrix.getNewProducts();
        System.out.print(newProducts.toString());
        for(ProductHolder productHolder: binPackage.getWasteBundleCopy()){
            double totalProductUsed = 0;
            for(Product newProduct: newProducts){
                System.out.println(productHolder.toString());
                double qt = productHolder.getQuantity() * transformationMatrix.getValue(productHolder.getProduct(), newProduct);
                totalProductUsed += qt;
                if (binPackage.find(newProduct)){
                    binPackage.updateQuantity(newProduct, qt);
                }
                else{
                    binPackage.addProductHolder(new ProductHolder(newProduct, qt));
                }
            }
            double updatedQuantity = productHolder.getQuantity() - totalProductUsed;
            double recuperation = updatedQuantity / productHolder.getQuantity() * productHolder.getRecuperation();
            System.out.println(recuperation);
            productHolder.setQuantity(updatedQuantity);
            productHolder.setRecuperation(recuperation);
        }
        
        setOutputBinPackage(binPackage);
    }

    public final int getSizeX(){
        return sizeX;
    }

    public final int getSizeY(){
        return sizeY;
    }
    
    @Override
    public ArrayList<Point> delete() throws DeleteEquipmentBeforeConveyorException{
        ArrayList<Point> toDelete = new ArrayList<Point>();
        List<Conveyor> inputConveyor = stationEntryPoint.getInputConveyors();
        for(Point point : stationExitPoints){
            if(point.getOutputConveyor() != null || !inputConveyor.isEmpty()){
                throw new DeleteEquipmentBeforeConveyorException("");
            }
        }
        toDelete.add(stationEntryPoint);
        toDelete.addAll(stationExitPoints); 
        return toDelete;
    }
    
    @Override
    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
    }

    @Override
    public String getImagePath(){
        return this.imagePath;
    }

    @Override
    public void changePosition(int dx, int dy){
        moveStation(dx, dy);
    }
    
    public void setBinPackage(BinPackage binPackage){
        this.binPackage = binPackage;
    }
    
    public void validate(){
        updateExitPoints();
    }
}
