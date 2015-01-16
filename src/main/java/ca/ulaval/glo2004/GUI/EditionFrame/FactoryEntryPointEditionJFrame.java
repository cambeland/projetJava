package ca.ulaval.glo2004.GUI.EditionFrame;

import ca.ulaval.glo2004.Domain.Product;
import ca.ulaval.glo2004.Domain.ProductHolder;
import ca.ulaval.glo2004.Domain.Point;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;

public class FactoryEntryPointEditionJFrame extends JFrame{
    private Point entryPoint;
    private Container pane;
    private JButton okButton;
    private JButton addButton;
    private List<JLabel> JLabels;

    private int nbProduct = 0;

    private final int startPosX = 30;
    private final int startPosY = 30;
    private final int txtSizeX = 63;
    private final int txtSizeY = 23;
    private final int txtRepeteX = 73;
    private final int txtRepeteY = 50;

    private List<textFieldHolder> lstTxt;
    private List<ProductHolder> productHolders;

    private class textFieldHolder{
        public Product product;
        public JTextField txt;

        public textFieldHolder(ProductHolder productHolder, JTextField txt){
            this.product = productHolder.getProduct();
            this.txt = txt;
        }
    }

    public FactoryEntryPointEditionJFrame(Point entryPoint){
        super();
        lstTxt = new ArrayList<textFieldHolder>();
        productHolders = new ArrayList<ProductHolder>();
        this.entryPoint= entryPoint;

        this.pane = getContentPane();
        this.pane.setLayout(null);

        init(entryPoint);
        initButtons();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init(Point entryPoint){
        productHolders = entryPoint.getBinPackage().getWasteBundleCopy();  
        JLabels = new ArrayList<JLabel>();

        for (int i = 0; i < productHolders.size(); i++) {
            JLabel label = new JLabel(productHolders.get(i).getProductName());
            label.setBounds(startPosX, startPosY + (i + 1) * txtRepeteY, txtSizeX, txtSizeY);
            label.setVisible(true);
            JLabels.add(label);
            pane.add(label);
        }
        for (int i = 0; i < productHolders.size(); i++) {

            double quantity = productHolders.get(i).getQuantity();
            JTextField txt = new JTextField(String.valueOf(quantity));
            txt.setBounds(startPosX + txtRepeteX, startPosY + (i + 1) * txtRepeteY, txtSizeX, txtSizeY);
            txt.setVisible(true);
            lstTxt.add(new textFieldHolder(productHolders.get(i), txt)); 
            pane.add(txt);
        }
        nbProduct = productHolders.size();
        setSize(startPosX + 3 * txtRepeteX, startPosY * 2 + (nbProduct + 2) * txtRepeteY);
    }
    private void initButtons(){
        okButton = new JButton("Ok");
        okButton.setBounds(startPosX + txtRepeteX,  startPosY * 2 + (nbProduct) * txtRepeteY + 10, 60, 26);
        okButton.setVisible(true);
        okButton.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                okButtonActionPerformed(evt);
            }
        });
        pane.add(okButton);
        addButton = new JButton("Add");
        addButton.setBounds(startPosX,  startPosY * 2 + (nbProduct) * txtRepeteY + 10, 60, 26);
        addButton.setVisible(true);
        addButton.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                addButtonActionPerformed(evt);
            }
        });
        pane.add(addButton);
    }
    
    private void addButtonActionPerformed(java.awt.event.ActionEvent e){
        String strNewProduct = JOptionPane.showInputDialog(null, "Your new product name");
        Product newProduct = new Product(strNewProduct);
        ProductHolder newProductHolder = new ProductHolder(newProduct, 0); 
        productHolders.add(newProductHolder);
        nbProduct += 1;

        JLabel label = new JLabel(strNewProduct);
        label.setBounds(startPosX, startPosY + (nbProduct) * txtRepeteY, txtSizeX, txtSizeY);
        label.setVisible(true);
        pane.add(label);

        double quantity = newProductHolder.getQuantity();
        JTextField txt = new JTextField(String.valueOf(quantity));

        txt.setBounds(startPosX + txtRepeteX, startPosY + (nbProduct) * txtRepeteY, txtSizeX, txtSizeY);
        txt.setVisible(true);
        lstTxt.add(new textFieldHolder(newProductHolder, txt));
        pane.add(txt);
        
        okButton.setBounds(startPosX + txtRepeteX,  startPosY * 2 + (nbProduct) * txtRepeteY + 10, 60, 26);
        addButton.setBounds(startPosX,  startPosY * 2 + (nbProduct) * txtRepeteY + 10, 60, 26);
        setSize(startPosX + 3 * txtRepeteX, startPosY * 2 + (nbProduct + 2) * txtRepeteY);
    }

    private void okButtonActionPerformed(java.awt.event.ActionEvent e){
        save();
    }
    
    private void save(){
        try {
            entryPoint.getBinPackage().getWasteBundle().clear();
            entryPoint.getBinPackage().getWasteBundle().addAll(productHolders);
            
            for (textFieldHolder i : lstTxt){
                double d = Double.parseDouble(i.txt.getText());
                if(d <= 0){
                    throw new RuntimeException("");
                }
            }
            
            for (int i = 0 ; i < lstTxt.size(); i++){
                entryPoint.getBinPackage().getWasteBundle().get(i).setQuantity(Double.parseDouble((lstTxt.get(i).txt.getText())));
            }
            this.dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid value. Please enter a positive real number.");
        }
    }
}
