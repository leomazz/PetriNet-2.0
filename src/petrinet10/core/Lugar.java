/*
 * Lugar.java
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
public class Lugar extends LugarAbstrato {

    private ArrayList comandos, codigos;
    private String nome;
    
    /** Creates a new instance of Lugar */
    public Lugar(String nome, int capacidade, int tokens) {
        this.setNome(nome);
        this.setCapacidade(capacidade);
        this.setTokens(tokens);
        codigos = new ArrayList();
    }
    
    public void addCodigo(Codigo c) {
        codigos.add(c);
    }
    
    public void removeCodigo(Codigo c) {
        codigos.remove(c);
    }
    
    public Lugar(String nome) {
        this(nome,1,0);
    }
    
    public void addComando(Comando comando) {
        comandos.add(comando);
    }
    
    public void removeComando(Comando comando) {
        comandos.remove(comando);
    }
    
    public ArrayList getComandos() {
        return this.comandos;
    }
    
    public ArrayList getCodigos() {
        return this.codigos;
    }
    
    public void setCodigos(ArrayList codigos) {
        this.codigos = codigos;
    }
    
}
