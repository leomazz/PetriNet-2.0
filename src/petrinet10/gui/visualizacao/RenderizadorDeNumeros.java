/*
 * RenderizadorDeNumeros.java
 *
 * Created on 15 de Novembro de 2006, 16:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao;

import java.awt.Component;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author _Renatu
 */
public class RenderizadorDeNumeros extends JSpinner implements TableCellRenderer {
    
    /** Creates a new instance of RenderizadorDeNumeros */
    public RenderizadorDeNumeros() {
    }

    public Component getTableCellRendererComponent(JTable jTable, Object object, boolean b, boolean b0, int i, int i0) {
        setValue(object);
        return this;
    }

}
