/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chanFinal;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *Clears all points from the GUI and from the program.
 * @author Phil
 */
public class ClearButtonHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        PointEntry singleton = PointEntry.getPointEntry();
        
        singleton.reset();
        
        
    }
    
}
