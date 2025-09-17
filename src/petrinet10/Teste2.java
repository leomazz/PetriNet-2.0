/*
 * Teste2.java
 *
 * Created on 12 de Abril de 2006, 12:44
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
import petrinet10.core.Transicao;
import petrinet10.core.TransicaoDisparavel;

/**
 *
 * @author _Renatu
 */
public class Teste2 extends JFrame implements ActionListener {
    
    JTextArea ta;
    JButton b1, b2;
    PetriNet pn;
    TransicaoDisparavel t1, t2;
    
    /** Creates a new instance of Teste2 */
    public Teste2() {
        super("Teste2");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400,400);
        ta = new JTextArea();
        b1 = new JButton("T1");
        b2 = new JButton("T2");
        getContentPane().add("Center", ta);
        JPanel painel = new JPanel();
        painel.add("West", b1);
        painel.add("East", b2);
        getContentPane().add("South", painel);
        b1.addActionListener(this);
        b2.addActionListener(this);
        
        pn = new PetriNet("Multiplos Estados Com arcos com peso");
        
        Lugar l1 = new Lugar("L1");
        Lugar l2 = new Lugar("L2");
        Lugar l3 = new Lugar("L3");
        Lugar l4 = new Lugar("L4");
        l1.setTokens(1);
        l4.setCapacidade(10);
        
        t1 = new TransicaoDisparavel("T1");
        t2 = new TransicaoDisparavel("T2");
        Transicao t3 = new Transicao("T3");
        Transicao t4 = new Transicao("T4");
        Transicao t5 = new Transicao("T5");
        Transicao t6 = new Transicao("T6");
        
        Arco a1 = new Arco(l1, t1, 1, Arco.LUGAR_TRANSICAO);
        Arco a2 = new Arco(l1, t2, 1, Arco.LUGAR_TRANSICAO);
        Arco a3 = new Arco(l2, t1, 1, Arco.TRANSICAO_LUGAR);
        Arco a4 = new Arco(l3, t2, 1, Arco.TRANSICAO_LUGAR);
        Arco a5 = new Arco(l2, t5, 1, Arco.LUGAR_TRANSICAO);
        Arco a6 = new Arco(l3, t6, 1, Arco.LUGAR_TRANSICAO);
        Arco a7 = new Arco(l4, t5, 1, Arco.LUGAR_TRANSICAO);
        Arco a8 = new Arco(l4, t6, 1, Arco.TRANSICAO_LUGAR);
        ArcoInibidor a9 = new ArcoInibidor(l4, t3, 1);
        ArcoTeste a10 = new ArcoTeste(l4, t4, 10);
        Arco a11 = new Arco(l1, t5, 1, Arco.TRANSICAO_LUGAR);
        Arco a12 = new Arco(l1, t4, 1, Arco.TRANSICAO_LUGAR);
        Arco a13 = new Arco(l2, t3, 1, Arco.LUGAR_TRANSICAO);
        Arco a14 = new Arco(l3, t4, 1, Arco.LUGAR_TRANSICAO);
        Arco a15 = new Arco(l1, t3, 1, Arco.TRANSICAO_LUGAR);
        Arco a16 = new Arco(l1, t6, 1, Arco.TRANSICAO_LUGAR);
        
        pn.addLugar(l1);
        pn.addLugar(l2);
        pn.addLugar(l3);
        pn.addLugar(l4);
        pn.addTransicao(t1);
        pn.addTransicao(t2);
        pn.addTransicao(t3);
        pn.addTransicao(t4);
        pn.addTransicao(t5);
        pn.addTransicao(t6);
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
        pn.addArco(a13);
        pn.addArco(a14);
        pn.addArco(a15);
        pn.addArco(a16);
        
        while(pn.passo());
        
        ArrayList lugares = pn.getLugares();
        for (int i=0; i<lugares.size(); i++) {
            LugarAbstrato la = (LugarAbstrato)lugares.get(i);
            ta.append("Lugar: " + la.getNome() + ": " + la.getTokens() + " tokens\n");
        }
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource()==b1) {
            t1.disparar();
        } else {
            t2.disparar();
        }
        while (pn.passo());
        ta.setText("");
        ArrayList lugares = pn.getLugares();
        for (int i=0; i<lugares.size(); i++) {
            LugarAbstrato la = (LugarAbstrato)lugares.get(i);
            ta.append("Lugar: " + la.getNome() + ": " + la.getTokens() + " tokens\n");
        }
    }
}
