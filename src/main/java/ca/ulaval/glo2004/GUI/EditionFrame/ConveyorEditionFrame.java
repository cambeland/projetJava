package ca.ulaval.glo2004.GUI.EditionFrame;

import ca.ulaval.glo2004.Controller.ConveyorController;
import ca.ulaval.glo2004.Utils.ColorHolder;
import ca.ulaval.glo2004.Utils.ColorProvider;

import javax.swing.JOptionPane;

public class ConveyorEditionFrame extends ConveyorEditionJFrame{
    ConveyorController conveyorController;
    private final String MAX_STR = "MAX";
    
    public ConveyorEditionFrame(ConveyorController conveyorController){        
        this.conveyorController = conveyorController;
        okButton.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                okButtonActionPerformed(evt);
            }
        });
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setCapacityInField();
        colorComboBox.setModel(new javax.swing.DefaultComboBoxModel(ColorProvider.colors));
        ColorProvider.selectEquipmentColor(colorComboBox, conveyorController);
    }
    
    private void okButtonActionPerformed(java.awt.event.ActionEvent e){
        save();
    }

    private double getCapacityInField() throws Exception{
        double capacity = -1;
        if(conveyorCapacityTextField.getText().equals(MAX_STR)) {
            capacity = Double.MAX_VALUE;
        } else{
            capacity = Double.parseDouble(conveyorCapacityTextField.getText());
        }
        if (capacity < 0){
            throw new NumberFormatException();
        }
        return capacity;
    }

    private void setCapacityInField(){
        if (conveyorController.getCapacity() == Double.MAX_VALUE) {
            conveyorCapacityTextField.setText(MAX_STR);
        } else {
            conveyorCapacityTextField.setText(String.valueOf(conveyorController.getCapacity()));
        }
    }

    private void save(){
        ColorHolder colorHolder = (ColorHolder)colorComboBox.getSelectedItem();
        try{
            double capacity = getCapacityInField();
            conveyorController.setCapacity(capacity);
            conveyorController.setColor(colorHolder.color); 
            this.dispose();
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid value. Please enter a positive real number.");
            setCapacityInField();
        }
    }
}
