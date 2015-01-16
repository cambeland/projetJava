package ca.ulaval.glo2004.GUI.EditionFrame;

import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import javax.imageio.ImageIO;

import ca.ulaval.glo2004.Controller.StationController;
import ca.ulaval.glo2004.Utils.ColorHolder;
import ca.ulaval.glo2004.Utils.ColorProvider;


public class StationEditionFrame extends StationEditionJFrame{
    StationController stationController;
    private final String MAX_STR = "MAX";
    private final long MAX_IMAGE_FILE_SIZE = 5242880;
    
    public StationEditionFrame(StationController stationController){
        this.stationController = stationController;
        okButton.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                okButtonActionPerformed(evt);
            }
        });
        modifyTransformationMatrixButton.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                modifyTransformationMatrixButtonActionPerformed(evt);
            }
        });
        modifySortMatrixButton.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                modifySortingMatrixButtonActionPerformed(evt);
            }
        });
        selectImageFileButton.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                selectImageFileButtonActionPerformed(evt);
            }
        });
        stationNameField.setText(stationController.getName());
        colorComboBox.setModel(new javax.swing.DefaultComboBoxModel(ColorProvider.colors));
        ColorProvider.selectEquipmentColor(colorComboBox, stationController);
        setCapacityInField();
        outputJSpinner.setValue(stationController.getOutputNumber());
    }

    private void okButtonActionPerformed(java.awt.event.ActionEvent e){
        save();
    }

    private double getCapacityInField() throws NumberFormatException{
        double capacity = -1;
        if(capacityTextField.getText().equals(MAX_STR)) {
            capacity = Double.MAX_VALUE;
        } else{
            capacity = Double.parseDouble(capacityTextField.getText());
        }
        if (capacity < 0){
            throw new NumberFormatException();
        }
        return capacity;
    }

    private void setCapacityInField(){
        if (stationController.getCapacity() == Double.MAX_VALUE) {
            capacityTextField.setText(MAX_STR);
        } else {
            capacityTextField.setText(String.valueOf(stationController.getCapacity()));
        }
    }

    private void modifyTransformationMatrixButtonActionPerformed(java.awt.event.ActionEvent e){
        TransformationMatrixEditionJFrame transformationMatrixEditionJFrame =
                new TransformationMatrixEditionJFrame(stationController.getTransformationMatrix());
        
    }

    private void modifySortingMatrixButtonActionPerformed(java.awt.event.ActionEvent e){
        SortingMatrixEditionJFrame sortingMatrixEditionJFrame =
                new SortingMatrixEditionJFrame(stationController);

    }
    
    private void selectImageFileButtonActionPerformed(java.awt.event.ActionEvent e) {
        JFileChooser selectImageFileJFileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images Files", ImageIO.getReaderFileSuffixes());
        selectImageFileJFileChooser.setFileFilter(filter);
        int result = selectImageFileJFileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File imageFile = new File(selectImageFileJFileChooser.getSelectedFile().getPath());
            if (imageFile.isFile()){
                if(imageFile.length() <= MAX_IMAGE_FILE_SIZE){
                    stationController.setImagePath(imageFile.getPath());
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a file smaller than 5 Mo.");
                }
            }
        }
    }

    private void save(){
        ColorHolder colorHolder = (ColorHolder)colorComboBox.getSelectedItem();
        try{
            double capacity = getCapacityInField();
            stationController.setColor(colorHolder.color);
            stationController.setName(stationNameField.getText());
            stationController.setCapacity(capacity);
            stationController.setOutputNumber((Integer) outputJSpinner.getValue());
            stationController.updateExitPoints();
            this.dispose();
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Invalid value. Please enter a positive real number.");
            setCapacityInField();
        }
        catch(Exception e){
           throw new RuntimeException(""); 
        }
    }
}
