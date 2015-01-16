package ca.ulaval.glo2004.GUI;

import ca.ulaval.glo2004.Controller.GraphicalInterfaceController;
import ca.ulaval.glo2004.Drawing.GraphicPanelDrawer;
import ca.ulaval.glo2004.GUI.Listener.*;
import ca.ulaval.glo2004.GUI.Listener.Action.*;
import ca.ulaval.glo2004.GUI.Mouse.ModeHandleListener;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AppMasterFrame extends AppMasterJFrame{

    private GraphicalInterfaceController graphicalInterfaceController;
    private ApplicationMode applicationMode;
    private StateBarPanel stateBarPanel;
    private ToolBarPanel toolBarPanel;

    public Point actualMousePoint;

    public static enum ApplicationMode{

        ADD_CONVEYOR, ADD_ENTRY_POINT, ADD_EXIT_POINT, ADD_JUNCTION_POINT, ADD_STATION, SELECT
    }

    public AppMasterFrame(GraphicalInterfaceController graphicalInterfaceController, GraphicPanelDrawer graphicPanelDrawer){
        this.graphicalInterfaceController = graphicalInterfaceController;
        this.applicationMode = ApplicationMode.SELECT;

        initMenu(graphicPanelDrawer);
        initPanel(graphicPanelDrawer);
        initToolBarPanel();
        initStateBarPanel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        actualMousePoint = new Point();
    }

    private void initMenu(GraphicPanelDrawer graphicPanelDrawer){
        ActionListener actionPrinter = new ToggleGridListener(graphicPanelDrawer);
        showGridCheckBox.addActionListener(actionPrinter);

        ActionListener magneticActionPrinter = new MagnetizeGridListener(graphicPanelDrawer);
        magnetizeGrid.addActionListener(magneticActionPrinter);

        ActionListener saveListener = new SaveListener(this);
        saveMenu.addActionListener(saveListener);

        ActionListener openListener = new OpenListener(this);
        openMenu.addActionListener(openListener);
        ActionListener exportActionListener = new ExportImageListener(graphicPanelDrawer);
        exportImage.addActionListener(exportActionListener);
        
        ActionListener newListener = new NewButtonListener(this);
        newProjectMenu.addActionListener(newListener);     
    }

    private void initStateBarPanel(){
        stateBarPanel = new StateBarPanel();
        stateBarPanel.zoomBox.addChangeListener(new ZoomListener(this, stateBarPanel.zoomBox));
        getContentPane().add(stateBarPanel, BorderLayout.SOUTH);
    }

    private void initToolBarPanel(){
        toolBarPanel = new ToolBarPanel();
        toolBarPanel.conveyorButton.addActionListener(new AddConveyorModeAction(this, toolBarPanel));
        toolBarPanel.pointButton.addItemListener(new PointItemListener(toolBarPanel));
        toolBarPanel.stationButton.addActionListener(new AddStationModeAction(this, toolBarPanel));
        toolBarPanel.cusorButton.addActionListener(new SelectModeAction(this, toolBarPanel));
        toolBarPanel.entryPointButton.addActionListener(new AddEntryPointAction(this, toolBarPanel));
        toolBarPanel.junctionPointToggleButton.addActionListener(new AddJunctionPointModeAction(this, toolBarPanel));
        toolBarPanel.exitPointButton.addActionListener(new AddExitPointAction(this, toolBarPanel));
        toolBarPanel.deleteButton.addActionListener(new DeleteEquipmentAction(this, toolBarPanel));
        toolBarPanel.undoButton.addActionListener(new UndoButtonListener(this));
        toolBarPanel.redoButton.addActionListener(new RedoButtonListener(this));

        toolBarPanel.handButton.setVisible(false);
        toolBarPanel.magnifyButton.setVisible(false);

        getContentPane().add(toolBarPanel, BorderLayout.NORTH);
    }

    private void initPanel(GraphicPanelDrawer graphicPanelDrawer){
        JPanel topLevel = new JPanel();
        topLevel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topLevel.setBackground(Color.GRAY);

        JScrollPane mainScrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        mainScrollPane.setViewportView(topLevel);
        mainScrollPane.setOpaque(false);
        getContentPane().add(mainScrollPane, BorderLayout.CENTER);
        topLevel.add(graphicPanelDrawer);
    }

    public void initMouseInteractions(GraphicPanelDrawer graphicPanelDrawer){
        ModeHandleListener modeHandleListener = new ModeHandleListener(this, stateBarPanel);
        graphicPanelDrawer.addMouseListener(modeHandleListener);
        graphicPanelDrawer.addMouseMotionListener(modeHandleListener);
    }

    public GraphicalInterfaceController getGraphicalInterfaceController(){
        return graphicalInterfaceController;
    }

    public void setMode(ApplicationMode mode){
        graphicalInterfaceController.resetPreviousSelection();
        applicationMode = mode;
    }

    public ApplicationMode getActualMode(){
        return this.applicationMode;
    }

    public void setScale(double scale){
        graphicalInterfaceController.setScale(scale);
    }

    public void save(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save a RecyclApp project");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("RecyclApp Files (.rca)", "rca", "RecyclApp");
        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION){
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getPath();
            if (!filePath.endsWith(".rca")){
                filePath += ".rca";
            }

            fileToSave = new File(filePath);
            try {
                if (fileToSave.exists() && !fileToSave.isDirectory()){
                    String confirmationMessage = "Are you sure you want to overwrite this file?";
                    if (JOptionPane.showConfirmDialog(null, confirmationMessage, "Overwrite a file", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                        throw new IOException();
                    }
                }
                FileOutputStream outFile = new FileOutputStream(fileToSave.getAbsolutePath(), false);
                ObjectOutputStream outStream = new ObjectOutputStream(outFile);
                outStream.writeObject(this.graphicalInterfaceController);
                outStream.close();
                outFile.close();
            } catch (Exception e){
                JOptionPane.showMessageDialog(this, "An error occured. Please try again.");
            }
        }
    }

    public void open(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open an existing project");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("RecyclApp Files (.rca)", "rca", "RecyclApp");
        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION){
            File fileToLoad = fileChooser.getSelectedFile();
            try {
                getContentPane().removeAll();
                getContentPane().repaint();

                FileInputStream fileIn = new FileInputStream(fileToLoad.getAbsolutePath());
                ObjectInputStream inStream = new ObjectInputStream(fileIn);
                GraphicalInterfaceController localController = (GraphicalInterfaceController) inStream.readObject();
                inStream.close();
                fileIn.close();

                graphicalInterfaceController.setGraphicPanelDrawer(localController.getGraphicPanelDrawer());
                initToolBarPanel();
                initStateBarPanel();
                initPanel(localController.getGraphicPanelDrawer());
                initMouseInteractions(localController.getGraphicPanelDrawer());

                revalidate();
                repaint();
            } catch (Exception e){
                JOptionPane.showMessageDialog(this, "An error occured. Please try again.");
            }
        }
    }

    public void saveToUndo(){
        File fileToSave = new File("undo.tmp");
        try {
            FileOutputStream outFile = new FileOutputStream(fileToSave.getAbsolutePath(), false);
            ObjectOutputStream outStream = new ObjectOutputStream(outFile);
            outStream.writeObject(graphicalInterfaceController);
            outStream.close();
            outFile.close();
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "An error occured. Please try again.");
        }
    }

    public void saveToRedo(){
        File fileToSave = new File("redo.tmp");
        try {
            FileOutputStream outFile = new FileOutputStream(fileToSave.getAbsolutePath(), false);
            ObjectOutputStream outStream = new ObjectOutputStream(outFile);
            outStream.writeObject(this.graphicalInterfaceController);
            outStream.close();
            outFile.close();
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "An error occured. Please try again.");
        }
    }
    
    public void saveNewProjet(){
        File fileToSave = new File("new.tmp");
        try {
            FileOutputStream outFile = new FileOutputStream(fileToSave.getAbsolutePath(), false);
            ObjectOutputStream outStream = new ObjectOutputStream(outFile);
            outStream.writeObject(this.graphicalInterfaceController);
            outStream.close();
            outFile.close();
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "An error occured. Please try again.");
        }
    }

    public void loadToRedo(){
        File fileToLoad = new File("redo.tmp");
        try {
            getContentPane().removeAll();
            getContentPane().repaint();
            FileInputStream fileIn = new FileInputStream(fileToLoad.getAbsolutePath());
            ObjectInputStream inStream = new ObjectInputStream(fileIn);
            GraphicalInterfaceController localController = (GraphicalInterfaceController) inStream.readObject();
            inStream.close();
            fileIn.close();
            graphicalInterfaceController.setGraphicPanelDrawer(localController.getGraphicPanelDrawer());
            initToolBarPanel();
            initStateBarPanel();
            initPanel(localController.getGraphicPanelDrawer());
            initMouseInteractions(localController.getGraphicPanelDrawer());
            revalidate();
            repaint();
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "An error occured. Please try again.");
        }
    }

    public void loadToUndo() {
        File fileToLoad = new File("undo.tmp");
        try {
            getContentPane().removeAll();
            getContentPane().repaint();
            FileInputStream fileIn = new FileInputStream(fileToLoad.getAbsolutePath());
            ObjectInputStream inStream = new ObjectInputStream(fileIn);
            GraphicalInterfaceController localController = (GraphicalInterfaceController) inStream.readObject();
            inStream.close();
            fileIn.close();
            graphicalInterfaceController.setGraphicPanelDrawer(localController.getGraphicPanelDrawer());
            initToolBarPanel();
            initStateBarPanel();
            initPanel(localController.getGraphicPanelDrawer());
            initMouseInteractions(localController.getGraphicPanelDrawer());
            revalidate();
            repaint();
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "An error occured. Please try again.");
        }
    }
    
    public void loadNewProject() {
        File fileToLoad = new File("new.tmp");
        try {
            getContentPane().removeAll();
            getContentPane().repaint();
            FileInputStream fileIn = new FileInputStream(fileToLoad.getAbsolutePath());
            ObjectInputStream inStream = new ObjectInputStream(fileIn);
            GraphicalInterfaceController localController = (GraphicalInterfaceController) inStream.readObject();
            inStream.close();
            fileIn.close();
            graphicalInterfaceController.setGraphicPanelDrawer(localController.getGraphicPanelDrawer());
            initToolBarPanel();
            initStateBarPanel();
            initPanel(localController.getGraphicPanelDrawer());
            initMouseInteractions(localController.getGraphicPanelDrawer());
            revalidate();
            repaint();
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "An error occured. Please try again.");
        }
    }
}
