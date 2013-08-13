/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chanFinal;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author Phil
 */
public class StartButtonHandler implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        PointEntry singleton = PointEntry.getPointEntry();
        

        singleton.trimSet();

        
        ChanAnimated.runChan(singleton.getPointSet());

    }
    
}
