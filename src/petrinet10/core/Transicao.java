/*
 * Transicao.java
 *
 * Created on 4 de Abril de 2006, 15:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.core;

import java.util.ArrayList;

/**
 *
 * @author _Renatu
 */
public class Transicao extends TransicaoAbstrata {
    
    private ArrayList codigos;
    private String type;
    //fundida
    private char station1, station2;
    private String transicaoEstacao1, transicaoEstacao2;
    
    //temporizada
    private int tempo;
    private int tempoDisparo=-1;
    /** Creates a new instance of Transicao */
    //private boolean ativaLocal=false;
    
    //isso aqui cria uma "NORMAL"
    public Transicao(String nome) {
        this.setNome(nome);
        this.setAtiva(false);
        codigos = new ArrayList();
        //this.type="normal";
        this.setType("normal");        
    }
    //isso aqui cria uma "TF""
   public Transicao(String nomeTrans1, char estacao1, String nomeTrans2, char estacao2) {
        //setNome(nomeTrans1+"_"+estacao1+"/"+nomeTrans2+"_"+estacao2); //t1_A/t2_B
        setNome(nomeTrans1);
        setAtiva(false);
        setStation1(estacao1); //this.station1 = estacao1;
        setStation2(estacao2); //this.station2 = estacao2;
        setTransName1(nomeTrans1); //this.transicaoEstacao1 = nomeTrans1;
        setTransName2(nomeTrans2); //this.transicaoEstacao2 = nomeTrans2;
        //this.type="TF";
//        System.out.println("Transicao");
//        System.out.println("T1: "+this.getTransName1());
//        System.out.println("s1: "+this.getStation1());
//        System.out.println("T2: "+this.getTransName2());
//        System.out.println("s2: "+this.getStation2());
        codigos = new ArrayList();
        this.setType("TF");
   }
   
   //isso aqui cria uma "TT"
   public Transicao(String nome, int time) {
        this.setNome(nome);
        this.setAtiva(false);
        this.setTempo(time);
        //this.type="TT";
        codigos = new ArrayList();
        this.setType("TT");
    }
   
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
        this.station2=station2; //private
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
    
    public void setTempo(int tempo) {
       this.tempo=tempo;
    }
    
    public int getTempo() {
        return this.tempo;
    }
    
    //-1 se não disparado
    public void zeraTempo_Inicio_Disparo(){
        this.tempoDisparo=-1;
    }
    
    public void setTempo_Inicio_Disparo(int tempo) {
       if(this.tempoDisparo==-1){
            this.tempoDisparo=tempo;
       }
    }
    
    public int getTempo_Inicio_Disparo() {
        return this.tempoDisparo;
    }
    
//    public void setAtivaLocal(boolean ativa){
//        this.ativaLocal=ativa;
//    }
//    public boolean getAtivaLocal(){
//        return this.ativaLocal;
//    }
//    public String getType(){
//        return this.type;
//    }
}
