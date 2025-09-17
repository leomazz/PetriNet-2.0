/*
 * Teste3.java
 *
 * Created on 12 de Abril de 2006, 16:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import petrinet10.core.Arco;
import petrinet10.core.ArcoInibidor;
import petrinet10.core.ArcoTeste;
import petrinet10.core.Lugar;
import petrinet10.core.LugarAbstrato;
import petrinet10.core.PetriNet;
import petrinet10.core.PetriNetListener;
import petrinet10.core.Transicao;
import petrinet10.core.TransicaoDisparavel;
import petrinet10.core.TransicaoTemporizada;

/**
 *
 * @author _Renatu
 */
public class Teste3 extends JFrame implements ActionListener, PetriNetListener {
    
    JTextArea ta;
    PetriNet pn;
    TransicaoDisparavel t1;
    JButton b1, b2;
    
    /** Creates a new instance of Teste3 */
    public Teste3() {
        super("Teste3");
        setSize(400,400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ta = new JTextArea();
        getContentPane().add("Center", ta);
        b1 = new JButton("t1");
        b2 = new JButton("t4");
        b1.addActionListener(this);
        b2.addActionListener(this);
        JPanel p = new JPanel();
        p.add("West", b1);
        p.add("East", b2);
        getContentPane().add("South", p);
        
        pn = new PetriNet("Liga/Desliga Temporizado");
        pn.setPetriNetListener(this);
        
        Lugar l1 = new Lugar("L1");
        Lugar l2 = new Lugar("L2");
        Lugar l3 = new Lugar("L3");
        Lugar l4 = new Lugar("L4");
        l1.setTokens(1);
        l4.setTokens(1);
        
        t1 = new TransicaoDisparavel("T1");
        Transicao t2 = new Transicao("T2");
        Transicao t3 = new Transicao("T3");
        TransicaoTemporizada t4 = new TransicaoTemporizada("T4", 5000);
        
        Arco a1 = new Arco(l1, t1, 1, Arco.LUGAR_TRANSICAO);
        Arco a2 = new Arco(l2, t1, 1, Arco.TRANSICAO_LUGAR);
        Arco a3 = new Arco(l2, t3, 1, Arco.LUGAR_TRANSICAO);
        Arco a4 = new Arco(l1, t2, 1, Arco.TRANSICAO_LUGAR);
        Arco a5 = new Arco(l2, t2, 1, Arco.LUGAR_TRANSICAO);
        ArcoInibidor a6 = new ArcoInibidor(l2, t4, 1);
        Arco a7 = new Arco(l3, t3, 1, Arco.TRANSICAO_LUGAR);
        Arco a8 = new Arco(l3, t4, 1, Arco.LUGAR_TRANSICAO);
        Arco a9 = new Arco(l4, t4, 1, Arco.TRANSICAO_LUGAR);
        Arco a10 = new Arco(l4, t3, 1, Arco.LUGAR_TRANSICAO);
        ArcoTeste a11 = new ArcoTeste(l3, t2, 1);
        Arco a12 = new Arco(l1, t3, 1, Arco.TRANSICAO_LUGAR);
        
        pn.addLugar(l1);
        pn.addLugar(l2);
        pn.addLugar(l3);
        pn.addLugar(l4);
        pn.addTransicao(t1);
        pn.addTransicao(t2);
        pn.addTransicao(t3);
        pn.addTransicao(t4);
        pn.addArco(a1);
        pn.addArco(a2);
        pn.addArco(a3);
        pn.addArco(a4);
        pn.addArco(a5);
        pn.addArco(a6);
        pn.addArco(a7);
        pn.addArco(a8);
        pn.addArco(a9);
        pn.addArco(a10);
        pn.addArco(a11);
        pn.addArco(a12);
        ArrayList al = pn.getLugares();
        for (int i=0; i<al.size(); i++) {
            LugarAbstrato la = (LugarAbstrato)al.get(i);
            ta.append("Lugar: " + la.getNome() + ": " + la.getTokens() + " tokens\n");
        }
    }
    
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource()==b1) {
            t1.disparar();
            while(pn.passo());
            ta.setText("");
            ArrayList al = pn.getLugares();
            for (int i=0; i<al.size(); i++) {
                LugarAbstrato la = (LugarAbstrato)al.get(i);
                ta.append("Lugar: " + la.getNome() + ": " + la.getTokens() + " tokens\n");
            }
        } else {
            ArrayList al = pn.getLugares();
            for (int i=0; i<al.size(); i++) {
                LugarAbstrato la = (LugarAbstrato)al.get(i);
                ta.append("Lugar: " + la.getNome() + ": " + la.getTokens() + " tokens\n");
            }
        }
    }
    
    public void redeModificada() {
        ArrayList al = pn.getLugares();
        ta.setText("");
        for (int i=0; i<al.size(); i++) {
            LugarAbstrato la = (LugarAbstrato)al.get(i);
            ta.append("Lugar: " + la.getNome() + ": " + la.getTokens() + " tokens\n");
        }
    }
}
