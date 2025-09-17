/*
 * EditorRenderizadorDeCor.java
 *
 * Created on 14 de Novembro de 2006, 16:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author _Renatu
 */
public class EditorDeCor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    
    JButton botao;
    Color cor;
    JColorChooser escolhedor;
    JDialog janela;
    
    /** Creates a new instance of EditorRenderizadorDeCor */
    public EditorDeCor() {
        botao = new JButton();
        botao.addActionListener(this);
        botao.setBorderPainted(false);
        escolhedor = new JColorChooser();
        janela = JColorChooser.createDialog(botao, "Escolha uma cor", true, escolhedor, this, null);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource()==botao) {
            botao.setBackground(cor);
            escolhedor.setColor(cor);
            janela.setVisible(true);
            fireEditingStopped();
        } else {
            cor = escolhedor.getColor();
        }
    }

    public Object getCellEditorValue() {
        return cor;
    }

    public Component getTableCellEditorComponent(JTable jTable, Object object, boolean b, int i, int i0) {
        cor = (Color)object;
        return botao;
    }

}
