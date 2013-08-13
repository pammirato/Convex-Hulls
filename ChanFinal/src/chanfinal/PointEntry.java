/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chanFinal;



import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;


/**
 *Singleton class  holds or has access to all the data
 * @author Phil
 */
public class PointEntry {
    
    
    private static PointEntry singleton;
    private EntryGUI gui;
    private ArrayList<Point> pointSet;//the input data
    private ArrayList<ArrayList<Point>> convexHulls; //hulls of groups of points
    private ArrayList<Point> mainHull;//the acutal convex hull

    
    
    private PointEntry(){
        pointSet = new ArrayList<Point>();
        gui = new EntryGUI();
    }
    
    
    public static PointEntry getPointEntry(){
        if(singleton == null){
            singleton = new PointEntry();
        }
        return singleton;
    }
    
    public EntryGUI getEntryGUI(){
        return gui;
    }
    
    public ArrayList<Point> getPointSet(){
        return pointSet;
    }
    
    /**
     * returns an array of arrays,each one storing the ordered points in a
     * convex hull. returns null if no hull has been added yet.
     * @return 
     */
    public ArrayList<ArrayList<Point>> getHulls(){
        return convexHulls;
    }
    
    public ArrayList<Point> getMainHull(){
        if(mainHull == null){
            mainHull = new ArrayList();
        }
        return mainHull;
    }
    
    public void clearPoints(){
        pointSet.clear();
        convexHulls.clear();
        mainHull.clear();
        gui.refresh();
    }
    
    /**
     * add a hull to the list of hulls for subsets of the data input
     * @param hull 
     */
    public void addConvexHull(ArrayList<Point> hull){
        if(convexHulls==null){
            convexHulls = new ArrayList<ArrayList<Point>>();
        }
        convexHulls.add(hull);
    }
    
    /**
     * add a point to the overall convex hull
     * @param p 
     */
    public void addToMainHull(Point p){
        if(mainHull == null){
            mainHull = new ArrayList();
        }
        mainHull.add(p);
    }
    
    
    public void clearMainHull(){
        if(mainHull == null){
            mainHull = new ArrayList();
        }
        mainHull.clear();
    }
    
    public void clearConvexHulls(){
        if(convexHulls!=null){
            convexHulls.clear();
        }
    }
    
    public void setConvexHulls(ArrayList<ArrayList<Point>> hulls){
        convexHulls = hulls;
    }
    
    
    public void removeLastHull(){
        convexHulls.remove(convexHulls.size()-1);
    }
    
    
    public void trimSet() {
        pointSet = BookKeeping.trimSet(pointSet);
    }
    
    
   /**
    * reset the entire application, so the user can input new data
    */ 
   public void reset(){
       clearPoints();
       clearConvexHulls();
       clearMainHull();
       ChanAnimated.reset();
   }
    
    
    
    
    
    
    
    
    
    /**
     * set up GUI and run program
     */
   
    public static void main(String[] args) {
        PointEntry p = getPointEntry();
        
        p.getEntryGUI().setSize(Settings.FRAME_LENGTH,Settings.FRAME_WIDTH);
        p.getEntryGUI().setVisible(true);
        JFrame frame = new JFrame();
        frame.add(p.getEntryGUI());
        frame.setSize(Settings.FRAME_LENGTH,Settings.FRAME_WIDTH);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
         
    }
    
}
