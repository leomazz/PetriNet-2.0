/*
 * Arco.java
 *
 * Created on 4 de Abril de 2006, 15:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.core;

/**
 *
 * @author _Renatu
 */
public class Arco extends ArcoAbstrato {
    
    public static int LUGAR_TRANSICAO = 0;
    public static int TRANSICAO_LUGAR = 1;
    
    private int sentido;
    
    /** Creates a new instance of Arco */
    public Arco(LugarAbstrato lugar, TransicaoAbstrata transicao, int peso, int sentido) {
        setLugar(lugar);
        setTransicao(transicao);
        setPeso(peso);
        setSentido(sentido);
    }
    
    public Arco(LugarAbstrato lugar, TransicaoAbstrata transicao) {
        this(lugar, transicao, 1, Arco.LUGAR_TRANSICAO);
    }
    
    public void setSentido(int sentido) {
        this.sentido = sentido;
    }
    
    public int getSentido() {
        return this.sentido;
    }
    
}
