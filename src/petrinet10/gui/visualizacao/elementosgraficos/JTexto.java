/*
 * JTexto.java
 *
 * Created on 8 de Novembro de 2006, 14:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao.elementosgraficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author _Renatu
 */
public class JTexto extends ElementoGrafico {
    
    private String texto;
    private Color cor;
    private Point2D.Double ponto;
    private int tamanho;
    private TextLayout tl;
    private Rectangle2D bounds;
    
    /** Creates a new instance of JTexto */
    public JTexto(double x, double y, Color cor, String texto) {
        this.posicao = new Point2D.Double(x,y);
        this.cor = cor;
        this.texto = texto;
        this.tamanho = 12;
    }
    
    public JTexto(Point p, Color cor, String texto) {
        this(p.getX(), p.getY(), Color.BLACK, texto);
    }
    
    public JTexto(Point p, String texto) {
        this(p, Color.BLACK, texto);
    }
    
    public void desenhar(Graphics2D g2) {
        Font fonte = new Font("Helvetica", Font.PLAIN, tamanho);
        FontRenderContext frc = g2.getFontRenderContext();
        g2.setFont(fonte);
        Rectangle2D rec = fonte.getStringBounds(texto, frc);
        bounds = new Rectangle2D.Double(rec.getX() + this.posicao.getX(), rec.getY() + this.posicao.getY(), rec.getWidth(), rec.getHeight());
        g2.setColor(cor);
        g2.drawString(texto, (float)posicao.getX(), (float)posicao.getY());
    }
    
    public Shape getShape() {
        return this.bounds;
    }
    
    public String getTexto() {
        return this.texto;
    }
    
    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    public void setCor(Color cor) {
        this.cor = cor;
    }
    
    public Color getCor() {
        return this.cor;
    }
    
    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
    
    public int getTamanto() {
        return this.tamanho;
    }
}
