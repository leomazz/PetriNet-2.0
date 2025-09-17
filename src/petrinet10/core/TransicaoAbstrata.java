/*
 * Transicao.java
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
public class TransicaoAbstrata {
    
    private ArrayList arcos;
    private String nome;
    private boolean ativa, ativaLocal;
    private String type;
    
    public ArrayList getArcos() {
        return this.arcos;
    }
    
    public void addArco(ArcoAbstrato a) {
        if (arcos==null) arcos = new ArrayList();
        arcos.add(a);
    }
    
    public void removeArco(ArcoAbstrato a) {
        arcos.remove(a);
    }
    
    public void setNome(String nome) {
        this.nome=nome;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public boolean isAtiva() {
        return this.ativa;
    }
    
    public void setAtiva(boolean b) {
        this.ativa = b;
    }
    
    public boolean isAtivaLocal() {
        return this.ativaLocal;
    }
    
    public void setAtivaLocal(boolean b) {
        this.ativaLocal = b;
    }
    
    public void setType (String type){
        this.type = type;
    }
    public String getType (){
        return this.type;
    }
}
