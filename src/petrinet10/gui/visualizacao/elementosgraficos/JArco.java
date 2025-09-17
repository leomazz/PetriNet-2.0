/*
 * JArco.java
 *
 * Created on 6 de Junho de 2006, 23:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao.elementosgraficos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author _Renatu
 */
public class JArco extends ElementoGrafico {
    
    public static int LUGAR_TRANSICAO = 0;
    public static int TRANSICAO_LUGAR = 1;
    public static int TIPO_NORMAL = 0;
    public static int TIPO_HABILITADOR = 1;
    public static int TIPO_INIBIDOR = 2;
    private JLugar lugar;
    private JTransicao transicao;
    private int sentido;
    private Line2D.Double linha;
    private ArrayList pontosgeral, vertices, pontosmedios;
    private Point2D.Double primeiroponto, ultimoponto;
    private int peso;
    private int tipo;
    private boolean ativo;
    
    /** Creates a new instance of JArco */
    public JArco(JLugar lugar, JTransicao transicao, int sentido) {
        this.lugar = lugar;
        this.transicao=transicao;
        this.sentido=sentido;
        this.linha = new Line2D.Double(lugar.getPosicao(), transicao.getPosicao());
        this.posicao = lugar.getPosicao();
        this.setTipo(TIPO_NORMAL);
        this.setPeso(1);
        this.ativo = false;
        vertices = new ArrayList();
        pontosmedios = new ArrayList();
        pontosgeral = new ArrayList();
        primeiroponto = new Point2D.Double(0,0);
        ultimoponto = primeiroponto;
    }
    
    public void desenhar(Graphics2D g2) {

        //reta:
        if (this.tipo==TIPO_HABILITADOR) {
            g2.setStroke(new BasicStroke() {
                public float[] getDashArray() {
                    return new float[] { 5, 5 };
                }
            });
        }
        ArrayList pontos = new ArrayList();
        pontos.add(new Point2D.Double(lugar.getPosicao().getX(), lugar.getPosicao().getY()));
        if (pontosgeral!=null) pontos.addAll(pontosgeral);
        pontos.add(new Point2D.Double(transicao.getPosicao().getX(), transicao.getPosicao().getY()));
        
        GeneralPath gp = new GeneralPath();
        Point2D.Double ponto1 = (Point2D.Double)pontos.get(0);
        Point2D.Double ponto2 = (Point2D.Double)pontos.get(1);
        
        double deltax = ponto1.getX() - ponto2.getX();
        double deltay = ponto1.getY() - ponto2.getY();
        double hipotenusa = Math.sqrt(deltax*deltax+deltay*deltay);
        double cosseno = deltax / hipotenusa;
        double seno = deltay / hipotenusa;
        double x1 = lugar.getPosicao().getX() - (lugar.getLargura()/2)*cosseno;
        double y1 = lugar.getPosicao().getY() - (lugar.getAltura()/2)*seno;
        primeiroponto = new Point2D.Double(x1, y1);
        
        gp.moveTo((float)x1,(float)y1);
        
        if (pontos.size()>2) {
            for (int i=1; i<pontos.size()-1; i++) {
                Point2D.Double p = (Point2D.Double)pontos.get(i);
                gp.lineTo((float)p.getX(), (float)p.getY());
            }
        }
        
        //ponto final da transicao:
        Point2D.Double penultimoponto = (Point2D.Double)pontos.get(pontos.size()-2);
        Point2D.Double pontofinal = (Point2D.Double)pontos.get(pontos.size()-1);
        double x2 = pontofinal.getX();
        double y2 = pontofinal.getY();
        double deltax2 = penultimoponto.getX() - pontofinal.getX();
        double deltay2 = penultimoponto.getY() - pontofinal.getY();
        double hipotenusa2 = Math.sqrt(deltax2*deltax2+deltay2*deltay2);
        double cosseno2 = deltax2 / hipotenusa2;
        double seno2 = deltay2 / hipotenusa2;
        if (cosseno2 >= 0.7 && seno2<=0.7 && seno2>=-0.7) {
            //area 1
            x2 = transicao.getPosicao().getX() + transicao.getTamanho();
            y2 = transicao.getPosicao().getY();
        } else if (seno2>=0.7 && cosseno2>=-0.7 && cosseno2<=0.7) {
            //area 2
            x2 = transicao.getPosicao().getX();
            y2 = transicao.getPosicao().getY() + (transicao.getTamanho()/3);
        } else if (cosseno2<=-0.7 && seno2<=0.7 && seno2>=-0.7) {
            //area 3
            x2 = transicao.getPosicao().getX() - transicao.getTamanho();
            y2 = transicao.getPosicao().getY();
        } else if (seno2<=-0.7 && cosseno2>=-0.7 && cosseno2<=0.7) {
            //area 4
            x2 = transicao.getPosicao().getX();
            y2 = transicao.getPosicao().getY() - (transicao.getTamanho()/3);
        }
        ultimoponto = new Point2D.Double(x2, y2);
        gp.lineTo((float)x2,(float)y2);
//        g2.setColor(this.isAtivo()?Color.RED:Color.BLACK);
        g2.setColor(this.isAtivo()?Color.BLACK:Color.BLACK);
        g2.draw(gp.createTransformedShape(new AffineTransform()));
        
        //desenha seta ou bolinha (normal ou inibidor)
        double tamanhodaseta=10;
        double xtemp, ytemp, xtemp1, xtemp2, ytemp1, ytemp2;
        double xseta, yseta;
        Point2D.Double p1, p2;
        if (sentido==LUGAR_TRANSICAO) {
            p1 = new Point2D.Double(x2,y2);
            p2 = (Point2D.Double)pontos.get(pontos.size()-2);
        } else {
            p1 = new Point2D.Double(x1,y1);
            p2 = (Point2D.Double)pontos.get(1);
        }
        deltax = p1.getX() - p2.getX();
        deltay = p1.getY() - p2.getY();
        hipotenusa = Math.sqrt(deltax*deltax+deltay*deltay);
        cosseno = deltax / hipotenusa;
        seno = deltay / hipotenusa;
        if (sentido==LUGAR_TRANSICAO) {
            xseta = x2;
            yseta = y2;
        } else {
            xseta = x1;
            yseta = y1;
        }
        xtemp = xseta - tamanhodaseta*cosseno;
        ytemp = yseta - tamanhodaseta*seno;
        ytemp2 = ytemp - (tamanhodaseta/2)*cosseno;
        xtemp1 = xtemp - (tamanhodaseta/2)*seno;
        ytemp1 = ytemp + (tamanhodaseta/2)*cosseno;
        xtemp2 = xtemp + (tamanhodaseta/2)*seno;
        if (tipo==TIPO_INIBIDOR) {
            double raio = 5;
            Ellipse2D.Double bolinha = new Ellipse2D.Double(
                    xseta-raio, yseta-raio, raio*2, raio*2);
            g2.setColor(Color.BLACK);
            g2.fill(bolinha);
        } else {
            gp = new GeneralPath();
            gp.moveTo((float)xseta, (float)yseta);
            gp.lineTo((float)xtemp1, (float)ytemp1);
            gp.lineTo((float)xtemp2, (float)ytemp2);
            gp.closePath();
            g2.fill(gp.createTransformedShape(new AffineTransform()));
        }
        
        double raio = 2.5;
        vertices = new ArrayList();
        for (int i=1; i<pontos.size()-1; i++) {
            Point2D.Double p = (Point2D.Double)pontos.get(i);
            Ellipse2D.Double bola = new Ellipse2D.Double(
                    p.getX()-raio, p.getY()-raio, raio*2, raio*2);
            vertices.add(bola);
            g2.setColor(Color.WHITE);
            g2.fill(bola);
            g2.setColor(Color.GRAY);
            g2.draw(bola);
        }
        
        raio = 2.5;
        pontosmedios = new ArrayList();
        for (int i=0; i<pontos.size()-1; i++) {
            Point2D.Double m1 = (Point2D.Double)pontos.get(i);
            Point2D.Double m2 = (Point2D.Double)pontos.get(i+1);
            if (i==0) m1 = new Point2D.Double(x1,y1);
            if (i==pontos.size()-2) m2 = new Point2D.Double(x2,y2);
            Point2D.Double med = new Point2D.Double(
                    (m1.getX()+m2.getX())/2, (m1.getY()+m2.getY())/2);
            Ellipse2D.Double bola = new Ellipse2D.Double(
                    med.getX()-raio, med.getY()-raio, raio*2, raio*2);
            pontosmedios.add(bola);
            g2.setColor(Color.GRAY);
            g2.fill(bola);
        }
        g2.setStroke(new BasicStroke());
    }
    
    public JTransicao getTransicao() {
        return this.transicao;
    }
    
    public JLugar getLugar() {
        return this.lugar;
    }
    
    public ArrayList getVertices() {
        return vertices;
    }
    
    public ArrayList getPontosMedios() {
        return pontosmedios;
    }
    
    public Shape getShape() {
        ArrayList pontos = new ArrayList();
        pontos.add(primeiroponto);
        if (pontosgeral!=null) pontos.addAll(pontosgeral);
        pontos.add(ultimoponto);
        GeneralPath gp = new GeneralPath();
        gp.moveTo((float)primeiroponto.getX(), (float)primeiroponto.getY());
        int l = 5;
        for (int i=1; i<pontos.size(); i++) {
            Point2D.Double p1 = (Point2D.Double)pontos.get(i-1);
            Point2D.Double p2 = (Point2D.Double)pontos.get(i);
            double alpha = Math.atan((p2.getY()-p1.getY())/(p2.getX()-p1.getX())) + Math.PI/(double)2;
            gp.lineTo((float)(p1.getX()+l*Math.cos(alpha)), (float)(p1.getY()+l*Math.sin(alpha)));
            gp.lineTo((float)(p2.getX()+l*Math.cos(alpha)), (float)(p2.getY()+l*Math.sin(alpha)));
        }
        for (int i=pontos.size()-1; i>0; i--) {
            Point2D.Double p2 = (Point2D.Double)pontos.get(i);
            Point2D.Double p1 = (Point2D.Double)pontos.get(i-1);
            double alpha = Math.atan((p2.getY()-p1.getY())/(p2.getX()-p1.getX())) + Math.PI/(double)2;
            gp.lineTo((float)(p2.getX()-l*Math.cos(alpha)), (float)(p2.getY()-l*Math.sin(alpha)));
            gp.lineTo((float)(p1.getX()-l*Math.cos(alpha)), (float)(p1.getY()-l*Math.sin(alpha)));
        }
        gp.closePath();
        return gp.createTransformedShape(new AffineTransform());
    }
    
    public void moveVertice(int numerodovertice, double x, double y) {
        pontosgeral.remove(numerodovertice);
        pontosgeral.add(numerodovertice, new Point2D.Double(x, y));
    }
    
    public void criaVertice(int numerodopontomedio, double x, double y) {
        pontosgeral.add(numerodopontomedio, new Point2D.Double(x, y));
    }
    
    public int getSentido() {
        return this.sentido;
    }
    
    public ArrayList getPontos() {
        return this.pontosgeral;
    }
    
    public void setPontos(ArrayList pontos) {
        this.pontosgeral = pontos;
    }
    
    public int getPeso() {
        return peso;
    }
    
    public void setPeso(int peso) {
        this.peso = peso;
    }
    
    public int getTipo() {
        return tipo;
    }
    
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    public boolean isAtivo() {
        return this.ativo;
    }
}
