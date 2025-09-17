/*
 * SelecaoLugarTransicao.java
 *
 * Created on 6 de Junho de 2006, 20:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao.elementosgraficos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 *
 * @author _Renatu
 */
public class SelecaoLugarTransicao extends ElementoGrafico {
    
    private JLugar lugar;
    private JTransicao transicao;
    private Ellipse2D.Double circulo;
    private double tamanhodocirculo=5;
    
    /** Creates a new instance of SelecaoLugarTransicao */
    public SelecaoLugarTransicao(JLugar lugar) {
        this.lugar = lugar;
        Point2D.Double ponto = lugar.getPosicao();
        this.posicao=ponto;
        circulo = new Ellipse2D.Double(
                ponto.getX()-(tamanhodocirculo/2),
                ponto.getY()-(tamanhodocirculo/2),
                tamanhodocirculo, tamanhodocirculo);
    }
    
    public SelecaoLugarTransicao(JTransicao transicao) {
        this.transicao = transicao;
        Point2D.Double ponto = transicao.getPosicao();
        this.posicao=ponto;
        circulo = new Ellipse2D.Double(
                ponto.getX()-(tamanhodocirculo/2),
                ponto.getY()-(tamanhodocirculo/2),
                tamanhodocirculo, tamanhodocirculo);
    }
    
    public void desenhar(Graphics2D g2) {
        Point2D.Double ponto=null;
        if (lugar!=null) {
            ponto = lugar.getPosicao();
        } else if (transicao!=null) {
            ponto = transicao.getPosicao();
        }
        if (ponto!=null) {
            circulo = new Ellipse2D.Double(
                    ponto.getX()-(tamanhodocirculo/2),
                    ponto.getY()-(tamanhodocirculo/2),
                    tamanhodocirculo, tamanhodocirculo);
            g2.setColor(Color.CYAN);
            g2.draw(circulo);
        }
    }
    
    public Shape getShape() {
        return circulo;
    }
    
    public ElementoGrafico getElemento() {
        if (lugar==null) return transicao;
        else return lugar;
    }
    
}
