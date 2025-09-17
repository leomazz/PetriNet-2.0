/*
 * EditorDeNumeros.java
 *
 * Created on 15 de Novembro de 2006, 15:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author _Renatu
 */
public class EditorDeNumeros extends AbstractCellEditor implements TableCellEditor, ChangeListener {
    
    public static int INT=0;
    public static int DOUBLE=1;
    
    private int tipo;
    private JSpinner spinner;
    
    /** Creates a new instance of EditorDeNumeros */
    public EditorDeNumeros(int tipo) {
        this.tipo = tipo;
        if (tipo==INT) {
            spinner = new JSpinner(new SpinnerNumberModel(0,0,100,1));
            spinner.addChangeListener(this);
        } else {
            spinner = new JSpinner(new SpinnerNumberModel(0,0,100,0.1));
            spinner.addChangeListener(this);
        }
    }
    
    public Component getTableCellEditorComponent(JTable jTable, Object object, boolean b, int i, int i0) {
        spinner.getModel().setValue(object);
        return spinner;
    }
    
    public Object getCellEditorValue() {
        return spinner.getValue();
    }

    public void stateChanged(ChangeEvent changeEvent) {
        fireEditingStopped();
    }

}
