/*
 * PetriPanel.java
 *
 * Created on 20 de Abril de 2006, 00:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JPanel;
import petrinet10.gui.controle.ExecutaRede;
import petrinet10.gui.visualizacao.elementosgraficos.CaixaSelecao;
import petrinet10.gui.visualizacao.elementosgraficos.ElementoGrafico;
import petrinet10.gui.visualizacao.elementosgraficos.SelecaoLugarTransicao;

/**
 *
 * @author _Renatu
 */
public class PetriPanel extends JPanel {
    
    private ArrayList elementosgraficos, selecionados;
    private CaixaSelecao selecao;
    private SelecaoLugarTransicao slt;
    private Thread t;
    private ExecutaRede executa;
    
    /** Creates a new instance of PetriPanel */
    public ExecutaRede getExecutaRede(){
        return this.executa;
    }
    
    public PetriPanel() {
        elementosgraficos = new ArrayList();
        selecionados = new ArrayList();
        PetriPanelListener ppl = new PetriPanelListener(this);
        this.addMouseListener(ppl);
        this.addMouseMotionListener(ppl);
        this.addMouseWheelListener(ppl);
        this.addKeyListener(ppl);
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Rectangle rec = g2.getClipBounds();
        Rectangle rec = this.getBounds();
        double x = rec.getWidth();
        double y = rec.getHeight();
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0,(int)x,(int)y);
        for (int i=0; i<x; i+=20) {
            for (int j=0; j<y; j+=20) {
                g2.setColor(Color.GRAY);
                g2.drawLine(i,j,i,j);
            }
        }
        if (elementosgraficos!=null) {
            for (int i=0; i<elementosgraficos.size(); i++) {
                ElementoGrafico eg = (ElementoGrafico)elementosgraficos.get(i);
                eg.desenhar(g2);
            }
        }
        if (selecionados!=null) {
            selecao = new CaixaSelecao(selecionados);
            selecao.desenhar(g2);
        }
        if (slt!=null) {
            slt.desenhar(g2);
        }
    }
    
    public void addElementoGrafico(ElementoGrafico eg) {
        elementosgraficos.add(eg);
    }
    
    public boolean removeElementoGrafico(ElementoGrafico eg) {
        return elementosgraficos.remove(eg);
    }
    
    public ArrayList getElementosGraficos() {
        return this.elementosgraficos;
    }
    
    public void setElementosGraficos(ArrayList elementosgraficos) {
        this.elementosgraficos = elementosgraficos;
    }
    
    public void limparSelecao() {
        for (int i=0; i<selecionados.size(); i++) {
            ((ElementoGrafico)selecionados.get(i)).setSelecionado(false);
        }
        selecionados = new ArrayList();
        selecao = null;
    }
    
    public void addSelecao(ElementoGrafico eg) {
        eg.setSelecionado(true);
        /*if (eg instanceof JLugar) {
            JLugar lugar = (JLugar)eg;
            SelecaoLugarTransicao slt = new SelecaoLugarTransicao(lugar);
            addElementoGrafico(slt);
        } else if (eg instanceof JTransicao) {
            JTransicao transicao = (JTransicao)eg;
            SelecaoLugarTransicao slt = new SelecaoLugarTransicao(transicao);
            addElementoGrafico(slt);
        }*/
        selecionados.add(eg);
    }
    
    public ArrayList getSelecionados() {
        return this.selecionados;
    }
    
    public void setSelecionados(ArrayList selecionados) {
        this.selecionados = selecionados;
    }
    
    public CaixaSelecao getCaixaSelecao() {
        return this.selecao;
    }
    
    public void setSelecaoLugarTransicao(SelecaoLugarTransicao slt) {
        this.slt = slt;
    }
    
    public void removeSelecaoLugarTransicao() {
        this.slt = null;
    }
    
    public SelecaoLugarTransicao getSelecaoLugarTransicao() {
        return this.slt;
    }
    
    public void ligar() {
        executa = new ExecutaRede(this);
        t = new Thread(executa);
    }
    
    public void rodar() {
        if (t!=null) t.start();
    }
    
    public void run_auto(){
        if(executa!=null) executa.run();
    }
    
    public void passo() {
        if (executa!=null) executa.passo();
    }
    public void refresh_chamada(){
        if(executa!=null) executa.refresh_chamada();
    }
    
    public void parar() {
        if (executa!=null) executa.parar();
    }
    
    public void desligar() {
        if (executa!=null) executa.parar();
        t = null;
        executa = null;
    }
    
    public void enviarPraTras() {
        if (selecionados.size()>0) {
            ElementoGrafico eg = (ElementoGrafico)selecionados.get(0);
            elementosgraficos.remove(eg);
            elementosgraficos.add(0, eg);
            this.repaint();
        }
    }
    
    public void trazerPraFrente() {
        if (selecionados.size()>0) {
            ElementoGrafico eg = (ElementoGrafico)selecionados.get(0);
            elementosgraficos.remove(eg);
            elementosgraficos.add(eg);
            this.repaint();
        }
    }
    
    public void avancar() {
        if (selecionados.size()>0) {
            ElementoGrafico eg = (ElementoGrafico)selecionados.get(0);
            int indice = elementosgraficos.indexOf(eg);
            elementosgraficos.remove(eg);
            indice++;
            if (indice<=elementosgraficos.size()-1) {
                elementosgraficos.add(indice, eg);
            } else {
                elementosgraficos.add(eg);
            }
            this.repaint();
        }
    }
    
    public void recuar() {
        if (selecionados.size()>0) {
            ElementoGrafico eg = (ElementoGrafico)selecionados.get(0);
            int indice = elementosgraficos.indexOf(eg);
            elementosgraficos.remove(eg);
            indice = indice-1>=0?indice-1:indice;
            elementosgraficos.add(indice, eg);
            this.repaint();
        }
    }
}
