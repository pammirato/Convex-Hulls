/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chanFinal;


import java.util.ArrayList;

/**
 *
 * @author Phil
 */
public class PointStack {

    private ArrayList<Point> pointList;
    
    public PointStack(){
        pointList = new ArrayList<Point>();
    }
    
    public ArrayList<Point> getAsList(){
        return pointList;
    }
    
    public void push(Point p){
        pointList.add(pointList.size(),p);
    }
    
    public Point pop(){
        if(pointList.size() > 0){
            Point p = pointList.get(pointList.size()-1);
            pointList.remove(pointList.size()-1);
            return p;
        }
        else return null;
    }
    
    public Point peek(){
        if(pointList.size() >0)
          return pointList.get(pointList.size()-1);  
        else return null;
    }

    public Point peek2(){
        if(pointList.size() >1)
          return pointList.get(pointList.size()-2);  
        else return null;
    }
    
}
