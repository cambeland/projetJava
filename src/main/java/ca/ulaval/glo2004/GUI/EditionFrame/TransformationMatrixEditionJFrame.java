package ca.ulaval.glo2004.GUI.EditionFrame;

import ca.ulaval.glo2004.Domain.InvalidTotalMatrixException;
import ca.ulaval.glo2004.Domain.Matrix.TransformationMatrix;
import ca.ulaval.glo2004.Domain.Product;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;

public class TransformationMatrixEditionJFrame extends JFrame{
    TransformationMatrix transformationMatrix;
    private List<Product> products;
    private Container pane;
    private JButton okButton;
    private JButton addButton;

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
        public Product newProduct;
        public JTextField txt;

        public textFieldHolder(Product product, Product newProduct, JTextField txt){
            this.product = product;
            this.newProduct = newProduct;
            this.txt = txt;
        }
    }

    public TransformationMatrixEditionJFrame(TransformationMatrix transformationMatrix){
        super();
        lstTxt = new ArrayList<textFieldHolder>();
        this.transformationMatrix = transformationMatrix;

        this.pane = getContentPane();
        this.pane.setLayout(null);

        init(transformationMatrix);
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
        addButton = new JButton("Add");
        addButton.setBounds(startPosX * 2 + (nbProduct) * txtRepeteX-100,  startPosY * 2 + (nbNewProduct) * txtRepeteY + 10, 60, 26);
        addButton.setVisible(true);
        addButton.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                addButtonActionPerformed(evt);
            }
        });
        pane.add(addButton);
    }

    private void save(){
        try {
            validateMatrix();
            //validate
            for(textFieldHolder i: lstTxt){
                double d = Double.parseDouble(i.txt.getText());
                if (d<0){
                    throw new RuntimeException();
                }
            }
            //editMatrix
            for(textFieldHolder i: lstTxt){
                transformationMatrix.set(i.product, i.newProduct, Double.parseDouble(i.txt.getText()) / 100);
            }
            this.dispose();

        }catch (InvalidTotalMatrixException e){
                JOptionPane.showMessageDialog(this, "Invalid values. Column total must be 100"); 
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, "Invalid value. Please enter a positive real number.");
        }
    }

    private void addButtonActionPerformed(java.awt.event.ActionEvent e){
        String strNewProduct = JOptionPane.showInputDialog(null, "Your new product name");
        Product newProduct = new Product(strNewProduct);
        //transformationMatrix.addOrFind(newProduct);
        nbNewProduct += 1;


        JLabel label = new JLabel(strNewProduct);
        label.setBounds(startPosX, startPosY + nbNewProduct * txtRepeteY, txtSizeX, txtSizeY);
        label.setVisible(true);
        pane.add(label);
        for(int i = 0;i < nbProduct; i++){
            double d = transformationMatrix.getValue(products.get(i), newProduct);
            JTextField txt = new JTextField(String.valueOf(d*100));
            txt.setBounds(startPosX + (i+1) * txtRepeteX, startPosY + nbNewProduct * txtRepeteY, txtSizeX, txtSizeY);
            txt.setVisible(true);
            lstTxt.add(new textFieldHolder(products.get(i), newProduct,txt));
            pane.add(txt);
        }


        okButton.setBounds(startPosX * 2 + (nbProduct) * txtRepeteX,  startPosY * 2 + (nbNewProduct) * txtRepeteY + 10, 60, 26);
        addButton.setBounds(startPosX * 2 + (nbProduct) * txtRepeteX-100,  startPosY * 2 + (nbNewProduct) * txtRepeteY + 10, 60, 26);
        setSize(startPosX * 2 + (nbProduct+2) * txtRepeteX, startPosY * 2 + (nbNewProduct+2) * txtRepeteY);
    }

    private void okButtonActionPerformed(java.awt.event.ActionEvent e){
        save();
    }

    private void init(TransformationMatrix transformationMatrix){
        products = transformationMatrix.getProducts();
        List<Product> newProducts = transformationMatrix.getNewProducts();
        List<JLabel> JLabels = new ArrayList<JLabel>();
        for(int i = 0;i < products.size(); i++){
            JLabel label = new JLabel(products.get(i).toString());
            label.setBounds(startPosX + (i+1) * txtRepeteX, startPosY, txtSizeX, txtSizeY);
            label.setVisible(true);
            JLabels.add(label);
            pane.add(label);
        }
        for(int i = 0;i < newProducts.size(); i++){
            JLabel label = new JLabel(newProducts.get(i).toString());
            label.setBounds(startPosX, startPosY + (i+1) * txtRepeteY, txtSizeX, txtSizeY);
            label.setVisible(true);
            JLabels.add(label);
            pane.add(label);
        }
        for(int i = 0;i < products.size(); i++){
            for(int j = 0;j < newProducts.size(); j++){
                double d = transformationMatrix.getValue(products.get(i),newProducts.get(j));
                JTextField txt = new JTextField(String.valueOf(d*100));
                txt.setBounds(startPosX + (i+1) * txtRepeteX, startPosY + (j+1) * txtRepeteY, txtSizeX, txtSizeY);
                txt.setVisible(true);
                lstTxt.add(new textFieldHolder(products.get(i),newProducts.get(j),txt));
                pane.add(txt);
            }
        }
        nbProduct = products.size();
        nbNewProduct = newProducts.size();
        setSize(startPosX * 2 + (nbProduct+2) * txtRepeteX, startPosY * 2 + (nbNewProduct+2) * txtRepeteY);
    }
    public void validateMatrix() throws InvalidTotalMatrixException{
        for(Product product : products){
            double total = 0;
            for(TransformationMatrixEditionJFrame.textFieldHolder textField : lstTxt){
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
