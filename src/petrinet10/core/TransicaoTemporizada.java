/*
 * TransicaoTemporizada.java
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
public class TransicaoTemporizada extends TransicaoAbstrata {
    
    private ArrayList codigos;
    private Thread timer;
    private long tempo;
    private PetriNet petrinet;
    boolean ocupada;
    private Tarefa tarefa;
    
    /** Creates a new instance of TransicaoTemporizada */
    public TransicaoTemporizada(String nome, long tempo) {
        setNome(nome);
        setAtiva(false);
        setTempo(tempo);
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
    
    private void setOcupada(boolean ocupada) {
        this.ocupada=ocupada;
    }
    
    public boolean isOcupada() {
        return this.ocupada;
    }
    
    public void setTempo(long tempo) {
        this.tempo=tempo;
    }
    
    public long getTempo() {
        return this.tempo;
    }
    
    public void ativar() {
        tarefa = new Tarefa(this);
        PetriNet.getTimer().schedule(tarefa, tempo);
        this.setOcupada(true);
    }
    
    public void desativar() {
        tarefa.cancel();
        this.setOcupada(false);
    }
    
    public void run() {
        try {
            timer.sleep(tempo);
        } catch (InterruptedException ie) { }
        ArrayList arcos = this.getArcos();
        for (int i=0; i<arcos.size(); i++) {
            ArcoAbstrato a = (ArcoAbstrato)arcos.get(i);
            if (a instanceof Arco) {
                Arco arco = (Arco)a;
                LugarAbstrato la = arco.getLugar();
                if (arco.getSentido()==Arco.TRANSICAO_LUGAR) la.addTokens(arco.getPeso());
                else if (arco.getSentido()==Arco.LUGAR_TRANSICAO) la.addTokens(-arco.getPeso());
            }
        }
        while (petrinet.passo());
        this.setOcupada(false);
        petrinet.fireRedeModificada();
    }
    
    public void setPetriNet(PetriNet petrinet) {
        this.petrinet=petrinet;
    }
    
    class Tarefa extends TimerTask {
        
        TransicaoTemporizada transicao;
        
        public Tarefa(TransicaoTemporizada tt) {
            this.transicao=tt;
        }
        
        public void run() {
            ArrayList arcos = transicao.getArcos();
            for (int i=0; i<arcos.size(); i++) {
                ArcoAbstrato a = (ArcoAbstrato)arcos.get(i);
                if (a instanceof Arco) {
                    Arco arco = (Arco)a;
                    LugarAbstrato la = arco.getLugar();
                    if (arco.getSentido()==Arco.TRANSICAO_LUGAR) la.addTokens(arco.getPeso());
                    else if (arco.getSentido()==Arco.LUGAR_TRANSICAO) la.addTokens(-arco.getPeso());
                }
            }
            while (petrinet.passo());
            transicao.setOcupada(false);
            petrinet.fireRedeModificada();
            
        }
    }
    
}
