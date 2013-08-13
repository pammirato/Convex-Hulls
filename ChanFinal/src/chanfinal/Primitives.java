/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chanFinal;




/**
 *
 * @author Phil
 */
public class Primitives {

    
    /*
     * returns true if point c is to the left of the line ab
     */
    public static boolean left(Point a, Point b, Point c){
        
        if (area2(a,b,c)>0){
            return true;
        }
        return false;
    }
    
    /*
     * return twice the SIGNED area of the triangle defined by the three points
     * using a cross product
     */
    public static int area2(Point a, Point b, Point c){
        int area2;
        
        
        double first = (b.getX() - a.getX()) * (c.getY() - a.getY());
        double second = (c.getX() - a.getX()) * (b.getY() - a.getY());
        area2 = (int) (first - second);
        
        return area2;
        
    }
}
