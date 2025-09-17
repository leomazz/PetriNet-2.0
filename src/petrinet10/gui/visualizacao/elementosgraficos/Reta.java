/*
 * Reta.java
 *
 * Created on 12 de Maio de 2006, 13:11
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
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 *
 * @author _Renatu
 */
public class Reta extends ElementoGrafico {
    
    private Line2D.Double linha;
    private Color cor;
    private double espessura;
    
    public Reta(double x1, double y1, double x2, double y2, Color c, double espessura) {
        this.posicao = new Point2D.Double(x1,y1);
        this.cor = c;
        this.espessura = espessura;
        this.largura = x2 - x1;
        this.altura = y2 - y1;
    }
    
    public Reta(double x1, double y1, double x2, double y2, Color c) {
        this(x1,y1,x2,y2,c,1);
    }
    
    /** Creates a new instance of Reta */
    public Reta(double x1, double y1, double x2, double y2) {
        this(x1,y1,x2,y2,Color.BLACK);
    }
    
    public Reta(Point p1, Point p2, Color c) {
        this(p1.getX(), p1.getY(), p2.getX(), p2.getY(), c);
    }
    
    public Reta(Point p1, Point p2) {
        this(p1,p2,Color.BLACK);
    }
    
    public void desenhar(Graphics2D g2) {
        g2.setColor(cor);
        g2.setStroke(new BasicStroke((float)espessura));
        double x1 = posicao.getX();
        double y1 = posicao.getY();
        double x2 = x1 + largura;
        double y2 = y1 + altura;
        linha = new Line2D.Double(x1,y1,x2,y2);
        g2.draw(linha);
        g2.setStroke(new BasicStroke(1));
    }
    
    public Shape getShape() {
        GeneralPath gp = new GeneralPath();
        float t = 5 + (float)(espessura/2);
        Point2D.Double p2 = new Point2D.Double(posicao.getX()+largura, posicao.getY()+altura);
        double alpha = Math.atan((p2.getY()-posicao.getY())/(p2.getX()-posicao.getX())) + Math.PI/(double)2;
        gp.moveTo((float)(posicao.getX()+t*Math.cos(alpha)), (float)(posicao.getY()+t*Math.sin(alpha)));
        gp.lineTo((float)(posicao.getX()-t*Math.cos(alpha)), (float)(posicao.getY()-t*Math.sin(alpha)));
        gp.lineTo((float)(p2.getX()-t*Math.cos(alpha)), (float)(p2.getY()-t*Math.sin(alpha)));
        gp.lineTo((float)(p2.getX()+t*Math.cos(alpha)), (float)(p2.getY()+t*Math.sin(alpha)));
        gp.closePath();
        return gp.createTransformedShape(new AffineTransform());
    }
    
    public Color getCor() {
        return this.cor;
    }
    
    public void setEspessura(double espessura) {
        this.espessura = espessura;
    }
    
    public double getEspessura() {
        return this.espessura;
    }
    
    public void setCor(Color cor) {
        this.cor = cor;
    }
}
