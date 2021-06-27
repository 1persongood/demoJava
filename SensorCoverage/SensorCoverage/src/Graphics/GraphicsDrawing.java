/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import Data.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GraphicsDrawing extends JPanel{
    int numbSensor;
    int sensorRadius;
    int cycleTime = 3;
    String nodeInfor = new String();
    ArrayList<Sensor> sensorList = new ArrayList<>();
    
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
    public void drawCircle(Graphics2D cg, int xCenter, int yCenter, int r) {
        cg.drawOval(xCenter-r, yCenter-r, 2*r, 2*r);
    }
    public void fillCircle(Graphics2D cg, int xCenter, int yCenter, int r) {
        cg.fillOval(xCenter-r, yCenter-r, 2*r, 2*r);
    }
    
    public void oneCycleRun(Graphics2D g2d) {
        createRandomNode(g2d);
//        readFile("SensorNetwork.txt");
        // Kiểm tra và tạo danh sách neighbor của từng node
        checkNeighbor();
        // Tô màu theo trạng thái hoạt động
        color_state(g2d);
//        writeToFile("SensorNetwork.txt", nodeInfor);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        oneCycleRun(g2d);
    }
   
    void createRandomNode(Graphics2D g2d) {
        Random rx = new Random();
        Random ry = new Random();
        nodeInfor += "numbSensor: " + this.numbSensor + "\n";
        nodeInfor += "sensingRadius: " + this.sensorRadius + "\n";
        
        for (int i=0; i<this.numbSensor; i++) {
            Sensor sensor = new Sensor();
            sensor.setRadius(this.sensorRadius);
            
            // Gán giá trị cho ID của node; thêm node vào danh sách
            sensor.setId(i);
            this.sensorList.add(sensor);
            
            // Lấy giá trị x, y ngẫu nhiên trong đoạn từ 0, width/height của window
            sensor.setX(rx.nextInt(this.getWidth()));
            sensor.setY(ry.nextInt(this.getHeight()));
            
            // Lưu lại thông tin của node
            nodeInfor += (sensor.toString() + "\n");
        }
    }
    
    void writeToFile(String filename, String s){
        // Ghi ra file
        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
                
            } catch (IOException ex) {
                Logger.getLogger(GraphicsDrawing.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        FileOutputStream output;
        try {
            output = new FileOutputStream(file);
            output.write(s.getBytes());
            output.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GraphicsDrawing.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GraphicsDrawing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void readFile(String filename) {
        String output = new String();
        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
                
            } catch (IOException ex) {
                Logger.getLogger(GraphicsDrawing.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            int c = fileInputStream.read();
            while (c != -1) {
                output += (char) c;
                c = fileInputStream.read();
                
            }
            
            fileInputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GraphicsDrawing.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GraphicsDrawing.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String[] outp = output.strip().split("\n");
        this.numbSensor = Integer.parseInt(outp[0].split(" ")[1].strip());
        this.sensorRadius = Integer.parseInt(outp[1].split(" ")[1].strip());
        
        for (int i=2; i<outp.length; i++) {
            Sensor sensor = new Sensor();
            String[] line = outp[i].split("--");
            sensor.setId(Integer.parseInt(line[0].split(" ")[2].strip()));
            sensor.setX(Integer.parseInt(line[1].split(" ")[1].strip()));
            sensor.setY(Integer.parseInt(line[2].split(" ")[1].strip()));
            sensor.setRadius(Integer.parseInt(line[3].split(" ")[2].strip()));
            this.sensorList.add(sensor);
        }
    }
    
    
    void checkNeighbor() {
        
        for (int i=0; i<this.numbSensor; i++) {
            // Node đang xét
            Sensor sensorX = this.sensorList.get(i);
            
            // Copy danh sách các node và loại bỏ node đang xét
            ArrayList<Sensor> ls = new ArrayList<>();
            ls.addAll(this.sensorList);
            ls.remove(i);
             
            // Thêm các node neighbor vào neighborList của node đang xét
            sensorX.addToNeighborList(ls, this.numbSensor - 1);
        }
        
        // Kiểm tra danh sách neighbor của node i
//        Sensor x = this.sensorList.get(1);
//        System.out.println(x);
//        ArrayList<Sensor> neighborx = x.getNeighborList();
//        for (int j=0; j<neighborx.size(); j++) {
//            System.out.println(neighborx.get(j).getId());
//        }
//        System.out.println(this.sensorList.get(1).isCoveredByNeighbor());
    }
    
    void color_state(Graphics2D g2d) {
        // Nếu node không thỏa mãn điều kiện được tắt, tô vòng tròn bao phủ màu xanh
        for (int i=0; i<this.numbSensor; i++) {
            Sensor x = this.sensorList.get(i);
            if (x.isCoveredByNeighbor() == false) {
                x.setState(false);
                g2d.setColor(Color.GREEN);
                fillCircle(g2d, x.getX(), x.getY(), x.getRadius());
            } 
       }
        
        // Nếu node thỏa mãn điều kiện được  tắt, vẽ vòng tròn bao phủ màu đỏ
        for (int i=0; i<this.numbSensor; i++) {
            Sensor x = this.sensorList.get(i);
            if (x.isCoveredByNeighbor() == true) {
                x.setState(true);
                g2d.setColor(Color.RED);
                drawCircle(g2d, x.getX(), x.getY(), x.getRadius());
            }
        }
        
        for (int i=0; i<this.numbSensor; i++) {
            Sensor x = this.sensorList.get(i);
            // Vẽ tâm, viết ID node
            g2d.setColor(Color.BLACK);
            drawCircle(g2d, x.getX(), x.getY(), 2);
            g2d.setFont(new Font("Comic Sans", Font.PLAIN, 15));
            g2d.drawString(i + "", x.getX(), x.getY());
        }
    }
}
