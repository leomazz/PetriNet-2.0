/*
 * TransicaoFundida.java
 *
 * Created on 12 de Abril de 2006, 15:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.core;

import java.util.ArrayList;
import java.util.TimerTask;

/**
 *
 * @author _Renatu
 */
public class TransicaoFundida extends TransicaoAbstrata {
    
    private ArrayList codigos;
//    private Thread timer;
    private char station1;
    private char station2;
    private String transicaoEstacao1;
    private String transicaoEstacao2;
    private PetriNet petrinet;
    boolean ocupada;
//    private Tarefa tarefa;
    
    /** Creates a new instance of TransicaoFundida */
   
    //Cria a transição e guarda informações sobre a fusão
    public TransicaoFundida(String nomeTrans1, char estacao1, String nomeTrans2, char estacao2) {
        //setNome(nomeTrans1+"_"+estacao1+"/"+nomeTrans2+"_"+estacao2); //t1_A/t2_B
        setNome(nomeTrans1);
        setAtiva(false);
        setStation1(estacao1); //this.station1 = estacao1;
        setStation2(estacao2); //this.station2 = estacao2;
        setTransName1(nomeTrans1); //this.transicaoEstacao1 = nomeTrans1;
        setTransName2(nomeTrans2); //this.transicaoEstacao2 = nomeTrans2;
    }
    
//    private void setOcupada(boolean ocupada) {
//        this.ocupada=ocupada;
//    }
//    
//    public boolean isOcupada() {
//        return this.ocupada;
//    }
    
    public void addCodigo(Codigo c) {
        codigos.add(c);
    }
    
    public void removeCodigo(Codigo c) {
        codigos.remove(c);
    }
    
    public ArrayList getCodigos() {
        return codigos;
    }
    
    public void setCodigos(ArrayList codigos) {
        this.codigos = codigos;
    }
        
    public void setStation1(char station1) {
        this.station1=station1; //private
    }
    
    public void setStation2(char station2) {
        this.station1=station2; //private
    }
    
    public char getStation1() {
        return this.station1;  //private
    }
    
    public char getStation2() {
        return this.station2; //private
    }
    
    public void setTransName1(String Trans1){
        this.transicaoEstacao1 = Trans1;
    }
    
    public void setTransName2(String Trans2){
        this.transicaoEstacao2 = Trans2;
    }
    
    public String getTransName1(){
        return this.transicaoEstacao1;
    }
    
    public String getTransName2(){
        return this.transicaoEstacao2;
    }
    
//    public void ativar() {
//        tarefa = new Tarefa(this);
//        PetriNet.getTimer().schedule(tarefa, tempo);
//        this.setOcupada(true);
//    }
    
//    public void desativar() {
//        tarefa.cancel();
//        this.setOcupada(false);
//    }
    
//    public void run() {
//        try {
//            timer.sleep(tempo);
//        } catch (InterruptedException ie) { }
//        ArrayList arcos = this.getArcos();
//        for (int i=0; i<arcos.size(); i++) {
//            ArcoAbstrato a = (ArcoAbstrato)arcos.get(i);
//            if (a instanceof Arco) {
//                Arco arco = (Arco)a;
//                LugarAbstrato la = arco.getLugar();
//                if (arco.getSentido()==Arco.TRANSICAO_LUGAR) la.addTokens(arco.getPeso());
//                else if (arco.getSentido()==Arco.LUGAR_TRANSICAO) la.addTokens(-arco.getPeso());
//            }
//        }
//        while (petrinet.passo());
//        this.setOcupada(false);
//        petrinet.fireRedeModificada();
//    }
    
//    public void setPetriNet(PetriNet petrinet) {
//        this.petrinet=petrinet;
//    }
    
//    class Tarefa extends TimerTask {
//        
//        TransicaoFundida transicao;
//        
//        public Tarefa(TransicaoFundida tt) {
//            this.transicao=tt;
//        }
//        
//        public void run() {
//            ArrayList arcos = transicao.getArcos();
//            for (int i=0; i<arcos.size(); i++) {
//                ArcoAbstrato a = (ArcoAbstrato)arcos.get(i);
//                if (a instanceof Arco) {
//                    Arco arco = (Arco)a;
//                    LugarAbstrato la = arco.getLugar();
//                    if (arco.getSentido()==Arco.TRANSICAO_LUGAR) la.addTokens(arco.getPeso());
//                    else if (arco.getSentido()==Arco.LUGAR_TRANSICAO) la.addTokens(-arco.getPeso());
//                }
//            }
//            while (petrinet.passo());
//            transicao.setOcupada(false);
//            petrinet.fireRedeModificada();
//            
//        }
//    }
    
}
