/*
 * PetriNet.java
 *
 * Created on 6 de Abril de 2006, 11:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.core;

import java.util.ArrayList;
import java.util.Timer;
import webservice.Conecta;
import webservice.Token;
import petrinet10.gui.controle.Controle;
import petrinet10.gui.controle.ExecutaRede;
import petrinet10.gui.visualizacao.PetriPanel;

/**
 *
 * @author _Renatu
 */
public class PetriNet {
    
    String nome;
    ArrayList arcos, lugares, transicoes;
    PetriNetListener listener;
    private static Timer timer;
        
    
    /** Creates a new instance of PetriNet */
    public PetriNet(String nome) {
        this.nome = nome;
        arcos = new ArrayList();
        lugares = new ArrayList();
        transicoes = new ArrayList();
    }
//    public PetriNet getPetriNet(){
//        return this;
//    }
    
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public static Timer getTimer() {
        if (timer==null) timer = new Timer();
        return timer;
    }
    
    public void addArco(ArcoAbstrato arco) {
        this.arcos.add(arco);
    }
    
    public void removeArco(ArcoAbstrato arco) {
        this.arcos.remove(arco);
    }
    
    public ArrayList getArcos() {
        return this.arcos;
    }
    
    public void addLugar(LugarAbstrato lugar) {
        this.lugares.add(lugar);
    }
    
    public void removeLugar(LugarAbstrato lugar) {
        this.lugares.remove(lugar);
    }
    
    public ArrayList getLugares() {
        return this.lugares;
    }
    
    public void addTransicao(TransicaoAbstrata transicao) {
        this.transicoes.add(transicao);
        if (transicao instanceof TransicaoTemporizada) {
            TransicaoTemporizada tt = (TransicaoTemporizada)transicao;
            tt.setPetriNet(this);
        }
    }
    
    public void removeTransicao(TransicaoAbstrata transicao) {
        this.transicoes.add(transicao);
        if (transicao instanceof TransicaoTemporizada) {
            TransicaoTemporizada tt = (TransicaoTemporizada)transicao;
            tt.setPetriNet(null);
        }
    }
    
    public ArrayList getTransicoes() {
        return this.transicoes;
    }
    
//    public boolean publicChamada(String transicao, boolean dispara){
//        boolean ativa = verificaChamadaDeMetodo(transicao, dispara);
//        return ativa;
//    }
    
    public boolean passo() {
        
        //for (int i=0; i<transicoes.size();i++) ((TransicaoAbstrata)transicoes.get(i)).setAtiva(false);
        
        //separar as transicoes potencialmente ativas
        
        //Retirado pois será solicitada antes da passo(), i.e.,
        //fará parte do "TrataEvento"
        
        separarTransicoesAtivas();
                
        //das separadas, verificar condicoes de teste e inibicao, separar
        verificarTesteEInibidores();
        
        //das separadas, verificar as condicoes de sinais:
        verificarSinais();
        
        //colecoes que nao permitem elementos repetidos:
        ArrayList lugaresantes = new ArrayList();
        ArrayList lugaresdepois = new ArrayList();
                
        //separar os lugares antes e depois ativaveis:
        for (int i=0; i<transicoes.size(); i++) {
            TransicaoAbstrata t = (TransicaoAbstrata)transicoes.get(i);
            if (t.isAtiva()) {
                ArrayList arcos = t.getArcos();
                for (int j=0; j<arcos.size(); j++) {
                    ArcoAbstrato arco = (ArcoAbstrato)arcos.get(j);
                    if (arco instanceof Arco) {
                        Arco a = (Arco)arco;
                        LugarAbstrato l = a.getLugar();
                        if (a.getSentido()==Arco.LUGAR_TRANSICAO && !lugaresantes.contains(l)) lugaresantes.add(l);
                        else if (a.getSentido()==Arco.TRANSICAO_LUGAR && !lugaresdepois.contains(l)) lugaresdepois.add(l);
                    }
                }
            }
        }
        //dos lugares ativaveis, separar os que tem transicoes comuns...
        //selecionar randomicamente um.
        for (int i=0; i<lugaresantes.size(); i++) {
            LugarAbstrato l = (LugarAbstrato)lugaresantes.get(i);
            ArrayList arcos = l.getArcos();
            ArrayList temp = new ArrayList();
            for (int j=0; j<arcos.size(); j++) {
                ArcoAbstrato a = (ArcoAbstrato)arcos.get(j);
                if (a instanceof Arco) {
                    Arco arco = (Arco)a;
                    TransicaoAbstrata t = arco.getTransicao();
                    if (arco.getSentido()==Arco.LUGAR_TRANSICAO && t.isAtiva()) {
                        temp.add(t);
                    }
                }
            }
            if (temp.size()>1) {
                int maximo = temp.size();
                int rnd = (int)(Math.random()*maximo);
                for (int j=0; j<temp.size(); j++) {
                    TransicaoAbstrata t = (TransicaoAbstrata)temp.get(j);
                    if (j!=rnd) {
                        t.setAtiva(false);
                    }
                }
            }
        }
        
        //********************************************
        for (int i=0; i<lugaresdepois.size(); i++) {
            LugarAbstrato l = (LugarAbstrato)lugaresdepois.get(i);
            ArrayList arcos = l.getArcos();
            ArrayList temp = new ArrayList();
            for (int j=0; j<arcos.size(); j++) {
                ArcoAbstrato a = (ArcoAbstrato)arcos.get(j);
                if (a instanceof Arco) {
                    Arco arco = (Arco)a;
                    TransicaoAbstrata t = arco.getTransicao();
                    if (arco.getSentido()==Arco.TRANSICAO_LUGAR && t.isAtiva()) temp.add(t);
                }
            }
            if (temp.size()>1) {
                int maximo = temp.size();
                int rnd = (int)(Math.random()*maximo);
                for (int j=0; j<temp.size(); j++) {
                    TransicaoAbstrata t = (TransicaoAbstrata)temp.get(j);
                    if (j!=rnd) {
                        t.setAtiva(false);
                    }
                }
            }
        }
        //AQUI VERIFICA AS TRANSICOES DA OUTRA ESTACAO (CHAMADA DE METODO)
        
        //if stype == TF; then servidor.envia( pergunta à outra estacao))
        
        //d; then verifica se tempo local ><= a tempo necessario para disparar.
        
        
        //finalmente ativa as transicoes necessarias:
        int quantidade=0;
        for (int i=0; i<transicoes.size(); i++) {
            TransicaoAbstrata t = (TransicaoAbstrata)transicoes.get(i);
            if (t.isAtiva()) {
                quantidade++;
                
                if (t instanceof Transicao) {
                    ArrayList arcosativos = t.getArcos();
                    if(t.getType().equals("normal")){
                        System.out.println(t.getNome()+" foi disparada. Tempo= "+Token.getToken().getRelogioLocal());
                        Conecta.getServidor().gravacaoResulto(t.getNome()+" foi disparada. Tempo= "+Token.getToken().getRelogioLocal());
                    } else if (t.getType().equals("TF")){
                        Transicao tf = (Transicao)t;
                        
                        //rodar o VERIFICACHAMADA DE METODO PARA DISPARAR=TRUE.
                        
                        String transicao = tf.getTransName2();//transicao
                        char station = tf.getStation2(); //da outra estacao
                        //aqui ele manda a chamada para saber se está ou não ativa
                        boolean ativa = Conecta.getServidor().enviaRecebeChamada(transicao,station, true);
                        tf.setAtivaLocal(false);
                        
                        
                        System.out.println(tf.getTransName1()+"_"+tf.getStation1()+"/"+
                                tf.getTransName2()+"_"+tf.getStation2()+" " +
                                "'fusion' foi disparada. Tempo= "+Token.getToken().getRelogioLocal());
                        Conecta.getServidor().gravacaoResulto(tf.getTransName1()+"_"+tf.getStation1()+"/"+
                                tf.getTransName2()+"_"+tf.getStation2()+" " +
                                "'fusion' foi disparada. Tempo= "+Token.getToken().getRelogioLocal());
                        
                    }  else if (t instanceof Transicao && t.getType().equals("TT")) {
                    Transicao tt = (Transicao)t;
                    tt.zeraTempo_Inicio_Disparo();
                    System.out.println(tt.getNome()+"'temporizada' foi disparada. Tempo= "+Token.getToken().getRelogioLocal());
                    Conecta.getServidor().gravacaoResulto(tt.getNome()+"'temporizada' foi disparada. Tempo= "+Token.getToken().getRelogioLocal());
                    }
                    
                    for (int j=0; j<arcosativos.size(); j++) {
                        ArcoAbstrato a = (ArcoAbstrato)arcosativos.get(j);
                        if (a instanceof Arco) {
                            Arco arco = (Arco)a;
                            LugarAbstrato l = arco.getLugar();
                            if (arco.getSentido()==Arco.LUGAR_TRANSICAO) {
                                l.addTokens(-arco.getPeso());
                            } else if (arco.getSentido()==Arco.TRANSICAO_LUGAR) {
                                l.addTokens(arco.getPeso());
                                atualizasinais(l);
                            }
                        }
                    }
                } else if (t instanceof TransicaoTemporizada) {
                    TransicaoTemporizada tt = (TransicaoTemporizada)t;
                    tt.ativar();
                } //else if (t instanceof Transicao && t.getType().equals("TT")) {
//                    Transicao tt = (Transicao)t;
//                    tt.zeraTempo_Inicio_Disparo();
//                    System.out.println(tt.getNome()+" foi disparada. Tempo= "+Token.getToken().getRelogioLocal());
//                    Conecta.getServidor().gravacaoResulto(tt.getNome()+" foi disparada. Tempo= "+Token.getToken().getRelogioLocal());
//                }
            }
        }
        if (quantidade>0) {
            if (listener!=null) {
                listener.redeModificada();
            }
        }
        
        //testezim basico
        /*for (int i=0; i<transicoes.size();i++) ((TransicaoAbstrata)transicoes.get(i)).setAtiva(true);
        
        System.out.println("T6 eh"+verificaChamadaDeMetodo("T6"));
        System.out.println("T6 eh"+verificaChamadaDeMetodo("T6"));
        */
        
        //o ideal seria ter um "repaint()"
        //mas Funciona.
        
        //Tem que chamar a SeparaTransiçoesAtivas de novo....pra atualizar o deadlock!!!!!
        //e tirar quem é ou naum ativa....
        separarTransicoesAtivas();
        
        return quantidade>0;
    }
    
    private void atualizasinais(LugarAbstrato la) {
        if (la instanceof Lugar) {
            Lugar l = (Lugar)la;
            ArrayList listacodigos = l.getCodigos();
            for (int i=0; i<listacodigos.size(); i++) {
                Codigo c = (Codigo)listacodigos.get(i);
                String operador = c.getOperador();
                Variavel sinal = c.getSinal();
                if (operador!=null && sinal!=null) {
                    if (operador.equals("S")) {
                        sinal.setLigado(true);
                    } else if (operador.equals("R")) {
                        sinal.setLigado(false);
                    }
                }
            }
        }
    }
    
    //Chamada de método serve para responder se uma transicao está ou não ativa.
    //E ao dispará-la, is.Ativa()=false.
    //FUNCIONA!
    // o "boolean dispara" é necessário para separar consulta e disparo...
    public boolean verificaChamadaDeMetodo(String transicao, boolean dispara){
        
        boolean ativa = false;
        System.out.println("Trans: "+transicao);
        System.out.println("dispara= "+dispara);
        
        for(int i=0; i<transicoes.size();i++){
            if(((TransicaoAbstrata)transicoes.get(i)).getNome().equals(transicao)){
                //ativa = ((TransicaoAbstrata)transicoes.get(i)).isAtiva();
                ativa = ((TransicaoAbstrata)transicoes.get(i)).isAtivaLocal();
                
                //isso ~e um teste!!!!
                
                if(ativa==true && dispara==true) {//dispara
                    
//                    System.out.println("Uhuuuu..I'm in...");
//                    System.out.println("Estou Disparando a Trans..."+transicao);
                    TransicaoAbstrata t = (TransicaoAbstrata)transicoes.get(i);
                    
                    Transicao tf = (Transicao)t;
                        System.out.println(tf.getTransName1()+"_"+tf.getStation1()+"/"+
                                tf.getTransName2()+"_"+tf.getStation2()+" " +
                                "'fusion' foi disparada. Tempo= "+Token.getToken().getRelogioLocal());
                        Conecta.getServidor().gravacaoResulto(tf.getTransName1()+"_"+tf.getStation1()+"/"+
                                tf.getTransName2()+"_"+tf.getStation2()+" " +
                                "'fusion' foi disparada. Tempo= "+Token.getToken().getRelogioLocal());

                    //aqui "dispara"
                    
                    t.setAtiva(false);
                    t.setAtivaLocal(false);
                                        
                    if (t instanceof Transicao) {
                        ArrayList arcosativos = t.getArcos();
                        for (int j=0; j<arcosativos.size(); j++) {
                            ArcoAbstrato a = (ArcoAbstrato)arcosativos.get(j);
                            if (a instanceof Arco) {
                                Arco arco = (Arco)a;
                                LugarAbstrato l = arco.getLugar();
                                if (arco.getSentido()==Arco.LUGAR_TRANSICAO) {
                                    //System.out.println("Pre.Antes= "+l.getTokens());
                                    l.addTokens(-arco.getPeso());
                                    //System.out.println("Pre.Depois= "+l.getTokens());
                                    
                                    
                            }   else if (arco.getSentido()==Arco.TRANSICAO_LUGAR) {
                                //System.out.println("Pos.Antes= "+l.getTokens());
                                l.addTokens(arco.getPeso());
                                //System.out.println("Pos.Depois= "+l.getTokens());
                                atualizasinais(l);
                               }
                            }
                        }
                    Controle.getControle().repintarJanelaInterna();
                    //Controle.getControle().getPainelIO().paintImmediately(0,0,1280,800);
                    
                    //ExecutaRede.ge
                    //repaint();
                    }
                }
            }
        }
        return ativa;
    }
    
    
    public void fireRedeModificada() {
        listener.redeModificada();
    }
    
    public void separarTransicoesAtivas() {
        //listar as transicoes
        //ver quais possuem a quantidade de tokens necessarias pra ativar, separar
        
        //zerando todo mundo.
        for (int i=0; i<transicoes.size();i++) ((TransicaoAbstrata)transicoes.get(i)).setAtiva(false);
        
        int deadlock0=0;//Tnorm/TT(relogioLocal) disparável.
        int deadlock1=0;//Só houver TT futura.
        int deadlock2=0;//nichts.
        int deadlock3=0;//TF
        
        for (int i=0; i<transicoes.size(); i++) {
            TransicaoAbstrata t = (TransicaoAbstrata)transicoes.get(i);
            ArrayList arcos_temp = t.getArcos();            
            int contagemdeerros=0;
            if (arcos_temp!=null) {
                for (int j=0; j<arcos_temp.size(); j++) {
                    ArcoAbstrato a = (ArcoAbstrato)arcos_temp.get(j);
                    if (a instanceof Arco) {
                        Arco arco = (Arco)a;
                        LugarAbstrato l = (LugarAbstrato)arco.getLugar();
                        if (arco.getSentido()==Arco.LUGAR_TRANSICAO && l.getTokens()<arco.getPeso()) {
                            contagemdeerros++;
                        }
                        if (arco.getSentido()==Arco.TRANSICAO_LUGAR && (l.getTokens()+arco.getPeso())>l.getCapacidade()) {
                            contagemdeerros++;
                        }
                    }
                }
                if (contagemdeerros==0) {                    
                    if (t instanceof Transicao && t.getType().equals("normal")) {                        
                        t.setAtiva(true);
                        deadlock0++;
                        
                    } else if (t instanceof Transicao && t.getType().equals("TT")) {
                        Transicao tt = (Transicao)t;
                        //int tempo = Token.getToken().getRelogioLocal()- Token.getToken().getTempoAnterior();
                        int tempo = Token.getToken().getRelogioLocal()- tt.getTempo_Inicio_Disparo();
                        int tempoProximo;
                        //System.out.println("Rel.Local: "+Token.getToken().getRelogioLocal());
                        //System.out.println("TempoAnt: "+Token.getToken().getTempoAnterior());
                        
                        //coloquei == ao inves de <=
                        if(tt.getTempo() == tempo && tt.getTempo_Inicio_Disparo()!=-1){ //na teoria, não existe menor...
                            tt.setAtiva(true);
                            deadlock0++;
                        } else {
                            tt.setAtiva(false);
                            deadlock1++;
                            
                            //criação do "tempo próximo/estimativa para o caso de eu entrar em deadlock"
                            
                            tt.setTempo_Inicio_Disparo(Token.getToken().getRelogioLocal());
                            tempoProximo = tt.getTempo_Inicio_Disparo()+tt.getTempo();
                            
//                            System.out.println("Transicao "+tt.getNome());
//                            System.out.println("TempoLocal "+Token.getToken().getRelogioLocal());
//                            System.out.println("TempoInicioDisparo "+tt.getTempo_Inicio_Disparo());
//                            System.out.println("Time Needed "+tt.getTempo());
//                            System.out.println(" ");
                            
                            if(Token.getToken().getTempoProximo()> tempoProximo){
                                Token.getToken().setTempoProximo(tempoProximo);
                            } else if(Token.getToken().getTempoProximo()==Token.getToken().getRelogioLocal()){
                                Token.getToken().setTempoProximo(tempoProximo);
                            }
                            //System.out.println("TempoProximo: "+Token.getToken().getTempoProximo());
                        }
                                        
                    } else if (t instanceof Transicao && t.getType().equals("TF")) {
                        Transicao tf = (Transicao)t;
                        //descobre qual é a Ti da outra Est.
                        tf.setAtivaLocal(true);
                        String transicao = tf.getTransName2();//transicao
                        char station = tf.getStation2(); //da outra estacao
                        
                        //aqui ele manda a chamada para saber se está ou não ativa
                        boolean ativa = Conecta.getServidor().enviaRecebeChamada(transicao,station, false);
                        tf.setAtiva(ativa);
                        
                        if (ativa==false){
                            deadlock3++;
                        } else {
                            deadlock0++;
                        }
                    }
                    
                }//contagem de erros!=0....significa que não tenho transições ativas? NÃO!!!
            }
        }
//        if (deadlock0!=0) Token.getToken().setDeadlock(0); //Tem algo para disparar "agora".
//        if (deadlock0==0 && deadlock1!=0) Token.getToken().setDeadlock(1);//--> deadlock 1 (No existe Ti/TF/TT(tempo local) disparavel.
//        if (deadlock0==0 && deadlock1==0 && deadlock3==0) Token.getToken().setDeadlock(2);//--> deadlock 2. (naum tem transicao disparavel em lugar algum)
//        if (deadlock0==0 && deadlock1==0 && deadlock3!=0) Token.getToken().setDeadlock(3);
        
        if (deadlock0!=0) { 
            Token.getToken().setDeadlock(0);
        } else {
            if (deadlock1!=0){
                Token.getToken().setDeadlock(1);
            }
            else {
                if (deadlock3==0){
                    Token.getToken().setDeadlock(2);
                }
                else {
                    Token.getToken().setDeadlock(3);
                }
            }
        }
   }
    
    private void verificarTesteEInibidores() {
        for (int i=0; i<transicoes.size(); i++) {
            TransicaoAbstrata t = (TransicaoAbstrata)transicoes.get(i);
            if (t.isAtiva()) {
                ArrayList arcos_temp = t.getArcos();
                for (int j=0; j<arcos_temp.size(); j++) {
                    ArcoAbstrato a = (ArcoAbstrato)arcos_temp.get(j);
                    LugarAbstrato l = (LugarAbstrato)a.getLugar();
                    if (a instanceof ArcoTeste) {
                        if (l.getTokens()<a.getPeso()) t.setAtiva(false);
                    } else if (a instanceof ArcoInibidor) {
                        if (l.getTokens()>=a.getPeso()) t.setAtiva(false);
                    }
                }
            }
        }
    }
    
    private void verificarSinais() {
        for (int i=0; i<transicoes.size(); i++) {
            TransicaoAbstrata t = (TransicaoAbstrata)transicoes.get(i);
            if (t.isAtiva()) {
                if (t instanceof Transicao) {
                    Transicao tran = (Transicao)t;
                    ArrayList listacodigos = tran.getCodigos();
                    int erros=0;
                    for (int j=0; j<listacodigos.size(); j++) {
                        Codigo c = (Codigo)listacodigos.get(j);
                        String operador = c.getOperador();
                        Variavel v = c.getSinal();
                        if (operador.equals("A")) {
                            if (v.isLigado()==false) erros++;
                        } else if (operador.equals("AN")) {
                            if (v.isLigado()==true) erros++;
                        }
                    }
                    if (erros>0) t.setAtiva(false);
                }
            }
        }
    }
    
    public void setPetriNetListener(PetriNetListener pnl) {
        this.listener = pnl;
    }
}
