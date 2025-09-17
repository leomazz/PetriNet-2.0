/*
 * Main.java
 *
 * Created on 4 de Abril de 2006, 15:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import petrinet10.gui.controle.Controle;

/**
 *
 * @author _Renatu
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here;
        /*
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (InstantiationException e) {
        } catch (ClassNotFoundException e) {
        } catch (UnsupportedLookAndFeelException e) {
        } catch (IllegalAccessException e) {
        }*/
        Controle.getControle().inicializar();
    }
    
}
