/*
 * Teste.java
 *
 * Created on 12 de Abril de 2006, 12:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10;

import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import petrinet10.core.Arco;
import petrinet10.core.Lugar;
import petrinet10.core.LugarAbstrato;
import petrinet10.core.PetriNet;
import petrinet10.core.Transicao;
import petrinet10.core.TransicaoDisparavel;

/**
 *
 * @author _Renatu
 */
public class Teste extends JFrame implements ActionListener {
    
    TextArea ta;
    PetriNet pn;
    TransicaoDisparavel t1;
    
    /** Creates a new instance of Teste */
    public Teste() {
        super("Teste");
        this.setSize(400,400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ta = new TextArea();
        getContentPane().add("Center", ta);
        JButton botao = new JButton("Ativar");
        botao.addActionListener(this);
        getContentPane().add("South", botao);
        
        pn = new PetriNet("Rede Liga Desliga");
        
        Lugar l1 = new Lugar("L1");
        Lugar l2 = new Lugar("L2");
        Lugar l3 = new Lugar("L3");
        Lugar l4 = new Lugar("L4");
        
        l1.setTokens(1);
        l4.setTokens(1);
        
        t1 = new TransicaoDisparavel("T1");
        Transicao t2 = new Transicao("T2");
        Transicao t3 = new Transicao("T3");
        
        Arco a1 = new Arco(l1, t1, 1, Arco.LUGAR_TRANSICAO);
        Arco a2 = new Arco(l2, t1, 1, Arco.TRANSICAO_LUGAR);
        Arco a3 = new Arco(l2, t2, 1, Arco.LUGAR_TRANSICAO);
        Arco a4 = new Arco(l2, t3, 1, Arco.LUGAR_TRANSICAO);
        Arco a5 = new Arco(l3, t2, 1, Arco.TRANSICAO_LUGAR);
        Arco a6 = new Arco(l3, t3, 1, Arco.LUGAR_TRANSICAO);
        Arco a7 = new Arco(l4, t2, 1, Arco.LUGAR_TRANSICAO);
        Arco a8 = new Arco(l4, t3, 1, Arco.TRANSICAO_LUGAR);
        Arco a9 = new Arco(l1, t2, 1, Arco.TRANSICAO_LUGAR);
        Arco a10 = new Arco(l1, t3, 1, Arco.TRANSICAO_LUGAR);
        
        pn.addLugar(l1);
        pn.addLugar(l2);
        pn.addLugar(l3);
        pn.addLugar(l4);
        pn.addTransicao(t1);
        pn.addTransicao(t2);
        pn.addTransicao(t3);
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
        
        ArrayList lugares = pn.getLugares();
        
        for (int i=0; i<lugares.size(); i++) {
            LugarAbstrato la = (LugarAbstrato)lugares.get(i);
            ta.append("Lugar: " + la.getNome() + ": " + la.getTokens() + " tokens\n");
        }
        
    }
    
    public void actionPerformed(ActionEvent actionEvent) {
        t1.disparar();
        ta.setText("");
        while (pn.passo());
        ArrayList lugares = pn.getLugares();
        for (int i=0; i<lugares.size(); i++) {
            LugarAbstrato la = (LugarAbstrato)lugares.get(i);
            ta.append("Lugar: " + la.getNome() + ": " + la.getTokens() + " tokens\n");
        }
    }
    
    
}
