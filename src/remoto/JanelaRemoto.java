/*
 * JanelaRemoto.java
 *
 * Created on 26 de Novembro de 2006, 23:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package remoto;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import petrinet10.core.Variavel;
import petrinet10.gui.visualizacao.PainelIO;

/**
 *
 * @author _Renatu
 */
public class JanelaRemoto extends JFrame implements ActionListener {
    
    JTextField ip;
    JButton botaook;
    JPanel painelcentral;
    Socket soquete;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    Hashtable tabela, botoes, tabelavariaveis;
    ArrayList variaveis;
    ThreadCliente thread;
    
    /** Creates a new instance of JanelaRemoto */
    public JanelaRemoto() {
        super("Acesso Remoto");
        JPanel painelsuperior = new JPanel();
        ip = new JTextField(20);
        painelsuperior.add(ip, "Center");
        botaook = new JButton("Conectar");
        botaook.addActionListener(this);
        painelsuperior.add(botaook, "East");
        this.getContentPane().add(painelsuperior, "North");
        painelcentral = new JPanel();
        painelcentral.add(new JLabel("Aguardando conexao..."), "Center");
        this.getContentPane().add(painelcentral, "Center");
        pack();
        tabela = new Hashtable();
        botoes = new Hashtable();
        tabelavariaveis = new Hashtable();
    }
    
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(botaook)) {
            if (botaook.getText().equals("Conectar")) {
                botaook.setText("Desconectar");
                ip.setEnabled(false);
                iniciarConexao();
            } else {
                botaook.setText("Conectar");
                ip.setEnabled(true);
            }
        } else {
            JToggleButton botao = (JToggleButton)actionEvent.getSource();
            Variavel v = (Variavel)botoes.get(actionEvent.getSource());
            v.setLigado(botao.isSelected());
            thread.enviar();
        }
    }
    
    private void iniciarConexao() {
        thread = new ThreadCliente(this);
        new Thread(thread).run();
    }
    
    public void montarjanela(ArrayList listavariaveis) {
        getContentPane().remove(painelcentral);
        this.variaveis = listavariaveis;
        painelcentral = new JPanel();
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
            tabelavariaveis.put(v.getNome(), v);
        }
        painelcentral.setLayout(new GridLayout(listavariaveis.size(), 2, 5, 5));
        for (int i=0; i<saidas.size(); i++) {
            Variavel v = (Variavel)saidas.get(i);
            JLabel label = null;
            if (v.isLigado()) {
                label = new JLabel(new ImageIcon(PainelIO.class.getResource("/petrinet10/imagens/led_on.png")));
                painelcentral.add(label);
            } else {
                label = new JLabel(new ImageIcon(PainelIO.class.getResource("/petrinet10/imagens/led_off.png")));
                painelcentral.add(label);
            }
            tabela.put(v, label);
            botoes.put(label, v);
            painelcentral.add(new JLabel(v.getNome()));
        }
        for (int i=0; i<entradas.size(); i++) {
            Variavel v = (Variavel)entradas.get(i);
            JToggleButton botao = new JToggleButton();
            botao.setSelectedIcon(new ImageIcon(PainelIO.class.getResource("/petrinet10/imagens/led_on.png")));
            botao.setIcon(new ImageIcon(PainelIO.class.getResource("/petrinet10/imagens/led_off.png")));
            botao.setSelected(v.isLigado());
            botao.addActionListener(this);
            painelcentral.add(botao);
            tabela.put(v, botao);
            botoes.put(botao, v);
            painelcentral.add(new JLabel(v.getNome()));
        }
        for (int i=0; i<entradaesaidas.size(); i++) {
            Variavel v = (Variavel)entradaesaidas.get(i);
            JToggleButton botao = new JToggleButton();
            botao.setSelectedIcon(new ImageIcon(PainelIO.class.getResource("/petrinet10/imagens/led_on.png")));
            botao.setIcon(new ImageIcon(PainelIO.class.getResource("/petrinet10/imagens/led_off.png")));
            botao.setSelected(v.isLigado());
            botao.addActionListener(this);
            painelcentral.add(botao);
            tabela.put(v, botao);
            botoes.put(botao, v);
            painelcentral.add(new JLabel(v.getNome()));
        }
        getContentPane().add(painelcentral);
        this.pack();
        this.repaint();
    }
    
    public ArrayList getVariaveis() {
        return this.variaveis;
    }
    
    public void atualizar(ArrayList listavariaveis) {
        for (int i=0; i<listavariaveis.size(); i++) {
            Variavel entrada = (Variavel)listavariaveis.get(i);
            Variavel v = (Variavel)tabelavariaveis.get(entrada.getNome());
            System.out.println("variavel: " + v.getNome() + " - valor: " + v.isLigado());
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
}
