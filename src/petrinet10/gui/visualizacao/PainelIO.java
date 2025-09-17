/*
 * PainelIO.java
 *
 * Created on 22 de Novembro de 2006, 22:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import petrinet10.core.Variavel;
import petrinet10.gui.controle.Controle;

/**
 *
 * @author _Renatu
 */
public class PainelIO extends JPanel implements ActionListener {
    
    private Hashtable tabela, botoes;
    private static PainelIO painel;
    
    public PainelIO() {
        tabela = new Hashtable();
        botoes = new Hashtable();
        ArrayList listavariaveis = Controle.getControle().getVariaveis();
        ArrayList entradas = new ArrayList();
        ArrayList saidas = new ArrayList();
        ArrayList entradaesaidas = new ArrayList();
        for (int i=0; i<listavariaveis.size(); i++) {
            Variavel v = (Variavel)listavariaveis.get(i);
            if (v.getEntradasaida()==Variavel.ENTRADA) {
                entradas.add(v);
            } else if (v.getEntradasaida()==Variavel.SAIDA) {
                saidas.add(v);
            } else if (v.getEntradasaida()==Variavel.ENTRADASAIDA) {
                entradaesaidas.add(v);
            }
        }
        setLayout(new GridLayout(listavariaveis.size(), 2, 5, 5));
        for (int i=0; i<saidas.size(); i++) {
            Variavel v = (Variavel)saidas.get(i);
            JLabel label = null;
            if (v.isLigado()) {
                label = new JLabel(new ImageIcon(PainelIO.class.getResource("/petrinet10/imagens/led_on.png")));
                add(label);
            } else {
                label = new JLabel(new ImageIcon(PainelIO.class.getResource("/petrinet10/imagens/led_off.png")));
                add(label);
            }
            tabela.put(v, label);
            botoes.put(label, v);
            add(new JLabel(v.getNome()));
        }
        for (int i=0; i<entradas.size(); i++) {
            Variavel v = (Variavel)entradas.get(i);
            JToggleButton botao = new JToggleButton();
            botao.setSelectedIcon(new ImageIcon(PainelIO.class.getResource("/petrinet10/imagens/led_on.png")));
            botao.setIcon(new ImageIcon(PainelIO.class.getResource("/petrinet10/imagens/led_off.png")));
            botao.setSelected(v.isLigado());
            botao.addActionListener(this);
            add(botao);
            tabela.put(v, botao);
            botoes.put(botao, v);
            add(new JLabel(v.getNome()));
        }
        for (int i=0; i<entradaesaidas.size(); i++) {
            Variavel v = (Variavel)entradaesaidas.get(i);
            JToggleButton botao = new JToggleButton();
            botao.setSelectedIcon(new ImageIcon(PainelIO.class.getResource("/petrinet10/imagens/led_on.png")));
            botao.setIcon(new ImageIcon(PainelIO.class.getResource("/petrinet10/imagens/led_off.png")));
            botao.setSelected(v.isLigado());
            botao.addActionListener(this);
            add(botao);
            tabela.put(v, botao);
            botoes.put(botao, v);
            add(new JLabel(v.getNome()));
        }
    }
    
    public void atualiza() {
        ArrayList listavariaveis = Controle.getControle().getVariaveis();
        for (int i=0; i<listavariaveis.size(); i++) {
            Variavel v = (Variavel)listavariaveis.get(i);
            if (v.getEntradasaida()==Variavel.ENTRADA) {
                JToggleButton botao = (JToggleButton)tabela.get(v);
                botao.setSelected(v.isLigado());
            } else if (v.getEntradasaida()==Variavel.SAIDA) {
                JLabel label = (JLabel)tabela.get(v);
                if (v.isLigado()) {
                    label.setIcon(new ImageIcon(PainelIO.class.getResource("/petrinet10/imagens/led_on.png")));
                } else {
                    label.setIcon(new ImageIcon(PainelIO.class.getResource("/petrinet10/imagens/led_off.png")));
                }
            } else if (v.getEntradasaida()==Variavel.ENTRADASAIDA) {
                JToggleButton botao = (JToggleButton)tabela.get(v);
                botao.setSelected(v.isLigado());
            }
        }
        this.repaint();
    }
    
    public static JPanel montaPainel() {
        painel = new PainelIO();
        return painel;
    }
    
    public static PainelIO getCurrentPainel() {
        return painel;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        JToggleButton botao = (JToggleButton)actionEvent.getSource();
        Variavel v = (Variavel)botoes.get(botao);
        v.setLigado(botao.isSelected());
        Controle.getControle().atualizarVariaveis();
    }

}
