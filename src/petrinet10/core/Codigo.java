/*
 * Codigo.java
 *
 * Created on 16 de Novembro de 2006, 13:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.core;

/**
 *
 * @author _Renatu
 */
public class Codigo {
    
    private String operador;
    private Variavel sinal;
    
    /** Creates a new instance of Codigo */
    public Codigo(String operador, Variavel sinal) {
        this.operador = operador;
        this.sinal = sinal;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public Variavel getSinal() {
        return sinal;
    }

    public void setSinal(Variavel variavel) {
        this.sinal = variavel;
    }
    
}
