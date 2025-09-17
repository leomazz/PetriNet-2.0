/*
 * TransicaoDisparavel.java
 *
 * Created on 12 de Abril de 2006, 11:50
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
public class TransicaoDisparavel extends TransicaoAbstrata {
    
    /** Creates a new instance of TransicaoDisparavel */
    public TransicaoDisparavel(String nome) {
        this.setNome(nome);
        this.setAtiva(false);
    }
    
    public boolean disparar() {
        
        //fazer verificacao dos arcos teste e inibidores!!!!!
        
        
        ArrayList arcos = this.getArcos();
        int contagemdeerros=0;
        boolean retorno=false;
        if (arcos!=null) {
            for (int i=0; i<arcos.size(); i++) {
                ArcoAbstrato a = (ArcoAbstrato)arcos.get(i);
                if (a instanceof Arco) {
                    Arco arco = (Arco)a;
                    LugarAbstrato la = arco.getLugar();
                    if (arco.getSentido()==Arco.LUGAR_TRANSICAO && (la.getTokens()<arco.getPeso())) contagemdeerros++;
                    if (arco.getSentido()==Arco.TRANSICAO_LUGAR && ((la.getTokens()+arco.getPeso())>la.getCapacidade())) contagemdeerros++;
                }
            }
            if (contagemdeerros==0) {
                for (int i=0; i<arcos.size(); i++) {
                    ArcoAbstrato a = (ArcoAbstrato)arcos.get(i);
                    if (a instanceof Arco) {
                        Arco arco = (Arco)a;
                        LugarAbstrato la = arco.getLugar();
                        if (arco.getSentido()==Arco.LUGAR_TRANSICAO) {
                            la.addTokens(-arco.getPeso());
                        } else if (arco.getSentido()==Arco.TRANSICAO_LUGAR) {
                            la.addTokens(arco.getPeso());
                        }
                    }
                }
                retorno=true;
            }
        }
        return retorno;
    }
    
}
