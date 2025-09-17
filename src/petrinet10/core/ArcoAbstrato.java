/*
 * Arco.java
 *
 * Created on 4 de Abril de 2006, 15:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.core;

/**
 *
 * @author _Renatu
 */
public abstract class ArcoAbstrato {

    private LugarAbstrato lugar;
    private TransicaoAbstrata transicao;
    private int peso;
    
    public LugarAbstrato getLugar() {
        return this.lugar;
    }
    public void setLugar(LugarAbstrato lugar) {
        this.lugar = lugar;
        lugar.addArco(this);
    }
    public TransicaoAbstrata getTransicao() {
        return this.transicao;
    }
    public void setTransicao(TransicaoAbstrata transicao) {
        this.transicao = transicao;
        transicao.addArco(this);
    }
    public void setPeso(int peso) {
        this.peso = peso;
    }
    public int getPeso() {
        return this.peso;
    }

}
