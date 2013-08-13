/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chanFinal;


import java.awt.Color;


/**
 *
 * @author Phil
 */
public class Point {
    private double x;
    private double y;
    private int indexInHull;
    private boolean delete;
    private Color color;
    private int diameter;
    
    public Point(){
        delete = false;
        color = Color.BLACK;
        diameter = Settings.POINT_DIAMETER;
    }
    
    public Point(double x, double y){
        this.x=x;
        this.y=y;
        delete = false;
        color = Color.BLACK;
        diameter = Settings.POINT_DIAMETER;
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public boolean getDelete(){
        return delete;
    }
    
    public Color getColor(){
        return color;
    }
    
    public int getDiameter(){
        return diameter;
    }
    
    public int getIndexInHull(){
        return indexInHull;
    }
    
    public void setX(double x){
        this.x=x;
    }
    
    public void setY(double y){
        this.y=y;
    }
    
    public void setIndexInHull(int i){
        indexInHull = i;
    }
    
    public void setDelete(boolean delete){
        this.delete = delete;
    }
    
    public void setColor(Color c){
        color = c;
    }
    
    public void setDiamter(int diam){
        this.diameter = diam;
    }
 
    /**
     * returns true if two Points hae the same x and y values
     * @param p1
     * @param p2
     * @return 
     */
    public boolean equals(Point p1){
        
        if(p1.getX()==this.getX() && p1.getY()==this.getY()){
            return true;
        }
        return false;
    }
}
