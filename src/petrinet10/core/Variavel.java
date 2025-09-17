/*
 * Variavel.java
 *
 * Created on 16 de Novembro de 2006, 16:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.core;

import java.io.Serializable;

/**
 *
 * @author _Renatu
 */
public class Variavel implements Serializable {
    
    private String nome, tipo, endereco;
    private int entradasaida;
    private boolean ligado;
    
    public static int ENTRADA = 0;
    public static int SAIDA = 1;
    public static int ENTRADASAIDA = 2;
    
    /** Creates a new instance of Variavel */
    public Variavel(String nome, String tipo, int entradasaida, String endereco) {
        this.setNome(nome);
        this.setTipo(tipo);
        this.setEntradasaida(entradasaida);
        this.setEndereco(endereco);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getEntradasaida() {
        return entradasaida;
    }

    public void setEntradasaida(int entradasaida) {
        this.entradasaida = entradasaida;
    }
    
    public String toString() {
        return this.nome;
    }

    public boolean isLigado() {
        return ligado;
    }

    public void setLigado(boolean ligado) {
        this.ligado = ligado;
    }
}
