/*
 * PetriPopUp.java
 *
 * Created on 20 de Novembro de 2006, 16:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import petrinet10.gui.controle.Controle;

/**
 *
 * @author _Renatu
 */
public class PetriPopUp extends JPopupMenu implements ActionListener {
    
    private PetriPanel painel;
    private JMenuItem enviarparatras, trazerparafrente;
    private JMenuItem avancar, recuar;
    
    /** Creates a new instance of PetriPopUp */
    public PetriPopUp(PetriPanel painel) {
        this.painel = painel;
        ArrayList selecionados = painel.getSelecionados();
        if (selecionados.size()>0) {
            enviarparatras = new JMenuItem("Enviar Para Trás");
            enviarparatras.addActionListener(this);
            this.add(enviarparatras);
            trazerparafrente = new JMenuItem("Trazer Para Frente");
            trazerparafrente.addActionListener(this);
            this.add(trazerparafrente);
            avancar = new JMenuItem("Avancar");
            avancar.addActionListener(this);
            this.add(avancar);
            recuar = new JMenuItem("Recuar");
            recuar.addActionListener(this);
            this.add(recuar);
        }
    }
    
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source==enviarparatras) {
            Controle.getControle().enviarParaTras();
        } else if (source==trazerparafrente) {
            Controle.getControle().trazerParaFrente();
        } else if (source==avancar) {
            Controle.getControle().avancar();
        } else if (source==recuar) {
            Controle.getControle().recuar();
        }
        Controle.getControle().atualizarGerenciador();
        Controle.getControle().atualizarPropriedades();
    }
    
    
    
}
