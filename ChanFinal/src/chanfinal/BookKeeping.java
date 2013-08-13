/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chanFinal;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;


/**
 *
 * @author Phil
 */
public class BookKeeping {

    /**
     * Splits a set of points in a given ArrayList of points into n/m groups
     * of size m each(where n is the total number of points in the original set)
     * and returns them as an ArrayList of ArrayLists.
     * Like a double pointer in C.
     * @param m
     * @return 
     */
    public static ArrayList<ArrayList<Point>> splitPoints(ArrayList<Point> originalPointSet,int m){
        ArrayList<ArrayList<Point>> pointSets = new ArrayList<ArrayList<Point>>();
        
       int n = originalPointSet.size();
       
       int groups = (n/m) + 1; //better to have an extra group than be short
       
       //maybe check n%m, to see if we really need that extra group
       
       
       int totalCount = 0;  //counts the total number of points divided so far
       Point nextPoint = new Point();
       
       int red = 1;
       int green = 1;
       int blue = 1;
       Color color = new Color(red,green,blue);
       
       while(groups-->0){
           ArrayList<Point> nextList = new ArrayList<Point>();
           for (int i = 0; i<m; i++) {
               if(totalCount>=(n)){
                   break;
               }
               ///boolean badPoint =false;
               int k = totalCount;
               do{
                  nextPoint = originalPointSet.get(k++);
               }while(nextPoint.getDelete());
               
               totalCount++;
               nextPoint.setColor(color);
               nextList.add(nextPoint);
           }
           pointSets.add(nextList);
           
           //make each point set a different color
           red = (red+77)%255;
           green = (green+57)%255;
           blue = (blue+37)%255;
           color = new Color(red,blue,green);
       }
        
        return pointSets;
    }//splitPoints
    
    
    
    public static ArrayList<Point> trimSet(ArrayList<Point> pointSet){
        
        ArrayList<Point> newSet = new ArrayList();
        
        markForDeletion(pointSet);
        
        Iterator<Point> pointIt = pointSet.iterator();
        
        
        while(pointIt.hasNext()){
            Point p1 = pointIt.next();
            if(p1.getDelete()){
                continue;
            }
            newSet.add(p1);
        }
        
        return newSet;
    }
    
    
    public static void markForDeletion(ArrayList<Point> pointSet){
        
        Iterator<Point> pointIt = pointSet.iterator();
        
        Point p1 = pointIt.next();
        Point p2;
        
        while(pointIt.hasNext()){
            p2 = pointIt.next();
            
            if((p1.getX()==p2.getX())  && (p1.getY()==p2.getY())){
                p1.setDelete(true);
            }
            p1 = p2;
        }
    }//mark for deletion
    
}
