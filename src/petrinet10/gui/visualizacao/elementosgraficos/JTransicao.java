/*
 * JTransicao.java
 *
 * Created on 6 de Junho de 2006, 19:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao.elementosgraficos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import petrinet10.gui.controle.Controle;
import webservice.Token;

/**
 *
 * @author _Renatu
 */
public class JTransicao extends ElementoGrafico {
    
    private int tamanho;
    private static final int tamanhopadrao=12;
    private Rectangle2D.Double retangulo;
    private JTexto texto;
    private String nome;
    private ArrayList codigo;
    private boolean ativa;
    
    private String type;
    //TEMPORIZADA
    private int tempo;
    
    //FUNDIDA
    private char s1, s2; //station
    private String t1, t2; //transition
    
    
    //NORMAL --> não se pode alterar senão dará pau!
    public JTransicao(double x, double y, int tamanho, String type) {
        posicao = new Point2D.Double(x,y);
        this.tamanho=tamanho;
        this.nome = Controle.getControle().getProximoNomeTransicao();
        this.texto = new JTexto(posicao.getX()+tamanho+10, posicao.getY()+tamanhopadrao-5, Color.BLACK, nome);
        this.codigo = new ArrayList();
        this.ativa = false;
        //inluih
        this.type = type;
    }
    
    //TEMPORIZADA
    public JTransicao(double x, double y, int tamanho, String type, int tempo) {
        posicao = new Point2D.Double(x,y);
        this.tamanho=tamanho;
        this.nome = Controle.getControle().getProximoNomeTransicao();
        this.texto = new JTexto(posicao.getX()+tamanho+10, posicao.getY()+tamanhopadrao-5, Color.BLACK, nome);
        this.codigo = new ArrayList();
        this.ativa = false;
        //incluih
        this.type = type;
        this.tempo = tempo;
    }
    //FUSION
    public JTransicao(double x, double y, int tamanho,  String type,
            String station1, String station2, String t1, String t2) {
        posicao = new Point2D.Double(x,y);
        this.tamanho=tamanho;
        this.nome = Controle.getControle().getProximoNomeTransicao();
        this.texto = new JTexto(posicao.getX()+tamanho+10, posicao.getY()+tamanhopadrao-5, Color.BLACK, nome);
        this.codigo = new ArrayList();
        this.ativa = false;
        //incluih
        this.type = type;
        this.s1 = station1.charAt(0);
        this.s2 = station2.charAt(0);
        this.t1 = t1;
        this.t2 = t2;
//        System.out.println("JJJTransicao");
//        System.out.println("T1: "+this.getT1());
//        System.out.println("s1: "+this.getS1());
//        System.out.println("T2: "+this.getT2());
//        System.out.println("s2: "+this.getS2());
        
        //modificação do token.status = 10, i.e., distribuída.
        //Token.getToken().setStatus(10);
    }
    
    /** Creates a new instance of JTransicao */
    public JTransicao(Point p, int tamanho) {
        this(p.getX(), p.getY(), tamanho, "normal");
    }
    
    public JTransicao(Point p) {
        this(p, tamanhopadrao);
    }
    
    public void desenhar(Graphics2D g2) {
        retangulo = new Rectangle2D.Double(
                posicao.getX()-(tamanho),
                posicao.getY()-(tamanho/3),
                tamanho*2, tamanho*2/3);
        //g2.setColor(this.isAtiva()?Color.RED:Color.BLACK);
        if(this.getType().equals("TF")) g2.setColor(Color.PINK);
        if(this.getType().equals("TT")) g2.setColor(Color.BLUE);
        if(this.getType().equals("Normal")) g2.setColor(Color.BLACK);
        
        
        g2.fill(retangulo);
        Point2D.Double posicaotexto = new Point2D.Double(posicao.getX()+tamanho+10, posicao.getY()+tamanhopadrao-5);
        texto.setPosicao(posicaotexto);
        texto.desenhar(g2);
    }
    
    public Shape getShape() {
        return this.retangulo;
    }
    
    public int getTamanho() {
        return this.tamanho;
    }
    
    public void setTamanho(int tamanho) {
        this.tamanho=tamanho;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public String getType(){
        return this.type;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
        this.texto = new JTexto(posicao.getX()+tamanho+10, posicao.getY()+tamanhopadrao-5, Color.BLACK, nome);
    }

    public ArrayList getCodigo() {
        return codigo;
    }

    public void setCodigo(ArrayList codigo) {
        this.codigo = codigo;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
    
    public char getS1(){
        return this.s1;
    }
    
    public char getS2(){
        return this.s2;
    }
    
    public String getT1(){
        return this.t1;
    }
    
    public String getT2(){
        return this.t2;
    }
    
    public int getTempo(){
        return this.tempo;
    }
    
}
