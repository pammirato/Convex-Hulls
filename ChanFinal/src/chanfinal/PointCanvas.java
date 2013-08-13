/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chanFinal;



import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JPanel;


/**
 *
 * @author Phil
 */
public class PointCanvas extends JPanel{

    
    
    PointCanvas(){    }
    
    @Override protected void paintComponent(Graphics g){
        super.paintComponent(g);
        
        Graphics2D g2D = (Graphics2D) g;
        //Draw all the points
        PointEntry singleton = PointEntry.getPointEntry();
        ArrayList<Point> pointList = singleton.getPointSet();
        Iterator<Point> pointIt = pointList.iterator();
        
        //show the points on the hull of the entire point set
        ArrayList<Point> mainHull = singleton.getMainHull();
        if(mainHull != null){
            for(int i=0;i<mainHull.size();i++){
                Point nextPoint = mainHull.get(i);
                nextPoint.setDiamter(2*Settings.POINT_DIAMETER);
            }
        }
        
        //draw the lines of the main hull 3 times as thick as the other hulls
        BasicStroke normalStroke =(BasicStroke) g2D.getStroke();
        float width = normalStroke.getLineWidth();
        width *= 3;
        BasicStroke thickStroke = new BasicStroke(width);
        g2D.setStroke(thickStroke);
        
        Iterator<Point> mainIt = mainHull.iterator();
        Point first=null;
        if(mainIt.hasNext())
          first = mainIt.next();
        Point second;
        while(mainIt.hasNext()){
            second = mainIt.next();
            drawLine(first,second,g);
            first = second;
        }
        
        g2D.setStroke(normalStroke);//reset the line thickness
        
        
        
        //show all the points
        while(pointIt.hasNext()){
            Point p = pointIt.next();
            //if the point is marked for deletion, do not render it
            if(p.getDelete()){
                continue;
            }
            g.setColor(p.getColor());
            g.fillOval((int)p.getX(),(int)p.getY(),p.getDiameter(),p.getDiameter());
        }//while ponitIt
        
        
        //now draw in the lines of the convex hulls, if there are any
        ArrayList<ArrayList<Point>> hulls = PointEntry.getPointEntry().getHulls();
        if(hulls!=null){
            int numHulls = hulls.size();
            ArrayList<Point> currentHull;
            
            //for each hull
            for(int i =0;i<numHulls;i++){
                currentHull = hulls.get(i);
                if(currentHull.size()<1){
                    continue;
                }
                Point p1 = currentHull.get(0);
                Point p2;
                g.setColor(p1.getColor());
                
               
                
                int x1;
                int x2;
                int y1;
                int y2;
                
                //for each point in the hull, but the last, draw a line from
                //thatp point to the next point in the hull
                for(int j=0;j<currentHull.size()-1;j++){
                    p1 = currentHull.get(j);
                    p2 = currentHull.get(j+1);
                    drawLine(p1,p2,g);
                }
                
                //connect the last point to the first point
                p1 = currentHull.get(currentHull.size()-1);
                p2 = currentHull.get(0);
                drawLine(p1,p2,g);
            }
        }
        

        
    }//paintComp
    
    
    
    public void addPoint(Point p){
        PointEntry singleton = PointEntry.getPointEntry();
        ArrayList<Point> pointList = singleton.getPointSet();
        pointList.add(p);
        repaint();
    }
    
    public void paint(Graphics g){
        paintComponent(g);
    }
    
    
    private void drawLine(Point p1, Point p2, Graphics g){
                   
                    int centerOffset1 = (int) (p1.getDiameter() / 2); //so the line is in the middle of the point
                    int centerOffset2 = (int) (p2.getDiameter() / 2);
                     //g.drawString(Integer.toString(j), (int)p1.getX(), (int)p1.getY());
                    int x1 = (int)p1.getX()+centerOffset1;
                    int y1 = (int)p1.getY()+centerOffset1;
                    int x2 = (int)p2.getX()+centerOffset2;
                    int y2 = (int)p2.getY()+centerOffset2;
                    g.drawLine(x1,y1,x2,y2);
    }
}
