/*
 * ModeloTabelaCodigo.java
 *
 * Created on 16 de Novembro de 2006, 13:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao;

import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import petrinet10.core.Codigo;
import petrinet10.core.Variavel;

/**
 *
 * @author _Renatu
 */
public class ModeloTabelaCodigo extends AbstractTableModel implements TableModelListener {
    
    private ArrayList codigos;
    
    /** Creates a new instance of ModeloTabelaCodigo */
    public ModeloTabelaCodigo() {
        codigos = new ArrayList();
    }
    
    public int getColumnCount() {
        return 2;
    }
    
    public int getRowCount() {
        return codigos.size();
    }
    
    public Object getValueAt(int i, int i0) {
        Codigo c = (Codigo)codigos.get(i);
        if (i0==0) return c.getOperador();
        else return c.getSinal();
    }
    
    public void setCodigo(ArrayList codigos) {
        this.codigos = (ArrayList)codigos.clone();
    }
    
    public ArrayList getCodigo() {
        return codigos;
    }
    
    public void addLinha() {
        codigos.add(new Codigo("", new Variavel("", "" , 0, "")));
        fireTableDataChanged();
    }
    
    public void removeLinha(int linha) {
        if (linha >= 0 && linha<codigos.size()-1) {
            codigos.remove(linha);
            fireTableDataChanged();
        }
    }
    
    public boolean isCellEditable(int i, int i0) {
        return true;
    }
    
    public void tableChanged(TableModelEvent tableModelEvent) {
        int coluna = tableModelEvent.getColumn();
        int linha = tableModelEvent.getLastRow();
        Codigo c = (Codigo)codigos.get(linha);
        if (coluna==0) {
            c.setOperador((String)getValueAt(linha, coluna));
        } else {
            c.setSinal((Variavel)getValueAt(linha, coluna));
        }
    }
    
    public Class getColumnClass(int i) {
        return i==3?Variavel.class:String.class;
    }
    
    public void setValueAt(Object object, int i, int i0) {
        Codigo c = (Codigo)codigos.get(i);
        if (i0==0) {
            c.setOperador((String)object);
        } else {
            c.setSinal((Variavel)object);
        }
    }
}
