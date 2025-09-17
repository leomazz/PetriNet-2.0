/*
 * RenderizadorDeCodigo.java
 *
 * Created on 16 de Novembro de 2006, 08:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author _Renatu
 */
public class RenderizadorDeCodigo extends JButton implements TableCellRenderer {
    
    /** Creates a new instance of RenderizadorDeCodigo */
    public RenderizadorDeCodigo() {
        super("...");
    }

    public Component getTableCellRendererComponent(JTable jTable, Object object, boolean b, boolean b0, int i, int i0) {
        return this;
    }

}
