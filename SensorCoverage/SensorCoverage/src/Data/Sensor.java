/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Sensor extends Point2d {
    int id;
    Boolean state = true;
    ArrayList<Sensor> neighborList = new ArrayList<>();
    

    public Sensor() {

    }
    
    @Override
    public String toString() {

        return "Node ID: " + this.id + super.toString() + "--Node status: " + this.state;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Boolean getState() {
        return state;
    }
    public void setState(Boolean state) {
        this.state = state;
    }
    public ArrayList<Sensor> getNeighborList() {
        return neighborList;
    }
    public void setNeighborList(ArrayList<Sensor> neighborList) {
        this.neighborList = neighborList;
    }
    
    // Hàm tính khoảng cách giữa sensor đang xét và sensor truyền vào (sensor b)
    public double calc_distance(Sensor b) {
        return Math.sqrt((b.y - this.y) * (b.y - this.y) + (b.x - this.x) * (b.x - this.x));
    }
    
    // Hàm thêm vào danh sách neighbor
    public void addToNeighborList(ArrayList<Sensor> list, int numbSensor) {
        for (int i=0; i<numbSensor; i++) {
            // Điều kiện để 2 node là neighbor: khoảng cách giữa 2 node <= bán kính cảm biến
            if (this.calc_distance(list.get(i)) <= this.radius) {
                neighborList.add(list.get(i));
            }
        }
    }
    
    // Hàm tính góc hỗ trợ tâm
    public double calc_central_angle(Sensor b) {
        return 2 * Math.acos(this.calc_distance(b) / (2 * this.radius));
    }
    
    public Boolean isCoveredByNeighbor() {
        double sum = 0.0;
        for (int i=0; i<this.neighborList.size(); i++) {
            // Tính tổng các góc hỗ trợ tâm của các node neighbor đối với node đang xét
            Sensor x = this.neighborList.get(i);
            if (x.getState() == true) {
                sum = sum + this.calc_central_angle(x);
            }
        }
        // Nếu tống các góc hỗ trợ tâm >= 360 độ thì node đang xét được che phủ hoàn toàn 
        if (sum >= (Math.PI * 2)) {
            return true;
        }
        else {
            return false;
        }
    }
    
    
   
}
