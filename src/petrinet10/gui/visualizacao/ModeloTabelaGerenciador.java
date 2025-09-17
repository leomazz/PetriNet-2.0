/*
 * ModeloTabelaGerenciador.java
 *
 * Created on 21 de Novembro de 2006, 08:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import petrinet10.gui.controle.Controle;
import petrinet10.gui.visualizacao.elementosgraficos.ElementoGrafico;
import petrinet10.gui.visualizacao.elementosgraficos.JLugar;
import petrinet10.gui.visualizacao.elementosgraficos.JTransicao;

/**
 *
 * @author _Renatu
 */
public class ModeloTabelaGerenciador extends AbstractTableModel implements ListSelectionListener {
    
    ArrayList elementos;
    JTable tabela;
    
    /** Creates a new instance of ModeloTabelaGerenciador */
    public ModeloTabelaGerenciador(JTable tabela) {
        atualizar();
        this.tabela = tabela;
    }
    
    public int getColumnCount() {
        return 2;
    }
    
    public int getRowCount() {
        return elementos==null?0:elementos.size();
    }
    
    public Object getValueAt(int i, int i0) {
        if (i0==0) {
            return elementos.get(i).getClass();
        } else {
            ElementoGrafico eg = (ElementoGrafico)elementos.get(i);
            if (eg instanceof JLugar) return ((JLugar)eg).getNome();
            if (eg instanceof JTransicao) return ((JTransicao)eg).getNome();
            else return "Gráfico";
        }
    }
    
    public Class getColumnClass(int i) {
        if (i==0) return Class.class;
        else return String.class;
    }
    
    public boolean isCellEditable(int i, int i0) {
        return false;
    }
    
    public String getColumnName(int i) {
        if (i==0) return "Elemento";
        else return "Tipo";
    }
    
    public void atualizar() {
        ArrayList janelas = Controle.getControle().getJanelasInternas();
        if (janelas!=null) {
            for (int i=0; i<janelas.size(); i++) {
                JanelaInterna ji = (JanelaInterna)janelas.get(i);
                if (ji.isSelected()) {
                    elementos = ji.getPetriPanel().getElementosGraficos();
                }
            }
        }
        fireTableDataChanged();
    }

    public void valueChanged(ListSelectionEvent listSelectionEvent) {
        int linhas[] = tabela.getSelectedRows();
        ArrayList janelas = Controle.getControle().getJanelasInternas();
        for (int i=0; i<janelas.size(); i++) {
            JanelaInterna ji = (JanelaInterna)janelas.get(i);
            if (ji.isSelected()) {
                ArrayList elementos = ji.getPetriPanel().getElementosGraficos();
                ji.getPetriPanel().limparSelecao();
                for (int j=0; j<linhas.length; j++) {
                    ji.getPetriPanel().addSelecao((ElementoGrafico)elementos.get(linhas[j]));
                }
            }
        }
        Controle.getControle().atualizarPropriedades();
        Controle.getControle().repintarJanelaInterna();
    }    
}
