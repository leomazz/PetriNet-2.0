/*
 * ModeloTabelaVariaveis.java
 *
 * Created on 16 de Novembro de 2006, 16:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import petrinet10.core.Variavel;
import petrinet10.gui.controle.Controle;

/**
 *
 * @author _Renatu
 */
public class ModeloTabelaVariaveis extends AbstractTableModel implements TableModel {
    
    ArrayList variaveis;
    String nomescolunas[] = new String[] { "Nome", "Tipo", "E/S", "Endereço" };
    
    /** Creates a new instance of ModeloTabelaVariaveis */
    public ModeloTabelaVariaveis() {
        variaveis = (ArrayList)Controle.getControle().getVariaveis().clone();
    }
    
    public Class getColumnClass(int i) {
        return String.class;
    }
    
    public int getColumnCount() {
        return nomescolunas.length;
    }
    
    public String getColumnName(int i) {
        return nomescolunas[i];
    }
    
    public void setValueAt(Object object, int i, int i0) {
        Variavel v = (Variavel)variaveis.get(i);
        if (i0==0) {
            v.setNome((String)object);
        } else if (i0==1) {
            v.setTipo((String)object);
        } else if (i0==2) {
            String s = (String)object;
            if (s.equals("ENTRADA")) {
                v.setEntradasaida(Variavel.ENTRADA);
            } else if (s.equals("SAÍDA")) {
                v.setEntradasaida(Variavel.SAIDA);
            } else {
                v.setEntradasaida(Variavel.ENTRADASAIDA);
            }
        } else if (i0==3) {
            v.setEndereco((String)object);
        }
        fireTableCellUpdated(i, i0);
    }
    
    public boolean isCellEditable(int i, int i0) {
        return true;
    }
    
    public Object getValueAt(int i, int i0) {
        Variavel v = (Variavel)variaveis.get(i);
        Object retorno = null;
        if (i0==0) {
            retorno = v.getNome();
        } else if (i0==1) {
            retorno = v.getTipo();
        } else if (i0==2) {
            int n = v.getEntradasaida();
            if (n==Variavel.ENTRADA) {
                retorno = "ENTRADA";
            } else if (n==Variavel.SAIDA) {
                retorno = "SAÍDA";
            } else {
                retorno = "ENTRADA/SAÍDA";
            }
        } else if (i0==3) {
            retorno = v.getEndereco();
        }
        return retorno;
    }
    
    public int getRowCount() {
        return variaveis.size();
    }
    
    public void addLinha() {
        variaveis.add(new Variavel("", "", 0, ""));
        fireTableDataChanged();
    }
    
    public void removeLinha(int linha) {
        if (linha>=0 && linha < variaveis.size()) {
            variaveis.remove(linha);
            fireTableDataChanged();
        }
    }
    
    public ArrayList getVariaveis() {
        return this.variaveis;
    }
}
