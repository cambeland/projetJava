package ca.ulaval.glo2004.GUI.Listener;

import ca.ulaval.glo2004.GUI.AppMasterFrame;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JSlider;

public class ZoomListener implements ChangeListener{
    private final AppMasterFrame appMasterFrame;
    private JSlider zoomBox;

    public ZoomListener(AppMasterFrame appMasterFrame, JSlider zoomBox){
        this.appMasterFrame = appMasterFrame;
        this.zoomBox = zoomBox;
    }

    @Override
    public void stateChanged(ChangeEvent e){
        double scale;
        scale = (zoomBox.getValue()+ 1 ) * 0.25;
        appMasterFrame.setScale(scale);
    }
}
