/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chanFinal;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;


/**
 *
 * @author Phil
 */
public class ChanAnimated {
    
    //All instance variables,  everything is static
    
    //controls animation for switching betwen m=4,16,...
    private static Timer mTimer;
    //controls animation for wrapping the hulls for a given m
    private static Timer wrapTimer;
    
    private static int m = 2;//we sqaure m before start, so m will be 4,16,...
    private static int wrapCounter = 0;
    
    private static ArrayList<Point> pointSet;
    private static ArrayList<ArrayList<Point>> hulls;
    
    private static Point bottom;
    private static Point currentHullPoint;
    
    private static ArrayList<Point> currentHull=null;
    private static int lastHullPointIndex;
    
    private static boolean wrapRunning;
    private static boolean mRunning;
    
    /*
     * pauses the animation
     */
    public static void pause(){
        if(wrapTimer.isRunning()) {
            wrapRunning = true;
            wrapTimer.stop();
        }
        if(mTimer.isRunning()) {
            mRunning = true;
            mTimer.stop();
        }        
        
        
    }
    
    /*
     * resumes the animation
     */
    public static void unpause(){
        if(mRunning){
          mTimer.start();
        }
        if(wrapRunning){
          wrapTimer.start();
        }
    }
    
    
    /*
     * resets the counters for a new point set
     */
    public static void reset(){
        m = 2;
        wrapCounter = 0;
    }
    
    /*
     * **********************************************************8
     * driver method, runs the chan algorithm using a lot of subroutines
     */
    public static void runChan(ArrayList<Point> set){
        
        // get the set of inoutted points
        ChanAnimated.pointSet = BookKeeping.trimSet(set);
        
        
        /*
         * SET UP THE mTimer  - if a wrap is not running, square m
         *                       call mStep, which computes convex hullls 
         *                        of the new groups
         *                       then rerun wrap
        */
        int mDelay = 2000; //milliseconds
        ActionListener mTaskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if(!wrapTimer.isRunning()){
                    PointEntry.getPointEntry().clearMainHull();
                    PointEntry.getPointEntry().clearConvexHulls();
                    m = m*m;
                    ChanAnimated.hulls = mStep(m,ChanAnimated.pointSet);
                    PointEntry.getPointEntry().setConvexHulls(hulls);
                    PointEntry.getPointEntry().getEntryGUI().refresh();
                    bottom = getBottomMost(hulls);
                    PointEntry.getPointEntry().addToMainHull(bottom);
                    currentHullPoint = bottom;
                    wrapCounter = 0;
                    wrapTimer.start();
                }
            }
        };
        
        /*
         * SET UP WwrapTimer  - call wrapStep, which finds the next tangent
         *                      update GUI
         */
        int wrapDelay = 500; //milliseconds
        ActionListener wrapTaskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if(wrapCounter >= m-2){ //STOP IF WE ARE DONE WRAPPING
                    wrapTimer.stop();
                }
                Point nextHullPoint = wrapStep(currentHullPoint,hulls);
                PointEntry.getPointEntry().addToMainHull(nextHullPoint);
                currentHullPoint = nextHullPoint;
                wrapCounter++;
                if(currentHullPoint == bottom){
                   mTimer.stop();
                   PointEntry singleton = PointEntry.getPointEntry();
                   singleton.addToMainHull(singleton.getMainHull().get(0));//put the first point as the last 
                   singleton.getEntryGUI().refresh();
                   wrapTimer.stop();
                }
                if(wrapCounter >= m-1){//STOP IF WE ARE DONE WRAPPING
                    wrapTimer.stop();
                }             
                PointEntry.getPointEntry().getEntryGUI().refresh();
            }
        };
        
        
       
        mTimer = new Timer(mDelay,mTaskPerformer);
        wrapTimer = new Timer(wrapDelay,wrapTaskPerformer);
        
        mTimer.start();
        
    }//runChan
    
    
    
    /*
     * Does the pre-wrap setup forr a given value of m
     * 
     * namely it splits the point set into n/m groups, and finds each
     * groups convex hull via GrahamScan
     */
    public static ArrayList<ArrayList<Point>> mStep(int m,ArrayList<Point> pointSet){
        //split the point sets
        ArrayList<ArrayList<Point>> splitSets = BookKeeping.splitPoints(pointSet, m);
        //holds the convex hull of each group of points
        ArrayList<ArrayList<Point>> hulls = new ArrayList();
        
        //find the convex hull of each of the n/m groups
        for (int i = 0; i < splitSets.size(); i++) {
            ArrayList<Point> currentSet = splitSets.get(i);
            if (currentSet.size() > 0) {
                ArrayList<Point> nextHull = GrahamScan.findConvexHull(currentSet);
                hulls.add(nextHull);
            }
        }//for
        
        return hulls;
    }
    
    
    
    
    /**
     * takes a list of convex hulls and gift wraps them to find the hull
     * of their union. Wraps at most m points.
     * @param hulls 
     * @param m
     */
    /*
    public static boolean wrapHulls(ArrayList<ArrayList<Point>> hulls, int m){
        
        Point bottom = getBottomMost(hulls);
        bottom.setDiamter(4*POINT_DIAMETER);
        
        Point current = bottom;
        Point mostLeft;
        
 
        for (int i = 0; i < m; i++) {
          mostLeft =  wrapStep(current,hulls); 
        
          PointEntry.getPointEntry().addToMainHull(mostLeft);
          current = mostLeft;
          mostLeft = null;
            
          if(current == bottom){
              return true;
          }

        }//for m
        
        return false;
    }
    */
   
    
    public static Point wrapStep(Point current,ArrayList<ArrayList<Point>> hulls){
        
        Point mostLeft = null;
        for (int j = 0; j < hulls.size(); j++) {
            Point tan = null;
            
            //if the hull we are checking has the last point we added to the main hull
            //then just get the next point on that hull, otherwise find tangent
           if(currentHull!=null){
               if(currentHull.get(0).equals(hulls.get(j).get(0))){
                   tan = currentHull.get((current.getIndexInHull()+1)%currentHull.size());
               }
               else{
                   tan = findTangent(current, hulls.get(j));   
               }
           } 
           else{
             tan = findTangent(current, hulls.get(j));   
           }   
            
           System.out.println(tan);
            //the left tangent of the hull current is on may be current,
            //and so this may currupt future left tests
            if (tan == current) {
                continue;
            }
            if (mostLeft == null) {
                mostLeft = tan;
                currentHull = hulls.get(j);
            } 
            else {
                if (Primitives.left(current, mostLeft, tan)) {
                    mostLeft = tan;
                    currentHull = hulls.get(j);
                }
            }
        }//for each hull
        return mostLeft;
    }//wrapStep
    
    
    
    
    
    
    
    
    
    
   
    
    
    
    
    
    
    
    
    
    
      /**
     * Finds the point on the hull, q, such that the entire hull is left of pq
     * in log(m) time, where m = size of hull
     * 
     * the hull is oriented clockwise with the lowest(rightmost to break ties) point at index 0;
     * @param p
     * @param hull
     * @return 
     */
    public static Point findTangent(Point p, ArrayList<Point> hull){
        
        
        int mid = hull.size()/2;
        int upperBound  = hull.size();
        int lowerBound = 0;
        int lastMid = mid;
        boolean leftChain;
        boolean rightChain;
        Point right = null;
        Point left = null;
        
        while(mid>=0){
            Point midPoint = hull.get(mid);
            int rightIndex = 0;
            int leftIndex = 0;
            if(mid == 0){
              rightIndex = (hull.size()-1);
            }
            else{
              rightIndex = (mid-1);    
            }
            leftIndex = ((mid+1)%(hull.size()));
            
            right = hull.get(rightIndex);
            left = hull.get(leftIndex);
            
            
            //if the point we are trying to find the tangent to is on this hull,
            //then its left tangent is just the next point on the hull
            if(midPoint.equals(p)){
                return hull.get((mid+1)%hull.size());
            }
            else if(right.equals(p)){
                return hull.get((rightIndex+1)%hull.size());
            }
            else if(left.equals(p)){
                return hull.get((leftIndex+1)%hull.size());
            }
  
            rightChain = Primitives.left(p, midPoint, right);
            leftChain = Primitives.left(p, midPoint, left);
            
            //if both points were right, then we found the left tangent
            if((!rightChain) && (!leftChain)){
                return midPoint;
            }
             
           
            //can not be true if mid==0 HAHAHAHAHAHAHAHAHAHAHAHAHA
            //tangent must a point of lower index
            if (rightChain && !leftChain) {
                
                if(mid == 0){
                    lowerBound = hull.size()/2 + 1;
                    upperBound = hull.size();
                    if(mid == lowerBound){
                        mid = lowerBound + (int)Math.ceil((upperBound-lowerBound)/2);
                    }
                    else{
                       mid = lowerBound + (upperBound-lowerBound)/2;     
                    }
                   
                }
                else{
                  upperBound = mid;
                  mid = lowerBound + (upperBound-lowerBound)/2;
                }
            }
            
            //go to the a higher index, shouldnt be 0
            else if(leftChain && !rightChain){
                lowerBound = mid;
                if(mid==upperBound){
                  mid = lowerBound + (upperBound-lowerBound)/2;  
                }
                else{
                  mid = lowerBound + (int)Math.ceil((upperBound-lowerBound)/2.0);   
                }
                if(mid==hull.size()){
                    return hull.get(0);
                }
            }
            //if left is left of p right, go left
            else if (Primitives.left(p, right, left)){
                lowerBound = mid;
                if(mid==upperBound){
                  mid = lowerBound + (upperBound-lowerBound)/2;  
                }
                else{
                    
                  mid = lowerBound + (int)Math.ceil((upperBound-lowerBound)/2.0);   
                }
                if(mid==hull.size()){
                    return hull.get(0);
                }
            }
            else{
                upperBound = mid;
                mid = lowerBound + (upperBound-lowerBound)/2;
            }

            
        }//while
                     
        return null;
    }//findLeftTangent
    

    
    
    /**
     * returns the bottomost point of all the points on all the hulls
     * ****************** assumes each hull has its bottommost point in 
     * ****************** as its first point
     * @return 
     */
    public static Point getBottomMost(ArrayList<ArrayList<Point>> hulls){
        Point bottom = hulls.get(0).get(0); //just a starting point
        
        for(int i=0;i<hulls.size();i++){
            Point temp = hulls.get(i).get(0);
            if(temp.getY()< bottom.getY()){
                bottom = temp;
            }
            if(temp.getY()==bottom.getY()){
                if(temp.getX()>bottom.getX()){
                    bottom=temp;
                }
            }
        }
        
        return bottom;
    }//get bottommost

}//class
