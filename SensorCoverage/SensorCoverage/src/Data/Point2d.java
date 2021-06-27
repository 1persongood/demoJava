/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author Admin
 */
public class Point2d {
    int x;
    int y;
    int radius;
    
    Point2d() {

    }

    Point2d(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public String toString() {
        return "--x_coords: " + this.x + "--y_coords: " + this.y + "--Sensing radius: " + this.radius;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
