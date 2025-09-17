/*
 * ArcoTeste.java
 *
 * Created on 6 de Abril de 2006, 12:57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.core;

/**
 *
 * @author _Renatu
 */
public class ArcoTeste extends ArcoAbstrato {
    
    /** Creates a new instance of ArcoTeste */
    public ArcoTeste(LugarAbstrato lugar, TransicaoAbstrata transicao, int peso) {
        setLugar(lugar);
        setTransicao(transicao);
        setPeso(peso);
    }
    
    public ArcoTeste(Lugar lugar, Transicao transicao) {
        setLugar(lugar);
        setTransicao(transicao);
        setPeso(1);
    }
    
}
