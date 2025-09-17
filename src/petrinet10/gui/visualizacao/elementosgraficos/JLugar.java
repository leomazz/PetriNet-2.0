/*
 * JLugar.java
 *
 * Created on 6 de Junho de 2006, 14:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao.elementosgraficos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import petrinet10.gui.controle.Controle;

/**
 *
 * @author _Renatu
 */
public class JLugar extends ElementoGrafico {
    
    private Color corfront, corback;
    private double espessura;
    private Ellipse2D.Double elipse;
    private int tamanho;
    private static final int tamanhopadrao=12;
    private int tokens, initialtokens;
    private String nome;
    private JTexto texto;
    private int capacidade;
    private ArrayList codigo;
    
    /** Creates a new instance of JLugar */
    public JLugar(double x, double y, int tamanho, Color corfront, Color corback, double espessura) {
        this.posicao = new Point2D.Double(x,y);
        this.tamanho=tamanho;
        this.corfront = corfront;
        this.corback = corback;
        this.capacidade = 1;
        this.espessura = espessura;
        this.nome = Controle.getControle().getProximoNomeLugar();
        this.texto = new JTexto(posicao.getX()+tamanhopadrao+10, posicao.getY()+tamanhopadrao-5, Color.BLACK, nome);
        this.codigo = new ArrayList();
    }
    
    public JLugar(double x, double y, int tamanho) {
        this(x,y,tamanho,Color.BLACK, null, 1);
    }
    
    public JLugar(Point p1, int tamanho, Color corfront, Color corback, double espessura) {
        this(p1.getX(), p1.getY(), tamanho, corfront, corback, espessura);
    }
    
    public JLugar(Point p1, Color corfront, Color corback) {
        this(p1,tamanhopadrao,corfront,corback);
    }
    
    public JLugar(Point p1, int tamanho, Color corfront, Color corback) {
        this(p1,tamanho,corfront,corback,1);
    }
    
    public JLugar(Point p1) {
        this(p1,tamanhopadrao,Color.BLACK, null, 1);
    }
    
    public void desenhar(Graphics2D g2) {
        elipse = new Ellipse2D.Double(posicao.getX()-tamanho, posicao.getY()-tamanho,
                2*tamanho, 2*tamanho);
        if (corback!=null) {
            g2.setColor(corback);
            g2.fill(elipse);
        }
        g2.setColor(Color.WHITE);
        g2.fill(elipse);
        g2.setColor(corfront);
        g2.draw(elipse);
        double tamanhodotoken;
        largura = altura = 2*tamanho;
        if (tokens==1) {
            tamanhodotoken=tamanho/2;
            Ellipse2D.Double token = new Ellipse2D.Double(
                    posicao.getX()-tamanhodotoken,
                    posicao.getY()-tamanhodotoken,
                    tamanhodotoken*2, tamanhodotoken*2);
            g2.fill(token);
        } else if (tokens==2) {
            tamanhodotoken=tamanho/2;
            Ellipse2D.Double token = new Ellipse2D.Double(
                    posicao.getX()-(tamanhodotoken*3/2),
                    posicao.getY()-(tamanhodotoken/2),
                    tamanhodotoken, tamanhodotoken);
            g2.fill(token);
            token = new Ellipse2D.Double(
                    posicao.getX()+(tamanhodotoken/2),
                    posicao.getY()-(tamanhodotoken/2),
                    tamanhodotoken, tamanhodotoken);
            g2.fill(token);
        } else if (tokens==3) {
            tamanhodotoken = tamanho/2;
            Ellipse2D.Double token = new Ellipse2D.Double(
                    posicao.getX()-(tamanhodotoken/2),
                    posicao.getY()-(altura/4)-(tamanhodotoken/2),
                    tamanhodotoken, tamanhodotoken);
            g2.fill(token);
            token = new Ellipse2D.Double(
                    posicao.getX()-(tamanhodotoken*3/2),
                    posicao.getY()+(altura/8)-(tamanhodotoken/2),
                    tamanhodotoken, tamanhodotoken);
            g2.fill(token);
            token = new Ellipse2D.Double(
                    posicao.getX()+(tamanhodotoken/2),
                    posicao.getY()+(altura/8)-(tamanhodotoken/2),
                    tamanhodotoken, tamanhodotoken);
            g2.fill(token);
        } else if (tokens==4) {
            tamanhodotoken = tamanho/2;
            Ellipse2D.Double token = new Ellipse2D.Double(
                    posicao.getX()-(tamanhodotoken*1.2),
                    posicao.getY()-(tamanhodotoken*1.2),
                    tamanhodotoken, tamanhodotoken);
            g2.fill(token);
            token = new Ellipse2D.Double(
                    posicao.getX()+(tamanhodotoken*.4),
                    posicao.getY()-(tamanhodotoken*1.2),
                    tamanhodotoken, tamanhodotoken);
            g2.fill(token);
            token = new Ellipse2D.Double(
                    posicao.getX()-(tamanhodotoken*1.2),
                    posicao.getY()+(tamanhodotoken*.4),
                    tamanhodotoken, tamanhodotoken);
            g2.fill(token);
            token = new Ellipse2D.Double(
                    posicao.getX()+(tamanhodotoken*.4),
                    posicao.getY()+(tamanhodotoken*.4),
                    tamanhodotoken, tamanhodotoken);
            g2.fill(token);
        } else if (tokens>4) {
            Rectangle2D r2 = g2.getFontMetrics().getStringBounds(""+tokens, g2);
            g2.drawString(""+tokens,(float)(posicao.getX()-(r2.getWidth()/2)), (float)(posicao.getY()));
        }
        Point2D.Double posicaotexto = new Point2D.Double(posicao.getX()+tamanho+10, posicao.getY()+tamanho-5);
        texto.setPosicao(posicaotexto);
        texto.desenhar(g2);
    }
    
    public Shape getShape() {
        return this.elipse;
    }
    
    public int getTamanho() {
        return this.tamanho;
    }
    
    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
    
    public void setTokens(int tokens) {
        this.tokens=tokens<0?0:tokens;
    }
    
    public int getTokens() {
        return this.tokens;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
        this.texto = new JTexto(posicao.getX()+tamanho+10, posicao.getY()+tamanho-5, Color.BLACK, nome);
    }
    
    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }
    
    public int getCapacidade() {
        return this.capacidade;
    }

    public ArrayList getCodigo() {
        return codigo;
    }
    
    public void setCodigo(ArrayList codigo) {
        this.codigo = codigo;
    }

    public int getInitialtokens() {
        return initialtokens;
    }

    public void setInitialtokens(int initialtokens) {
        this.initialtokens = initialtokens;
        this.tokens = initialtokens;
    }

    public void resetToInitialTokens() {
        this.tokens = this.initialtokens;
    }
}
