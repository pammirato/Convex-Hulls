package chanFinal;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;


/**
 *
 * @author Phil
 */
public class GrahamScan {
    
    //private ArrayList<Point> finalHull;
    
    
    public GrahamScan(){
        
    }
    
    public static ArrayList<Point> findConvexHull(ArrayList<Point> pointSet){
        ArrayList<Point> ch = new ArrayList<Point>();
        
        //get the bottommost point
        Point bottomRight = getBottomRight(pointSet);
        //remove this point from the points set
        pointSet.remove(bottomRight);
        //sort the rest of the points around the bottommost point
        //in ascending order by angle
        Collections.sort(pointSet, new PointComparator(bottomRight));
        
        
        //put the bottom point back in front
        pointSet.add(0, bottomRight);
        
                
        ch = scan(pointSet);
        
        for(int i=0;i<ch.size();i++){
            Point k = ch.get(i);{
            k.setIndexInHull(i);
        }
        }
        
        //GrahamScanAnimated animate = new GrahamScanAnimated(pointSet);
        //Thread t = new Thread(animate);
       // t.start();
        /*synchronized (this) {
            try {
                this.wait();

            } catch (InterruptedException ex) {
                Logger.getLogger(GrahamScan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/

        //while(t.isAlive()){
            
        //}
        
        PointEntry.getPointEntry().addConvexHull(ch);
        PointEntry.getPointEntry().getEntryGUI().refresh();
        
        
        //Point p = new Point(bottomRight.getX()-20,500);
        //Chan.findLeftTangent(p, ch);

        return ch;
        
    }
   
    /**
     * preforms Graham Scan on a sorted list of points, assuming the first point
     * in the list is the bottom point.
     * @param pointList
     * @param bottom
     * @return 
     */
    public static ArrayList<Point> scan(ArrayList<Point> pointList){
        
        //a non degenerate hull needs at least three vertices
        if(pointList.size()<=3){
            return pointList;
        }
        
        Iterator<Point>  pointIt = pointList.iterator();
        PointStack stack = new PointStack();
        
        stack.push(pointIt.next());
        stack.push(pointIt.next());
        
        
        
        while(pointIt.hasNext()){
            Point current = pointIt.next();
            scanStep(stack,current);
        }
        
        return stack.getAsList();
    }

    
    
    /**
     * performs one step of the scan, and displays the current hull
     * @param stack
     * @param current
     * @return 
     */
    public static ArrayList<Point> scanStep(PointStack stack,Point current){
        
        //PointEntry.getPointEntry().clearConvexHulls();
        
        Point p1 = stack.peek2();
        Point p2 = stack.peek();

        //if there are duplicate points, skip over the copies,
        //since this will mess up the left tests
        if (current.equals(p2)) {
            current.setDelete(true);
            return null;
        }

        boolean left = Primitives.left(p1, p2, current);

        while (left) {
            stack.pop();
            p1 = stack.peek2();
            p2 = stack.peek();
            left = Primitives.left(p1, p2, current);
        }

        stack.push(current);
        
        PointEntry singleton = PointEntry.getPointEntry();
        singleton.addConvexHull(stack.getAsList());
        singleton.getEntryGUI().refresh();
        singleton.removeLastHull();

        
        return stack.getAsList();
    }
    
    
    
     /*
     * returns the bottommost point in a point set, breaking ties by picking
     * the leftmost
     */
    public static Point getBottomRight(ArrayList<Point> pointSet){
        Point bottom = pointSet.get(0);
        Iterator<Point> pointIt = pointSet.iterator();
        
        while(pointIt.hasNext()){
            Point next = pointIt.next();
            if(next.getY() < bottom.getY()){
                bottom = next;
            }
            else if(next.getY() == bottom.getY()){
                if(next.getX() > bottom.getX()){
                    bottom = next;
                }
            }
        }
        
        return bottom;
    }
}//end of class
