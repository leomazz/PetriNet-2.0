/*
 * RenderizadorGerenciador.java
 *
 * Created on 21 de Novembro de 2006, 09:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package petrinet10.gui.visualizacao;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import petrinet10.gui.visualizacao.elementosgraficos.ElementoGrafico;
import petrinet10.gui.visualizacao.elementosgraficos.Elipse;
import petrinet10.gui.visualizacao.elementosgraficos.JArco;
import petrinet10.gui.visualizacao.elementosgraficos.JLugar;
import petrinet10.gui.visualizacao.elementosgraficos.JTexto;
import petrinet10.gui.visualizacao.elementosgraficos.JTransicao;
import petrinet10.gui.visualizacao.elementosgraficos.Reta;
import petrinet10.gui.visualizacao.elementosgraficos.Retangulo;

/**
 *
 * @author _Renatu
 */
public class RenderizadorGerenciador extends DefaultTableCellRenderer implements TableCellRenderer {
    
    Hashtable imagens;
    Class classedoelemento;
    
    /** Creates a new instance of RenderizadorGerenciador */
    public RenderizadorGerenciador() {
        imagens = new Hashtable();
        imagens.put(JLugar.class, "lugar.png");
        imagens.put(JTransicao.class, "transicao.png");
        imagens.put(Reta.class, "reta.gif");
        imagens.put(Retangulo.class, "retangulo.gif");
        imagens.put(Elipse.class, "elipse.gif");
        imagens.put(JTexto.class, "texto.gif");
        imagens.put(JArco.class, "arco.gif");
    }

    public Component getTableCellRendererComponent(JTable jTable, Object object, boolean b, boolean b0, int i, int i0) {
        this.classedoelemento = (Class)object;
        if (b) this.setBackground(jTable.getSelectionBackground());
        else this.setBackground(Color.WHITE);
        return this;
    }
    
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Image img = new ImageIcon(getClass().getResource("/petrinet10/imagens/" + imagens.get(classedoelemento))).getImage();
        int x = (this.getWidth()-img.getWidth(this))/2;
        int y = (this.getHeight()-img.getHeight(this))/2;
        graphics.drawImage(img, x, y, this);
    }
}
