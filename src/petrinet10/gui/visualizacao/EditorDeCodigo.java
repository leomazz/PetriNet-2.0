/*
 * EditorDeCodigo.java
 *
 * Created on 16 de Novembro de 2006, 09:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author _Renatu
 */
public class EditorDeCodigo extends AbstractCellEditor implements TableCellEditor, ActionListener {
    
    JButton botao;
    ArrayList codigo;
    String nomeelemento;
    
    /** Creates a new instance of EditorDeCodigo */
    public EditorDeCodigo() {
        botao = new JButton("...");
        botao.addActionListener(this);
    }

    public Object getCellEditorValue() {
        return codigo;
    }

    public Component getTableCellEditorComponent(JTable jTable, Object object, boolean b, int i, int i0) {
        codigo = (ArrayList)object;
        nomeelemento = (String)jTable.getValueAt(0,1);
        return botao;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        new JanelaEditorCodigo(this, nomeelemento, codigo).setVisible(true);
        fireEditingStopped();
    }
    
    public void setCodigo(ArrayList codigo) {
        this.codigo = codigo;
    }

}
