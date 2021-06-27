/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import javax.swing.*;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Admin
 */
public class MainFrame extends JFrame{
    int numbSensor;
    int sensorRadius;
    
    public GraphicsDrawing graphics = new GraphicsDrawing();;
    
    public MainFrame(int width, int height) {
        this.setSize(width, height);
        graphics.setNumbSensor(numbSensor);
        graphics.setSensorRadius(sensorRadius);
        this.setVisible(true);
        this.add(graphics);
    }

    
    public int getNumbSensor() {
        return numbSensor;
    }
    public int getSensorRadius() {
        return sensorRadius;
    }
    public void setNumbSensor(int numbSensor) {
        this.numbSensor = numbSensor;
    }
    public void setSensorRadius(int sensorRadius) {
        this.sensorRadius = sensorRadius;
    }
    
    
}
