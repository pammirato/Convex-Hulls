/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chanFinal;



import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;


/**
 *
 * @author Phil
 */
public class PointEntryHandler extends MouseAdapter{
    
    public void mousePressed(MouseEvent e){
        PointEntry singleton = PointEntry.getPointEntry();
        EntryGUI gui = singleton.getEntryGUI();
        
        Point2D point = (Point2D)e.getPoint();
        Point p = new Point();
        p.setX(point.getX());
        p.setY(point.getY());
        gui.addPoint(p);
    }
    
}
