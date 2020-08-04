/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package halcyon.ide;

import java.io.File; 

/**
 *
 * @author s4n7h0
 */
public class HalcyonIDE {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // load configuration 
        ConfigurationManager cm = new ConfigurationManager();
        cm.init(); 
        //cm.LoadConfiguration();  
        // load panel
        MainPanel mp = new MainPanel(cm);
        mp.setVisible(true);
    }

   
    
}
