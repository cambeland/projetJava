package ca.ulaval.glo2004.GUI.EditionFrame;

import ca.ulaval.glo2004.Domain.InvalidTotalMatrixException;
import ca.ulaval.glo2004.Domain.Matrix.SortingMatrix;
import ca.ulaval.glo2004.Domain.Product;
import ca.ulaval.glo2004.Domain.Station;
import ca.ulaval.glo2004.Domain.StationExitPoint;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SortingMatrixEditionJFrame extends JFrame{
    private SortingMatrix sortingMatrix;
    private Station station;
    private List<Product> products;
    private Container pane;
    private JButton okButton;

    private int nbProduct = 0;
    private int nbNewProduct = 0;

    private final int startPosX = 30;
    private final int startPosY = 30;
    private final int txtSizeX = 63;
    private final int txtSizeY = 23;
    private final int txtRepeteX = 73;
    private final int txtRepeteY = 50;

    private List<textFieldHolder> lstTxt;

    private class textFieldHolder{
        public Product product;
        public StationExitPoint exitPoint;
        public JTextField txt;

        public textFieldHolder(Product product, StationExitPoint exitPoint, JTextField txt){
            this.product = product;
            this.exitPoint = exitPoint;
            this.txt = txt;
        }
    }

    public SortingMatrixEditionJFrame(Station station){
        super();
        lstTxt = new ArrayList<textFieldHolder>();
        this.sortingMatrix = station.getSortingMatrix();
        this.station = station;

        this.pane = getContentPane();
        this.pane.setLayout(null);

        init(sortingMatrix);
        initButtons();
        setVisible(true);
    }

    private void initButtons(){
        okButton = new JButton("Ok");
        okButton.setBounds(startPosX * 2 + (nbProduct) * txtRepeteX,  startPosY * 2 + (nbNewProduct) * txtRepeteY + 10, 60, 26);
        okButton.setVisible(true);
        okButton.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                okButtonActionPerformed(evt);
            }
        });
        pane.add(okButton);
    }

    private void save(){
        try {
            //validate
            validateMatrix();
            for(textFieldHolder i: lstTxt){
                double d = Double.parseDouble(i.txt.getText());
                if(d < 0){
                    throw new RuntimeException();
                }
            }
            //editMatrix
            for(textFieldHolder i: lstTxt){
                sortingMatrix.set(i.product, i.exitPoint, Double.parseDouble(i.txt.getText()));
            }
            this.dispose();
            
        }catch (InvalidTotalMatrixException e){
                JOptionPane.showMessageDialog(this, "Invalid values. Column total must be 100");
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "Invalid value. Please enter a positive real number.");
    }
}
    private void okButtonActionPerformed(java.awt.event.ActionEvent e){
        save();
    }

    private void init(SortingMatrix sortingMatrix){
        products = sortingMatrix.getProducts();
        List<StationExitPoint> exitPoints = sortingMatrix.getExits();
        List<JLabel> JLabels = new ArrayList<JLabel>();
        for(int i = 0;i < products.size(); i++){
            JLabel label = new JLabel(products.get(i).toString());
            label.setBounds(startPosX + (i+1) * txtRepeteX, startPosY, txtSizeX, txtSizeY);
            label.setVisible(true);
            JLabels.add(label);
            pane.add(label);
        }
        for(int i = 0;i < exitPoints.size(); i++){
            JLabel label = new JLabel("Exit" + (i+1));
            label.setBounds(startPosX, startPosY + (i+1) * txtRepeteY, txtSizeX, txtSizeY);
            label.setVisible(true);
            JLabels.add(label);
            pane.add(label);
        }
        for(int i = 0;i < products.size(); i++){
            for(int j = 0;j < exitPoints.size(); j++){
                double d = sortingMatrix.getValue(products.get(i),exitPoints.get(j));
                JTextField txt = new JTextField(String.valueOf(d));
                txt.setBounds(startPosX + (i+1) * txtRepeteX, startPosY + (j+1) * txtRepeteY, txtSizeX, txtSizeY);
                txt.setVisible(true);
                lstTxt.add(new textFieldHolder(products.get(i), exitPoints.get(j), txt));
                pane.add(txt);
            }
        }
        nbProduct = products.size();
        nbNewProduct = exitPoints.size();
        setSize(startPosX * 2 + (nbProduct+2) * txtRepeteX, startPosY * 2 + (nbNewProduct+2) * txtRepeteY);
    }
    
    public void validateMatrix() throws InvalidTotalMatrixException{
        for(Product product : products){
            double total = 0;
            for(textFieldHolder textField : lstTxt){
                if(product.toString().equals(textField.product.toString())){
                    total += Double.parseDouble(textField.txt.getText());
                }
            }
            if(total > 100){
                throw new InvalidTotalMatrixException("");
            }
        }
    }
}
