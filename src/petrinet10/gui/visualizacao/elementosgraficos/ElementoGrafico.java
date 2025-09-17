/*
 * ElementoGrafico.java
 *
 * Created on 21 de Abril de 2006, 00:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao.elementosgraficos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author _Renatu
 */
public abstract class ElementoGrafico {

    boolean selecionado;
    boolean sobreposicionado;
    Point2D.Double posicao;
    double largura, altura;
    
    public abstract void desenhar(Graphics2D g2);
    public abstract Shape getShape();
    
    public void setSelecionado(boolean b) {
        this.selecionado = b;
    }
    
    public boolean isSelecionado() {
        return this.selecionado;
    }
    
    public void setSobreposicionado(boolean b) {
        this.sobreposicionado = b;
    }
    
    public boolean isSobreposicionado() {
        return this.sobreposicionado;
    }
    
    public void setPosicao(Point2D.Double p) {
        this.posicao = p;
    }
    
    public Point2D.Double getPosicao() {
        return this.posicao;
    }
    
    public void setLargura (double largura) {
        this.largura=largura;
    }
    
    public double getLargura () {
        return this.largura;
    }
    
    public void setAltura(double altura) {
        this.altura=altura;
    }
    
    public double getAltura() {
        return this.altura;
    }
}
