/*
 * ExecutaRede.java
 *
 * Created on 8 de Novembro de 2006, 12:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.controle;

import java.util.ArrayList;
import java.util.Hashtable;
import petrinet10.core.Arco;
import petrinet10.core.ArcoAbstrato;
import petrinet10.core.ArcoInibidor;
import petrinet10.core.ArcoTeste;
import petrinet10.core.Lugar;
import petrinet10.core.PetriNet;
import petrinet10.core.Transicao;
import petrinet10.core.TransicaoFundida;
import petrinet10.core.TransicaoTemporizada;
import petrinet10.gui.visualizacao.PetriPanel;
import petrinet10.gui.visualizacao.elementosgraficos.ElementoGrafico;
import petrinet10.gui.visualizacao.elementosgraficos.JArco;
import petrinet10.gui.visualizacao.elementosgraficos.JLugar;
import petrinet10.gui.visualizacao.elementosgraficos.JTransicao;
import petrinet10.gui.visualizacao.JanelaPrincipal;

import webservice.Token;

/**
 *
 * @author _Renatu
 */
public class ExecutaRede implements Runnable {
    
    private PetriPanel painel;
    public static PetriNet rede;
    private boolean executar;
    
    //incluih isso aqui tbm.
    public ExecutaRede getExecutaRede(){
        return this;
    }
    
    /** Creates a new instance of ExecutaRede */
    public ExecutaRede(PetriPanel painel) {
        this.painel = painel;
        this.executar = true;
        rede = new PetriNet("Rede");
        ArrayList elementos = painel.getElementosGraficos();
        Hashtable tabela = new Hashtable();
        //adiciona os lugares e as transicoes a rede:
        for (int i=0; i<elementos.size(); i++) {
            ElementoGrafico eg = (ElementoGrafico)elementos.get(i);
            if (eg instanceof JLugar) {
                JLugar jlugar = (JLugar)eg;
                String nome = jlugar.getNome();
                Lugar l = new Lugar(nome);
                l.setTokens(jlugar.getTokens());
                l.setCodigos(jlugar.getCodigo());
                l.setCapacidade(jlugar.getCapacidade());
                rede.addLugar(l);
                tabela.put(nome, l);
            } else if (eg instanceof JTransicao) {
                JTransicao jtransicao = (JTransicao)eg;
                String nome = jtransicao.getNome();
                if(jtransicao.getType().equals("normal")){
                    
                    Transicao t = new Transicao(nome);
                    t.setCodigos(jtransicao.getCodigo());
                    rede.addTransicao(t);
                    tabela.put(nome,t);
                    System.out.println("Criou Normal");
                    
                } else if (jtransicao.getType().equals("TF")){
                    //getStation1
                    //getTransName1
                    
                    Transicao t = new Transicao(
                            jtransicao.getT1(),
                            jtransicao.getS1(),
                            jtransicao.getT2(),
                            jtransicao.getS2());
                    t.setCodigos(jtransicao.getCodigo());
                    rede.addTransicao(t);
                    tabela.put(nome,t);
                    System.out.println("Criou TF");
                    
                } else if(jtransicao.getType().equals("TT")){
                    Transicao t = new Transicao(
                            nome,
                            jtransicao.getTempo());
                    t.setCodigos(jtransicao.getCodigo());
                    rede.addTransicao(t);
                    tabela.put(nome,t);
                    System.out.println("Criou TT");
                }
                
            }
//                bkp
//                String nome = jtransicao.getNome();
//                Transicao t = new Transicao(nome);
//                t.setCodigos(jtransicao.getCodigo());
//                rede.addTransicao(t);
//                tabela.put(nome, t);
            
        }
        
        
        //AQUI EU DEVO INSERIR OS ARCOS PARA CADA TIPO DE TRANSICAO... SENÃO TRAVA.
        
        //adiciona os arcos:
        for (int i=0; i<elementos.size(); i++) {
            ElementoGrafico eg = (ElementoGrafico)elementos.get(i);
            if (eg instanceof JArco) {
                JArco jarco = (JArco)eg;
                Lugar l = (Lugar)tabela.get(jarco.getLugar().getNome());
                
                //IF TABELA.GET.EQUALS(TIPO).. CREATE DIFFERENT TYPE OF t
               
                //ele pega o ponteiro da transicao com o nome devido...
                Transicao t = (Transicao)tabela.get(jarco.getTransicao().getNome());
                
                //System.out.println("Transicao tipo "+jarco.getTransicao().getType()); 
                //System.out.println("Transicao tipo "+t.getType()); 
                
                ArcoAbstrato a = null;
                if (jarco.getTipo()==JArco.TIPO_NORMAL) a = new Arco(l, t, jarco.getPeso(), jarco.getSentido());
                else if (jarco.getTipo()==JArco.TIPO_INIBIDOR) a = new ArcoInibidor(l, t, jarco.getPeso());
                else if (jarco.getTipo()==JArco.TIPO_HABILITADOR) a = new ArcoTeste(l, t, jarco.getPeso());
                if (a!=null) rede.addArco(a);
            }
        }
    }
    
// ESSA AQUI EU COPIEI DEPOIS... MAS NÃO DEU MUITO CERTO... ELA TRAVA DEPOIS DE DISPARAR...   
//    public void run() {
//        int tempo = 1000;
//        while(executar) {
//            
//            painel.repaint();
//            painel.paintImmediately(0,0,1024,768);            
//            
//            try {
//                Thread.sleep(tempo);
//            } catch (InterruptedException ie) {
//                System.out.println("Erro na interrupcao.");
//            }
//            
//            //Aditivos (inicio)-->
//            rede.separarTransicoesAtivas(); //para rodar analise de deadlock...        
//            Token.getToken().TrataToken_Recebe();
//            //<-- Aditivos(fim)
//
//            
//            
//            if (rede.passo()) {
//                ArrayList elementos = painel.getElementosGraficos();
//                limparTransicoes();
//                ArrayList transicoes = rede.getTransicoes();
//                for (int i=0; i<transicoes.size(); i++) {
//                    Transicao t = (Transicao)transicoes.get(i);
//                    if (t.isAtiva()) {
//                        String nome = t.getNome();
//                        for (int j=0; j<elementos.size(); j++) {
//                            ElementoGrafico eg = (ElementoGrafico)elementos.get(j);
//                            if (eg instanceof JTransicao) {
//                                JTransicao jt = (JTransicao)eg;
//                                if (jt.getNome().equals(nome)) jt.setAtiva(true);
//                            }
//                        }
//                    }
//                }
//                for (int i=0; i<elementos.size(); i++) {
//                    ElementoGrafico eg = (ElementoGrafico)elementos.get(i);
//                    if (eg instanceof JArco) {
//                        JArco ja = (JArco)eg;
//                        if (ja.getTransicao().isAtiva()) ja.setAtivo(true);
//                    }
//                }
//                
//                painel.repaint();
//                painel.paintImmediately(0,0,1024,768);
//                
//                try {
//                    Thread.sleep(tempo);
//                } catch (InterruptedException ie) {
//                    System.out.println("Erro na interrupcao.");
//                }
//                limparTransicoes();
//                ArrayList lugares = rede.getLugares();
//                for (int i=0; i<lugares.size(); i++) {
//                    Lugar l = (Lugar)lugares.get(i);
//                    String nome = l.getNome();
//                    JLugar jlugar = null;
//                    for (int j=0; j<elementos.size(); j++) {
//                        ElementoGrafico eg = (ElementoGrafico)elementos.get(j);
//                        if (eg instanceof JLugar) {
//                            JLugar jl = (JLugar)eg;
//                            if (jl.getNome().equals(nome)) jlugar = jl;
//                        }
//                    }
//                    jlugar.setTokens(l.getTokens());
//                }
//                painel.repaint();
//                painel.paintImmediately(0,0,1024,768);
//                
//                Controle.getControle().atualizarPropriedades();
//                Controle.getControle().atualizarVariaveis();
//            }
//            if (Token.getToken().getInstrucao()!=0){
//                Token.getToken().ControleToken();
//            } else{
//                executar=false; //pronto.
//            }
//        }
//    }    
    
    
    public void run() {
        int tempo = 500;
        while(executar) {
            
            Controle.getControle().refresh_tempo();
            painel.repaint();
            painel.paintImmediately(0,0,1024,768);
            
            
            try {
                Thread.sleep(tempo);
            } catch (InterruptedException ie) {
                System.out.println("Erro na interrupcao.");
            }
                        
            /*this.passo();
            if (Token.getToken().getInstrucao()!=0) executar=false;//Token.getToken().ControleToken();*/
            
            //é só inserir o que pusemos na "PASSO()"
            
            //STA
            //System.out.println("pré-STA");
            rede.separarTransicoesAtivas(); //para rodar analise de deadlock...        
            //System.out.println("pós-STA");
            Token.getToken().TrataToken_Recebe();        
            //System.out.println("pós-TTrecebe");
            
            Controle.getControle().refresh_tempo();
            painel.repaint();
            painel.paintImmediately(0,0,1024,768);
            
            
            if (rede.passo()) {
                //rodaremos
                                
                ArrayList elementos = painel.getElementosGraficos();
                limparTransicoes();
                ArrayList transicoes = rede.getTransicoes();
                for (int i=0; i<transicoes.size(); i++) {
                    Transicao t = (Transicao)transicoes.get(i);
                    if (t.isAtiva()) {
                        String nome = t.getNome();
                        for (int j=0; j<elementos.size(); j++) {
                            ElementoGrafico eg = (ElementoGrafico)elementos.get(j);
                            if (eg instanceof JTransicao) {
                                JTransicao jt = (JTransicao)eg;
                                if (jt.getNome().equals(nome)) jt.setAtiva(true);
                            }
                        }
                    }
                }
                for (int i=0; i<elementos.size(); i++) {
                    ElementoGrafico eg = (ElementoGrafico)elementos.get(i);
                    if (eg instanceof JArco) {
                        JArco ja = (JArco)eg;
                        if (ja.getTransicao().isAtiva()) ja.setAtivo(true);
                    }
                }
                Controle.getControle().refresh_tempo();
                painel.repaint();
                painel.paintImmediately(0,0,1024,768);
                
                try {
                    Thread.sleep(tempo);
                } catch (InterruptedException ie) {
                    System.out.println("Erro na interrupcao.");
                }
                limparTransicoes();
                ArrayList lugares = rede.getLugares();
                for (int i=0; i<lugares.size(); i++) {
                    Lugar l = (Lugar)lugares.get(i);
                    String nome = l.getNome();
                    JLugar jlugar = null;
                    for (int j=0; j<elementos.size(); j++) {
                        ElementoGrafico eg = (ElementoGrafico)elementos.get(j);
                        if (eg instanceof JLugar) {
                            JLugar jl = (JLugar)eg;
                            if (jl.getNome().equals(nome)) jlugar = jl;
                        }
                    }
                    jlugar.setTokens(l.getTokens());
                }
                /*painel.repaint();
                painel.paintImmediately(0,0,1024,768);*/
                Controle.getControle().atualizarPropriedades();
                Controle.getControle().atualizarVariaveis();
            }
            Controle.getControle().refresh_tempo();
            painel.repaint();
            painel.paintImmediately(0,0,1024,768);
            
            
            //System.out.println("pós-passo");
            Token.getToken().TrataToken_Envia();
            //System.out.println("pós-TTenvia");
            Token.getToken().enviaToken();
            
            if (Token.getToken().getInstrucao()!=0){
                //painel.repaint();
                //painel.repaint(0);
                Token.getToken().ControleToken();
                if(Token.getToken().getInstrucao()==0) {
                    executar=false;
                    Token.getToken().enviaToken();
                }
                //executar=false;
            } else{
                executar=false; //pronto.
            }
        }
    }
    
    public void parar() {
        this.executar = false;
        limparTransicoes();
        painel.repaint();
    }
    
    public void refresh(){
        painel.repaint();
        painel.paintImmediately(0,0,1280,800);
    }
    
    private void limparTransicoes() {
        ArrayList elementos = painel.getElementosGraficos();
        for (int i=0; i<elementos.size(); i++) {
            ElementoGrafico eg = (ElementoGrafico)elementos.get(i);
            if (eg instanceof JTransicao) ((JTransicao)eg).setAtiva(false);
            else if (eg instanceof JArco) ((JArco)eg).setAtivo(false);
        }
    }
    
    public boolean refresh_chamada(){
        if (true) {
            ArrayList lugares = rede.getLugares();
            ArrayList elementos = painel.getElementosGraficos();
            for (int i=0; i<lugares.size(); i++) {
                Lugar l = (Lugar)lugares.get(i);
                String nome = l.getNome();
                JLugar jlugar = null;
                for (int j=0; j<elementos.size(); j++) {
                    ElementoGrafico eg = (ElementoGrafico)elementos.get(j);
                    if (eg instanceof JLugar) {
                        JLugar jl = (JLugar)eg;
                        if (jl.getNome().equals(nome)) jlugar = jl;
                    }
                }
                jlugar.setTokens(l.getTokens());
            }
            Controle.getControle().refresh_tempo();
            painel.repaint();
            painel.paintImmediately(0,0,1280,800);
            Controle.getControle().atualizarVariaveis();            
        }
        return true;
    }
    
    //NOVO FLUXO DE PROGRAMA
    public void passo() {
        
        Controle.getControle().refresh_tempo();
        painel.repaint(); //para mostrar mudanças ocorridas via chamada de método...
        painel.paintImmediately(0,0,1280,800);
        //STA
        //System.out.println("pré-STA");
        rede.separarTransicoesAtivas(); //para rodar analise de deadlock...        
        //System.out.println("pós-STA");
        Token.getToken().TrataToken_Recebe();        
        //System.out.println("pós-TTrecebe");
        if (rede.passo()) {
            ArrayList lugares = rede.getLugares();
            ArrayList elementos = painel.getElementosGraficos();
            for (int i=0; i<lugares.size(); i++) {
                Lugar l = (Lugar)lugares.get(i);
                String nome = l.getNome();
                JLugar jlugar = null;
                for (int j=0; j<elementos.size(); j++) {
                    ElementoGrafico eg = (ElementoGrafico)elementos.get(j);
                    if (eg instanceof JLugar) {
                        JLugar jl = (JLugar)eg;
                        if (jl.getNome().equals(nome)) jlugar = jl;
                    }
                }
                jlugar.setTokens(l.getTokens());
            }

            Controle.getControle().atualizarVariaveis();            
        }
        Controle.getControle().refresh_tempo();
        painel.repaint();
        painel.paintImmediately(0,0,1280,800);        
        //System.out.println("pós-passo");
        Token.getToken().TrataToken_Envia();
        //System.out.println("pós-TTenvia");
        Token.getToken().enviaToken();
        //System.out.println("pós-enviaToken");
                
        //fica aqui até receber o token...tb checa as chamadas de metodo e dispara...
        
        //Espera mensagem se a instrucao nao for "parar"
        if (Token.getToken().getInstrucao()!=0) Token.getToken().ControleToken();       
    }
}
