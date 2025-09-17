/*
 * Lugar.java
 *
 * Created on 4 de Abril de 2006, 15:42
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
public abstract class LugarAbstrato {
    
    private int capacidade, tokens;
    private ArrayList arcos;
    private String nome;
    
    public void setNome(String nome) {
        this.nome=nome;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public int getCapacidade() {
        return this.capacidade;
    }
    
    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }
    
    public int getTokens() {
        return this.tokens;
    }
    
    public void setTokens(int tokens) {
        this.tokens = tokens;
        confereTransicoesTemporizadas();
    }
    
    public void addTokens(int tokens) {
        this.tokens+=tokens;
        confereTransicoesTemporizadas();
    }
    
    public void addArco(ArcoAbstrato arco) {
        if (arcos==null) arcos = new ArrayList();
        arcos.add(arco);
    }
    
    public void removeArco(ArcoAbstrato arco) {
        arcos.remove(arco);
    }
    
    public ArrayList getArcos() {
        return this.arcos;
    }
    
    private void confereTransicoesTemporizadas() {
        if (arcos!=null) {
            for (int i=0; i<arcos.size(); i++) {
                ArcoAbstrato a = (ArcoAbstrato)arcos.get(i);
                TransicaoAbstrata ta = a.getTransicao();
                if (a instanceof ArcoInibidor && ta instanceof TransicaoTemporizada) {
                    TransicaoTemporizada tt = (TransicaoTemporizada)ta;
                    if (this.getTokens()>=a.getPeso() && tt.isOcupada()) {
                        System.out.println("desativando1...");
                        tt.desativar();
                    }
                } else if (a instanceof ArcoTeste && ta instanceof TransicaoTemporizada) {
                    TransicaoTemporizada tt = (TransicaoTemporizada)ta;
                    if (this.getTokens()<a.getPeso() && tt.isOcupada()) {
                        System.out.println("desativando2...");
                        tt.desativar();
                    }
                }
            }
        }
    }
    
}
