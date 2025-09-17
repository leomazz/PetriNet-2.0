/*
 * JanelaInterna.java
 *
 * Created on 20 de Abril de 2006, 00:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import petrinet10.gui.controle.Controle;

/**
 *
 * @author _Renatu
 */
public class JanelaInterna extends JInternalFrame implements ComponentListener {
    
    PetriPanel petripanel;
    
    /** Creates a new instance of JanelaInterna */
    public JanelaInterna(String titulo) {
        super(titulo);
        setSize(new Dimension(400,300));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        petripanel = new PetriPanel();
        petripanel.setPreferredSize(new Dimension(1200,1200));
        JScrollPane jsp = new JScrollPane();
        jsp.setViewportView(petripanel);
        jsp.setPreferredSize(new Dimension(300,300));
        getContentPane().add(jsp);
        addComponentListener(this);
    }
    
    public PetriPanel getPetriPanel() {
        return this.petripanel;
    }

    public void componentHidden(ComponentEvent componentEvent) {
        Controle.getControle().repintarJanelaInterna();
    }

    public void componentMoved(ComponentEvent componentEvent) {
        Controle.getControle().repintarJanelaInterna();
    }

    public void componentResized(ComponentEvent componentEvent) {
        Controle.getControle().repintarJanelaInterna();
    }

    public void componentShown(ComponentEvent componentEvent) {
        Controle.getControle().repintarJanelaInterna();
    }

}
