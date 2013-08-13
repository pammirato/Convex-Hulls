/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chanFinal;

import java.util.Comparator;


/**
 *
 * @author Phil
 */
public class PointComparator implements Comparator {
    
    Point p0;
    
    PointComparator(Point p0){
        this.p0 = p0;
    }

    /*
     * sorts points by angle around given point p0. 
     * sorts using left tests, if three points are collinear,
     * it marks the point closer to p0 for deltion
     */
    @Override
    public int compare(Object o1, Object o2) {
      Point p1 = (Point)o1;
      Point p2 = (Point)o2;
      
      //get the SIGNED area of the triangle defined by the three points
      int area2 = Primitives.area2(p0, p1, p2);
      
      
      if( area2 >0){
          return 1;  //p2 is left of line p0p1, so p1 has a greater angle
      }
      //if the points are all collinear, mark one for deletion
      else if(area2 == 0){
          //since we are always sorting around the bottommost, leftmost point,
          //the point with the greater y-value is the point further away.
          if(p1.getY() < p2.getY())
              p1.setDelete(true);
          else
              p2.setDelete(true);
      }
      
      //otherwise here p2 is left or collinear with p0p1, so p1<=p2
      //so return -1 becuse one of them will be deleted anyway
      return -1;
    }
    
}
