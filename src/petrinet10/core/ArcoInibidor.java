/*
 * ArcoInibidor.java
 *
 * Created on 6 de Abril de 2006, 13:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.core;

/**
 *
 * @author _Renatu
 */
public class ArcoInibidor extends ArcoAbstrato {
    
    /** Creates a new instance of ArcoInibidor */
    public ArcoInibidor(LugarAbstrato lugar, TransicaoAbstrata transicao, int peso) {
        this.setTransicao(transicao);
        this.setLugar(lugar);
        this.setPeso(peso);
    }
    
    public ArcoInibidor(Lugar lugar, Transicao transicao) {
        this.setLugar(lugar);
        this.setTransicao(transicao);
        this.setPeso(1);
    }
}
