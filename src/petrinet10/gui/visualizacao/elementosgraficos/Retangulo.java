/*
 * Retangulo.java
 *
 * Created on 12 de Maio de 2006, 14:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao.elementosgraficos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author _Renatu
 */
public class Retangulo extends ElementoGrafico {
    
    private Color corfront, corback;
    private Rectangle2D retangulo;
    private double espessura;
    
    /** Creates a new instance of Retangulo */
    public Retangulo(double x, double y, double largura, double altura, Color front, Color back) {
        this.posicao = new Point2D.Double(x,y);
        this.largura = largura;
        this.altura = altura;
        this.corback = back;
        this.corfront = front;
    }
    
    public Retangulo(double x, double y, double largura, double altura) {
        this(x,y,largura,altura,Color.BLACK, null);
    }
    
    public Retangulo(Point p1, Point p2, Color front, Color back) {
        double x, y;
        x=p1.getX()>p2.getX()?p2.getX():p1.getX();
        y=p1.getY()>p2.getY()?p2.getY():p1.getY();
        this.posicao = new Point2D.Double(x,y);
        largura=Math.abs(p1.getX()-p2.getX());
        altura=Math.abs(p1.getY()-p2.getY());
        this.corfront=front;
        this.corback=back;
    }
    
    public Retangulo(Point p1, Point p2) {
        this(p1,p2,Color.BLACK, null);
    }
    
    public void desenhar(Graphics2D g2) {
        g2.setStroke(new BasicStroke((float)espessura));
        this.retangulo = new Rectangle2D.Double(posicao.getX(), posicao.getY(), largura, altura);
        if (corback!=null) {
            g2.setColor(corback);
            g2.fill(retangulo);
        }
        g2.setColor(corfront);
        g2.draw(retangulo);
        g2.setStroke(new BasicStroke(1));
    }
    
    public Shape getShape() {
        return retangulo;
    }
    
    public void setEspessura(double espessura) {
        System.out.println("setou espessura: " + espessura);
        this.espessura = espessura;
    }
    
    public double getEspessura() {
        return this.espessura;
    }

    public void setCor(Color cor) {
        this.corfront = cor;
    }
    
    public Color getCor() {
        return this.corfront;
    }
    
    public void setFundo(Color cor) {
        this.corback = cor;
    }
    
    public Color getFundo() {
        return this.corback;
    }
}
