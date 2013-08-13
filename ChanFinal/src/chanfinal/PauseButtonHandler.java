/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chanFinal;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;


/**
 *
 * @author Phil
 */
public class PauseButtonHandler implements ActionListener {
    
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton)e.getSource();
        String name = button.getText();
        
        
        if(name.equals(Settings.PAUSE_TEXT)){
            button.setText(Settings.UNPAUSE_TEXT);
            ChanAnimated.pause();
            
        }
        else{
            button.setText(Settings.PAUSE_TEXT);
            ChanAnimated.unpause();
        }
        
    }
    
}
