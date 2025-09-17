/*
 * PetriPanelListener.java
 *
 * Created on 21 de Abril de 2006, 00:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import petrinet10.gui.controle.Controle;
import petrinet10.gui.visualizacao.elementosgraficos.CaixaSelecao;
import petrinet10.gui.visualizacao.elementosgraficos.ElementoGrafico;
import petrinet10.gui.visualizacao.elementosgraficos.Elipse;
import petrinet10.gui.visualizacao.elementosgraficos.JArco;
import petrinet10.gui.visualizacao.elementosgraficos.JLugar;
import petrinet10.gui.visualizacao.elementosgraficos.JTexto;
import petrinet10.gui.visualizacao.elementosgraficos.JTransicao;
import petrinet10.gui.visualizacao.elementosgraficos.Reta;
import petrinet10.gui.visualizacao.elementosgraficos.Retangulo;
import petrinet10.gui.visualizacao.elementosgraficos.SelecaoLugarTransicao;

/**
 *
 * @author _Renatu
 */
public class PetriPanelListener implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {
    
    private Point pontopressionado;
    private ArrayList inicialtemp;
    private PetriPanel painel;
    private Reta retatemp;
    private Retangulo retangulotemp;
    private Elipse elipsetemp;
    private JLugar lugartemp;
    private JTransicao transicaotemp;
    private boolean moverglobal;
    private int quina;
    private boolean alterartamanho;
    private boolean sobrecirculo, criandoarco, movevertice, movepontomedio;
    private int verticetemp, pontomediotemp;
    private JArco arcotemp;
    
    public PetriPanelListener(PetriPanel painel) {
        this.painel = painel;
        moverglobal=false;
        criandoarco=false;
        alterartamanho=false;
        quina=-1;
    }
    
    public void mouseClicked(MouseEvent mouseEvent) {
        painel.requestFocus();
        int funcao = Controle.getControle().getFuncao();
        pontopressionado = mouseEvent.getPoint();
        if (mouseEvent.getButton()==mouseEvent.BUTTON1) {
            switch (funcao) {
                case Controle.SETA:
                    ArrayList elementos = painel.getElementosGraficos();
                    Point ponto = mouseEvent.getPoint();
                    Controle.getControle().atualizarPropriedades();
                    boolean selecionado=false;
                    int i=elementos.size()-1;
                    while (!selecionado && i>=0) {
                        ElementoGrafico eg = (ElementoGrafico)elementos.get(i);
                        if (eg.getShape().contains(ponto)) {
                            painel.limparSelecao();
                            painel.addSelecao(eg);
                            selecionado=true;
                            if (eg instanceof JLugar) {
                                JLugar lugar = (JLugar)eg;
                                SelecaoLugarTransicao slt = new SelecaoLugarTransicao(lugar);
                                painel.setSelecaoLugarTransicao(slt);
                            } else if (eg instanceof JTransicao) {
                                JTransicao transicao = (JTransicao)eg;
                                SelecaoLugarTransicao slt = new SelecaoLugarTransicao(transicao);
                                painel.setSelecaoLugarTransicao(slt);
                            }
                            painel.repaint();
                        }
                        i--;
                    }
                    if (!selecionado) {
                        painel.removeSelecaoLugarTransicao();
                        painel.limparSelecao();
                        painel.repaint();
                    }
                    break;
                case Controle.LUGAR:
                    JLugar lugar = new JLugar(mouseEvent.getPoint());
                    painel.addElementoGrafico(lugar);
                    Controle.getControle().incrementaNumeroLugar();
                    painel.repaint();
                    break;
                case Controle.TRANSICAO:
                    JTransicao transicao = new JTransicao(mouseEvent.getPoint());
                    painel.addElementoGrafico(transicao);
                    Controle.getControle().incrementaNumeroTransicao();
                    painel.repaint();
                    break;
                case Controle.TEXTO:
                    String texto = JOptionPane.showInputDialog(painel, "Digite o texto: ");
                    if (texto!=null && !texto.trim().equals("")) {
                        JTexto t = new JTexto(mouseEvent.getPoint(), texto);
                        painel.addElementoGrafico(t);
                    }
                    Controle.getControle().repintarJanelaInterna();
                    painel.repaint();
                    break;
            }
            criandoarco = false;
            if (lugartemp!=null) painel.removeElementoGrafico(lugartemp);
            if (transicaotemp!=null) painel.removeElementoGrafico(transicaotemp);
            //Controle.getControle().atualizarGerenciador();
        } else if (mouseEvent.getButton()==mouseEvent.BUTTON3) {
            new PetriPopUp(painel).show(painel, mouseEvent.getX(), mouseEvent.getY());
        }
    }
    
    public void mouseEntered(MouseEvent mouseEvent) {
        painel.setCursor(Controle.getControle().getPetriPanelCursor());
    }
    
    public void mouseExited(MouseEvent mouseEvent) {
        painel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        if (lugartemp!=null || transicaotemp!=null) {
            painel.removeElementoGrafico(lugartemp);
            painel.removeElementoGrafico(transicaotemp);
            painel.repaint();
            lugartemp = null;
            transicaotemp=null;
        }
    }
    
    public void mousePressed(MouseEvent mouseEvent) {
        painel.requestFocus();
        if (mouseEvent.getButton()==MouseEvent.BUTTON1) {
            int funcao = Controle.getControle().getFuncao();
            pontopressionado = mouseEvent.getPoint();
            switch (funcao) {
                case Controle.SETA:
                    if (quina>=0) {
                        alterartamanho=true;
                    } else if (sobrecirculo) {
                        criandoarco=true;
                    } else if (clicouEmAlgumVertice(pontopressionado)){
                        //vertices dos arcos
                        movevertice = true;
                    } else if (clicouEmAlgumPontoMedio(pontopressionado)) {
                        //pontos medios dos arcos:
                        movepontomedio = true;
                    } else {
                        ArrayList selecionados = painel.getSelecionados();
                        boolean mover=false;
                        inicialtemp = new ArrayList();
                        for (int i=0; i<selecionados.size(); i++) {
                            ElementoGrafico eg = (ElementoGrafico)selecionados.get(i);
                            if (eg.getShape().contains(pontopressionado)) {
                                mover=true;
                            }
                        }
                        if (!mover) {
                            ElementoGrafico eg2 = null;
                            int i = painel.getElementosGraficos().size()-1;
                            boolean continuar=true;
                            while (i>=0 && continuar) {
                                eg2 = (ElementoGrafico)painel.getElementosGraficos().get(i);
                                if (eg2.getShape().contains(pontopressionado)) continuar=false;
                                i--;
                            }
                            if (!continuar) {
                                painel.limparSelecao();
                                painel.addSelecao(eg2);
                                mover=true;
                            }
                        }
                        if (mover) {
                            moverglobal=true;
                            selecionados = painel.getSelecionados();
                            for (int i=0; i<selecionados.size(); i++) {
                                inicialtemp.add(((ElementoGrafico)selecionados.get(i)).getPosicao());
                            }
                        } else {
                            painel.limparSelecao();
                            retangulotemp = new Retangulo(pontopressionado, pontopressionado);
                            painel.addElementoGrafico(retangulotemp);
                            painel.repaint();
                        }
                    }
                    break;
                case Controle.RETA:
                    retatemp = new Reta(pontopressionado, pontopressionado);
                    painel.addElementoGrafico(retatemp);
                    painel.repaint();
                    break;
                case Controle.RETANGULO:
                    retangulotemp = new Retangulo(pontopressionado, pontopressionado);
                    painel.addElementoGrafico(retangulotemp);
                    painel.repaint();
                    break;
                case Controle.ELIPSE:
                    elipsetemp = new Elipse(pontopressionado, pontopressionado);
                    painel.addElementoGrafico(elipsetemp);
                    painel.repaint();
                    break;
            }
        }
    }
    
    public void mouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton()==MouseEvent.BUTTON1) {
            if (!moverglobal && !alterartamanho) {
                int funcao = Controle.getControle().getFuncao();
                Point pontofinal = mouseEvent.getPoint();
                switch (funcao) {
                    case Controle.SETA:
                        if (criandoarco) {
                            ArrayList elementos = painel.getElementosGraficos();
                            int i = elementos.size()-1;
                            boolean continuar=true;
                            ElementoGrafico elemento = painel.getSelecaoLugarTransicao().getElemento();
                            while (i>=0 && continuar) {
                                ElementoGrafico eg = (ElementoGrafico)elementos.get(i);
                                if (elemento instanceof JLugar && eg instanceof JTransicao) {
                                    if (eg.getShape().contains(pontofinal)) {
                                        continuar=false;
                                        JLugar lugar = (JLugar)elemento;
                                        JTransicao transicao = (JTransicao)eg;
                                        JArco arco = new JArco(lugar, transicao, JArco.LUGAR_TRANSICAO);
                                        painel.addElementoGrafico(arco);
                                        painel.removeElementoGrafico(retatemp);
                                        criandoarco=false;
                                        painel.repaint();
                                    }
                                } else if (elemento instanceof JTransicao && eg instanceof JLugar) {
                                    if (eg.getShape().contains(pontofinal)) {
                                        continuar=false;
                                        JLugar lugar = (JLugar)eg;
                                        JTransicao transicao = (JTransicao)elemento;
                                        JArco arco = new JArco(lugar, transicao, JArco.TRANSICAO_LUGAR);
                                        painel.addElementoGrafico(arco);
                                        painel.removeElementoGrafico(retatemp);
                                        criandoarco=false;
                                        painel.repaint();
                                    }
                                }
                                i--;
                            }
                            if (continuar==true) {
                                painel.removeElementoGrafico(retatemp);
                                painel.repaint();
                                criandoarco = false;
                            }
                        } else {
                            ArrayList elementos = painel.getElementosGraficos();
                            double x,y,w,h;
                            x = Math.min(pontopressionado.getX(), pontofinal.getX());
                            y = Math.min(pontopressionado.getY(), pontofinal.getY());
                            w = Math.abs(pontopressionado.getX()-pontofinal.getX());
                            h = Math.abs(pontopressionado.getY()-pontofinal.getY());
                            Rectangle2D r2 = new Rectangle2D.Double(x,y,w,h);
                            if (elementos!=null) {
                                for (int i=0; i<elementos.size(); i++) {
                                    ElementoGrafico eg = (ElementoGrafico)elementos.get(i);
                                    if (eg!=retangulotemp) {
                                        Rectangle2D compara = eg.getShape().getBounds2D();
                                        if (r2.contains(compara)) {
                                            painel.addSelecao(eg);
                                        }
                                    }
                                }
                            }
                            painel.removeElementoGrafico(retangulotemp);
                            painel.repaint();
                            Controle.getControle().atualizarPropriedades();
                            break;
                        }
                }
            }
            pontopressionado = null;
            criandoarco=false;
            sobrecirculo=false;
            moverglobal = false;
            alterartamanho=false;
            movevertice=false;
            retatemp = null;
            retangulotemp = null;
            elipsetemp = null;
            Controle.getControle().atualizarGerenciador();
        }
    }
    
    public void mouseDragged(MouseEvent mouseEvent) {
        painel.requestFocus();
        int funcao = Controle.getControle().getFuncao();
        Point pontomovido = mouseEvent.getPoint();
        switch (funcao) {
            case Controle.SETA:
                if (pontopressionado!=null) {
                    if (criandoarco) {
                        painel.removeElementoGrafico(retatemp);
                        Point p = new Point((int)painel.getSelecaoLugarTransicao().getPosicao().getX(),
                                (int)painel.getSelecaoLugarTransicao().getPosicao().getY());
                        retatemp = new Reta(p, pontomovido);
                        painel.addElementoGrafico(retatemp);
                        painel.repaint();
                    } else if (movevertice) {
                        arcotemp.moveVertice(verticetemp, pontomovido.getX(), pontomovido.getY());
                        painel.repaint();
                    } else if(movepontomedio) {
                        arcotemp.criaVertice(pontomediotemp, pontomovido.getX(), pontomovido.getY());
                        movepontomedio = false;
                        verticetemp = pontomediotemp;
                        movevertice = true;
                    } else if (alterartamanho) {
                        ArrayList selecionados = painel.getSelecionados();
                        switch (quina) {
                            case CaixaSelecao.SUL:
                                for (int i=0; i<selecionados.size(); i++) {
                                    double deltay = pontomovido.getY()-pontopressionado.getY();
                                    ElementoGrafico eg = (ElementoGrafico)selecionados.get(i);
                                    eg.setAltura(eg.getAltura()+deltay);
                                }
                                pontopressionado = pontomovido;
                                painel.repaint();
                                break;
                            case CaixaSelecao.NORTE:
                                for (int i=0; i<selecionados.size(); i++) {
                                    double deltay = -pontomovido.getY()+pontopressionado.getY();
                                    ElementoGrafico eg = (ElementoGrafico)selecionados.get(i);
                                    Point2D.Double p = eg.getPosicao();
                                    p.y = p.y-deltay;
                                    eg.setAltura(eg.getAltura()+deltay);
                                }
                                pontopressionado = pontomovido;
                                painel.repaint();
                                break;
                            case CaixaSelecao.LESTE:
                                for (int i=0; i<selecionados.size(); i++) {
                                    double deltax = pontomovido.getX()-pontopressionado.getX();
                                    ElementoGrafico eg = (ElementoGrafico)selecionados.get(i);
                                    eg.setLargura(eg.getLargura()+deltax);
                                }
                                pontopressionado = pontomovido;
                                painel.repaint();
                                break;
                            case CaixaSelecao.OESTE:
                                for (int i=0; i<selecionados.size(); i++) {
                                    double deltax = -pontomovido.getX()+pontopressionado.getX();
                                    ElementoGrafico eg = (ElementoGrafico)selecionados.get(i);
                                    Point2D.Double p = eg.getPosicao();
                                    p.x = p.x-deltax;
                                    eg.setLargura(eg.getLargura()+deltax);
                                }
                                pontopressionado = pontomovido;
                                painel.repaint();
                                break;
                            case CaixaSelecao.SUDESTE:
                                for (int i=0; i<selecionados.size(); i++) {
                                    double deltay = pontomovido.getY()-pontopressionado.getY();
                                    double deltax = pontomovido.getX()-pontopressionado.getX();
                                    ElementoGrafico eg = (ElementoGrafico)selecionados.get(i);
                                    eg.setAltura(eg.getAltura()+deltay);
                                    eg.setLargura(eg.getLargura()+deltax);
                                }
                                pontopressionado = pontomovido;
                                painel.repaint();
                                break;
                            case CaixaSelecao.SUDOESTE:
                                for (int i=0; i<selecionados.size(); i++) {
                                    double deltay = pontomovido.getY()-pontopressionado.getY();
                                    double deltax = -pontomovido.getX()+pontopressionado.getX();
                                    ElementoGrafico eg = (ElementoGrafico)selecionados.get(i);
                                    Point2D.Double p = eg.getPosicao();
                                    p.x = p.x-deltax;
                                    eg.setAltura(eg.getAltura()+deltay);
                                    eg.setLargura(eg.getLargura()+deltax);
                                }
                                pontopressionado = pontomovido;
                                painel.repaint();
                                break;
                            case CaixaSelecao.NORDESTE:
                                for (int i=0; i<selecionados.size(); i++) {
                                    double deltay = -pontomovido.getY()+pontopressionado.getY();
                                    double deltax = pontomovido.getX()-pontopressionado.getX();
                                    ElementoGrafico eg = (ElementoGrafico)selecionados.get(i);
                                    Point2D.Double p = eg.getPosicao();
                                    p.y = p.y-deltay;
                                    eg.setAltura(eg.getAltura()+deltay);
                                    eg.setLargura(eg.getLargura()+deltax);
                                }
                                pontopressionado = pontomovido;
                                painel.repaint();
                                break;
                            case CaixaSelecao.NOROESTE:
                                for (int i=0; i<selecionados.size(); i++) {
                                    double deltay = -pontomovido.getY()+pontopressionado.getY();
                                    double deltax = -pontomovido.getX()+pontopressionado.getX();
                                    ElementoGrafico eg = (ElementoGrafico)selecionados.get(i);
                                    Point2D.Double p = eg.getPosicao();
                                    p.y = p.y-deltay;
                                    p.x = p.x-deltax;
                                    eg.setAltura(eg.getAltura()+deltay);
                                    eg.setLargura(eg.getLargura()+deltax);
                                }
                                pontopressionado = pontomovido;
                                painel.repaint();
                                break;
                                
                        } //end switch
                    } else if (!moverglobal) {
                        painel.removeElementoGrafico(retangulotemp);
                        retangulotemp = new Retangulo(pontopressionado, pontomovido, Color.GRAY, null);
                        painel.addElementoGrafico(retangulotemp);
                        painel.repaint();
                    } else {
                        ArrayList selecionados = painel.getSelecionados();
                        for (int i=0; i<selecionados.size(); i++) {
                            ElementoGrafico eg = (ElementoGrafico)selecionados.get(i);
                            Point2D.Double pi = (Point2D.Double)inicialtemp.get(i);
                            Point2D.Double p = new Point2D.Double(pi.getX()+pontomovido.getX()-pontopressionado.getX(),
                                    pi.getY()+pontomovido.getY()-pontopressionado.getY());
                            eg.setPosicao(p);
                            painel.repaint();
                        }
                    }
                }
                break;
            case Controle.RETA:
                if (pontopressionado!=null) {
                    painel.removeElementoGrafico(retatemp);
                    retatemp = new Reta(pontopressionado, pontomovido);
                    painel.addElementoGrafico(retatemp);
                    painel.repaint();
                }
                break;
            case Controle.RETANGULO:
                if (pontopressionado!=null) {
                    painel.removeElementoGrafico(retangulotemp);
                    retangulotemp = new Retangulo(pontopressionado, pontomovido, Color.BLUE, Color.YELLOW);
                    painel.addElementoGrafico(retangulotemp);
                    painel.repaint();
                }
                break;
            case Controle.ELIPSE:
                if (pontopressionado!=null) {
                    painel.removeElementoGrafico(elipsetemp);
                    elipsetemp = new Elipse(pontopressionado, pontomovido, Color.BLUE, Color.RED);
                    painel.addElementoGrafico(elipsetemp);
                    painel.repaint();
                }
                break;
        }
    }
    
    public void mouseMoved(MouseEvent mouseEvent) {
        if (Controle.getControle().getFuncao()==Controle.SETA) {
            Point2D.Double ponto = new Point2D.Double(mouseEvent.getPoint().getX(), mouseEvent.getPoint().getY());
            if (painel.getCaixaSelecao()!=null) {
                quina = painel.getCaixaSelecao().contem(ponto);
                Cursor cursor = Controle.getControle().getPetriPanelCursor();
                if (quina!=-1) {
                    switch (quina) {
                        case CaixaSelecao.LESTE: cursor = new Cursor(Cursor.E_RESIZE_CURSOR); break;
                        case CaixaSelecao.OESTE: cursor = new Cursor(Cursor.W_RESIZE_CURSOR); break;
                        case CaixaSelecao.NORTE: cursor = new Cursor(Cursor.N_RESIZE_CURSOR); break;
                        case CaixaSelecao.SUL: cursor = new Cursor(Cursor.S_RESIZE_CURSOR); break;
                        case CaixaSelecao.SUDESTE: cursor = new Cursor(Cursor.SE_RESIZE_CURSOR); break;
                        case CaixaSelecao.SUDOESTE: cursor = new Cursor(Cursor.SW_RESIZE_CURSOR); break;
                        case CaixaSelecao.NORDESTE: cursor = new Cursor(Cursor.NE_RESIZE_CURSOR); break;
                        case CaixaSelecao.NOROESTE: cursor = new Cursor(Cursor.NW_RESIZE_CURSOR); break;
                    }
                }
                painel.setCursor(cursor);
            }
            if (painel.getSelecaoLugarTransicao()!=null) {
                SelecaoLugarTransicao slt = painel.getSelecaoLugarTransicao();
                if (slt.getShape().contains(mouseEvent.getPoint())) {
                    painel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    sobrecirculo=true;
                }
            } else {
                sobrecirculo=false;
            }
        } else if (Controle.getControle().getFuncao()==Controle.LUGAR) {
            painel.removeElementoGrafico(lugartemp);
            lugartemp = new JLugar(mouseEvent.getPoint());
            painel.addElementoGrafico(lugartemp);
            painel.repaint();
        } else if (Controle.getControle().getFuncao()==Controle.TRANSICAO) {
            painel.removeElementoGrafico(transicaotemp);
            transicaotemp = new JTransicao(mouseEvent.getPoint());
            painel.addElementoGrafico(transicaotemp);
            painel.repaint();
        }
    }
    
    public void mouseWheelMoved(MouseWheelEvent mouseEvent) {
        ArrayList selecionados = painel.getSelecionados();
        for (int i=0; i<selecionados.size(); i++) {
            ElementoGrafico eg = (ElementoGrafico)selecionados.get(i);
            if (eg instanceof JLugar) {
                JLugar lugar = (JLugar)eg;
                lugar.setInitialtokens(lugar.getInitialtokens() + mouseEvent.getWheelRotation());
                painel.repaint();
            }
        }
    }
    
    private boolean clicouEmAlgumVertice(Point p) {
        boolean retorno = false;
        ArrayList elementos = painel.getElementosGraficos();
        for (int i=0; i<elementos.size(); i++) {
            ElementoGrafico eg = (ElementoGrafico)elementos.get(i);
            if (eg instanceof JArco) {
                JArco arco = (JArco)eg;
                ArrayList vertices = arco.getVertices();
                for (int j=0; j<vertices.size(); j++) {
                    Ellipse2D.Double circulo = (Ellipse2D.Double)vertices.get(j);
                    if (circulo.contains(p)) {
                        retorno = true;
                        verticetemp = j;
                        arcotemp = arco;
                    }
                }
            }
        }
        return retorno;
    }
    
    private boolean clicouEmAlgumPontoMedio(Point p) {
        boolean retorno = false;
        ArrayList elementos = painel.getElementosGraficos();
        for (int i=0; i<elementos.size(); i++) {
            ElementoGrafico eg = (ElementoGrafico)elementos.get(i);
            if (eg instanceof JArco) {
                JArco arco = (JArco)eg;
                ArrayList pontosmedios = arco.getPontosMedios();
                for (int j=0; j<pontosmedios.size(); j++) {
                    Ellipse2D.Double circulo = (Ellipse2D.Double)pontosmedios.get(j);
                    if (circulo.contains(p)) {
                        retorno = true;
                        pontomediotemp = j;
                        arcotemp = arco;
                    }
                }
            }
        }
        return retorno;
    }
    
    public void keyTyped(KeyEvent keyEvent) {
    }
    
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode()==KeyEvent.VK_DELETE) {
            ArrayList selecionados = painel.getSelecionados();
            ArrayList elementosgraficos = painel.getElementosGraficos();
            if (selecionados!=null) {
                for (int i=0; i<selecionados.size(); i++) {
                    ElementoGrafico eg = (ElementoGrafico)selecionados.get(i);
                    if (eg instanceof JLugar) {
                        for (int j=0; j<elementosgraficos.size(); j++) {
                            ElementoGrafico eg2 = (ElementoGrafico)elementosgraficos.get(j);
                            if (eg2 instanceof JArco) {
                                JArco arco = (JArco)eg2;
                                if (arco.getLugar().equals(eg)) {
                                    painel.removeElementoGrafico(arco);
                                }
                            }
                        }
                    } else if (eg instanceof JTransicao) {
                        for (int j=0; j<elementosgraficos.size(); j++) {
                            ElementoGrafico eg2 = (ElementoGrafico)elementosgraficos.get(j);
                            if (eg2 instanceof JArco) {
                                JArco arco = (JArco)eg2;
                                if (arco.getTransicao().equals(eg)) {
                                    painel.removeElementoGrafico(arco);
                                }
                            }
                        }
                    }
                    painel.removeElementoGrafico(eg);
                }
            }
            painel.setSelecionados(new ArrayList());
            painel.setSelecaoLugarTransicao(null);
            painel.repaint();
            Controle.getControle().atualizarGerenciador();
        }
    }
    
    public void keyReleased(KeyEvent keyEvent) {
    }
    
}
