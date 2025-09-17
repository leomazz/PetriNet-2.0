/*
 * RenderizadorDeCor.java
 *
 * Created on 14 de Novembro de 2006, 16:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author _Renatu
 */
public class RenderizadorDeCor extends JLabel implements TableCellRenderer {
    
    Color cor;
    
    /** Creates a new instance of RenderizadorDeCor */
    public RenderizadorDeCor() {
        setOpaque(true);
    }
    
    public Component getTableCellRendererComponent(JTable jTable, Object object, boolean b, boolean b0, int i, int i0) {
        cor = (Color)object;
        return this;
    }
    
    public void paint(Graphics graphics) {
        graphics.setColor(cor);
        graphics.fillRect(2,2,getWidth()-2,getHeight()-2);
    }
    
}
