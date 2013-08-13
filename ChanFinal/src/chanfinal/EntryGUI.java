/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chanFinal;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;


/**
 *
 * @author Phil
 */
public class EntryGUI extends JPanel{
    
    //displays all the user inputed points
    PointCanvas pointCanvas;
   
    //has the vuttons for user control
    JPanel buttonPanel;
    JButton startButton; 
    JButton clearButton;
    JButton pauseButton;
    
    

    
    
    public EntryGUI(){
        
        initComponents();
        initHandlers();
        initLayout();
    }
    
    //initializes all the components
    private void initComponents(){
        
        
        
        pointCanvas = new PointCanvas();
        pointCanvas.setBackground(Color.GRAY);
        
        buttonPanel = new JPanel();
        startButton = new JButton("START");
        clearButton = new JButton("CLEAR");
        pauseButton = new JButton(Settings.PAUSE_TEXT);
        
        
        buttonPanel.add(startButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(pauseButton);
        
    }
    
    //sets up all event handlers
    private void initHandlers(){
        //for when a user clicks in a point
        PointEntryHandler peh = new PointEntryHandler();
        pointCanvas.addMouseListener(peh);
        
        StartButtonHandler sbh = new StartButtonHandler();
        startButton.addActionListener(sbh);
        
        ClearButtonHandler ch = new ClearButtonHandler();
        clearButton.addActionListener(ch);
        
        PauseButtonHandler ph = new PauseButtonHandler();
        pauseButton.addActionListener(ph);
      
    }
    
    //places all components in frame
    private void initLayout(){
        setLayout(new BorderLayout());
        
        add(pointCanvas,BorderLayout.CENTER);
        add(buttonPanel,BorderLayout.SOUTH);
    }
    
    public void setCanvasBackground(Color c){
        
        pointCanvas.setBackground(c);
        
    }

    
    public void addPoint(Point p){
        pointCanvas.addPoint(p);
    }
    
    public void refresh(){
        pointCanvas.repaint();
    }

}
